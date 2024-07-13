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
import pe.com.claro.post.activaciones.plume.canonical.request.ObtenerEquiposRequest;
import pe.com.claro.post.activaciones.plume.canonical.response.ObtenerEquiposResponse;
import pe.com.claro.post.activaciones.plume.canonical.response.ObtenerEquiposResponseType;
import pe.com.claro.post.activaciones.plume.canonical.type.ListaEquiposOEType;
import pe.com.claro.post.activaciones.plume.canonical.type.ObtenerEquiposResponseDataType;
import pe.com.claro.post.activaciones.plume.canonical.type.ResponseAuditType;
import pe.com.claro.post.activaciones.plume.common.constants.Constantes;
import pe.com.claro.post.activaciones.plume.common.exceptions.BDException;
import pe.com.claro.post.activaciones.plume.common.exceptions.BaseException;
import pe.com.claro.post.activaciones.plume.common.exceptions.WSException;
import pe.com.claro.post.activaciones.plume.common.property.PropertiesExterno;
import pe.com.claro.post.activaciones.plume.common.util.UtilJAXRS;
import pe.com.claro.post.activaciones.plume.domain.service.ObtenerEquiposPlumeService;
import pe.com.claro.post.activaciones.plume.integration.dao.SgaDao;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.request.DatosClienteBeanReq;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.response.DatosClienteBeanRes;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.response.EquipoRes;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.type.CursorValidaCuenta;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.type.POCursorType;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.utiltarios.UtilTokens;
import pe.com.claro.post.activaciones.plume.integration.ws.PlumeLocationClient;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.ObtenerRepetidorResponse;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.Node;

@Component
public class ObtenerEquiposPlumeServiceImpl implements ObtenerEquiposPlumeService {

  private static final Logger logger = LoggerFactory.getLogger(ObtenerLocacionesPlumeServiceImpl.class);

  @Autowired
  private PropertiesExterno prop;

  @Autowired
  private SgaDao sgaDao;

  @Autowired
  private PlumeLocationClient locationClientePlume;

  @Autowired
  private UtilTokens UtilTokens;

  @Override
  public ObtenerEquiposResponse obtenerEquipos(String idTransaccion, HeaderRequest header,
                                               ObtenerEquiposRequest request) {

    String nomMetodoActual = Thread.currentThread().getStackTrace()[1].getMethodName();
    long tiempoInicio = System.currentTimeMillis();
    String mensajeTrx = "[obtenerEquipos idTx=" + idTransaccion + "] ";
    ObtenerEquiposResponse response = new ObtenerEquiposResponse();
    ObtenerEquiposResponseType responseType = new ObtenerEquiposResponseType();
    ResponseAuditType responseAudit = new ResponseAuditType();
    ObtenerEquiposResponseDataType responseData = new ObtenerEquiposResponseDataType();

    String tokenClaro = Constantes.STRING_VACIO;
    String tokenPlume = Constantes.STRING_VACIO;
    String tokenPlumeTokenClaroUnidos = Constantes.STRING_VACIO;
    try {

      logger.info(mensajeTrx + "HEADER REQUEST: " + UtilJAXRS.anyObjectToPrettyJson(header));
      logger.info(mensajeTrx + "BODY REQUEST: " + UtilJAXRS.anyObjectToPrettyJson(request));

      logger.info(Constantes.LOG_PARAM, mensajeTrx, Constantes.INICIO_ACTIVIDAD_LOG, "1. Validar Parametros Obligatorios");
      validateInput(request);
      logger.info(mensajeTrx + " Parametros de entrada validos.....!");
      logger.info(Constantes.LOG_PARAM, mensajeTrx, Constantes.FIN_ACTIVIDAD_LOG, "1. Validar Parametros Obligatorios");

      DatosClienteBeanRes responseDatosClienteSGA = obtenerDatosSGA(idTransaccion, request, mensajeTrx);

      logger.info(Constantes.LOG_PARAM, idTransaccion, Constantes.INICIO_ACTIVIDAD_LOG, "4. Validar Locacion");

      String idLocPlume = Constantes.VACIO;
      String idCustPlume = Constantes.VACIO;
      boolean esValido = false;
      List<CursorValidaCuenta> cursorSGA = responseDatosClienteSGA.getCursor();
      for (CursorValidaCuenta cursorValidaCuenta : cursorSGA) {
        if (responseDatosClienteSGA.getCodigoSucursal().equals(cursorValidaCuenta.getCodSuc())) {
          idLocPlume = cursorValidaCuenta.getLocation();
          idCustPlume = responseDatosClienteSGA.getCodigoClientePlm();
          esValido = true;
        }
      }

      logger.info("validando esValido : " + esValido);
      logger.info(Constantes.LOG_PARAM, idTransaccion, Constantes.FIN_ACTIVIDAD_LOG, "4. Validar Locacion");

      if (!esValido) {
        throw new WSException(prop.idf4CodigoObtenerEquipos, prop.idf4MensajeObtenerEquipos);
      }
      logger.info(Constantes.LOG_PARAM, idTransaccion, Constantes.INICIO_ACTIVIDAD_LOG, "5. Obtener Equipos SGA");

      EquipoRes respEquipos = sgaDao.listarEquipos(idTransaccion, responseDatosClienteSGA.getCodigoCliente(),
        responseDatosClienteSGA.getCodigoSucursal());
      logger.info(Constantes.LOG_PARAM, idTransaccion, Constantes.FIN_ACTIVIDAD_LOG, "5. Obtener Equipos SGA");

      if (!Constantes.S_CERO.equals(respEquipos.getCodigoRespuesta())) {
        throw new WSException(prop.idf5CodigoObtenerEquipos, prop.idf5MensajeObtenerEquipos);
      }

      tokenPlumeTokenClaroUnidos = UtilTokens.subprocesoTokens(idTransaccion, header, mensajeTrx, prop,
        Constantes.S_SEIS, null, null);
      logger.info("TOKENS JUNTOS: " + tokenPlumeTokenClaroUnidos);
      String[] tokens = tokenPlumeTokenClaroUnidos.split(Constantes.SALTO_LINEA);
      tokenPlume = tokens[Constantes.CERO];
      tokenClaro = tokens[Constantes.UNO];
      logger.info("TOKENS_PLUME: " + tokenPlume);
      logger.info("TOKENS_CLARO: " + tokenClaro);

      logger.info(Constantes.LOG_PARAM, idTransaccion, Constantes.INICIO_ACTIVIDAD_LOG, "7. Obtener Repetidores");

      ObtenerRepetidorResponse responseClientePlume = locationClientePlume.obtenerRepetidor(idTransaccion,
        prop.wsPlumeLocationObtenerRepetidorAuthorization, prop.nombreBearer + Constantes.ESPACIO + tokenPlume,
        tokenClaro, idCustPlume, idLocPlume);

      logger.info(Constantes.LOG_PARAM, idTransaccion, Constantes.FIN_ACTIVIDAD_LOG, "7. Obtener Repetidores");

      validarObtenerRepetidorResponse(responseClientePlume);

      logger.info(Constantes.LOG_PARAM, idTransaccion, Constantes.INICIO_ACTIVIDAD_LOG, "8. Filtrar Resultados");

      List<Node> listaPLUME = responseClientePlume.getMessageResponse().getBody().getNodes();
      List<POCursorType> listaSGA = respEquipos.getPoCursor();

      logger.info(idTransaccion + " tamanioLISTASGA " + listaSGA.size());
      logger.info(idTransaccion + " tamanioLISTAPLUME " + listaPLUME.size());

      List<ListaEquiposOEType> listaFiltroNode = new ArrayList<>();
      ListaEquiposOEType objFiltroNode = getListaEquiposOEType(listaPLUME, listaSGA, listaFiltroNode);
      logger.info(Constantes.LOG_PARAM, idTransaccion, Constantes.FIN_ACTIVIDAD_LOG, "8. Filtrar Resultados");

      if (Objects.isNull(objFiltroNode) && listaFiltroNode.size() <= 0) {
        throw new WSException(prop.idf8CodigoObtenerEquipos, prop.idf8MensajeObtenerEquipos);
      }

      this.armarResponse(response, responseType, responseAudit, responseData, responseDatosClienteSGA, listaFiltroNode);

    } catch (WSException e) {
      logger.error(mensajeTrx + e.getMessage(), e);
      responseAudit.setCodigoRespuesta(e.getCode());
      responseAudit.setMensajeRespuesta(e.getMessage());
      responseType.setResponseAudit(responseAudit);
      response.setObtenerEquiposResponse(responseType);
    } catch (BDException e) {
      logger.error(mensajeTrx + e.getMessage(), e);
      responseAudit.setCodigoRespuesta(e.getCode());
      responseAudit.setMensajeRespuesta(e.getMessage());
      responseType.setResponseAudit(responseAudit);
      response.setObtenerEquiposResponse(responseType);
    } catch (Exception e) {
      logger.error(mensajeTrx + e.getMessage(), e);
      responseAudit.setCodigoRespuesta(prop.idt3Codigo);
      responseAudit.setMensajeRespuesta(String.format(prop.idt3Mensaje, e.getMessage()));
      responseType.setResponseAudit(responseAudit);
      response.setObtenerEquiposResponse(responseType);
    } finally {
      responseAudit.setIdTransaccion(header.getIdTransaccion());
      logger.info("RESPONSE: " + UtilJAXRS.anyObjectToPrettyJson(response));
      logger.info(mensajeTrx + "[FIN de metodo: " + nomMetodoActual + " ] Tiempo total de proceso(ms): "
        + (System.currentTimeMillis() - tiempoInicio) + " milisegundos.");
    }

    return response;
  }

  public ListaEquiposOEType getListaEquiposOEType(List<Node> listaPLUME, List<POCursorType> listaSGA, List<ListaEquiposOEType> listaFiltroNode) {
    ListaEquiposOEType objFiltroNode = null;
    for (int i = 0; i < listaSGA.size(); i++) {
      for (int j = 0; j < listaPLUME.size(); j++) {
        if (listaSGA.get(i).getMatvNroSerie().equals(listaPLUME.get(j).getId())) {
          objFiltroNode = new ListaEquiposOEType();

          objFiltroNode.setId(listaPLUME.get(j).getId());
          objFiltroNode.setNumeroSerie(listaPLUME.get(j).getSerialNumber());
          objFiltroNode.setModelo(listaPLUME.get(j).getModel());
          objFiltroNode.setMac(listaPLUME.get(j).getMac());
          objFiltroNode.setEstadoConexion(listaPLUME.get(j).getConnectionState());
          listaFiltroNode.add(objFiltroNode);
        }
      }
    }
    return objFiltroNode;
  }

  public void validarObtenerRepetidorResponse(ObtenerRepetidorResponse responseClientePlume) throws WSException {
    if (!Constantes.S_CERO
      .equals(responseClientePlume.getMessageResponse().getHeader().getHeaderResponse().getStatus().getCode())) {
      throw new WSException(prop.idf7Codigo, prop.idf7Mensaje.replace(Constantes.DETALLE,
        responseClientePlume.getMessageResponse().getBody().getFault().getDetail().getIntegrationError()));
    }

    if (Objects.isNull(responseClientePlume.getMessageResponse().getBody().getNodes())
      || responseClientePlume.getMessageResponse().getBody().getNodes().size() <= 0) {
      throw new WSException(prop.idf8CodigoObtenerEquipos, prop.idf8MensajeObtenerEquipos);
    }
  }

  public void armarResponse(ObtenerEquiposResponse response, ObtenerEquiposResponseType responseType,
                            ResponseAuditType responseAudit, ObtenerEquiposResponseDataType responseData,
                            DatosClienteBeanRes resDatosClienteSGA, List<ListaEquiposOEType> listaFiltroNode) {
    responseData.setAccountId(resDatosClienteSGA.getAccountId());
    responseData.setCodigoSucursal(resDatosClienteSGA.getCodigoSucursal());
    responseData.setNombreSucursal(resDatosClienteSGA.getNombreSurcursal());
    responseData.setEquipos(listaFiltroNode);
    responseType.setResponseData(responseData);
    responseAudit.setCodigoRespuesta(prop.idf0Codigo);
    responseAudit.setMensajeRespuesta(prop.idf0Mensaje);
    responseType.setResponseAudit(responseAudit);
    response.setObtenerEquiposResponse(responseType);
  }

  public DatosClienteBeanRes obtenerDatosSGA(String idTransaccion, ObtenerEquiposRequest request, String mensajeTrx)
    throws BDException {
    logger.info(Constantes.LOG_PARAM, mensajeTrx, Constantes.INICIO_ACTIVIDAD_LOG, "2. Obtener Datos");
    DatosClienteBeanReq requeDatosCl = new DatosClienteBeanReq();
    requeDatosCl.setNroSot(request.getObtenerEquiposRequest().getNumeroSot());
    DatosClienteBeanRes resDatosCliente = sgaDao.datosCliente(idTransaccion, requeDatosCl);
    logger.info(Constantes.LOG_PARAM, mensajeTrx, Constantes.FIN_ACTIVIDAD_LOG, "2. Obtener Datos");

    if (resDatosCliente.getCodigoRespuesta().equals(Constantes.S_CIEN) && Objects.isNull(resDatosCliente.getCursor())) {
      throw new BDException(prop.idf2CodigoObtenerEquipos,
        prop.idf2MensajeObtenerEquipos.replace(Constantes.DETALLE, resDatosCliente.getCodigoRespuesta()
          .concat(Constantes.GUION_ESPACIO).concat(resDatosCliente.getMensajeRespuesta())));
    }
    return resDatosCliente;
  }

  public void validateInput(ObtenerEquiposRequest request) throws WSException {
    if (StringUtils.isBlank(request.getObtenerEquiposRequest().getNumeroSot())) {
      throw new WSException(prop.idf1Codigo, prop.idf1Mensaje.replace(Constantes.DETALLE, "numeroSot"));
    }
  }

}
