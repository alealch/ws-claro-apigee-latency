package pe.com.claro.post.activaciones.plume.domain.service.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pe.com.claro.post.activaciones.plume.canonical.header.HeaderRequest;
import pe.com.claro.post.activaciones.plume.canonical.request.AprovisionarPlumeRequest;
import pe.com.claro.post.activaciones.plume.canonical.response.AprovisionarPlumeResponse;
import pe.com.claro.post.activaciones.plume.canonical.response.AprovisionarPlumeResponseType;
import pe.com.claro.post.activaciones.plume.canonical.type.AprovisionarPlumeResponseDataType;
import pe.com.claro.post.activaciones.plume.canonical.type.ResponseAuditType;
import pe.com.claro.post.activaciones.plume.common.constants.Constantes;
import pe.com.claro.post.activaciones.plume.common.exceptions.BDException;
import pe.com.claro.post.activaciones.plume.common.exceptions.BaseException;
import pe.com.claro.post.activaciones.plume.common.exceptions.IDFException;
import pe.com.claro.post.activaciones.plume.common.exceptions.WSException;
import pe.com.claro.post.activaciones.plume.common.property.PropertiesExterno;
import pe.com.claro.post.activaciones.plume.common.util.ClaroUtil;
import pe.com.claro.post.activaciones.plume.common.util.UtilJAXRS;
import pe.com.claro.post.activaciones.plume.domain.service.AprovisionarPlumeService;
import pe.com.claro.post.activaciones.plume.integration.dao.SgaDao;
import pe.com.claro.post.activaciones.plume.integration.dao.TimeaiDao;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.request.DatosClienteBeanReq;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.request.RegistrarClieteReq;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.request.RegistrarTrazabilidadRequest;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.response.DatosClienteBeanRes;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.response.RegistrarClieteRes;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.type.CursorEquipoType;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.type.ListaEquiposProvisionados;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.utiltarios.UtilTokens;
import pe.com.claro.post.activaciones.plume.integration.ws.PlumeCustomerClient;
import pe.com.claro.post.activaciones.plume.integration.ws.PlumeLocationClient;
import pe.com.claro.post.activaciones.plume.integration.ws.PlumeNodeClient;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.request.BodyCrearCuentaRequest;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.request.BodyCrearRepetidorRequest;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.request.BodyCrearUbicacionRequest;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.request.ConfigNivelMessageRequest;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.request.ConfigNivelRequest;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.request.CrearCuentaMessageRequest;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.request.CrearCuentaRequest;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.request.CrearRepetidorMessageRequest;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.request.CrearRepetidorRequest;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.request.CrearUbicacionMessageRequest;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.request.CrearUbicacionRequest;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.request.EliminarRepetidorRequest;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.request.HeaderDatapower;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.BodyConfigNivelRequest;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.ConfigNivelResponse;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.CrearCuentaResponse;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.CrearRepetidorResponse;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.CrearUbicacionResponse;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.EliminarCuentaResponse;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.EliminarRepetidorResponse;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.EliminarUbicacionResponse;

@Component
public class AprovisionarPlumeServiceImpl implements AprovisionarPlumeService {

  private static final Logger logger = LoggerFactory.getLogger(AprovisionarPlumeServiceImpl.class);

  @Autowired
  private SgaDao sgaDao;

  @Autowired
  private TimeaiDao timeaiDao;

  @Autowired
  private PlumeCustomerClient plumeCustomerClient;

  @Autowired
  private PlumeLocationClient plumeLocationClient;

  @Autowired
  private PlumeNodeClient plumeNodeClient;

  @Autowired
  private UtilTokens UtilTokens;

  @Override
  public AprovisionarPlumeResponse aprovisionarPlume(String idTransaccion, HeaderRequest header,
                                                     AprovisionarPlumeRequest request, PropertiesExterno prop) {

    String nomMetodoActual = Thread.currentThread().getStackTrace()[1].getMethodName();
    long tiempoInicio = System.currentTimeMillis();
    String mensajeTrx = "[aprovisionarPlume idTx=" + idTransaccion + "] ";
    AprovisionarPlumeResponse response = new AprovisionarPlumeResponse();
    AprovisionarPlumeResponseType responseType = new AprovisionarPlumeResponseType();

    ResponseAuditType responseAudit = new ResponseAuditType();
    AprovisionarPlumeResponseDataType responseData = new AprovisionarPlumeResponseDataType();

    String tokenClaro = Constantes.STRING_VACIO;
    String tokenPlume = Constantes.STRING_VACIO;
    String tokenPlumeTokenClaroUnidos = Constantes.STRING_VACIO;
    String idLocPlume = Constantes.NULL;
    String proceso = Constantes.VACIO;

    CrearCuentaResponse responseCrearCuenta = null;
    CrearUbicacionResponse responseCrearUbicacion = null;
    RegistrarClieteRes responseRegistrarClienteSGA = null;
    DatosClienteBeanRes responseDatosClienteSGA = null;
    List<ListaEquiposProvisionados> lstEquipoProvisionados = null;
    boolean flagEQP = false;

    try {

      logger.info(mensajeTrx + "HEADER REQUEST: " + UtilJAXRS.anyObjectToPrettyJson(header));
      logger.info(mensajeTrx + "BODY REQUEST: " + UtilJAXRS.anyObjectToPrettyJson(request));

      logger.info(Constantes.LOG_PARAM, mensajeTrx, Constantes.INICIO_ACTIVIDAD_LOG, "1. Validar Parametros Obligatorios");
      validateInput(request, prop);
      logger.info(mensajeTrx + " Parametros de entrada validos.....!");
      logger.info(Constantes.LOG_PARAM, mensajeTrx, Constantes.FIN_ACTIVIDAD_LOG, "1. Validar Parametros Obligatorios");

      responseDatosClienteSGA = obtenerDatosSGA(idTransaccion, request, mensajeTrx);

      validarDatosSGA(responseDatosClienteSGA, prop);
      logger.info("antes de entrar.subprocesoTokens  :" + ClaroUtil.printJSONString(responseDatosClienteSGA));
      tokenPlumeTokenClaroUnidos = UtilTokens.subprocesoTokens(idTransaccion, header, mensajeTrx, prop,
        Constantes.S_CINCO, request, responseDatosClienteSGA);
      logger.info("TOKENS JUNTOS: " + tokenPlumeTokenClaroUnidos);
      String[] tokens = tokenPlumeTokenClaroUnidos.split(Constantes.SALTO_LINEA);
      tokenPlume = tokens[Constantes.CERO];
      tokenClaro = tokens[Constantes.UNO];
      logger.info("TOKENS_PLUME: " + tokenPlume);
      logger.info("TOKENS_CLARO: " + tokenClaro);

      logger.info(Constantes.LOG_PARAM, mensajeTrx, Constantes.INICIO_ACTIVIDAD_LOG, "4 Obtener PROCESO");

      if (responseDatosClienteSGA.getValidaCuenta().equals(Constantes.S_UNO)) {
        boolean sucursalFound = false;
        sucursalFound = validarProcesoEQP(responseDatosClienteSGA, sucursalFound);
        idLocPlume = sucursalFound ? validarLocPlume(responseDatosClienteSGA, idLocPlume) : Constantes.NULL;
        proceso = !sucursalFound ? Constantes.PROCESO_LOC : Constantes.PROCESO_EQP;
      } else if (responseDatosClienteSGA.getValidaCuenta().equals(Constantes.S_CERO)) {
        proceso = Constantes.PROCESO_CTA;
      } else {
        throw new IDFException(prop.idf2Codigo, prop.idf2Mensaje.replace("[FIEL_NAME]", "PO_VALIDA_CUENTA[]"));
      }

      logger.info(mensajeTrx + " PROCESO: " + proceso);
      logger.info(Constantes.LOG_PARAM, mensajeTrx, Constantes.FIN_ACTIVIDAD_LOG, "4 Obtener PROCESO");

      switch (proceso) {
        case Constantes.PROCESO_CTA:
          logger.info(Constantes.ENTRO_PROCESO + Constantes.PROCESO_CTA);
          responseCrearCuenta = crearCuentaCliente(mensajeTrx, header, tokenPlume, tokenClaro, responseDatosClienteSGA,
            request, Constantes.PROCESO_CTA, prop);
          configNivelServicioSub(idTransaccion, header, mensajeTrx, tokenPlume, tokenClaro, responseCrearCuenta, null,
            null, proceso, request, prop);
          break;
        case Constantes.PROCESO_LOC:
          logger.info(Constantes.ENTRO_PROCESO + Constantes.PROCESO_LOC);
          responseCrearUbicacion = crearUbicacion(idTransaccion, header, mensajeTrx, tokenPlume, tokenClaro,
            responseDatosClienteSGA, request, Constantes.PROCESO_LOC, prop);
          configNivelServicioSub(idTransaccion, header, mensajeTrx, tokenPlume, tokenClaro, null, responseDatosClienteSGA,
            responseCrearUbicacion, proceso, request, prop);
          break;
        case Constantes.PROCESO_EQP:
          logger.info(Constantes.ENTRO_PROCESO + Constantes.PROCESO_EQP);
          lstEquipoProvisionados = subProcesoAgregarEquipoPlume(idTransaccion, request, header, mensajeTrx, tokenPlume,
            tokenClaro, responseDatosClienteSGA, idLocPlume, proceso, responseCrearCuenta, responseCrearUbicacion, prop);
          flagEQP = true;
          break;
        default:
          break;
      }

      lstEquipoProvisionados = actividad9(idTransaccion, header, request, mensajeTrx, tokenClaro, tokenPlume, idLocPlume, proceso, responseCrearCuenta, responseCrearUbicacion, responseDatosClienteSGA, lstEquipoProvisionados, flagEQP, prop);

      responseRegistrarClienteSGA = registrarClienteSGA(idTransaccion, mensajeTrx, responseDatosClienteSGA,
        lstEquipoProvisionados, proceso, responseCrearCuenta, idLocPlume, responseCrearUbicacion, prop, tokenClaro, tokenPlume, header, request);

      validarRegistrarClienteSGA(responseRegistrarClienteSGA, prop);

      logger.info(mensajeTrx + " Registro de Trazabilidad Exitoso");
      registrarTrazabilidad(header, mensajeTrx, request, responseDatosClienteSGA, proceso, Constantes.S_CERO,
        Constantes.EXITO, lstEquipoProvisionados, prop);

      this.armarResponse(response, responseType, responseAudit, responseData, prop);

    } catch (IDFException idf) {
      logger.error(mensajeTrx + idf.getMessage(), idf);
      responseAudit.setCodigoRespuesta(idf.getCode());
      responseAudit.setMensajeRespuesta(idf.getMessage());
      responseType.setResponseAudit(responseAudit);
      response.setAprovisionarPlumeResponse(responseType);
    } catch (BDException bd) {
      logger.error(mensajeTrx + bd.getMessage(), bd);
      responseAudit.setCodigoRespuesta(bd.getCode());
      responseAudit.setMensajeRespuesta(bd.getMessage());
      responseType.setResponseAudit(responseAudit);
      response.setAprovisionarPlumeResponse(responseType);
    } catch (WSException e) {
      logger.error(mensajeTrx + e.getMessage(), e);
      responseAudit.setCodigoRespuesta(e.getCode());
      responseAudit.setMensajeRespuesta(e.getMessage());
      responseType.setResponseAudit(responseAudit);
      response.setAprovisionarPlumeResponse(responseType);
    } catch (Exception e) {
      logger.error(mensajeTrx + e.getMessage(), e);
      responseAudit.setCodigoRespuesta(prop.idt3Codigo);
      responseAudit.setMensajeRespuesta(String.format(prop.idt3Mensaje, e.getMessage()));
      responseType.setResponseAudit(responseAudit);
      response.setAprovisionarPlumeResponse(responseType);
    } finally {
      responseAudit.setIdTransaccion(header.getIdTransaccion());
      logger.info("RESPONSE: " + UtilJAXRS.anyObjectToPrettyJson(response));
      logger.info(mensajeTrx + "[FIN de metodo: " + nomMetodoActual + " ] Tiempo total de proceso(ms): "
        + (System.currentTimeMillis() - tiempoInicio) + " milisegundos.");
    }
    return response;
  }

  public Boolean validarProcesoEQP(DatosClienteBeanRes responseDatosClienteSGA,Boolean sucursalFound) {
    for (int i = 0; i < responseDatosClienteSGA.getCursor().size(); i++) {
      if (responseDatosClienteSGA.getCodigoSucursal().equals(responseDatosClienteSGA.getCursor().get(i).getCodSuc())) {
        sucursalFound = true;
        break;
      }
    }
    return sucursalFound;
  }

  public String validarLocPlume(DatosClienteBeanRes responseDatosClienteSGA, String idLocPlume){
    for (int i = 0; i < responseDatosClienteSGA.getCursor().size(); i++) {
      if (responseDatosClienteSGA.getCodigoSucursal().equals(responseDatosClienteSGA.getCursor().get(i).getCodSuc())) {
        idLocPlume = responseDatosClienteSGA.getCursor().get(i).getLocation();
        break;
      }
    }
    return idLocPlume;
  }

  public void validarRegistrarClienteSGA(RegistrarClieteRes responseRegistrarClienteSGA, PropertiesExterno prop) throws WSException {
    if (!Constantes.S_CERO.equals(responseRegistrarClienteSGA.getCodigoRespuesta())) {
      throw new WSException(prop.idf10Codigo,
        prop.idf10Mensaje.replace(Constantes.DETALLE, responseRegistrarClienteSGA.getMensajeRespuesta()));
    }
  }

  public List<ListaEquiposProvisionados> actividad9(String idTransaccion, HeaderRequest header, AprovisionarPlumeRequest request, String mensajeTrx, String tokenClaro, String tokenPlume, String idLocPlume, String proceso, CrearCuentaResponse responseCrearCuenta, CrearUbicacionResponse responseCrearUbicacion, DatosClienteBeanRes responseDatosClienteSGA, List<ListaEquiposProvisionados> lstEquipoProvisionados, boolean flagEQP, PropertiesExterno prop) throws WSException, IDFException {
    List<ListaEquiposProvisionados> listaAprov = new ArrayList<ListaEquiposProvisionados>();
    if (!flagEQP) {
      listaAprov = subProcesoAgregarEquipoPlume(idTransaccion, request, header, mensajeTrx, tokenPlume,
        tokenClaro, responseDatosClienteSGA, idLocPlume, proceso, responseCrearCuenta, responseCrearUbicacion, prop);
    } else {
      listaAprov = lstEquipoProvisionados;
    }
    logger.info(idTransaccion + " ListaEquiposProvisionados.size(): " + listaAprov.size());
    logger.info(idTransaccion + " ListaEquiposProvisionados: " + ClaroUtil.printJSONString(listaAprov));
    return listaAprov;
  }

  public void validarDatosSGA(DatosClienteBeanRes responseDatosClienteSGA, PropertiesExterno prop) throws WSException {
    if (!responseDatosClienteSGA.getCodigoRespuesta().equals(Constantes.STRING_CERO)) {
      throw new WSException(prop.idf3Codigo,
        prop.idf3Mensaje.replace(Constantes.DETALLE, responseDatosClienteSGA.getCodigoRespuesta()
          .concat(Constantes.GUION_ESPACIO).concat(responseDatosClienteSGA.getMensajeRespuesta())));
    }
  }

  public void ejecutarRollback(String idTransaccion, String mensajeTrx, String tokenClaro, String tokenPlume,
                               DatosClienteBeanRes resDatosClienteSGA, String proceso, CrearCuentaResponse resCrearCliente,
                               CrearUbicacionResponse resCrearUbi, List<ListaEquiposProvisionados> equiProvisionados, String codigo,
                               String mensaje, HeaderRequest header, AprovisionarPlumeRequest request, PropertiesExterno prop) throws WSException {
    logger.info(Constantes.LOG_PARAM, mensajeTrx, Constantes.INICIO_ACTIVIDAD_LOG, "10. Ejecutar Rolback");

    switch (proceso) {
      case Constantes.PROCESO_CTA:
        EliminarCuentaResponse resEliminarCuenta = eliminarCuentaCliente(idTransaccion, mensajeTrx, tokenPlume,
          tokenClaro, resCrearCliente, prop);
        logger.info("EliminarCuentaResponse: "
          + resEliminarCuenta.getMessageResponse().getBody().getError() != null ? Constantes.EXITO
          : resEliminarCuenta.getMessageResponse().getBody().getError().getMessage());
        break;

      case Constantes.PROCESO_LOC:
        EliminarUbicacionResponse resEliminarUbicacion = eliminarUbicacionCliente(idTransaccion, mensajeTrx, tokenClaro,
          tokenPlume, resDatosClienteSGA, resCrearUbi, prop);

        logger.info("EliminarUbicacionResponse: "
          + resEliminarUbicacion.getMessageResponse().getBody().getError() != null ? Constantes.EXITO
          : resEliminarUbicacion.getMessageResponse().getBody().getError().getMessage());
        break;

      case Constantes.PROCESO_EQP:
        if (Objects.isNull(equiProvisionados) || equiProvisionados.size() == 0) {
          break;
        }
        EliminarRepetidorResponse resEliminarRepetidor = eliminarRepetidor(idTransaccion, tokenPlume, tokenClaro,
          equiProvisionados, prop);
        logger.info("EliminarEquipo: "
          + resEliminarRepetidor.getMessageResponse().getBody().getError() != null ? Constantes.EXITO
          : resEliminarRepetidor.getMessageResponse().getBody().getError().getMessage());
        break;
      default:
        break;
    }
    logger.info(Constantes.LOG_PARAM, mensajeTrx, Constantes.FIN_ACTIVIDAD_LOG, "10. Ejecutar Rolback");

    try {
      registrarTrazabilidad(header, mensajeTrx, request, resDatosClienteSGA, proceso, codigo, mensaje,
        equiProvisionados, prop);
    } catch (BaseException | SQLException e) {
      logger.error(mensajeTrx + e.getMessage(), e);
    }

  }

  public EliminarRepetidorResponse eliminarRepetidor(String mensajeTrx, String tokenPlume, String tokenClaro,
                                                     List<ListaEquiposProvisionados> equiProvisionados, PropertiesExterno prop) throws WSException {
    EliminarRepetidorRequest eliminarReque = null;
    EliminarRepetidorResponse resEliminarRepetidor = null;
    logger.info(Constantes.LOG_PARAM, mensajeTrx, Constantes.INICIO_ACTIVIDAD_LOG, "10.3. Eliminar Equipo");

    eliminarReque = new EliminarRepetidorRequest();
    for (int i = 0; i < equiProvisionados.size(); i++) {
      eliminarReque.setAccountId(equiProvisionados.get(i).getCodigoCliPLM());
      eliminarReque.setIdLocation(equiProvisionados.get(i).getCodigoSucPLM());
      eliminarReque.setSerialNumber(equiProvisionados.get(i).getSerialNumber());

      resEliminarRepetidor = plumeNodeClient.eliminarRepetidor(mensajeTrx, eliminarReque,
        prop.wsPlumeLocationEliminarRepetidorAuthorization, prop.nombreBearer + Constantes.ESPACIO + tokenPlume,
        tokenClaro);
    }

    logger.info(Constantes.LOG_PARAM, mensajeTrx, Constantes.FIN_ACTIVIDAD_LOG, "10.3. Eliminar Equipo");
    return resEliminarRepetidor;
  }

  public EliminarUbicacionResponse eliminarUbicacionCliente(String idTransaccion, String mensajeTrx, String tokenClaro,
                                                            String tokenPlume, DatosClienteBeanRes resDatosClienteSGA, CrearUbicacionResponse resCrearUbi, PropertiesExterno prop)
    throws WSException {
    logger.info(Constantes.LOG_PARAM, mensajeTrx, Constantes.INICIO_ACTIVIDAD_LOG, "10.2. Eliminar Ubicacion Plume");
    logger.info("resDatosClienteSGA.getCodigoClientePlm: " + resDatosClienteSGA.getCodigoClientePlm());
    logger.info("resCrearUbi.getId: " + resCrearUbi.getMessageResponse().getBody().getId());

    EliminarUbicacionResponse responseEliminar = plumeLocationClient.eliminarUbicacion(idTransaccion,
      prop.wsPlumeLocationEliminarUbiAuthorization, prop.nombreBearer + Constantes.ESPACIO + tokenPlume, tokenClaro,
      resDatosClienteSGA.getCodigoClientePlm(), resCrearUbi.getMessageResponse().getBody().getId());

    logger.info(Constantes.LOG_PARAM, mensajeTrx, Constantes.INICIO_ACTIVIDAD_LOG, "10.2. Eliminar Ubicacion Plume");
    return responseEliminar;
  }

  public EliminarCuentaResponse eliminarCuentaCliente(String idTransaccion, String mensajeTrx, String tokenPlume,
                                                      String tokenClaro, CrearCuentaResponse resCrearCliente, PropertiesExterno prop) throws WSException {
    logger.info(Constantes.LOG_PARAM, mensajeTrx, Constantes.INICIO_ACTIVIDAD_LOG, "10.1. Eliminar Cliente Plume");
    logger.info("resCrearCliente.getId(): " + resCrearCliente.getMessageResponse().getBody().getId());
    logger.info("tokenPlume: " + tokenPlume);
    String idAccount = resCrearCliente.getMessageResponse().getBody().getId();
    EliminarCuentaResponse eliminar = plumeCustomerClient.eliminarCuentaCliente(idTransaccion,
      prop.wsPlumeCustomerEliminarCuentaAuthorization, prop.nombreBearer + Constantes.ESPACIO + tokenPlume,
      tokenClaro, idAccount);
    logger.info(Constantes.LOG_PARAM, mensajeTrx, Constantes.INICIO_ACTIVIDAD_LOG, "10.1. Eliminar Cliente Plume");
    return eliminar;
  }

  public void registrarTrazabilidad(HeaderRequest header, String mensajeTrx, AprovisionarPlumeRequest request,
                                    DatosClienteBeanRes responseAct2, String proceso, String codigo, String mensaje,
                                    List<ListaEquiposProvisionados> equiProvisionados, PropertiesExterno prop) throws BaseException, SQLException {
    logger.info(Constantes.LOG_PARAM, mensajeTrx, Constantes.INICIO_ACTIVIDAD_LOG, "11. Registrar Trazabilidad");
    RegistrarTrazabilidadRequest reqRegistrar = new RegistrarTrazabilidadRequest();
    Date currentDate = new Date();

    if (!Objects.isNull(equiProvisionados) && equiProvisionados.size() > 0) {
      logger.info(header.getIdTransaccion() + " tamanio de registrarTrazabilidad.listaEquiposProvisionados.size()"
        + equiProvisionados.size());
      for (ListaEquiposProvisionados listaEquiposProvisionados : equiProvisionados) {
        reqRegistrar = reqRegistrarAprov(header, request, responseAct2, proceso, codigo, mensaje, prop, currentDate, listaEquiposProvisionados);
        timeaiDao.registrarTrazabilidad(header.getIdTransaccion(), reqRegistrar);
        break;
      }
    } else {
      reqRegistrar = reqRegistrarAprovError(header, request, responseAct2, proceso, codigo, mensaje, prop, currentDate);
      timeaiDao.registrarTrazabilidad(header.getIdTransaccion(), reqRegistrar);
    }

    logger.info(Constantes.LOG_PARAM, mensajeTrx, Constantes.FIN_ACTIVIDAD_LOG, "11. Registrar Trazabilidad");

  }

  private RegistrarTrazabilidadRequest reqRegistrarAprovError(HeaderRequest header, AprovisionarPlumeRequest request, DatosClienteBeanRes responseAct2, String proceso, String codigo, String mensaje, PropertiesExterno prop, Date currentDate) {
    RegistrarTrazabilidadRequest reqRegistrar = new RegistrarTrazabilidadRequest();
    reqRegistrar.setEstado(codigo.equals(Constantes.S_CERO) ? Constantes.CERO : Constantes.UNO);
    reqRegistrar.setCanal(header.getCanal());
    reqRegistrar.setAccountId(responseAct2 != null ? responseAct2.getAccountId() : "");
    reqRegistrar.setMensajeError(mensaje);
    reqRegistrar.setCodigoSucursalPlm(Constantes.VACIO);
    reqRegistrar.setCodigoCliente(responseAct2 != null ? responseAct2.getAccountId() : "");
    reqRegistrar.setCodigoSeriePlm(Constantes.VACIO);
    reqRegistrar.setRequest(UtilJAXRS.anyObjectToPrettyJson(request));
    reqRegistrar.setIdTransaccion(header.getIdTransaccion());
    reqRegistrar.setCodigoError(codigo);
    reqRegistrar.setCodigoClientePlm(Constantes.VACIO);
    reqRegistrar.setTipoOperacion(proceso);
    reqRegistrar.setFeExec(new Timestamp(currentDate.getTime()));
    reqRegistrar.setUserRegistro(prop.registrarTrazabilidadUserId);
    return reqRegistrar;
  }

  private RegistrarTrazabilidadRequest reqRegistrarAprov(HeaderRequest header, AprovisionarPlumeRequest request, DatosClienteBeanRes responseAct2, String proceso, String codigo, String mensaje, PropertiesExterno prop, Date currentDate, ListaEquiposProvisionados listaEquiposProvisionados) {
    RegistrarTrazabilidadRequest reqRegistrar = new RegistrarTrazabilidadRequest();
    reqRegistrar.setIdTransaccion(header.getIdTransaccion());
    reqRegistrar.setEstado(codigo.equals(Constantes.S_CERO) ? Constantes.CERO : Constantes.UNO);
    reqRegistrar.setFeExec(new Timestamp(currentDate.getTime()));
    reqRegistrar.setCodigoCliente(responseAct2.getCodigoCliente() != null ? responseAct2.getCodigoCliente() : "");
    reqRegistrar.setCanal(header.getCanal());
    reqRegistrar.setAccountId(responseAct2.getAccountId() != null ? responseAct2.getAccountId().trim() : "");
    reqRegistrar.setCodigoClientePlm(listaEquiposProvisionados.getCodigoCliPLM());
    reqRegistrar.setCodigoSucursalPlm(listaEquiposProvisionados.getCodigoSucPLM());
    reqRegistrar.setCodigoSeriePlm(Constantes.VACIO);
    reqRegistrar.setRequest(UtilJAXRS.anyObjectToPrettyJson(request));
    reqRegistrar.setCodigoError(codigo);
    reqRegistrar.setMensajeError(mensaje);
    reqRegistrar.setTipoOperacion(proceso);
    reqRegistrar.setUserRegistro(prop.registrarTrazabilidadUserId);
    return reqRegistrar;
  }

  public List<ListaEquiposProvisionados> subProcesoAgregarEquipoPlume(String idTransaccion,
                                                                      AprovisionarPlumeRequest request, HeaderRequest headerWs, String mensajeTrx, String tokenPlume, String tokenClaro,
                                                                      DatosClienteBeanRes resDatosClienteSGA, String idLocPlume, String proceso, CrearCuentaResponse resCrearCliente,
                                                                      CrearUbicacionResponse resCrearUbi, PropertiesExterno prop) throws WSException, IDFException {

    CrearRepetidorRequest request2 = new CrearRepetidorRequest();
    CrearRepetidorMessageRequest requeMessag = new CrearRepetidorMessageRequest();
    HeaderDatapower headerDp = UtilTokens.headerDP(idTransaccion, headerWs, prop, Constantes.CREARREPETIDOR);
    BodyCrearRepetidorRequest body = null;
    requeMessag.setHeader(headerDp);

    List<ListaEquiposProvisionados> objListaEquiposProvisionados = new ArrayList<>();
    ListaEquiposProvisionados obj = null;

    String idAccount = Constantes.VACIO;
    String idLocation = Constantes.VACIO;
    CrearRepetidorResponse responseCrear = null;

    if (Objects.isNull(request.getAprovisionarPlumeRequest().getEquipos()) || request.getAprovisionarPlumeRequest().getEquipos().isEmpty()) {
      return objListaEquiposProvisionados;
    }

    body = new BodyCrearRepetidorRequest();
    for (int i = 0; i < request.getAprovisionarPlumeRequest().getEquipos().size(); i++) {

      if (validarEquipo(request, i)) {
        continue;
      }
      logger.info(Constantes.LOG_PARAM, mensajeTrx, Constantes.INICIO_ACTIVIDAD_LOG, "8. AGREGAR EQUIPO");
      logger.info("TAMANIO TOTAL DE CURSOR: " + request.getAprovisionarPlumeRequest().getEquipos().size());
      idAccount = obtenerCustomerIdPlume(resDatosClienteSGA, proceso, resCrearCliente);
      idLocation = obtenerLocationIdPlume(proceso, idLocPlume, resCrearUbi, resCrearCliente);

      body.setSerialNumber(request.getAprovisionarPlumeRequest().getEquipos().get(i).getNumeroSerie());
      body.setNickName(request.getAprovisionarPlumeRequest().getEquipos().get(i).getAlias());

      requeMessag.setBody(body);
      request2.setMessageRequest(requeMessag);

      logger.info("Request :" + " VA EN: " + (i + Constantes.UNO) + " - "
        + UtilJAXRS.anyObjectToPrettyJson(request.getAprovisionarPlumeRequest().getEquipos().get(i)));

      try {
        responseCrear = plumeNodeClient.crearRepetidor(idTransaccion, request2,
          prop.wsPlumeLocationCrearRepetidorAuthorization, prop.nombreBearer + Constantes.ESPACIO + tokenPlume,
          tokenClaro, idAccount, idLocation);

      } catch (WSException e) {
        logger.info(Constantes.ERROR_PARAM, "WS Error: ", UtilJAXRS.anyObjectToPrettyJson(e));
        ejecutarRollback(idTransaccion, mensajeTrx, tokenClaro, tokenPlume, resDatosClienteSGA, proceso, resCrearCliente,
          resCrearUbi, objListaEquiposProvisionados, e.getCode(), e.getMessage(), headerWs, request, prop);
        throw new WSException(e.getCode(), e.getMessage());
      }

      validarIdfIdtCrearRepetidor(idTransaccion, request, headerWs, mensajeTrx, tokenPlume, tokenClaro, resDatosClienteSGA, proceso, resCrearCliente, resCrearUbi, prop, objListaEquiposProvisionados, responseCrear);

      String idKvConfig = Objects.isNull(responseCrear.getMessageResponse().getBody().getError()) ?
        responseCrear.getMessageResponse().getBody().getClaimed().get(0).getKvConfigs().get(0).getId() :
        null;

      obj = getEquiposProvisionados(request, body, idAccount, idLocation, i, idKvConfig);
      objListaEquiposProvisionados.add(obj);

    }

    logger.info(Constantes.LOG_PARAM, mensajeTrx, Constantes.FIN_ACTIVIDAD_LOG, "8. AGREGAR EQUIPO");

    return objListaEquiposProvisionados;
  }

  public void validarIdfIdtCrearRepetidor(String idTransaccion, AprovisionarPlumeRequest request, HeaderRequest headerWs, String mensajeTrx, String tokenPlume, String tokenClaro, DatosClienteBeanRes resDatosClienteSGA, String proceso, CrearCuentaResponse resCrearCliente, CrearUbicacionResponse resCrearUbi, PropertiesExterno prop, List<ListaEquiposProvisionados> objListaEquiposProvisionados, CrearRepetidorResponse responseCrear) throws WSException, IDFException {
    if (Constantes.NO_SERVICE_FOUND_CODE.equals(responseCrear.getMessageResponse().getHeader().getHeaderResponse().getStatus().getCode())) {
      ejecutarRollback(idTransaccion, mensajeTrx, tokenClaro, tokenPlume, resDatosClienteSGA, proceso, resCrearCliente,
        resCrearUbi, objListaEquiposProvisionados, responseCrear.getMessageResponse().getHeader().getHeaderResponse().getStatus().getCode(),
        responseCrear.getMessageResponse().getHeader().getHeaderResponse().getStatus().getMessage(), headerWs, request, prop);
      throw new WSException(prop.idt2Codigo, String.format(prop.idt2Mensaje, Constantes.CONFIGNIVEL, responseCrear.getMessageResponse().getHeader().getHeaderResponse().getStatus().getMessage()));
    }

    if (responseCrear.getMessageResponse().getBody().getError() != null || !Constantes.S_CERO
      .equals(responseCrear.getMessageResponse().getHeader().getHeaderResponse().getStatus().getCode())) {
      ejecutarRollback(idTransaccion, mensajeTrx, tokenClaro, tokenPlume, resDatosClienteSGA, proceso, resCrearCliente,
        resCrearUbi, objListaEquiposProvisionados,
        responseCrear.getMessageResponse().getBody().getError() != null ? responseCrear.getMessageResponse().getBody().getError().getStatusCode() : responseCrear.getMessageResponse().getHeader().getHeaderResponse().getStatus().getCode(),
        responseCrear.getMessageResponse().getBody().getError() != null ? responseCrear.getMessageResponse().getBody().getError().getMessage() : responseCrear.getMessageResponse().getHeader().getHeaderResponse().getStatus().getMessage(),
        headerWs, request, prop);
      throw new IDFException(prop.idf9Codigo, prop.idf9Mensaje.replace(Constantes.DETALLE,
        responseCrear.getMessageResponse().getBody().getError().getMessage()));
    }
  }

  private boolean validarEquipo(AprovisionarPlumeRequest request, int i) {
    return request.getAprovisionarPlumeRequest().getEquipos().get(i).getAlias().isEmpty()
      && request.getAprovisionarPlumeRequest().getEquipos().get(i).getModelo().isEmpty()
      && request.getAprovisionarPlumeRequest().getEquipos().get(i).getNumeroSerie().isEmpty();
  }

  public ListaEquiposProvisionados getEquiposProvisionados(AprovisionarPlumeRequest request, BodyCrearRepetidorRequest body, String idAccount, String idLocation, int i, String idKvConfig) {
    ListaEquiposProvisionados obj;
    obj = new ListaEquiposProvisionados();
    obj.setCodigoCliPLM(idAccount);
    obj.setCodigoSucPLM(idLocation);
    obj.setNickName(body.getNickName());
    obj.setModelo(request.getAprovisionarPlumeRequest().getEquipos().get(i).getModelo());
    obj.setSerialNumber(request.getAprovisionarPlumeRequest().getEquipos().get(i).getNumeroSerie());
    obj.setCodSeriePLM(idKvConfig);
    return obj;
  }

  public String obtenerCustomerIdPlume(DatosClienteBeanRes resDatosClienteSGA, String proceso,
                                       CrearCuentaResponse resCrearCliente) {
    return proceso.equals(Constantes.PROCESO_LOC) || proceso.equals(Constantes.PROCESO_EQP)
      ? resDatosClienteSGA.getCodigoClientePlm()
      : resCrearCliente.getMessageResponse().getBody().getId();
  }

  public String obtenerLocationIdPlume(String proceso, String idLocPlume, CrearUbicacionResponse resCrearUbi,
                                       CrearCuentaResponse resCrearCliente) {

    String resultado = Constantes.VACIO;
    switch (proceso) {
      case Constantes.PROCESO_EQP:
        resultado = idLocPlume;
        break;
      case Constantes.PROCESO_LOC:
        resultado = resCrearUbi.getMessageResponse().getBody().getId();
        break;
      case Constantes.PROCESO_CTA:
        resultado = resCrearCliente.getMessageResponse().getBody().getLocationId();
        break;
      default:
        break;
    }

    return resultado;
  }

  public RegistrarClieteRes registrarClienteSGA(String idTransaccion, String mensajeTrx,
                                                DatosClienteBeanRes resDatosClienteSGA, List<ListaEquiposProvisionados> equiProvisionados, String proceso,
                                                CrearCuentaResponse resCrearCliente, String idLocPlume, CrearUbicacionResponse resCrearUbi, PropertiesExterno prop,
                                                String tokenClaro, String tokenPlume, HeaderRequest header, AprovisionarPlumeRequest request) throws BDException, IDFException, WSException {
    logger.info(Constantes.LOG_PARAM, mensajeTrx, Constantes.INICIO_ACTIVIDAD_LOG, "9. Registrar Cliente SGA");
    List<CursorEquipoType> cursorEquipo = new ArrayList<>();
    CursorEquipoType beanTypeEqu = null;
    logger.info(mensajeTrx, " Tamanio del cursor.ListaEquiposProvisionados.size()" + equiProvisionados);

    logger.info("ARRAY :" + ClaroUtil.printJSONString(equiProvisionados));

    for (ListaEquiposProvisionados cursorEquipoType : equiProvisionados) {
      beanTypeEqu = new CursorEquipoType();
      beanTypeEqu.setNroSerie(cursorEquipoType.getSerialNumber());
      beanTypeEqu.setCodSerie(cursorEquipoType.getCodSeriePLM());
      beanTypeEqu.setMdlEquipo(cursorEquipoType.getModelo());
      cursorEquipo.add(beanTypeEqu);
    }

    RegistrarClieteReq reqRegistrarCliente = new RegistrarClieteReq();
    reqRegistrarCliente.setCodigoCliente(resDatosClienteSGA.getCodigoCliente());
    reqRegistrarCliente.setCodigoClientePlm(obtenerCodigoClientePlm(resDatosClienteSGA, proceso, resCrearCliente));
    reqRegistrarCliente.setCodigoSuc(resDatosClienteSGA.getCodigoSucursal());
    reqRegistrarCliente.setCodigoSucPlm(obtenerCodigoSucPlm(proceso, resCrearCliente, idLocPlume, resCrearUbi));
    reqRegistrarCliente.setCursorEquipo(cursorEquipo);
    reqRegistrarCliente.setNombreCliente(resDatosClienteSGA.getNombres());
    reqRegistrarCliente.setUsuarioReg(prop.dbSgaSpRegistrarClienteUsuario);

    RegistrarClieteRes respRegistrarClient = new RegistrarClieteRes();

    try {
      respRegistrarClient = sgaDao.registrarCliente(idTransaccion, reqRegistrarCliente);
      logger.info(Constantes.LOG_PARAM, mensajeTrx, Constantes.FIN_ACTIVIDAD_LOG, "9. Registrar Cliente SGA");
    } catch (BDException e) {
      logger.info(Constantes.ERROR_PARAM, "BD Error: ", UtilJAXRS.anyObjectToPrettyJson(e));
      ejecutarRollback(idTransaccion, mensajeTrx, tokenClaro, tokenPlume, resDatosClienteSGA, proceso, resCrearCliente,
        resCrearUbi, equiProvisionados, e.getCode(), e.getMessage(), header, request, prop);
      throw new BDException(e.getCode(), e.getMessage());
    }

    if (!Constantes.S_CERO
      .equals(respRegistrarClient.getCodigoRespuesta())) {
      ejecutarRollback(idTransaccion, mensajeTrx, tokenClaro, tokenPlume, resDatosClienteSGA, proceso, resCrearCliente,
        resCrearUbi, equiProvisionados, respRegistrarClient.getCodigoRespuesta(),
        respRegistrarClient.getMensajeRespuesta(), header, request, prop);
      throw new IDFException(prop.idf10Codigo, prop.idf10Mensaje.replace(Constantes.DETALLE,
        respRegistrarClient.getMensajeRespuesta()));
    }

    return respRegistrarClient;
  }

  public String obtenerCodigoClientePlm(DatosClienteBeanRes resDatosClienteSGA, String proceso,
                                        CrearCuentaResponse resCrearCliente) {

    String codigoCliente = Constantes.VACIO;
    switch (proceso) {
      case Constantes.PROCESO_EQP:
      case Constantes.PROCESO_LOC:
        codigoCliente = resDatosClienteSGA.getCodigoClientePlm();
        break;
      default:
        codigoCliente = resCrearCliente.getMessageResponse().getBody().getId();
        break;
    }
    return codigoCliente;

  }

  public String obtenerCodigoSucPlm(String proceso, CrearCuentaResponse resCrearCliente, String idLocPlume,
                                    CrearUbicacionResponse resCrearUbi) {

    String codigoCliente = Constantes.VACIO;
    switch (proceso) {
      case Constantes.PROCESO_EQP:
        codigoCliente = idLocPlume;
        break;
      case Constantes.PROCESO_LOC:
        codigoCliente = resCrearUbi.getMessageResponse().getBody().getId();
        break;
      case Constantes.PROCESO_CTA:
        codigoCliente = resCrearCliente.getMessageResponse().getBody().getLocationId();
        break;
      default:
        break;
    }

    return codigoCliente;
  }

  public DatosClienteBeanRes obtenerDatosSGA(String idTransaccion, AprovisionarPlumeRequest request, String mensajeTrx)
    throws BaseException {
    logger.info(Constantes.LOG_PARAM, mensajeTrx, Constantes.INICIO_ACTIVIDAD_LOG, "2. Obtener Datos");
    DatosClienteBeanReq requeDatosCl = new DatosClienteBeanReq();
    requeDatosCl.setNroSot(request.getAprovisionarPlumeRequest().getNumeroSot());
    DatosClienteBeanRes resDatosCliente = sgaDao.datosCliente(idTransaccion, requeDatosCl);
    logger.info(Constantes.LOG_PARAM, mensajeTrx, Constantes.FIN_ACTIVIDAD_LOG, "2. Obtener Datos");
    return resDatosCliente;
  }

  public CrearUbicacionResponse crearUbicacion(String idTransaccion, HeaderRequest headerWs, String mensajeTrx,
                                               String tokenPlume, String tokenClaro, DatosClienteBeanRes resDatosCliente, AprovisionarPlumeRequest request,
                                               String proceso, PropertiesExterno prop) throws BaseException, SQLException {
    logger.info(Constantes.LOG_PARAM, mensajeTrx, Constantes.INICIO_ACTIVIDAD_LOG, "6 Crear Locacion PLUME");
    logger.info("RESPONSE.datosCliente.size(): " + resDatosCliente.getCursor().size());

    HeaderDatapower headerDp = UtilTokens.headerDP(idTransaccion, headerWs, prop, Constantes.CREARUBICACION);
    CrearUbicacionRequest requestCrear = new CrearUbicacionRequest();
    CrearUbicacionMessageRequest requeMessa = new CrearUbicacionMessageRequest();
    BodyCrearUbicacionRequest bodyReq = new BodyCrearUbicacionRequest();

    bodyReq.setName(resDatosCliente.getNombreSurcursal());
    requeMessa.setBody(bodyReq);
    requeMessa.setHeader(headerDp);
    requestCrear.setMessageRequest(requeMessa);
    CrearUbicacionResponse resCrearUbi = plumeLocationClient.crearUbicacion(idTransaccion, requestCrear,
      prop.wsCrearUbicacionAuthorization, prop.nombreBearer + Constantes.ESPACIO + tokenPlume, tokenClaro,
      resDatosCliente.getCodigoClientePlm());

    logger.info(Constantes.LOG_PARAM, mensajeTrx, Constantes.FIN_ACTIVIDAD_LOG, "6 Crear Locacion PLUME");
    logger.info("CODE: " + resCrearUbi.getMessageResponse().getHeader().getHeaderResponse().getStatus().getCode());
    logger
      .info("MESSAGE: " + resCrearUbi.getMessageResponse().getHeader().getHeaderResponse().getStatus().getMessage());

    if (resCrearUbi.getMessageResponse().getBody().getError() != null) {
      registrarTrazabilidad(headerWs, mensajeTrx, request, resDatosCliente, proceso,
        resCrearUbi.getMessageResponse().getBody().getError().getStatusCode(),
        resCrearUbi.getMessageResponse().getBody().getError().getMessage(),
        null, prop);
      throw new WSException(prop.idf7Codigo, prop.idf7Mensaje.replace(Constantes.DETALLE,
        resCrearUbi.getMessageResponse().getBody().getError().getMessage()));
    }

    return resCrearUbi;
  }

  public ConfigNivelResponse configNivelServicioSub(String idTransaccion, HeaderRequest headerWs, String mensajeTrx,
                                                    String tokenPlume, String tokenClaro, CrearCuentaResponse respon, DatosClienteBeanRes responseSGA,
                                                    CrearUbicacionResponse responseCrearUbi, String proceso, AprovisionarPlumeRequest requestWs, PropertiesExterno prop) throws WSException {
    logger.info(Constantes.LOG_PARAM, mensajeTrx, Constantes.INICIO_ACTIVIDAD_LOG, "7. Configurar Servicio PLUME");

    ConfigNivelRequest request = new ConfigNivelRequest();
    HeaderDatapower headerDp = UtilTokens.headerDP(idTransaccion, headerWs, prop, Constantes.CONFIGNIVEL);
    ConfigNivelMessageRequest requeMessa = new ConfigNivelMessageRequest();

    BodyConfigNivelRequest bodyReq = new BodyConfigNivelRequest();
    String accountId = Constantes.VACIO;
    String locationId = Constantes.VACIO;

    if (Constantes.PROCESO_CTA.equals(proceso)) {
      accountId = respon.getMessageResponse().getBody().getId();
      locationId = respon.getMessageResponse().getBody().getLocationId();
    } else if (Constantes.PROCESO_LOC.equals(proceso)) {
      accountId = responseSGA.getCodigoClientePlm();
      locationId = responseCrearUbi.getMessageResponse().getBody().getId();
    }
    bodyReq.setStatus(prop.wsPlumeLocationConfigNivelBasicService);

    requeMessa.setBody(bodyReq);
    requeMessa.setHeader(headerDp);
    request.setMessageRequest(requeMessa);

    ConfigNivelResponse response = new ConfigNivelResponse();

    try {
      response = plumeLocationClient.configNivelServicio(idTransaccion, request,
        prop.wsConfigNivelServiAuthorization, prop.nombreBearer + Constantes.ESPACIO + tokenPlume, tokenClaro,
        accountId, locationId);

      logger.info(Constantes.LOG_PARAM, mensajeTrx, Constantes.FIN_ACTIVIDAD_LOG, "7. Configurar Servicio PLUME");
    } catch (WSException e) {
      logger.info(Constantes.ERROR_PARAM, "WS Error: ", UtilJAXRS.anyObjectToPrettyJson(e));
      ejecutarRollback(idTransaccion, mensajeTrx, tokenClaro, tokenPlume, responseSGA, proceso, respon,
        responseCrearUbi, null, e.getCode(),
        e.getMessage(), headerWs, requestWs, prop);
      throw new WSException(e.getCode(), e.getMessage());
    }

    if (Constantes.NO_SERVICE_FOUND_CODE.equals(response.getMessageResponse().getHeader().getHeaderResponse().getStatus().getCode())) {
      ejecutarRollback(idTransaccion, mensajeTrx, tokenClaro, tokenPlume, responseSGA, proceso, respon,
        responseCrearUbi, null, response.getMessageResponse().getHeader().getHeaderResponse().getStatus().getCode(),
        response.getMessageResponse().getHeader().getHeaderResponse().getStatus().getMessage(), headerWs, requestWs, prop);
      throw new WSException(prop.idt2Codigo, String.format(prop.idt2Mensaje, Constantes.CONFIGNIVEL, response.getMessageResponse().getBody().getFault().getDetail().getIntegrationError()));
    }

    if (!Constantes.S_CERO
      .equals(response.getMessageResponse().getHeader().getHeaderResponse().getStatus().getCode())) {
      ejecutarRollback(idTransaccion, mensajeTrx, tokenClaro, tokenPlume, responseSGA, proceso, respon,
        responseCrearUbi, null, response.getMessageResponse().getHeader().getHeaderResponse().getStatus().getCode(),
        response.getMessageResponse().getHeader().getHeaderResponse().getStatus().getMessage(), headerWs, requestWs, prop);
      throw new WSException(prop.idf8Codigo, prop.idf8Mensaje.replace(Constantes.DETALLE,
        response.getMessageResponse().getBody().getFault().getDetail().getIntegrationError()));
    }

    return response;
  }

  public CrearCuentaResponse crearCuentaCliente(String mensajeTrx, HeaderRequest headerWs, String tokenPlume,
                                                String tokenClaro, DatosClienteBeanRes resDatosCliente, AprovisionarPlumeRequest request, String proceso, PropertiesExterno prop)
    throws BaseException, SQLException {
    logger.info(Constantes.LOG_PARAM, mensajeTrx, Constantes.INICIO_ACTIVIDAD_LOG, "5 Crear Cuenta PLUME");
    CrearCuentaRequest reqCrearCliente = new CrearCuentaRequest();
    CrearCuentaMessageRequest requeMessag = new CrearCuentaMessageRequest();
    HeaderDatapower header = UtilTokens.headerDP(headerWs.getIdTransaccion(), headerWs, prop, Constantes.CREARCUENTA);
    BodyCrearCuentaRequest bodyReq = new BodyCrearCuentaRequest();

    bodyReq.setPartnerId(prop.wsPlumeCustomerCrearCuentaPartnerId);
    bodyReq.setAccountId(resDatosCliente.getAccountId().trim());
    bodyReq.setEmail(resDatosCliente.getEmail());
    bodyReq.setName(resDatosCliente.getNombres());
    bodyReq.setProfile(prop.wsPlumeCustomerCrearCuentaProfile);
    bodyReq.setAcceptLanguage(prop.wsPlumeCustomerCrearCuentaAcceptLeng);
    bodyReq.setOnboardingCheckpoint(prop.wsPlumeCustomerCrearCuentaOnboardingCheck);

    requeMessag.setHeader(header);
    requeMessag.setBody(bodyReq);
    reqCrearCliente.setMessageRequest(requeMessag);
    CrearCuentaResponse resCrearCliente = plumeCustomerClient.crearCuentaCliente(mensajeTrx, reqCrearCliente,
      prop.wsPlumeCustomerCrearCuentaAuthorization, prop.nombreBearer + Constantes.ESPACIO + tokenPlume, tokenClaro);
    logger.info(Constantes.LOG_PARAM, mensajeTrx, Constantes.FIN_ACTIVIDAD_LOG, "5 Crear Cuenta PLUME");

    if (!Constantes.S_CERO.equals(resCrearCliente.getMessageResponse().getHeader().getHeaderResponse().getStatus().getCode()) || resCrearCliente.getMessageResponse().getBody().getError() != null) {
      registrarTrazabilidad(headerWs, mensajeTrx, request, resDatosCliente, proceso,
        resCrearCliente.getMessageResponse().getBody().getError().getStatusCode(),
        resCrearCliente.getMessageResponse().getBody().getError().getMessage(), null, prop);

      throw new WSException(prop.idf6Codigo, prop.idf6Mensaje.replace(Constantes.DETALLE,
        resCrearCliente.getMessageResponse().getBody().getError().getMessage()));
    }
    return resCrearCliente;
  }

  public void armarResponse(AprovisionarPlumeResponse response, AprovisionarPlumeResponseType responseType,
                            ResponseAuditType responseAudit, AprovisionarPlumeResponseDataType responseData, PropertiesExterno prop) {
    responseAudit.setCodigoRespuesta(prop.idf0Codigo);
    responseAudit.setMensajeRespuesta(prop.idf0Mensaje);

    responseType.setResponseAudit(responseAudit);
    responseType.setResponseData(responseData);
    response.setAprovisionarPlumeResponse(responseType);
  }

  public void validateInput(AprovisionarPlumeRequest request, PropertiesExterno prop) throws IDFException {
    String idTipo = request.getAprovisionarPlumeRequest().getIdTipo();
    String numeroSot = request.getAprovisionarPlumeRequest().getNumeroSot();
    String coId = request.getAprovisionarPlumeRequest().getCoId();

    if (StringUtils.isBlank(idTipo)) {
      throw new IDFException(prop.idf1Codigo, prop.idf1Mensaje.replace(Constantes.DETALLE, "idTipo"));
    }

    if (!idTipo.equals(Constantes.S_UNO) && !idTipo.equals(Constantes.S_DOS)) {
      throw new IDFException(prop.idf2Codigo, prop.idf2Mensaje.replace(Constantes.FIEL_NAME, "idTipo"));
    }

    if (idTipo.equals(Constantes.S_UNO) && StringUtils.isBlank(numeroSot)) {
      throw new IDFException(prop.idf2Codigo, prop.idf2Mensaje.replace(Constantes.FIEL_NAME, "numeroSot"));
    }

    if (idTipo.equals(Constantes.S_DOS) && StringUtils.isBlank(coId)) {
      throw new IDFException(prop.idf2Codigo, prop.idf2Mensaje.replace(Constantes.FIEL_NAME, "coId"));
    }
  }
}
