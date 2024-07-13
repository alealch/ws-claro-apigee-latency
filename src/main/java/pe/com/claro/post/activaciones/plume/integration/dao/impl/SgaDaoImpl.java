package pe.com.claro.post.activaciones.plume.integration.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;
import oracle.jdbc.OracleTypes;
import pe.com.claro.post.activaciones.plume.common.constants.Constantes;
import pe.com.claro.post.activaciones.plume.common.exceptions.BDException;
import pe.com.claro.post.activaciones.plume.common.property.PropertiesExterno;
import pe.com.claro.post.activaciones.plume.common.util.ClaroUtil;
import pe.com.claro.post.activaciones.plume.integration.dao.SgaDao;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.request.DatosClienteBeanReq;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.request.RegistrarClieteReq;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.response.DatosClienteBeanRes;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.response.EquipoRes;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.response.RegistrarClieteRes;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.type.CursorEquipoType;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.type.CursorValidaCuenta;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.type.POCursorType;

@Repository
public class SgaDaoImpl implements SgaDao {

  private static final Logger logger = LoggerFactory.getLogger(SgaDaoImpl.class);

  @Autowired
  private PropertiesExterno prop;

  @Autowired
  @Qualifier("sgaJndiName")
  private DataSource sgaJndiName;

  @SuppressWarnings("unchecked")
  @Override
  public DatosClienteBeanRes datosCliente(String mensajeTransaccion, DatosClienteBeanReq request) throws BDException {

    DatosClienteBeanRes response = new DatosClienteBeanRes();
    String idTransaccion = "[" + mensajeTransaccion + "] ";
    long tiempoInicio = System.currentTimeMillis();

    String nombreBD = prop.dbSgaNombre;
    String owner = prop.dbSgaOwner;
    String pkg = prop.dbSgaPkgProvPlume;
    String sp = prop.dbSgaSpDatosCliente;
    String nombreProcedimiento = pkg + Constantes.SEPARADOR_PUNTO + sp;
    try {
      logger.info(idTransaccion + Constantes.JNDI + prop.dbSgaJndi);
      logger.info(idTransaccion + Constantes.CONSULTANDO_DB + prop.dbSgaNombre);
      logger.info(idTransaccion + Constantes.EJECUTANDO_SP + owner + Constantes.SEPARADOR_PUNTO + nombreProcedimiento);
      logger.info(idTransaccion + Constantes.TIMEOUT_CONNECT + prop.dbSgaSpDatosClienteTimeout);
      logger.info(idTransaccion + Constantes.TIMEOUT_REQUEST + prop.dbSgaSpDatosClienteEjecucion);

      logger.info(idTransaccion + Constantes.PARAMETROS_IN + ClaroUtil.printPrettyJSONString(request));
      
      this.sgaJndiName.setLoginTimeout(Integer.parseInt(prop.dbSgaSpDatosClienteTimeout));
      SimpleJdbcCall objJdbcCall = new SimpleJdbcCall(this.sgaJndiName).withoutProcedureColumnMetaDataAccess()
        .withSchemaName(owner).withProcedureName(nombreProcedimiento)
        .declareParameters(new SqlParameter("PI_NROSOT", OracleTypes.VARCHAR),
          new SqlOutParameter(Constantes.PO_COD_RESPUESTA, OracleTypes.VARCHAR),
          new SqlOutParameter(Constantes.PO_MSJ_RESPUESTA, OracleTypes.VARCHAR),
          new SqlOutParameter("PO_CODCLI", OracleTypes.VARCHAR),
          new SqlOutParameter("PO_EMAIL", OracleTypes.VARCHAR),
          new SqlOutParameter("PO_NOMBRES", OracleTypes.VARCHAR),
          new SqlOutParameter("PO_ACCOUNTID", OracleTypes.VARCHAR),
          new SqlOutParameter("PO_CODSUC", OracleTypes.VARCHAR),
          new SqlOutParameter("PO_NOMSUC", OracleTypes.VARCHAR),
          new SqlOutParameter("PO_TIPOTRABAJO", OracleTypes.VARCHAR),
          new SqlOutParameter("PO_VALIDA_CUENTA", OracleTypes.VARCHAR),
          new SqlOutParameter(Constantes.PO_CODCLIPLM, OracleTypes.VARCHAR),
          new SqlOutParameter(Constantes.PO_CURSOR, OracleTypes.CURSOR, new RowMapper<CursorValidaCuenta>() {
            @Override
            public CursorValidaCuenta mapRow(ResultSet rs, int rowNum) throws SQLException {
              CursorValidaCuenta cursor = new CursorValidaCuenta();
              cursor.setCodSuc(rs.getString(1));
              cursor.setLocation(rs.getString(2));
              cursor.setNroSerie(rs.getString(3));
              cursor.setNickName(rs.getString(4));
              return cursor;
            }
          }));

      objJdbcCall.getJdbcTemplate().setQueryTimeout(Integer.parseInt(prop.dbSgaSpDatosClienteEjecucion));

      Map<String, Object> resultMap = objJdbcCall
        .execute(new MapSqlParameterSource().addValue("PI_NROSOT", request.getNroSot()));
      response.setCodigoRespuesta((String) resultMap.getOrDefault(Constantes.PO_COD_RESPUESTA, null));
      response.setMensajeRespuesta((String) resultMap.getOrDefault(Constantes.PO_MSJ_RESPUESTA, null));
      response.setCodigoCliente((String) resultMap.getOrDefault("PO_CODCLI", null));
      response.setEmail((String) resultMap.getOrDefault("PO_EMAIL", null));
      response.setNombres((String) resultMap.getOrDefault("PO_NOMBRES", null));
      response.setAccountId((String) resultMap.getOrDefault("PO_ACCOUNTID", null));
      response.setCodigoSucursal((String) resultMap.getOrDefault("PO_CODSUC", null));
      response.setNombreSurcursal((String) resultMap.getOrDefault("PO_NOMSUC", null));
      response.setTipoTrabajo((String) resultMap.getOrDefault("PO_TIPOTRABAJO", null));
      response.setValidaCuenta((String) resultMap.getOrDefault("PO_VALIDA_CUENTA", null));
      response.setCodigoClientePlm((String) resultMap.getOrDefault(Constantes.PO_CODCLIPLM, null));
      response.setCursor((ArrayList<CursorValidaCuenta>) resultMap.get(Constantes.PO_CURSOR));

      logger.info(idTransaccion + Constantes.PARAMETROS_OUTPUT + ClaroUtil.printPrettyJSONString(response));

    } catch (Exception ex) {
      ClaroUtil.capturarErrorSP(ex, idTransaccion, nombreProcedimiento, nombreBD, prop);
    } finally {
      logger.info(idTransaccion + Constantes.TIEMPO_TOTAL_PROC + (System.currentTimeMillis() - tiempoInicio)
        + Constantes.MILISEC);
      logger.info(idTransaccion + " ====== [FIN DEL SP DatosCliente] ======");
    }
    return response;
  }

  @Override
  public RegistrarClieteRes registrarCliente(String mensajeTransaccion, RegistrarClieteReq request) throws BDException {
    long tiempoInicio = System.currentTimeMillis();

    RegistrarClieteRes response = new RegistrarClieteRes();
    String idTransaccion = "[" + mensajeTransaccion + "] ";

    String nombreBD = prop.dbSgaNombre;
    String owner = prop.dbSgaOwner;
    String pkg = prop.dbSgaPkgProvPlume;
    String sp = prop.dbSgaSpRegistrarCliente;
    String typeObject = owner + Constantes.PUNTO + prop.registrarClientelistaObject;
    String typeTable = owner + Constantes.PUNTO + prop.registrarClientelistaType;

    String nombreProcedimiento = pkg + Constantes.SEPARADOR_PUNTO + sp;

    Connection conn = null;
    SimpleJdbcCall objJdbcCall = null;
    try {
      logger.info(idTransaccion + Constantes.JNDI + prop.dbSgaJndi);
      logger.info(idTransaccion + Constantes.CONSULTANDO_DB + prop.dbSgaNombre);
      logger.info(idTransaccion + Constantes.EJECUTANDO_SP + nombreProcedimiento);
      logger.info(idTransaccion + Constantes.TIMEOUT_CONNECT + prop.dbSgaSpRegistrarClienteTimeout);
      logger.info(idTransaccion + Constantes.TIMEOUT_REQUEST + prop.dbSgaSpRegistrarClienteEjecucion);
      logger.info(idTransaccion + " Type Object: " + typeObject);
      logger.info(idTransaccion + " Type Table: " + typeTable);
      logger.info(idTransaccion + " PARAMETROS [INPUT]: " + ClaroUtil.printPrettyJSONString(request));

      conn = sgaJndiName.getConnection();
      this.sgaJndiName.setLoginTimeout(Integer.parseInt(prop.dbSgaSpRegistrarClienteTimeout));
      
      List<CursorEquipoType> listaEquipos = request.getCursorEquipo();

      StructDescriptor structDesc = StructDescriptor.createDescriptor(typeTable, conn);

      STRUCT[] struct = new STRUCT[listaEquipos.size()];
      ArrayDescriptor arrayDesc = ArrayDescriptor.createDescriptor(typeObject, conn);

      for (int i = 0; i < listaEquipos.size(); i++) {
        CursorEquipoType itemData = listaEquipos.get(i);
        Object[] atribut = new Object[]{itemData.getNroSerie(), itemData.getCodSerie(), itemData.getMdlEquipo()};

        STRUCT objectTypeStruct = new STRUCT(structDesc, conn, atribut);

        struct[i] = objectTypeStruct;
      }

      ARRAY array = new ARRAY(arrayDesc, conn, struct);

      SqlParameterSource objParametrosIN = new MapSqlParameterSource().addValue(Constantes.PI_CODCLI, request.getCodigoCliente())
        .addValue("PI_CODCLIPLM", request.getCodigoClientePlm())
        .addValue("PI_NOM_CLIENTE", request.getNombreCliente()).addValue(Constantes.PI_CODSUC, request.getCodigoSuc())
        .addValue("PI_CODSUCPLM", request.getCodigoSucPlm()).addValue("PI_USUREG", request.getUsuarioReg())
        .addValue("PI_CURSOREQUIPOS", array);

      objJdbcCall = new SimpleJdbcCall(sgaJndiName);
      objJdbcCall.getJdbcTemplate().setQueryTimeout(Integer.valueOf(prop.dbSgaSpEquiposEjecucion));

      Map<String, Object> objJdbcCallOut = objJdbcCall.withoutProcedureColumnMetaDataAccess().withSchemaName(owner)
        .withCatalogName(pkg).withProcedureName(sp)
        .declareParameters(new SqlParameter(Constantes.PI_CODCLI, OracleTypes.VARCHAR),
          new SqlParameter("PI_CODCLIPLM", OracleTypes.VARCHAR),
          new SqlParameter("PI_NOM_CLIENTE", OracleTypes.VARCHAR),
          new SqlParameter(Constantes.PI_CODSUC, OracleTypes.VARCHAR), new SqlParameter("PI_CODSUCPLM", OracleTypes.VARCHAR),
          new SqlParameter("PI_USUREG", OracleTypes.VARCHAR),
          new SqlParameter("PI_CURSOREQUIPOS", OracleTypes.ARRAY),
          new SqlOutParameter(Constantes.PO_COD_RESPUESTA, OracleTypes.VARCHAR),
          new SqlOutParameter(Constantes.PO_MSJ_RESPUESTA, OracleTypes.VARCHAR))
        .execute(objParametrosIN);

      String poErrnum = Constantes.VACIO;
      if (objJdbcCallOut.get(Constantes.PO_COD_RESPUESTA) != null) {
        poErrnum = objJdbcCallOut.get(Constantes.PO_COD_RESPUESTA).toString();
      }
      String poErrmsj = (String) objJdbcCallOut.get(Constantes.PO_MSJ_RESPUESTA);

      response.setCodigoRespuesta(poErrnum);
      response.setMensajeRespuesta(poErrmsj);

      logger.info(idTransaccion + " Tiempo total de Ejecucion: " + (System.currentTimeMillis() - tiempoInicio));
      logger.info(idTransaccion + Constantes.PARAMETROS_OUTPUT + ClaroUtil.printPrettyJSONString(response));

    } catch (Exception ex) {
      ClaroUtil.capturarErrorSP(ex, idTransaccion, nombreProcedimiento, nombreBD, prop);
    } finally {
      cerrarConexiones(conn, null, nombreProcedimiento);
      logger.info(idTransaccion + Constantes.TIEMPO_TOTAL_PROC + (System.currentTimeMillis() - tiempoInicio)
        + Constantes.MILISEC);
      logger.info(idTransaccion + " ====== [FIN DEL SP RegistrarCliente] ======");
    }

    return response;
  }

  @SuppressWarnings("unchecked")
  @Override
  public EquipoRes listarEquipos(String mensajeTransaccion, String codigoCliente, String codigoSucurlas)
    throws BDException {
    long tiempoInicio = System.currentTimeMillis();
    String idTransaccion = "[" + mensajeTransaccion + "] ";
    String nombreBD = prop.dbSgaNombre;
    String owner = prop.dbSgaOwner;
    String pkg = prop.dbSgaPkgProvPlume;
    String sp = prop.dbSgaSpEquipos;
    String nombreProcedimiento = pkg + Constantes.SEPARADOR_PUNTO + sp;

    EquipoRes response = new EquipoRes();
    try {

      logger.info(idTransaccion + Constantes.JNDI + prop.dbSgaJndi);
      logger.info(idTransaccion + Constantes.CONSULTANDO_DB + prop.dbSgaNombre);
      logger.info(idTransaccion + Constantes.EJECUTANDO_SP + owner + Constantes.SEPARADOR_PUNTO + nombreProcedimiento);
      logger.info(idTransaccion + Constantes.TIMEOUT_CONNECT + prop.dbSgaSpEquiposTimeout);
      logger.info(idTransaccion + Constantes.TIMEOUT_REQUEST + prop.dbSgaSpEquiposEjecucion);

      logger.info(idTransaccion + "PARAMETROS [INPUT]: ");
      logger.info(idTransaccion + "[PI_CODCLI]: " + codigoCliente);
      logger.info(idTransaccion + "[PI_CODSUC]: " + codigoSucurlas);
      
      sgaJndiName.setLoginTimeout(Integer.valueOf(prop.dbSgaSpEquiposTimeout));

      SimpleJdbcCall objJdbcCall = new SimpleJdbcCall(this.sgaJndiName).withoutProcedureColumnMetaDataAccess()
        .withSchemaName(owner).withProcedureName(nombreProcedimiento).declareParameters(
          new SqlParameter(Constantes.PI_CODCLI, OracleTypes.VARCHAR), new SqlParameter(Constantes.PI_CODSUC, OracleTypes.VARCHAR),
          new SqlOutParameter(Constantes.PO_COD_RESPUESTA, OracleTypes.VARCHAR),
          new SqlOutParameter(Constantes.PO_MSJ_RESPUESTA, OracleTypes.VARCHAR),
          new SqlOutParameter(Constantes.PO_CODCLIPLM, OracleTypes.VARCHAR),
          new SqlOutParameter(Constantes.PO_CURSOR, OracleTypes.CURSOR, new RowMapper<POCursorType>() {
            @Override
            public POCursorType mapRow(ResultSet rs, int rowNum) throws SQLException {
              POCursorType cursor = new POCursorType();
              cursor.setMatvLocation(rs.getString(1));
              cursor.setMatvNickName(rs.getString(2));
              cursor.setMatvNroSerie(rs.getString(3));
              cursor.setMatvRepetidor(rs.getString(4));
              return cursor;
            }
          }));

      objJdbcCall.getJdbcTemplate().setQueryTimeout(Integer.valueOf(prop.dbSgaSpEquiposEjecucion));

      Map<String, Object> resultMap = objJdbcCall.execute(
        new MapSqlParameterSource().addValue(Constantes.PI_CODCLI, codigoCliente).addValue(Constantes.PI_CODSUC, codigoSucurlas));

      response.setCodigoRespuesta((String) resultMap.getOrDefault(Constantes.PO_COD_RESPUESTA, null));
      response.setMensajeRespuesta((String) resultMap.getOrDefault(Constantes.PO_MSJ_RESPUESTA, null));
      response.setCodigoClientePlm((String) resultMap.getOrDefault(Constantes.PO_CODCLIPLM, null));
      response.setPoCursor((ArrayList<POCursorType>) resultMap.get(Constantes.PO_CURSOR));

      logger.info(idTransaccion + Constantes.PARAMETROS_OUTPUT + ClaroUtil.printPrettyJSONString(response));

    } catch (Exception ex) {
      logger.info("ERROR: " + ex.getMessage() + " ------------------------ ", ex);
      ClaroUtil.capturarErrorSP(ex, idTransaccion, nombreProcedimiento, nombreBD, prop);
    } finally {
      logger.info(idTransaccion + Constantes.TIEMPO_TOTAL_PROC + (System.currentTimeMillis() - tiempoInicio)
        + Constantes.MILISEC);
      logger.info(idTransaccion + " ====== [FIN DEL SP listarEquipos] ======");
    }

    return response;
  }
  
  public void cerrarConexiones(Connection conn, StringBuilder storedProcedure, String msj) {
    try {
      if (conn != null) {
        conn.close();
        logger.info("{}{}", msj, Constantes.CONEXION_CERRADA_EXITOSAMENTE_CONN);
      }
    } catch (Exception e) {
      logger.info("{}{}{}{}{}", msj, Constantes.ERROR_CERRAR_RECURSOS_SP, storedProcedure,
          Constantes.PARA_MAYOR_DETALLE_ORIGINAL, e);
    }
  }
  
}
