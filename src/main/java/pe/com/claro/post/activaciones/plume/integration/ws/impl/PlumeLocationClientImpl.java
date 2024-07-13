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
import pe.com.claro.post.activaciones.plume.integration.ws.PlumeLocationClient;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.request.ConfigNivelRequest;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.request.CrearUbicacionRequest;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.ConfigNivelResponse;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.CrearUbicacionResponse;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.EliminarUbicacionBodyResponse;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.EliminarUbicacionResponse;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.ObtenerRepetidorResponse;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.EliminarUbicacionBodyResponseDeserializer;

@Component
public class PlumeLocationClientImpl implements PlumeLocationClient {
  private static final Logger logger = LoggerFactory.getLogger(PlumeLocationClientImpl.class);

  @Autowired
  PropertiesExterno prop;

  @Override
  public CrearUbicacionResponse crearUbicacion(String idTransaccion, CrearUbicacionRequest request, String tokenBasic,
                                               String tokenPlume, String tokenClaro, String accountId) throws WSException {
    long tiempoInicio = System.currentTimeMillis();

    String mensajeMetodo = idTransaccion + " [crearUbicacion] ";
    CrearUbicacionResponse tokenResponse = new CrearUbicacionResponse();
    String url = prop.wsUrlPlumeLocationCrearUbi;
    String nombreWS = prop.wsPlumeLocationCrearUbiNombre;
    String nombreMetodo = prop.wsPlumeLocationCrearUbiMetodo;
    try {
      Gson inputGson = new Gson();
      String inputJson = inputGson.toJson(request, CrearUbicacionRequest.class);

      logger.info(mensajeMetodo + Constantes.SERVICIO + nombreWS);
      logger.info(mensajeMetodo + Constantes.METODO + nombreMetodo);
      logger.info(mensajeMetodo + Constantes.URL_SERVICIO_A_INVOCAR + url);
      logger.info(mensajeMetodo + Constantes.TIMEOUT_CONNECT + prop.wsPlumeLocationCrearUbiTimeoutConnect);
      logger.info(mensajeMetodo + Constantes.TIMEOUT_REQUEST + prop.wsPlumeLocationCrearUbiTimeoutEjecucion);
      logger.info(mensajeMetodo + " JSON: " + UtilJAXRS.formatJson(inputJson));

      Client client = Client.create();
      String urlFinal = url.replace(Constantes.IDCUSTPLUME, accountId);

      client.setConnectTimeout(Integer.parseInt(prop.wsPlumeLocationCrearUbiTimeoutConnect));
      client.setReadTimeout(Integer.parseInt(prop.wsPlumeLocationCrearUbiTimeoutEjecucion));
      logger.info(mensajeMetodo + Constantes.URL_ARMADO + urlFinal);

      WebResource webResource = client.resource(urlFinal);

      logger.info(mensajeMetodo + Constantes.SALTO_LINEA + "[" + Constantes.NAMEHEADERAPPCONTENTTYPE
        + Constantes.STRING_DOSPUNTOS + prop.wsDpPlumeContentType + "]" + Constantes.SALTO_LINEA + "["
        + Constantes.NAMEHEADERAPPAUTHORIZATION + Constantes.STRING_DOSPUNTOS + tokenBasic + "]"
        + Constantes.SALTO_LINEA + "[" + Constantes.NAMEHEADERAPPACCESSTOKEN + Constantes.STRING_DOSPUNTOS
        + tokenClaro + "]" + Constantes.SALTO_LINEA + "[" + Constantes.NAMEHEADERTOKENPLUME
        + Constantes.STRING_DOSPUNTOS + tokenPlume + "]");

      ClientResponse responseCrearUb = null;

      responseCrearUb = headerAuthorizationPost(tokenBasic, tokenPlume, tokenClaro, inputJson, webResource);

      if (responseCrearUb != null) {

        logger.info(mensajeMetodo + Constantes.STATUS + responseCrearUb.getStatus());

        String rpta = responseCrearUb.getEntity(String.class);
        logger.info(mensajeMetodo + Constantes.PARAMETROS_SALIDA + UtilJAXRS.formatJson(rpta));

        if (responseCrearUb.getStatus() == 200) {
          tokenResponse = new CrearUbicacionResponse();
          Gson gson = new Gson();
          tokenResponse = gson.fromJson(rpta, CrearUbicacionResponse.class);

        } else {
          logger.info(mensajeMetodo + " Error en el Servicio WS CrearUbicacion Error:" + responseCrearUb.getStatus());

          Gson gson = new Gson();
          tokenResponse = gson.fromJson(rpta, CrearUbicacionResponse.class);

          logger.info(mensajeMetodo + Constantes.PARAMETROS_SALIDA_ERROR
            + UtilJAXRS.anyObjectToPrettyJson(tokenResponse.getMessageResponse().getBody().getError()));
        }
      }
    } catch (Exception e) {
      logger.error(
        mensajeMetodo + Constantes.EXCEPCION_EN_WS + "CrearUbicacion - WS" + "]: [" + e.getMessage() + "]", e);
      ClaroUtil.capturarErrorWS(e, nombreWS, nombreMetodo, prop);
    } finally {
      logger.info(mensajeMetodo + Constantes.TIEMPOTOTALPROCESO + (System.currentTimeMillis() - tiempoInicio)
        + Constantes.MILISEG);
      logger.info(mensajeMetodo + " [FIN] - METODO: [CrearUbicacion - WS] ");
    }
    return tokenResponse;
  }

  @Override
  public ConfigNivelResponse configNivelServicio(String idTransaccion, ConfigNivelRequest request, String tokenBasic,
                                                 String tokenPlume, String tokenClaro, String accountId, String idLocation) throws WSException {
    long tiempoInicio = System.currentTimeMillis();

    ConfigNivelResponse tokenResponse = new ConfigNivelResponse();
    String mensajeMetodo = idTransaccion + " [ConfigNivelServicio] ";

    String url = prop.wsUrlPlumeLocationConfigNivel;
    String nombreWS = prop.wsPlumeLocationConfigNivelNombre;
    String nombreMetodo = prop.wsPlumeLocationConfigNivelMetodo;
    try {

      Gson inputGson = new Gson();
      String inputJson = inputGson.toJson(request, ConfigNivelRequest.class);

      logger.info(mensajeMetodo + Constantes.SERVICIO + nombreWS);
      logger.info(mensajeMetodo + Constantes.METODO + nombreMetodo);
      logger.info(mensajeMetodo + Constantes.URL_SERVICIO_A_INVOCAR + url);
      logger.info(mensajeMetodo + Constantes.TIMEOUT_CONNECT + prop.wsPlumeLocationConfigNivelTimeoutConnect);
      logger.info(mensajeMetodo + Constantes.TIMEOUT_REQUEST + prop.wsPlumeLocationConfigNivelTimeoutEjecucion);
      logger.info(mensajeMetodo + " JSON: " + UtilJAXRS.formatJson(inputJson));

      Client client = Client.create();
      String urlFinal = url.replace(Constantes.IDCUSTPLUME, accountId).replace(Constantes.ID_LOCPLUME, idLocation);

      client.setConnectTimeout(Integer.parseInt(prop.wsPlumeLocationConfigNivelTimeoutConnect));
      client.setReadTimeout(Integer.parseInt(prop.wsPlumeLocationConfigNivelTimeoutEjecucion));
      logger.info(mensajeMetodo + Constantes.URL_ARMADO + urlFinal);

      WebResource webResource = client.resource(urlFinal);

      ClientResponse response = null;

      logger.info(mensajeMetodo + Constantes.SALTO_LINEA + "[" + Constantes.NAMEHEADERAPPCONTENTTYPE
        + Constantes.STRING_DOSPUNTOS + prop.wsDpPlumeContentType + "]" + Constantes.SALTO_LINEA + "["
        + Constantes.NAMEHEADERAPPAUTHORIZATION + Constantes.STRING_DOSPUNTOS + tokenBasic + "]"
        + Constantes.SALTO_LINEA + "[" + Constantes.NAMEHEADERAPPACCESSTOKEN + Constantes.STRING_DOSPUNTOS
        + tokenClaro + "]" + Constantes.SALTO_LINEA + "[" + Constantes.NAMEHEADERTOKENPLUME
        + Constantes.STRING_DOSPUNTOS + tokenPlume + "]");

      response = headerAuthorizationPost(tokenBasic, tokenPlume, tokenClaro, inputJson, webResource);

      if (response != null) {

        logger.info(mensajeMetodo + Constantes.STATUS + response.getStatus());

        String rpta = response.getEntity(String.class);
        logger.info(mensajeMetodo + Constantes.PARAMETROS_SALIDA + UtilJAXRS.formatJson(rpta));

        if (response.getStatus() == 200) {
          tokenResponse = new ConfigNivelResponse();
          Gson gson = new Gson();
          tokenResponse = gson.fromJson(rpta, ConfigNivelResponse.class);

        } else {
          logger
            .info(mensajeMetodo + " Error en el Servicio WS ConfigNivelServicio Plume Error:" + response.getStatus());

          Gson gson = new Gson();
          tokenResponse = gson.fromJson(rpta, ConfigNivelResponse.class);
          logger.info(mensajeMetodo + Constantes.PARAMETROS_SALIDA_ERROR
            + UtilJAXRS.anyObjectToPrettyJson(tokenResponse.getMessageResponse().getBody().getFault()));
        }
      }

    } catch (Exception e) {
      logger.error(
        mensajeMetodo + Constantes.EXCEPCION_EN_WS + "ConfigNivelServicio - WS" + "]: [" + e.getMessage() + "]",
        e);
      ClaroUtil.capturarErrorWS(e, nombreWS, nombreMetodo, prop);
    } finally {
      logger.info(mensajeMetodo + Constantes.TIEMPOTOTALPROCESO + (System.currentTimeMillis() - tiempoInicio)
        + Constantes.MILISEG);
      logger.info(mensajeMetodo + " [FIN] - METODO: [ConfigNivelServicio - WS] ");
    }

    return tokenResponse;
  }

  @Override
  public EliminarUbicacionResponse eliminarUbicacion(String idTransaccion, String tokenBasic, String tokenPlume,
                                                     String tokenClaro, String accountId, String idLocation) throws WSException {
    long tiempoInicio = System.currentTimeMillis();

    EliminarUbicacionResponse tokenResponse = new EliminarUbicacionResponse();

    String mensajeMetodo = idTransaccion + " [EliminarUbicacion] ";

    String url = prop.wsUrlPlumeLocationEliminarUbi;
    String nombreWS = prop.wsUrlPlumeLocationEliminarUbiNombre;
    String nombreMetodo = prop.wsUrlPlumeLocationEliminarUbiMetodo;

    try {

      logger.info(mensajeMetodo + Constantes.SERVICIO + nombreWS);
      logger.info(mensajeMetodo + Constantes.METODO + nombreMetodo);
      logger.info(mensajeMetodo + Constantes.URL_SERVICIO_A_INVOCAR + url);
      logger.info(mensajeMetodo + Constantes.TIMEOUT_CONNECT + prop.wsPlumeLocationEliminarUbiTimeoutConnect);
      logger.info(mensajeMetodo + Constantes.TIMEOUT_REQUEST + prop.wsPlumeLocationEliminarUbiEjecucion);

      Client client = Client.create();
      String urlFinal = url.replace(Constantes.IDCUSTPLUME, accountId).replace(Constantes.ID_LOCPLUME, idLocation);

      client.setConnectTimeout(Integer.parseInt(prop.wsPlumeLocationEliminarUbiTimeoutConnect));
      client.setReadTimeout(Integer.parseInt(prop.wsPlumeLocationEliminarUbiEjecucion));
      logger.info(mensajeMetodo + Constantes.URL_ARMADO + urlFinal);

      ClientResponse responseEliminar = null;

      WebResource webResourceEliminar = client.resource(urlFinal);

      logger.info(mensajeMetodo + Constantes.SALTO_LINEA + "[" + Constantes.NAMEHEADERAPPCONTENTTYPE
        + Constantes.STRING_DOSPUNTOS + prop.wsDpPlumeContentType + "]" + Constantes.SALTO_LINEA + "["
        + Constantes.NAMEHEADERAPPAUTHORIZATION + Constantes.STRING_DOSPUNTOS + tokenBasic + "]"
        + Constantes.SALTO_LINEA + "[" + Constantes.NAMEHEADERAPPACCESSTOKEN + Constantes.STRING_DOSPUNTOS
        + tokenClaro + "]" + Constantes.SALTO_LINEA + "[" + Constantes.NAMEHEADERTOKENPLUME
        + Constantes.STRING_DOSPUNTOS + tokenPlume + "]");

      responseEliminar = headerAuthorizationDelete(tokenBasic, tokenPlume, tokenClaro, webResourceEliminar);

      if (responseEliminar != null) {

        logger.info(mensajeMetodo + Constantes.STATUS + responseEliminar.getStatus());

        String rpta = responseEliminar.getEntity(String.class);
        logger.info(mensajeMetodo + Constantes.PARAMETROS_SALIDA + UtilJAXRS.formatJson(rpta));

        if (responseEliminar.getStatus() == 200) {
          tokenResponse = new EliminarUbicacionResponse();

          GsonBuilder gsonBuilder = new GsonBuilder();
          gsonBuilder.registerTypeAdapter(EliminarUbicacionBodyResponse.class, new EliminarUbicacionBodyResponseDeserializer());
          Gson gson = gsonBuilder.create();
          tokenResponse = gson.fromJson(rpta, EliminarUbicacionResponse.class);

        } else {
          logger.info(mensajeMetodo + " Error en el Servicio WS EliminarUbicacion Error:" + responseEliminar.getStatus());

          Gson gson = new Gson();
          tokenResponse = gson.fromJson(rpta, EliminarUbicacionResponse.class);

          logger.info(mensajeMetodo + Constantes.PARAMETROS_SALIDA_ERROR
            + UtilJAXRS.anyObjectToPrettyJson(tokenResponse.getMessageResponse().getBody().getError()));
        }
      }

    } catch (Exception e) {
      logger.error(
        mensajeMetodo + Constantes.EXCEPCION_EN_WS + "EliminarUbicacion - WS" + "]: [" + e.getMessage() + "]",
        e);
      ClaroUtil.capturarErrorWS(e, nombreWS, nombreMetodo, prop);
    } finally {
      logger.info(mensajeMetodo + Constantes.TIEMPOTOTALPROCESO + (System.currentTimeMillis() - tiempoInicio)
        + Constantes.MILISEG);
      logger.info(mensajeMetodo + " [FIN] - METODO: [EliminarUbicacion - WS] ");
    }

    return tokenResponse;
  }

  @Override
  public ObtenerRepetidorResponse obtenerRepetidor(String idTransaccion, String tokenBasic, String tokenPlume,
                                                   String tokenClaro, String accountId, String idLocation) throws WSException {
    long tiempoInicio = System.currentTimeMillis();

    String mensajeMetodo = idTransaccion + " [ObtenerRepetidor] ";

    ObtenerRepetidorResponse tokenResponse = new ObtenerRepetidorResponse();
    String url = prop.wsUrlPlumeLocationObtenerRepetidor;
    String nombreWS = prop.wsPlumeLocationObtenerRepetidorNombre;
    String nombreMetodo = prop.wsPlumeLocationObtenerRepetidorMetodo;

    try {

      logger.info(mensajeMetodo + Constantes.SERVICIO + nombreWS);
      logger.info(mensajeMetodo + Constantes.METODO + nombreMetodo);
      logger.info(mensajeMetodo + Constantes.URL_SERVICIO_A_INVOCAR + url);
      logger.info(mensajeMetodo + Constantes.TIMEOUT_CONNECT + prop.wsPlumeLocationObtenerRepetidorTimeoutConnect);
      logger.info(mensajeMetodo + Constantes.TIMEOUT_REQUEST + prop.wsPlumeLocationObtenerRepetidorEjecucion);

      Client client = Client.create();
      String urlFinal = url.replace(Constantes.IDCUSTPLUME, accountId).replace(Constantes.ID_LOCPLUME, idLocation);

      client.setConnectTimeout(Integer.parseInt(prop.wsPlumeLocationObtenerRepetidorTimeoutConnect));
      client.setReadTimeout(Integer.parseInt(prop.wsPlumeLocationObtenerRepetidorEjecucion));
      logger.info(mensajeMetodo + Constantes.URL_ARMADO + urlFinal);

      WebResource webResourceObtRep = client.resource(urlFinal);

      ClientResponse responseObtRep = null;

      logger.info(mensajeMetodo + Constantes.SALTO_LINEA + "[" + Constantes.NAMEHEADERAPPCONTENTTYPE
        + Constantes.STRING_DOSPUNTOS + prop.wsDpPlumeContentType + "]" + Constantes.SALTO_LINEA + "["
        + Constantes.NAMEHEADERAPPAUTHORIZATION + Constantes.STRING_DOSPUNTOS + tokenBasic + "]"
        + Constantes.SALTO_LINEA + "[" + Constantes.NAMEHEADERAPPACCESSTOKEN + Constantes.STRING_DOSPUNTOS
        + tokenClaro + "]" + Constantes.SALTO_LINEA + "[" + Constantes.NAMEHEADERTOKENPLUME
        + Constantes.STRING_DOSPUNTOS + tokenPlume + "]");

      responseObtRep = headerAuthorizationGet(tokenBasic, tokenPlume, tokenClaro, webResourceObtRep);

      if (responseObtRep != null) {

        logger.info(mensajeMetodo + Constantes.STATUS + responseObtRep.getStatus());

        String rpta = responseObtRep.getEntity(String.class);
        logger.info(mensajeMetodo + Constantes.PARAMETROS_SALIDA + UtilJAXRS.formatJson(rpta));

        if (responseObtRep.getStatus() == 200) {
          tokenResponse = new ObtenerRepetidorResponse();
          Gson gson = new Gson();
          tokenResponse = gson.fromJson(rpta, ObtenerRepetidorResponse.class);

        } else {
          logger.info(mensajeMetodo + " Error en el Servicio WS ObtenerRepetidor Error:" + responseObtRep.getStatus());

          Gson gson = new Gson();
          tokenResponse = gson.fromJson(rpta, ObtenerRepetidorResponse.class);

          logger.info(mensajeMetodo + Constantes.PARAMETROS_SALIDA_ERROR
            + UtilJAXRS.anyObjectToPrettyJson(tokenResponse.getMessageResponse().getBody().getFault()));
        }
      }

    } catch (Exception e) {
      logger.error(
        mensajeMetodo + Constantes.EXCEPCION_EN_WS + "ObtenerRepetidor - WS" + "]: [" + e.getMessage() + "]",
        e);
      ClaroUtil.capturarErrorWS(e, nombreWS, nombreMetodo, prop);
    } finally {
      logger.info(mensajeMetodo + Constantes.TIEMPOTOTALPROCESO + (System.currentTimeMillis() - tiempoInicio)
        + Constantes.MILISEG);
      logger.info(mensajeMetodo + " [FIN] - METODO: [ObtenerRepetidor - WS] ");
    }

    return tokenResponse;
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
