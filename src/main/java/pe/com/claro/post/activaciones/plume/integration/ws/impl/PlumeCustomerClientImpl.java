package pe.com.claro.post.activaciones.plume.integration.ws.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import pe.com.claro.post.activaciones.plume.common.constants.Constantes;
import pe.com.claro.post.activaciones.plume.common.exceptions.WSException;
import pe.com.claro.post.activaciones.plume.common.property.PropertiesExterno;
import pe.com.claro.post.activaciones.plume.common.util.ClaroUtil;
import pe.com.claro.post.activaciones.plume.common.util.UtilJAXRS;
import pe.com.claro.post.activaciones.plume.integration.ws.PlumeCustomerClient;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.request.CrearCuentaRequest;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.CrearCuentaResponse;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.EliminarCuentaResponse;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.ObtenerUbicacionResponse;

@Component
public class PlumeCustomerClientImpl implements PlumeCustomerClient {

  private static final Logger logger = LoggerFactory.getLogger(PlumeCustomerClientImpl.class);

  @Autowired
  PropertiesExterno prop;

  @Override
  public CrearCuentaResponse crearCuentaCliente(String idTransaccion, CrearCuentaRequest request, String tokenBasic,
                                                String tokenPlume, String tokenClaro) throws WSException {
    long tiempoInicio = System.currentTimeMillis();

    String mensajeMetodo = idTransaccion + " [CrearCuentaCliente] ";

    CrearCuentaResponse tokenResponse = new CrearCuentaResponse();

    String url = prop.wsUrlPlumeCustomerCrearCuenta;
    String nombreWS = prop.wsPlumeCustomerCrearCuentaNombre;
    String nombreMetodo = prop.wsPlumeCustomerCrearCuentaMetodo;
    try {

      Gson inputGson = new Gson();
      String inputJson = inputGson.toJson(request, CrearCuentaRequest.class);

      logger.info(mensajeMetodo + Constantes.SERVICIO + nombreWS);
      logger.info(mensajeMetodo + Constantes.METODO + nombreMetodo);
      logger.info(mensajeMetodo + Constantes.URL_SERVICIO_A_INVOCAR + url);
      logger.info(mensajeMetodo + Constantes.TIMEOUT_CONNECT + prop.wsPlumeCustomerCrearCuentaTimeoutConnect);
      logger.info(mensajeMetodo + Constantes.TIMEOUT_REQUEST + prop.wsPlumeCustomerCrearCuentaTimeoutEjecucion);
      logger.info(mensajeMetodo + Constantes.JSON + UtilJAXRS.formatJson(inputJson));

      Client client = Client.create();

      client.setConnectTimeout(Integer.parseInt(prop.wsPlumeCustomerCrearCuentaTimeoutConnect));
      client.setReadTimeout(Integer.parseInt(prop.wsPlumeCustomerCrearCuentaTimeoutEjecucion));

      WebResource webResource = client.resource(url);

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
          tokenResponse = new CrearCuentaResponse();
          Gson gson = new Gson();
          tokenResponse = gson.fromJson(rpta, CrearCuentaResponse.class);

        } else {
          logger.info(mensajeMetodo + " Error en el Servicio WS CrearCuentaCliente Error:" + response.getStatus());

          Gson gson = new Gson();
          tokenResponse = gson.fromJson(rpta, CrearCuentaResponse.class);

          logger.info(mensajeMetodo + Constantes.PARAMETROS_SALIDA_ERROR
            + UtilJAXRS.anyObjectToPrettyJson(tokenResponse.getMessageResponse().getBody().getError()));
        }
      }

    } catch (Exception e) {
      logger.error(
        mensajeMetodo + Constantes.EXCEPCION_EN_WS + "CrearCuentaCliente - WS" + "]: [" + e.getMessage() + "]",
        e);
      ClaroUtil.capturarErrorWS(e, nombreWS, nombreMetodo, prop);
    } finally {
      logger.info(mensajeMetodo + Constantes.TIEMPOTOTALPROCESO + (System.currentTimeMillis() - tiempoInicio)
        + Constantes.MILISEG);
      logger.info(mensajeMetodo + " [FIN] - METODO: [CrearCuentaCliente - WS] ");
    }

    return tokenResponse;
  }

  @Override
  public EliminarCuentaResponse eliminarCuentaCliente(String idTransaccion, String tokenBasic, String tokenPlume,
                                                      String tokenClaro, String accountId) throws WSException {
    long tiempoInicio = System.currentTimeMillis();

    EliminarCuentaResponse tokenResponse = new EliminarCuentaResponse();

    String mensajeMetodo = idTransaccion + " [EliminarCuentaCliente] ";

    String url = prop.wsUrlPlumeCustomerEliminarCuenta;
    String nombreWS = prop.wsPlumeCustomerEliminarCuentaNombre;
    String nombreMetodo = prop.wsPlumeCustomerEliminarCuentaMetodo;
    try {

      logger.info(mensajeMetodo + Constantes.SERVICIO + nombreWS);
      logger.info(mensajeMetodo + Constantes.METODO + nombreMetodo);
      logger.info(mensajeMetodo + Constantes.URL_SERVICIO_A_INVOCAR + url);
      logger.info(mensajeMetodo + Constantes.TIMEOUT_CONNECT + prop.wsPlumeCustomerEliminarCuentaTimeoutConnect);
      logger.info(mensajeMetodo + Constantes.TIMEOUT_REQUEST + prop.wsPlumeCustomerEliminarCuentaTimeoutEjecucion);

      Client client = Client.create();
      String urlFinal = url.replace(Constantes.ID_CUSTPLUME, accountId);

      client.setConnectTimeout(Integer.parseInt(prop.wsPlumeCustomerEliminarCuentaTimeoutConnect));
      client.setReadTimeout(Integer.parseInt(prop.wsPlumeCustomerEliminarCuentaTimeoutEjecucion));
      logger.info(mensajeMetodo + "URL ARMADO: " + urlFinal);

      WebResource webResourceECC = client.resource(urlFinal);

      logger.info(mensajeMetodo + Constantes.SALTO_LINEA + "[" + Constantes.NAMEHEADERAPPCONTENTTYPE
        + Constantes.STRING_DOSPUNTOS + prop.wsDpPlumeContentType + "]" + Constantes.SALTO_LINEA + "["
        + Constantes.NAMEHEADERAPPAUTHORIZATION + Constantes.STRING_DOSPUNTOS + tokenBasic + "]"
        + Constantes.SALTO_LINEA + "[" + Constantes.NAMEHEADERAPPACCESSTOKEN + Constantes.STRING_DOSPUNTOS
        + tokenClaro + "]" + Constantes.SALTO_LINEA + "[" + Constantes.NAMEHEADERTOKENPLUME
        + Constantes.STRING_DOSPUNTOS + tokenPlume + "]");

      ClientResponse responseECC = null;

      responseECC = headerAuthorizationDelete(tokenBasic, tokenPlume, tokenClaro, webResourceECC);

      if (responseECC != null) {

        logger.info(mensajeMetodo + Constantes.STATUS + responseECC.getStatus());

        String rpta = responseECC.getEntity(String.class);
        logger.info(mensajeMetodo + Constantes.PARAMETROS_SALIDA + UtilJAXRS.formatJson(rpta));

        if (responseECC.getStatus() == 200) {
          tokenResponse = new EliminarCuentaResponse();
          Gson gson = new Gson();
          tokenResponse = gson.fromJson(rpta, EliminarCuentaResponse.class);

        } else {
          logger.info(mensajeMetodo + " Error en el Servicio WS EliminarCuentaCliente Error:" + responseECC.getStatus());

          Gson gson = new Gson();
          tokenResponse = gson.fromJson(rpta, EliminarCuentaResponse.class);

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
  public ObtenerUbicacionResponse obtenerUbicacion(String idTransaccion, String tokenBasic, String tokenPlume,
                                                   String tokenClaro, String accountId) throws WSException {
    long tiempoInicio = System.currentTimeMillis();

    String mensajeMetodo = idTransaccion + " [ObtenerUbicacion] ";

    ObtenerUbicacionResponse tokenResponse = new ObtenerUbicacionResponse();
    String url = prop.wsUrlPlumeCustomerObtenerUbicacion;
    String nombreWS = prop.wsPlumeCustomerObtenerUbicacionNombre;
    String nombreMetodo = prop.wsPlumeCustomerObtenerUbicacionMetodo;

    try {

      logger.info(mensajeMetodo + Constantes.SERVICIO + nombreWS);
      logger.info(mensajeMetodo + Constantes.METODO + nombreMetodo);
      logger.info(mensajeMetodo + Constantes.URL_SERVICIO_A_INVOCAR + url);
      logger.info(mensajeMetodo + Constantes.TIMEOUT_CONNECT + prop.wsPlumeCustomerObtenerUbicacionTimeoutConnect);
      logger.info(mensajeMetodo + Constantes.TIMEOUT_REQUEST + prop.wsPlumeCustomerObtenerUbicacionTimeoutEjecucion);

      Client client = Client.create();
      String urlFinal = url.replace(Constantes.IDCUSTPLUME, accountId);

      client.setConnectTimeout(Integer.parseInt(prop.wsPlumeCustomerObtenerUbicacionTimeoutConnect));
      client.setReadTimeout(Integer.parseInt(prop.wsPlumeCustomerObtenerUbicacionTimeoutEjecucion));
      logger.info(mensajeMetodo + "URL ARMADO: " + urlFinal);

      WebResource webResourceObtUbic = client.resource(urlFinal);

      ClientResponse response = null;

      logger.info(mensajeMetodo + Constantes.SALTO_LINEA + "[" + Constantes.NAMEHEADERAPPCONTENTTYPE
        + Constantes.STRING_DOSPUNTOS + prop.wsDpPlumeContentType + "]" + Constantes.SALTO_LINEA + "["
        + Constantes.NAMEHEADERAPPAUTHORIZATION + Constantes.STRING_DOSPUNTOS + tokenBasic + "]"
        + Constantes.SALTO_LINEA + "[" + Constantes.NAMEHEADERAPPACCESSTOKEN + Constantes.STRING_DOSPUNTOS
        + tokenClaro + "]" + Constantes.SALTO_LINEA + "[" + Constantes.NAMEHEADERTOKENPLUME
        + Constantes.STRING_DOSPUNTOS + tokenPlume + "]");

      response = headerAuthorizationGet(tokenBasic, tokenPlume, tokenClaro, webResourceObtUbic);

      if (response != null) {

        logger.info(mensajeMetodo + Constantes.STATUS + response.getStatus());

        String rpta = response.getEntity(String.class);
        logger.info(mensajeMetodo + Constantes.PARAMETROS_SALIDA + UtilJAXRS.formatJson(rpta));

        if (response.getStatus() == 200) {
          tokenResponse = new ObtenerUbicacionResponse();
          Gson gson = new Gson();
          tokenResponse = gson.fromJson(rpta, ObtenerUbicacionResponse.class);

        } else {
          logger.info(mensajeMetodo + " Error en el Servicio WS ObtenerUbicacion Error:" + response.getStatus());

          Gson gson = new Gson();
          tokenResponse = gson.fromJson(rpta, ObtenerUbicacionResponse.class);

          logger.info(mensajeMetodo + Constantes.PARAMETROS_SALIDA_ERROR
            + UtilJAXRS.anyObjectToPrettyJson(tokenResponse.getMessageResponse().getBody().get(0).getError()));
        }
      }

    } catch (Exception e) {
      logger.error(
        mensajeMetodo + Constantes.EXCEPCION_EN_WS + "ObtenerUbicacion - WS" + "]: [" + e.getMessage() + "]",
        e);
      ClaroUtil.capturarErrorWS(e, nombreWS, nombreMetodo, prop);
    } finally {
      logger.info(mensajeMetodo + Constantes.TIEMPOTOTALPROCESO + (System.currentTimeMillis() - tiempoInicio)
        + Constantes.MILISEG);
      logger.info(mensajeMetodo + " [FIN] - METODO: [ObtenerUbicacion - WS] ");
    }

    return tokenResponse;
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
}
