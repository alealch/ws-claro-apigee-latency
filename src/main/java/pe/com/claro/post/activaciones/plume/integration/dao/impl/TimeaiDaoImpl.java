package pe.com.claro.post.activaciones.plume.integration.dao.impl;

import java.sql.Clob;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import oracle.jdbc.OracleTypes;
import pe.com.claro.post.activaciones.plume.common.constants.Constantes;
import pe.com.claro.post.activaciones.plume.common.exceptions.BaseException;
import pe.com.claro.post.activaciones.plume.common.property.PropertiesExterno;
import pe.com.claro.post.activaciones.plume.common.util.ClaroUtil;
import pe.com.claro.post.activaciones.plume.integration.dao.TimeaiDao;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.request.RegistrarTrazabilidadRequest;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.response.RegistrarTrazabilidadResponse;

@Repository
public class TimeaiDaoImpl implements TimeaiDao {

  private static final Logger logger = LoggerFactory.getLogger(TimeaiDaoImpl.class);

  @Autowired
  private PropertiesExterno prop;

  @Autowired
  @Qualifier("timeaiJndiName")
  private DataSource timeaiJndiName;

  @Override
  public RegistrarTrazabilidadResponse registrarTrazabilidad(String mensajeTransaccion,
                                                             RegistrarTrazabilidadRequest request) throws BaseException, SQLException {
    RegistrarTrazabilidadResponse response = new RegistrarTrazabilidadResponse();

    long tiempoInicio = System.currentTimeMillis();
    String idTransaccion = "[" + mensajeTransaccion + "] ";

    String nombreBD = prop.dbTimeaiNombre;
    String owner = prop.dbTimeaiOwner;
    String pkg = prop.dbTimeaiPkgGestionPlume;
    String sp = prop.dbTimeaiSpRegistrarTrazabilidad;

    String nombreProcedimiento = owner + Constantes.SEPARADOR_PUNTO + pkg + Constantes.SEPARADOR_PUNTO + sp;

    String sql = "{" + "call" + " " + nombreProcedimiento + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

    java.sql.Connection conn = null;
    java.sql.CallableStatement cst = null;
    try {

      logger.info(idTransaccion + "JNDI: " + prop.dbTimeaiJndi);
      logger.info(idTransaccion + "Consultando BD: " + prop.dbSgaNombre);
      logger.info(idTransaccion + "EJECUNTANDO SP: " + nombreProcedimiento);
      logger.info(idTransaccion + " Timeout Connect: " + prop.dbTimeaiSpRegistrarTrazaTimeout);
      logger.info(idTransaccion + " Timeout Request: " + prop.dbTimeaiSpRegistrarTrazaEjecucion);

      logger.info(idTransaccion + "PARAMETROS [IN]: " + ClaroUtil.printPrettyJSONString(request));

      timeaiJndiName.setLoginTimeout(Integer.valueOf(prop.dbTimeaiSpRegistrarTrazaTimeout));

      conn = timeaiJndiName.getConnection();
      cst = conn.prepareCall(sql);

      cst.setString(1, request.getIdTransaccion());
      cst.setInt(2, request.getEstado());
      cst.setTimestamp(3, request.getFeExec());
      cst.setString(4, request.getCodigoCliente());
      cst.setString(5, request.getCanal());
      cst.setString(6, request.getAccountId());
      cst.setString(7, request.getCodigoClientePlm());
      cst.setString(8, request.getCodigoSucursalPlm());
      cst.setString(9, request.getCodigoSucursalPlm());
      Clob xmlOm = conn.createClob();
      xmlOm.setString(1, request.getRequest());
      cst.setClob(10, xmlOm);
      cst.setString(11, request.getCodigoError());
      cst.setString(12, request.getMensajeError());
      cst.setString(13, request.getTipoOperacion());
      cst.setString(14, request.getUserRegistro());
      cst.registerOutParameter(15, OracleTypes.VARCHAR);
      cst.registerOutParameter(16, OracleTypes.VARCHAR);

      cst.setQueryTimeout(Integer.valueOf(prop.dbTimeaiSpRegistrarTrazaEjecucion));

      cst.executeQuery();
      response.setCodigoRespuesta(cst.getString(15));
      response.setMensajeRespuesta(cst.getString(16));
      logger.info(idTransaccion + " Tiempo total de Ejecucion: " + (System.currentTimeMillis() - tiempoInicio));
      logger.info(idTransaccion + "PARAMETROS [OUTPUT]: " + ClaroUtil.printPrettyJSONString(response));

    } catch (Exception ex) {
      ClaroUtil.capturarErrorSP(ex, idTransaccion, nombreProcedimiento, nombreBD, prop);
    } finally {
      if (null != cst) {
        cst.close();
      }
      if (null != conn) {
        conn.close();
      }
      logger.info(mensajeTransaccion + " Tiempo total de proceso (ms): " + (System.currentTimeMillis() - tiempoInicio)
        + " milisegundos");
      logger.info(mensajeTransaccion + " ====== [FIN DEL SP RegistrarTrazabilidad] ======");
    }
    return response;
  }

}
