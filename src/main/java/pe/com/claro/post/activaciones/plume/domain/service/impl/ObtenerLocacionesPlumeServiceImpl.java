package pe.com.claro.post.activaciones.plume.domain.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pe.com.claro.post.activaciones.plume.canonical.header.HeaderRequest;
import pe.com.claro.post.activaciones.plume.canonical.request.ObtenerLocacionesRequest;
import pe.com.claro.post.activaciones.plume.canonical.response.ObtenerLocacionesResponse;
import pe.com.claro.post.activaciones.plume.canonical.response.ObtenerLocacionesResponseType;
import pe.com.claro.post.activaciones.plume.canonical.type.ListaLocacionesOLType;
import pe.com.claro.post.activaciones.plume.canonical.type.ObtenerLocacionesResponseDataType;
import pe.com.claro.post.activaciones.plume.canonical.type.ResponseAuditType;
import pe.com.claro.post.activaciones.plume.common.constants.Constantes;
import pe.com.claro.post.activaciones.plume.common.exceptions.BDException;
import pe.com.claro.post.activaciones.plume.common.exceptions.WSException;
import pe.com.claro.post.activaciones.plume.common.property.PropertiesExterno;
import pe.com.claro.post.activaciones.plume.common.util.UtilJAXRS;
import pe.com.claro.post.activaciones.plume.domain.service.ObtenerLocacionesPlumeService;
import pe.com.claro.post.activaciones.plume.integration.dao.SgaDao;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.request.DatosClienteBeanReq;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.response.DatosClienteBeanRes;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.type.CursorValidaCuenta;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.utiltarios.UtilTokens;
import pe.com.claro.post.activaciones.plume.integration.ws.PlumeCustomerClient;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.ObtenerUbicacionBodyResponse;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.ObtenerUbicacionResponse;

@Component
public class ObtenerLocacionesPlumeServiceImpl implements ObtenerLocacionesPlumeService {
  private static final Logger logger = LoggerFactory.getLogger(ObtenerLocacionesPlumeServiceImpl.class);

  @Autowired
  private PropertiesExterno prop;

  @Autowired
  private SgaDao sgaDao;

  @Autowired
  private PlumeCustomerClient customerCliente;

  @Autowired
  private UtilTokens UtilTokens;

  @Override
  public ObtenerLocacionesResponse obtenerLocaciones(String idTransaccion, HeaderRequest header,
                                                     ObtenerLocacionesRequest request) {

    String nomMetodoActual = Thread.currentThread().getStackTrace()[1].getMethodName();
    long tiempoInicio = System.currentTimeMillis();
    String mensajeTrx = "[obtenerLocaciones idTx=" + idTransaccion + "] ";
    ObtenerLocacionesResponse response = new ObtenerLocacionesResponse();
    ObtenerLocacionesResponseType responseType = new ObtenerLocacionesResponseType();

    List<ListaLocacionesOLType> locaciones = null;

    ResponseAuditType responseAudit = new ResponseAuditType();
    ObtenerLocacionesResponseDataType responseData = new ObtenerLocacionesResponseDataType();

    String tokenClaro = Constantes.STRING_VACIO;
    String tokenPlume = Constantes.STRING_VACIO;
    String tokenPlumeClaroPlumeUnidos = Constantes.STRING_VACIO;

    try {

      logger.info(mensajeTrx + "HEADER REQUEST: " + UtilJAXRS.anyObjectToPrettyJson(header));
      logger.info(mensajeTrx + "BODY REQUEST: " + UtilJAXRS.anyObjectToPrettyJson(request));

      logger.info(Constantes.LOG_PARAM, mensajeTrx, Constantes.INICIO_ACTIVIDAD_LOG, "1. Validar Parametros Obligatorios");
      validateInput(request);
      logger.info(mensajeTrx + " Parametros de entrada validos.....!");
      logger.info(Constantes.LOG_PARAM, mensajeTrx, Constantes.FIN_ACTIVIDAD_LOG, "1. Validar Parametros Obligatorios");

      DatosClienteBeanRes responseDatosClienteSGA = obtenerDatosSGA(idTransaccion, request, mensajeTrx);

      tokenPlumeClaroPlumeUnidos = UtilTokens.subprocesoTokens(idTransaccion, header, mensajeTrx, prop,
        Constantes.S_TRES, null, null);
      logger.info("TOKENS JUNTOS: " + tokenPlumeClaroPlumeUnidos);
      String[] tokens = tokenPlumeClaroPlumeUnidos.split(Constantes.SALTO_LINEA);
      tokenPlume = tokens[Constantes.CERO];
      tokenClaro = tokens[Constantes.UNO];
      logger.info("TOKENS_PLUME: " + tokenPlume);
      logger.info("TOKENS_CLARO: " + tokenClaro);

      logger.info(Constantes.LOG_PARAM, mensajeTrx, Constantes.INICIO_ACTIVIDAD_LOG, "4. Obtener Locaciones");
      String idCustPlume = responseDatosClienteSGA.getCodigoClientePlm();
      ObtenerUbicacionResponse obtenerResponse = customerCliente.obtenerUbicacion(idTransaccion,
        prop.wsPlumeLocationObtenerUbicaAuthorization, prop.nombreBearer + Constantes.ESPACIO + tokenPlume,
        tokenClaro, idCustPlume);
      logger.info(Constantes.LOG_PARAM, mensajeTrx, Constantes.FIN_ACTIVIDAD_LOG, "4. Obtener Locaciones");

      if (!Objects.isNull(obtenerResponse.getMessageResponse().getBody().get(0).getError())) {
        throw new WSException(prop.idf4CodigoLocaciones, prop.idf4MensajeLocaciones.replace(Constantes.DETALLE,
          obtenerResponse.getMessageResponse().getBody().get(0).getError().getMessage()));
      }

      logger.info(Constantes.LOG_PARAM, mensajeTrx, Constantes.INICIO_ACTIVIDAD_LOG, "5. Filtrar Resultados");
      List<CursorValidaCuenta> listaSGA = responseDatosClienteSGA.getCursor();
      List<ObtenerUbicacionBodyResponse> listaPlume = obtenerResponse.getMessageResponse().getBody();
      logger.info(mensajeTrx + " tamaño de listaSGA: " + listaSGA.size());
      logger.info(mensajeTrx + " tamaño de listaPLUME: " + listaPlume.size());

      locaciones = new ArrayList<>();
      obtenerLocacionesList(locaciones, listaSGA, listaPlume);
      logger.info(Constantes.LOG_PARAM, mensajeTrx, Constantes.FIN_ACTIVIDAD_LOG, "5. Filtrar Resultados");

      if (Objects.isNull(locaciones) || locaciones.size() <= 0) {
        throw new WSException(prop.idf5CodigoLocaciones, prop.idf5MensajeLocaciones);
      }
      logger.info("LOCACIONES : " + locaciones.size());

      this.armarResponse(response, responseType, locaciones, responseAudit, responseData, responseDatosClienteSGA,
        obtenerResponse);
    } catch (BDException bd) {
      logger.error(mensajeTrx + bd.getMessage(), bd);
      responseAudit.setCodigoRespuesta(bd.getCode());
      responseAudit.setMensajeRespuesta(bd.getMessage());
      responseType.setResponseAudit(responseAudit);
      response.setObtenerLocacionesResponse(responseType);
    } catch (WSException e) {
      logger.error(mensajeTrx + e.getMessage(), e);
      responseAudit.setCodigoRespuesta(e.getCode());
      responseAudit.setMensajeRespuesta(e.getMessage());
      responseType.setResponseAudit(responseAudit);
      response.setObtenerLocacionesResponse(responseType);
    } catch (Exception e) {
      logger.error(mensajeTrx + e.getMessage(), e);
      responseAudit.setCodigoRespuesta(prop.idt3Codigo);
      responseAudit.setMensajeRespuesta(String.format(prop.idt3Mensaje, e.getMessage()));
      responseType.setResponseAudit(responseAudit);
      response.setObtenerLocacionesResponse(responseType);
    } finally {
      responseAudit.setIdTransaccion(header.getIdTransaccion());
      logger.info("RESPONSE: " + UtilJAXRS.anyObjectToPrettyJson(response));
      logger.info(mensajeTrx + "[FIN de metodo: " + nomMetodoActual + " ] Tiempo total de proceso(ms): "
        + (System.currentTimeMillis() - tiempoInicio) + " milisegundos.");
    }
    return response;
  }


  public void obtenerLocacionesList(List<ListaLocacionesOLType> locaciones, List<CursorValidaCuenta> listaSGA, List<ObtenerUbicacionBodyResponse> listaPlume) {
    ListaLocacionesOLType objLocaciones;

    boolean plumeListIsValid = listaPlume != null && !listaPlume.isEmpty();

    for (int i = 0; i < listaSGA.size(); i++) {
      if (!plumeListIsValid) {
        objLocaciones = new ListaLocacionesOLType();
        objLocaciones.setCodigoSucursal(listaSGA.get(i).getCodSuc());
        locaciones.add(objLocaciones);
        continue;
      }
      for (int j = 0; j < listaPlume.size(); j++) {
        if (listaSGA.get(i).getLocation().equals(listaPlume.get(j).getId())) {
          objLocaciones = new ListaLocacionesOLType();
          objLocaciones.setId(listaPlume.get(j).getId());
          objLocaciones.setNombre(listaPlume.get(j).getName());
          objLocaciones.setNivelServicio(listaPlume.get(j).getServiceLevel().getStatus());
          objLocaciones.setCodigoSucursal(listaSGA.get(i).getCodSuc());
          objLocaciones.setFechaCreacion(listaPlume.get(j).getCreatedAt());
          locaciones.add(objLocaciones);
        }
      }
    }
  }


  public void armarResponse(ObtenerLocacionesResponse response, ObtenerLocacionesResponseType responseType,
                            List<ListaLocacionesOLType> locaciones, ResponseAuditType responseAudit,
                            ObtenerLocacionesResponseDataType responseData, DatosClienteBeanRes resDatosClienteSGA,
                            ObtenerUbicacionResponse obtenerResponse) {

    responseData.setAccountId(obtenerResponse.getMessageResponse().getBody().get(0).getAccountId());
    responseData.setIdCustPlume(resDatosClienteSGA.getCodigoClientePlm());
    responseAudit.setCodigoRespuesta(prop.idf0Codigo);
    responseAudit.setMensajeRespuesta(prop.idf0Mensaje);
    responseData.setLocaciones(locaciones);
    responseType.setResponseData(responseData);
    responseType.setResponseAudit(responseAudit);
    response.setObtenerLocacionesResponse(responseType);
  }

  public DatosClienteBeanRes obtenerDatosSGA(String idTransaccion, ObtenerLocacionesRequest request, String mensajeTrx)
    throws WSException, BDException {
    logger.info(Constantes.LOG_PARAM, mensajeTrx, Constantes.INICIO_ACTIVIDAD_LOG, "2. Obtener Datos");
    DatosClienteBeanReq requeDatosCl = new DatosClienteBeanReq();
    requeDatosCl.setNroSot(request.getObtenerLocacionesRequest().getNumeroSot());
    DatosClienteBeanRes resDatosCliente = sgaDao.datosCliente(idTransaccion, requeDatosCl);
    logger.info(Constantes.LOG_PARAM, mensajeTrx, Constantes.FIN_ACTIVIDAD_LOG, "2. Obtener Datos");

    if (!resDatosCliente.getCodigoRespuesta().equals(Constantes.STRING_CERO)) {
      throw new WSException(prop.idf2CodigoLocaciones,
        prop.idf2MensajeLocaciones.replace(Constantes.DETALLE, resDatosCliente.getCodigoRespuesta()
          .concat(Constantes.GUION_ESPACIO).concat(resDatosCliente.getMensajeRespuesta())));
    }
    return resDatosCliente;
  }

  public void validateInput(ObtenerLocacionesRequest request) throws WSException {
    String attribute = Constantes.VACIO;
    if (StringUtils.isBlank(request.getObtenerLocacionesRequest().getCodigoCliente())
      || StringUtils.isBlank(request.getObtenerLocacionesRequest().getNumeroSot())) {
      if (StringUtils.isBlank(request.getObtenerLocacionesRequest().getCodigoCliente())
        && StringUtils.isBlank(request.getObtenerLocacionesRequest().getNumeroSot())) {
        attribute = "numeroSot" + " y " + "codigoCliente";
      } else {
        attribute = (StringUtils.isBlank(request.getObtenerLocacionesRequest().getNumeroSot())) ? "numeroSot"
          : "codigoCliente";
      }
      throw new WSException(prop.idf1Codigo, prop.idf1Mensaje.replace(Constantes.DETALLE, attribute));
    }
  }

}
