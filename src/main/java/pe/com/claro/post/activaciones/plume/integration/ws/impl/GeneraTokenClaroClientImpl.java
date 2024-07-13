package pe.com.claro.post.activaciones.plume.integration.ws.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import pe.com.claro.post.activaciones.plume.integration.ws.GeneraTokenClaroClient;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.request.GeneraTokenClaroRequest;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.GeneraTokenClaroResponse;

@Component
public class GeneraTokenClaroClientImpl implements GeneraTokenClaroClient {

  private static final Logger logger = LoggerFactory.getLogger(GeneraTokenClaroClientImpl.class);

  @Override
  public GeneraTokenClaroResponse generarToken(String mensajeTransaccion, String idTx, GeneraTokenClaroRequest request,
                                               PropertiesExterno prop) throws WSException {
    long tiempoInicio = System.currentTimeMillis();

    String dpAuthorizationValidaRuta = prop.wsGeneraTokenClaroAuthorization;

    String mensajeMetodo = mensajeTransaccion + " [generaToken] ";

    GeneraTokenClaroResponse tokenResponse = new GeneraTokenClaroResponse();

    String url = prop.wsUrlGeneraTokenClaro;
    String nombreWS = prop.wsGeneraTokenClaroNombre;
    String nombreMetodo = prop.wsGeneraTokenClaroMetodo;

    try {

      Gson inputGson = new Gson();
      String inputJson = inputGson.toJson(request, GeneraTokenClaroRequest.class);

      logger.info(mensajeMetodo + " Servicio: " + nombreWS);
      logger.info(mensajeMetodo + " Metodo: " + nombreMetodo);
      logger.info(mensajeMetodo + " URL del Servicio a invocar: " + url);
      logger.info(mensajeMetodo + " Timeout Connect: " + prop.wsGeneraTokenClaroTimeoutConnect);
      logger.info(mensajeMetodo + " Timeout Request: " + prop.wsGeneraTokenClaroTimeoutEjecucion);
      logger.info(mensajeMetodo + " JSON: " + UtilJAXRS.formatJson(inputJson));

      Client client = Client.create();

      client.setConnectTimeout(Integer.parseInt(prop.wsGeneraTokenClaroTimeoutConnect));
      client.setReadTimeout(Integer.parseInt(prop.wsGeneraTokenClaroTimeoutEjecucion));

      WebResource webResource = client.resource(url);

      ClientResponse response = null;

      logger.info(mensajeMetodo + Constantes.SALTO_LINEA + " [" + Constantes.NAMEHEADERAPPCONTENTTYPE
        + Constantes.STRING_DOSPUNTOS + prop.wsGeneraTokenClaroContentType + "]" + Constantes.SALTO_LINEA + "["
        + Constantes.NAMEHEADERAPPAUTHORIZATION + Constantes.STRING_DOSPUNTOS + dpAuthorizationValidaRuta + "]");

      response = webResource.accept(Constantes.VALUEAPPLICATIONJSON)
        .header(Constantes.NAMEHEADERAPPCONTENTTYPE, prop.wsGeneraTokenClaroContentType)
        .header(Constantes.NAMEHEADERAPPAUTHORIZATION, dpAuthorizationValidaRuta)
        .post(ClientResponse.class, inputJson);

      if (response != null) {

        logger.info(mensajeMetodo + " Status: " + response.getStatus());

        String rpta = response.getEntity(String.class);
        logger.info(mensajeMetodo + " Parametros Salida:  " + UtilJAXRS.formatJson(rpta));

        if (response.getStatus() == 200) {
          tokenResponse = new GeneraTokenClaroResponse();
          Gson gson = new Gson();
          tokenResponse = gson.fromJson(rpta, GeneraTokenClaroResponse.class);

        } else {
          logger.info(mensajeMetodo + " Error en el Servicio WS generaToken Error:" + response.getStatus());

          Gson gson = new Gson();
          tokenResponse = gson.fromJson(rpta, GeneraTokenClaroResponse.class);

          logger.info(mensajeMetodo + " Parametros Salida ERROR:  "
            + UtilJAXRS.anyObjectToPrettyJson(tokenResponse.getMessageResponse().getBody().getError()));
        }
      }
    } catch (Exception e) {
      logger.error(
        mensajeMetodo + " Excepcion ocurrida en el WS [" + "generaToken - WS" + "]: [" + e.getMessage() + "]", e);
      ClaroUtil.capturarErrorWS(e, nombreWS, nombreMetodo, prop);
    } finally {
      logger.info(mensajeMetodo + Constantes.TIEMPOTOTALPROCESO + (System.currentTimeMillis() - tiempoInicio)
        + Constantes.MILISEG);
      logger.info(mensajeMetodo + " [FIN] - METODO: [generaToken - WS] ");
    }

    return tokenResponse;
  }

}
