package pe.com.claro.post.activaciones.plume.domain.service.impl;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pe.com.claro.post.activaciones.plume.canonical.header.HeaderRequest;
import pe.com.claro.post.activaciones.plume.canonical.request.ConsultarEquipoRequest;
import pe.com.claro.post.activaciones.plume.canonical.response.ConsultarEquipoResponse;
import pe.com.claro.post.activaciones.plume.canonical.response.ConsultarEquipoResponseType;
import pe.com.claro.post.activaciones.plume.canonical.type.ConsultarEquipoResponseDataType;
import pe.com.claro.post.activaciones.plume.canonical.type.ResponseAuditType;
import pe.com.claro.post.activaciones.plume.common.constants.Constantes;
import pe.com.claro.post.activaciones.plume.common.exceptions.BDException;
import pe.com.claro.post.activaciones.plume.common.exceptions.WSException;
import pe.com.claro.post.activaciones.plume.common.property.PropertiesExterno;
import pe.com.claro.post.activaciones.plume.common.util.UtilJAXRS;
import pe.com.claro.post.activaciones.plume.domain.service.ConsultarEquipoPlumeService;
import pe.com.claro.post.activaciones.plume.integration.dao.SgaDao;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.request.DatosClienteBeanReq;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.response.DatosClienteBeanRes;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.response.EquipoRes;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.type.POCursorType;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.utiltarios.UtilTokens;
import pe.com.claro.post.activaciones.plume.integration.ws.PlumeNodeClient;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.ConsultarRepetidorResponse;

@Component
public class ConsultarEquipoPlumeServiceImpl implements ConsultarEquipoPlumeService {
  private static final Logger logger = LoggerFactory.getLogger(ConsultarEquipoPlumeServiceImpl.class);

  @Autowired
  private SgaDao sgaDao;

  @Autowired
  private PlumeNodeClient nodeClient;

  @Autowired
  private UtilTokens utilTokens;

  @Override
  public ConsultarEquipoResponse consultarEquipo(String idTransaccion, HeaderRequest header,
                                                 ConsultarEquipoRequest request, PropertiesExterno prop) {

    String nomMetodoActual = Thread.currentThread().getStackTrace()[1].getMethodName();
    long tiempoInicio = System.currentTimeMillis();
    String mensajeTrx = "[consultarEquipo idTx=" + idTransaccion + "] ";
    ConsultarEquipoResponse response = new ConsultarEquipoResponse();
    ConsultarEquipoResponseType responseType = new ConsultarEquipoResponseType();

    ResponseAuditType responseAudit = new ResponseAuditType();
    ConsultarEquipoResponseDataType responseData = new ConsultarEquipoResponseDataType();

    String tokenClaro = Constantes.STRING_VACIO;
    String tokenPlume = Constantes.STRING_VACIO;
    String tokenPlumeClaroPlumeUnidos = Constantes.STRING_VACIO;

    try {
      logger.info(mensajeTrx + "HEADER REQUEST: " + UtilJAXRS.anyObjectToPrettyJson(header));
      logger.info(mensajeTrx + "BODY REQUEST: " + UtilJAXRS.anyObjectToPrettyJson(request));

      logger.info(Constantes.LOG_PARAM, mensajeTrx, Constantes.INICIO_ACTIVIDAD_LOG, "1. Validar Parametros Obligatorios");
      if (!StringUtils.isNotBlank(request.getConsultarEquipoRequest().getCodigoCliente())) {
        throw new WSException(prop.idf1Codigo, prop.idf1Mensaje.replace(Constantes.DETALLE, "codigoCliente"));
      }
      if (!StringUtils.isNotBlank(request.getConsultarEquipoRequest().getCodigoSucursal())) {
        throw new WSException(prop.idf1Codigo, prop.idf1Mensaje.replace(Constantes.DETALLE, "codigoSucursal"));
      }
      if (!StringUtils.isNotBlank(request.getConsultarEquipoRequest().getNumeroSerie())) {
        throw new WSException(prop.idf1Codigo, prop.idf1Mensaje.replace(Constantes.DETALLE, "numeroSerie"));
      }
      if (!StringUtils.isNotBlank(request.getConsultarEquipoRequest().getNumeroSot())) {
        throw new WSException(prop.idf1Codigo, prop.idf1Mensaje.replace(Constantes.DETALLE, "numeroSot"));
      }
      logger.info(mensajeTrx + " Parametros de entrada validos.....!");
      logger.info(Constantes.LOG_PARAM, mensajeTrx, Constantes.FIN_ACTIVIDAD_LOG, "1. Validar Parametros Obligatorios");

      logger.info(Constantes.LOG_PARAM, mensajeTrx, Constantes.INICIO_ACTIVIDAD_LOG, "2. Obtener Datos");
      DatosClienteBeanReq requeDatosCl = new DatosClienteBeanReq();
      requeDatosCl.setNroSot(request.getConsultarEquipoRequest().getNumeroSot());
      DatosClienteBeanRes responseDatosClienteSGA = sgaDao.datosCliente(idTransaccion, requeDatosCl);
      logger.info(Constantes.LOG_PARAM, mensajeTrx, Constantes.FIN_ACTIVIDAD_LOG, "2. Obtener Datos");

      if (!responseDatosClienteSGA.getCodigoRespuesta().equals(Constantes.STRING_CERO)) {
        throw new WSException(prop.idf2CodigoObtenerEquipos,
          prop.idf2MensajeConsultarEquipos.replace(Constantes.DETALLE, responseDatosClienteSGA.getCodigoRespuesta()
            .concat(Constantes.GUION_ESPACIO).concat(responseDatosClienteSGA.getMensajeRespuesta())));
      }

      logger.info(Constantes.LOG_PARAM, mensajeTrx, Constantes.INICIO_ACTIVIDAD_LOG, "3. Obtener Equipos SGA");
      EquipoRes responseEquipoSGA = sgaDao.listarEquipos(idTransaccion,
        request.getConsultarEquipoRequest().getCodigoCliente(),
        request.getConsultarEquipoRequest().getCodigoSucursal());
      logger.info(Constantes.LOG_PARAM, mensajeTrx, Constantes.FIN_ACTIVIDAD_LOG, "3. Obtener Equipos SGA");

      if (!responseEquipoSGA.getCodigoRespuesta().equals(Constantes.STRING_CERO)) {
        throw new WSException(prop.idf3CodigoConsultarEquipos,
          prop.idf3MensajeConsultarEquipos.replace(Constantes.DETALLE, responseEquipoSGA.getCodigoRespuesta()
            .concat(Constantes.GUION_ESPACIO).concat(responseEquipoSGA.getMensajeRespuesta())));
      }

      logger.info(Constantes.LOG_PARAM, mensajeTrx, Constantes.INICIO_ACTIVIDAD_LOG, "4. Validar Equipo");
      List<POCursorType> listaEquipoSGA = responseEquipoSGA.getPoCursor();

      boolean esValido = listaEquipoSGA.stream()
        .anyMatch(cursor -> request.getConsultarEquipoRequest().getNumeroSerie().equals(cursor.getMatvNroSerie()));
      logger.info(mensajeTrx + " VALIDAR EQUIPO: " + esValido);

      logger.info(Constantes.LOG_PARAM, mensajeTrx, Constantes.FIN_ACTIVIDAD_LOG, "4. Validar Equipo");

      if (!esValido || listaEquipoSGA.isEmpty()) {
        throw new WSException(prop.idf4CodigoConsultarEquipos, prop.idf4MensajeConsultarEquipos);
      }

      tokenPlumeClaroPlumeUnidos = utilTokens.subprocesoTokens(idTransaccion, header, mensajeTrx, prop,
        Constantes.S_CINCO + " " + "consultarEquipo", null, responseDatosClienteSGA);
      logger.info("TOKENS JUNTOS: " + tokenPlumeClaroPlumeUnidos);
      String[] tokens = tokenPlumeClaroPlumeUnidos.split(Constantes.SALTO_LINEA);
      tokenPlume = tokens[Constantes.CERO];
      tokenClaro = tokens[Constantes.UNO];
      logger.info("TOKENS_PLUME: " + tokenPlume);
      logger.info("TOKENS_CLARO: " + tokenClaro);

      logger.info(Constantes.LOG_PARAM, mensajeTrx, Constantes.INICIO_ACTIVIDAD_LOG, "6. Consultar Repetidor");

      ConsultarRepetidorResponse responseCR = nodeClient.consultarRepetidor(idTransaccion,
        prop.wsPlumeLocationConsultarRepetidorAuthorization, prop.nombreBearer + Constantes.ESPACIO + tokenPlume,
        tokenClaro, request.getConsultarEquipoRequest().getNumeroSerie());

      logger.info(Constantes.LOG_PARAM, mensajeTrx, Constantes.FIN_ACTIVIDAD_LOG, "6. Consultar Repetidor");

      if (!Objects.isNull(responseCR.getMessageResponse().getBody().getError())) {
        throw new WSException(prop.idf6CodigoConsultarEquipos, prop.idf6MensajeConsultarEquipos);
      }

      logger.info(Constantes.LOG_PARAM, mensajeTrx, Constantes.INICIO_ACTIVIDAD_LOG, "Fin del proceso - llenar respuesta final");

      this.armarResponse(response, responseType, responseAudit, responseData, responseCR, prop);

      logger.info(Constantes.LOG_PARAM, mensajeTrx, Constantes.FIN_ACTIVIDAD_LOG, "Fin del proceso - llenar respuesta final");

    } catch (BDException db) {
      logger.error(mensajeTrx + db.getMessage(), db);
      responseAudit.setCodigoRespuesta(db.getCode());
      responseAudit.setMensajeRespuesta(db.getMessage());
      responseType.setResponseAudit(responseAudit);
      response.setConsultarEquipoResponse(responseType);
    } catch (WSException e) {
      logger.error(mensajeTrx + e.getMessage(), e);
      responseAudit.setCodigoRespuesta(e.getCode());
      responseAudit.setMensajeRespuesta(e.getMessage());
      responseType.setResponseAudit(responseAudit);
      response.setConsultarEquipoResponse(responseType);
    } catch (Exception e) {
      logger.error(mensajeTrx + e.getMessage(), e);
      responseAudit.setCodigoRespuesta(prop.idt3Codigo);
      responseAudit.setMensajeRespuesta(String.format(prop.idt3Mensaje, e.getMessage()));
      responseType.setResponseAudit(responseAudit);
      response.setConsultarEquipoResponse(responseType);
    } finally {
      responseAudit.setIdTransaccion(header.getIdTransaccion());
      logger.info("RESPONSE: " + UtilJAXRS.anyObjectToPrettyJson(response));
      logger.info(mensajeTrx + "[FIN de metodo: " + nomMetodoActual + " ] Tiempo total de proceso(ms): "
        + (System.currentTimeMillis() - tiempoInicio) + " milisegundos.");
    }

    return response;
  }

  public void armarResponse(ConsultarEquipoResponse response, ConsultarEquipoResponseType responseType,
                            ResponseAuditType responseAudit, ConsultarEquipoResponseDataType responseData,
                            ConsultarRepetidorResponse responseCR, PropertiesExterno prop) {
    responseData.setIdCustPlume(responseCR.getMessageResponse().getBody().getCustomerId());
    responseData.setIdLocPlume(responseCR.getMessageResponse().getBody().getLocationId());
    responseAudit.setCodigoRespuesta(prop.idf0Codigo);
    responseAudit.setMensajeRespuesta(prop.idf0Mensaje);
    responseData.setEquipo(responseCR.getMessageResponse().getBody());
    responseType.setResponseData(responseData);
    responseType.setResponseAudit(responseAudit);
    response.setConsultarEquipoResponse(responseType);
  }

}
