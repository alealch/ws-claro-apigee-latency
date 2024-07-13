package pe.com.claro.post.activaciones.plume.integration.ws.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import pe.com.claro.post.activaciones.plume.common.constants.Constantes;
import pe.com.claro.post.activaciones.plume.common.exceptions.WSException;
import pe.com.claro.post.activaciones.plume.common.property.PropertiesExterno;
import pe.com.claro.post.activaciones.plume.common.util.ClaroUtil;
import pe.com.claro.post.activaciones.plume.common.util.UtilJAXRS;
import pe.com.claro.post.activaciones.plume.integration.ws.PlumeNodeClient;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.request.CrearRepetidorRequest;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.request.EliminarRepetidorRequest;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.ConsultarRepetidorResponse;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.CrearRepetidorResponse;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.EliminarRepetidorResponse;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.EliminarRepetidorBodyResponseDeserializer;

@Component
public class PlumeNodeClientImpl implements PlumeNodeClient {

  private static final Logger logger = LoggerFactory.getLogger(PlumeLocationClientImpl.class);

  @Autowired
  PropertiesExterno prop;

  @Override
  public CrearRepetidorResponse crearRepetidor(String idTransaccion, CrearRepetidorRequest request, String tokenBasic,
                                               String tokenPlume, String tokenClaro, String accountId, String idLocation) throws WSException {
    long tiempoInicio = System.currentTimeMillis();

    CrearRepetidorResponse tokenResponse = new CrearRepetidorResponse();
    String mensajeMetodo = idTransaccion + " [CrearRepetidor] ";

    String url = prop.wsUrlPlumeNodeCrearRepetidor;
    String nombreWS = prop.wsPlumeNodeCrearRepetidorNombre;
    String nombreMetodo = prop.wsPlumeNodeCrearRepetidorMetodo;
    try {

      Gson inputGson = new Gson();
      String inputJson = inputGson.toJson(request, CrearRepetidorRequest.class);

      logger.info(mensajeMetodo + Constantes.SERVICIO + nombreWS);
      logger.info(mensajeMetodo + Constantes.METODO + nombreMetodo);
      logger.info(mensajeMetodo + Constantes.URL_SERVICIO_A_INVOCAR + url);
      logger.info(mensajeMetodo + Constantes.TIMEOUT_CONNECT + prop.wsPlumeNodeCrearRepetidorTimeoutConnect);
      logger.info(mensajeMetodo + Constantes.TIMEOUT_REQUEST + prop.wsPlumeNodeCrearRepetidorTimeoutEjecucion);
      logger.info(mensajeMetodo + " JSON: " + UtilJAXRS.formatJson(inputJson));

      Client client = Client.create();
      String urlFinal = url.replace(Constantes.IDCUSTPLUME, accountId).replace(Constantes.ID_LOCPLUME, idLocation);

      client.setConnectTimeout(Integer.parseInt(prop.wsPlumeNodeCrearRepetidorTimeoutConnect));
      client.setReadTimeout(Integer.parseInt(prop.wsPlumeNodeCrearRepetidorTimeoutEjecucion));
      logger.info(mensajeMetodo + Constantes.URL_ARMADO + urlFinal);

      WebResource webResourceCrearRep = client.resource(urlFinal);

      ClientResponse responseCrearRep = null;

      logger.info(mensajeMetodo + Constantes.SALTO_LINEA + "[" + Constantes.NAMEHEADERAPPCONTENTTYPE
        + Constantes.STRING_DOSPUNTOS + prop.wsDpPlumeContentType + "]" + Constantes.SALTO_LINEA + "["
        + Constantes.NAMEHEADERAPPAUTHORIZATION + Constantes.STRING_DOSPUNTOS + tokenBasic + "]"
        + Constantes.SALTO_LINEA + "[" + Constantes.NAMEHEADERAPPACCESSTOKEN + Constantes.STRING_DOSPUNTOS
        + tokenClaro + "]" + Constantes.SALTO_LINEA + "[" + Constantes.NAMEHEADERTOKENPLUME
        + Constantes.STRING_DOSPUNTOS + tokenPlume + "]");

      responseCrearRep = headerAuthorizationPost(tokenBasic, tokenPlume, tokenClaro, inputJson, webResourceCrearRep);

      if (responseCrearRep != null) {

        logger.info(mensajeMetodo + Constantes.STATUS + responseCrearRep.getStatus());

        String rpta = responseCrearRep.getEntity(String.class);
        logger.info(mensajeMetodo + Constantes.PARAMETROS_SALIDA + UtilJAXRS.formatJson(rpta));

        if (responseCrearRep.getStatus() == 200) {
          tokenResponse = new CrearRepetidorResponse();
          Gson gson = new Gson();
          tokenResponse = gson.fromJson(rpta, CrearRepetidorResponse.class);

        } else {
          logger.info(mensajeMetodo + " Error en el Servicio WS CrearRepetidor Error:" + responseCrearRep.getStatus());

          Gson gson = new Gson();
          tokenResponse = gson.fromJson(rpta, CrearRepetidorResponse.class);

          logger.info(mensajeMetodo + Constantes.PARAMETROS_SALIDA_ERROR
            + UtilJAXRS.anyObjectToPrettyJson(tokenResponse.getMessageResponse().getBody().getError()));
        }
      }

    } catch (Exception e) {
      logger.error(
        mensajeMetodo + Constantes.EXCEPCION_EN_WS + "CrearRepetidor - WS" + "]: [" + e.getMessage() + "]", e);
      ClaroUtil.capturarErrorWS(e, nombreWS, nombreMetodo, prop);
    } finally {
      logger.info(mensajeMetodo + Constantes.TIEMPOTOTALPROCESO + (System.currentTimeMillis() - tiempoInicio)
        + Constantes.MILISEG);
      logger.info(mensajeMetodo + " [FIN] - METODO: [CrearRepetidor - WS] ");
    }
    return tokenResponse;
  }

  @Override
  public EliminarRepetidorResponse eliminarRepetidor(String idTransaccion, EliminarRepetidorRequest request,
                                                     String tokenBasic, String tokenPlume, String tokenClaro) throws WSException {
    long tiempoInicio = System.currentTimeMillis();
    EliminarRepetidorResponse tokenResponse = new EliminarRepetidorResponse();
    String mensajeMetodo = idTransaccion + " [EliminarRepetidor] ";

    String url = prop.wsUrlPlumeNodeEliminarRepetidor;
    String nombreWS = prop.wsPlumeNodeEliminarRepetidorNombre;
    String nombreMetodo = prop.wsPlumeNodeEliminarRepetidorMetodo;

    try {

      logger.info(mensajeMetodo + Constantes.SERVICIO + nombreWS);
      logger.info(mensajeMetodo + Constantes.METODO + nombreMetodo);
      logger.info(mensajeMetodo + Constantes.URL_SERVICIO_A_INVOCAR + url);
      logger.info(mensajeMetodo + Constantes.TIMEOUT_CONNECT + prop.wsPlumeNodeEliminarRepetidorTimeoutConnect);
      logger.info(mensajeMetodo + Constantes.TIMEOUT_REQUEST + prop.wsPlumeNodeEliminarRepetidorTimeoutEjecucion);

      Client client = Client.create();
      String urlFinal = url.replace(Constantes.IDCUSTPLUME, request.getAccountId())
        .replace(Constantes.ID_LOCPLUME, request.getIdLocation())
        .replace(Constantes.SERIAL_NUMBER, request.getSerialNumber());

      client.setConnectTimeout(Integer.parseInt(prop.wsPlumeNodeEliminarRepetidorTimeoutConnect));
      client.setReadTimeout(Integer.parseInt(prop.wsPlumeNodeEliminarRepetidorTimeoutEjecucion));
      logger.info(mensajeMetodo + Constantes.URL_ARMADO + urlFinal);

      WebResource webResourceElimRep = client.resource(urlFinal);

      ClientResponse responseElimRep = null;

      logger.info(mensajeMetodo + Constantes.SALTO_LINEA + "[" + Constantes.NAMEHEADERAPPCONTENTTYPE
        + Constantes.STRING_DOSPUNTOS + prop.wsDpPlumeContentType + "]" + Constantes.SALTO_LINEA + "["
        + Constantes.NAMEHEADERAPPAUTHORIZATION + Constantes.STRING_DOSPUNTOS + tokenBasic + "]"
        + Constantes.SALTO_LINEA + "[" + Constantes.NAMEHEADERAPPACCESSTOKEN + Constantes.STRING_DOSPUNTOS
        + tokenClaro + "]" + Constantes.SALTO_LINEA + "[" + Constantes.NAMEHEADERTOKENPLUME
        + Constantes.STRING_DOSPUNTOS + tokenPlume + "]");

      responseElimRep = headerAuthorizationDelete(tokenBasic, tokenPlume, tokenClaro, webResourceElimRep);

      if (responseElimRep != null) {

        logger.info(mensajeMetodo + Constantes.STATUS + responseElimRep.getStatus());

        String rpta = responseElimRep.getEntity(String.class);
        logger.info(mensajeMetodo + Constantes.PARAMETROS_SALIDA + UtilJAXRS.formatJson(rpta));

        if (responseElimRep.getStatus() == 200) {
          tokenResponse = new EliminarRepetidorResponse();
          GsonBuilder gsonBuilder = new GsonBuilder();
          gsonBuilder.registerTypeAdapter(EliminarRepetidorResponse.class,
            new EliminarRepetidorBodyResponseDeserializer());
          Gson gson = gsonBuilder.create();
          tokenResponse = gson.fromJson(rpta, EliminarRepetidorResponse.class);
        } else {
          logger.info(mensajeMetodo + " Error en el Servicio WS EliminarRepetidor Error:" + responseElimRep.getStatus());

          Gson gson = new Gson();
          tokenResponse = gson.fromJson(rpta, EliminarRepetidorResponse.class);

          logger.info(mensajeMetodo + Constantes.PARAMETROS_SALIDA_ERROR
            + UtilJAXRS.anyObjectToPrettyJson(tokenResponse.getMessageResponse().getBody().getError()));
        }
      }

    } catch (Exception e) {
      logger.error(
        mensajeMetodo + Constantes.EXCEPCION_EN_WS + "EliminarRepetidor - WS" + "]: [" + e.getMessage() + "]",
        e);
      ClaroUtil.capturarErrorWS(e, nombreWS, nombreMetodo, prop);
    } finally {
      logger.info(mensajeMetodo + Constantes.TIEMPOTOTALPROCESO + (System.currentTimeMillis() - tiempoInicio)
        + Constantes.MILISEG);
      logger.info(mensajeMetodo + " [FIN] - METODO: [EliminarRepetidor - WS] ");
    }
    return tokenResponse;
  }

  @Override
  public ConsultarRepetidorResponse consultarRepetidor(String idTransaccion, String tokenBasic, String tokenPlume,
                                                       String tokenClaro, String serialNumber) throws WSException {
    long tiempoInicio = System.currentTimeMillis();

    ConsultarRepetidorResponse tokenResponse = new ConsultarRepetidorResponse();
    String mensajeMetodoConsRep = idTransaccion + " [ConsultarRepetidor] ";

    String url = prop.wsUrlPlumeNodeConsultarRepetidor;
    String nombreWS = prop.wsPlumeNodeConsultarRepetidorNombre;
    String nombreMetodo = prop.wsPlumeNodeConsultarRepetidorMetodo;

    try {
      logger.info(mensajeMetodoConsRep + Constantes.SERVICIO + nombreWS);
      logger.info(mensajeMetodoConsRep + Constantes.METODO + nombreMetodo);
      logger.info(mensajeMetodoConsRep + Constantes.URL_SERVICIO_A_INVOCAR + url);
      logger.info(mensajeMetodoConsRep + Constantes.TIMEOUT_CONNECT + prop.wsPlumeNodeConsultarRepetidorTimeoutConnect);
      logger.info(mensajeMetodoConsRep + Constantes.TIMEOUT_REQUEST + prop.wsPlumeNodeConsultarRepetidorTimeoutEjecucion);

      Client client = Client.create();
      String urlFinal = url.replace(Constantes.SERIAL_NUMBER, serialNumber);

      client.setConnectTimeout(Integer.parseInt(prop.wsPlumeNodeConsultarRepetidorTimeoutConnect));
      client.setReadTimeout(Integer.parseInt(prop.wsPlumeNodeConsultarRepetidorTimeoutEjecucion));
      logger.info(mensajeMetodoConsRep + Constantes.URL_ARMADO + urlFinal);

      WebResource webResource = client.resource(urlFinal);
      ClientResponse responseConsRep = null;

      logger.info(mensajeMetodoConsRep + Constantes.SALTO_LINEA + "[" + Constantes.NAMEHEADERAPPCONTENTTYPE
        + Constantes.STRING_DOSPUNTOS + prop.wsDpPlumeContentType + "]" + Constantes.SALTO_LINEA + "["
        + Constantes.NAMEHEADERAPPAUTHORIZATION + Constantes.STRING_DOSPUNTOS + tokenBasic + "]"
        + Constantes.SALTO_LINEA + "[" + Constantes.NAMEHEADERAPPACCESSTOKEN + Constantes.STRING_DOSPUNTOS
        + tokenClaro + "]" + Constantes.SALTO_LINEA + "[" + Constantes.NAMEHEADERTOKENPLUME
        + Constantes.STRING_DOSPUNTOS + tokenPlume + "]");

      responseConsRep = headerAuthorizationGet(tokenBasic, tokenPlume, tokenClaro, webResource);

      if (responseConsRep != null) {

        logger.info(mensajeMetodoConsRep + Constantes.STATUS + responseConsRep.getStatus());

        String rpta = responseConsRep.getEntity(String.class);
        logger.info(mensajeMetodoConsRep + Constantes.PARAMETROS_SALIDA + UtilJAXRS.formatJson(rpta));

        if (responseConsRep.getStatus() == 200) {
          tokenResponse = new ConsultarRepetidorResponse();
          Gson gson = new Gson();
          tokenResponse = gson.fromJson(rpta, ConsultarRepetidorResponse.class);

        } else {
          logger.info(mensajeMetodoConsRep + " Error en el Servicio WS ConsultarRepetidor Error:" + responseConsRep.getStatus());

          Gson gson = new Gson();
          tokenResponse = gson.fromJson(rpta, ConsultarRepetidorResponse.class);

          logger.info(mensajeMetodoConsRep + Constantes.PARAMETROS_SALIDA_ERROR
            + UtilJAXRS.anyObjectToPrettyJson(tokenResponse.getMessageResponse().getBody().getError()));
        }
      }
    } catch (Exception e) {
      logger.error(
        mensajeMetodoConsRep + Constantes.EXCEPCION_EN_WS + "ConsultarRepetidor - WS" + "]: [" + e.getMessage() + "]",
        e);
      ClaroUtil.capturarErrorWS(e, nombreWS, nombreMetodo, prop);
    } finally {
      logger.info(mensajeMetodoConsRep + Constantes.TIEMPOTOTALPROCESO + (System.currentTimeMillis() - tiempoInicio)
        + Constantes.MILISEG);
      logger.info(mensajeMetodoConsRep + " [FIN] - METODO: [ConsultarRepetidor - WS] ");
    }
    return tokenResponse;
  }

  private ClientResponse headerAuthorizationGet(String tokenBasic, String tokenPlume, String tokenClaro,
                                                WebResource webResource) {
    ClientResponse response;
    response = webResource.accept(Constantes.VALUEAPPLICATIONJSON)
      .header(Constantes.NAMEHEADERAPPCONTENTTYPE, prop.wsDpPlumeContentType)
      .header(Constantes.NAMEHEADERAPPAUTHORIZATION, tokenBasic)
      .header(Constantes.NAMEHEADERAPPACCESSTOKEN, tokenClaro).header(Constantes.NAMEHEADERTOKENPLUME, tokenPlume)
      .get(ClientResponse.class);
    return response;
  }

  private ClientResponse headerAuthorizationPost(String tokenBasic, String tokenPlume, String tokenClaro,
                                                 String inputJson, WebResource webResource) {
    ClientResponse response;
    response = webResource.accept(Constantes.VALUEAPPLICATIONJSON)
      .header(Constantes.NAMEHEADERAPPCONTENTTYPE, prop.wsDpPlumeContentType)
      .header(Constantes.NAMEHEADERAPPAUTHORIZATION, tokenBasic)
      .header(Constantes.NAMEHEADERAPPACCESSTOKEN, tokenClaro).header(Constantes.NAMEHEADERTOKENPLUME, tokenPlume)
      .post(ClientResponse.class, inputJson);
    return response;
  }

  private ClientResponse headerAuthorizationDelete(String tokenBasic, String tokenPlume, String tokenClaro,
                                                   WebResource webResource) {
    ClientResponse response;
    response = webResource.accept(Constantes.VALUEAPPLICATIONJSON)
      .header(Constantes.NAMEHEADERAPPCONTENTTYPE, prop.wsDpPlumeContentType)
      .header(Constantes.NAMEHEADERAPPAUTHORIZATION, tokenBasic)
      .header(Constantes.NAMEHEADERAPPACCESSTOKEN, tokenClaro).header(Constantes.NAMEHEADERTOKENPLUME, tokenPlume)
      .delete(ClientResponse.class);
    return response;
  }
}
