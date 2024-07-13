package pe.com.claro.post.activaciones.plume.integration.ws.impl;

import java.net.URLEncoder;

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
import pe.com.claro.post.activaciones.plume.integration.ws.GeneraTokenPlumeClient;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.request.GeneraTokenPlumeRequest;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.GeneraTokenPlumeResponse;

@Component
public class GeneraTokenPlumeClientImpl implements GeneraTokenPlumeClient {

  private static final Logger logger = LoggerFactory.getLogger(GeneraTokenClaroClientImpl.class);

  @Autowired
  PropertiesExterno prop;

  @Override
  public GeneraTokenPlumeResponse generaTokenPlume(String idTransaccion, GeneraTokenPlumeRequest request, String token,
                                                   String tokeClaro, String scope, String granType) throws WSException {
    long tiempoInicio = System.currentTimeMillis();

    String mensajeMetodo = idTransaccion + " [generaTokenPlume] ";

    GeneraTokenPlumeResponse tokenResponse = new GeneraTokenPlumeResponse();

    String url = prop.wsUrlGeneraTokenPlume;
    String nombreWS = prop.wsGeneraTokenPlumeNombre;
    String nombreMetodo = prop.wsGeneraTokenPlumeMetodo;

    try {

      Gson inputGson = new Gson();
      String inputJson = inputGson.toJson(request, GeneraTokenPlumeRequest.class);

      logger.info(mensajeMetodo + " Servicio: " + nombreWS);
      logger.info(mensajeMetodo + " Metodo: " + nombreMetodo);
      logger.info(mensajeMetodo + " URL del Servicio a invocar: " + url);
      logger.info(mensajeMetodo + " Timeout Connect: " + prop.wsGeneraTokenPlumeTimeoutConnect);
      logger.info(mensajeMetodo + " Timeout Request: " + prop.wsGeneraTokenPlumeTimeoutEjecucion);
      logger.info(mensajeMetodo + " JSON: " + UtilJAXRS.formatJson(inputJson));

      Client client = Client.create();
      String urlFinal = url + Constantes.PARAM_SCOPE + URLEncoder.encode(scope, Constantes.UTF8)
        + Constantes.PARAM_GRAN_TYPE + granType;

      client.setConnectTimeout(Integer.parseInt(prop.wsGeneraTokenPlumeTimeoutConnect));
      client.setReadTimeout(Integer.parseInt(prop.wsGeneraTokenPlumeTimeoutEjecucion));
      logger.info(mensajeMetodo + "URL ARMADO: " + urlFinal);

      WebResource webResource = client.resource(urlFinal);

      ClientResponse response = null;

      logger.info(mensajeMetodo + Constantes.SALTO_LINEA + "[" + Constantes.NAMEHEADERAPPCONTENTTYPE
        + Constantes.STRING_DOSPUNTOS + prop.wsDpPlumeContentType + "]" + Constantes.SALTO_LINEA + "["
        + Constantes.NAMEHEADERAPPAUTHORIZATION + Constantes.STRING_DOSPUNTOS + token + "]" + Constantes.SALTO_LINEA
        + "[" + Constantes.NAMEHEADERAPPACCESSTOKEN + Constantes.STRING_DOSPUNTOS + tokeClaro + "]");

      response = webResource.accept(Constantes.VALUEAPPLICATIONJSON)
        .header(Constantes.NAMEHEADERAPPCONTENTTYPE, prop.wsDpPlumeContentType)
        .header(Constantes.NAMEHEADERAPPAUTHORIZATION, token).header(Constantes.NAMEHEADERAPPACCESSTOKEN, tokeClaro)
        .post(ClientResponse.class, inputJson);

      if (response != null) {

        logger.info(mensajeMetodo + " Status: " + response.getStatus());

        String rpta = response.getEntity(String.class);
        logger.info(mensajeMetodo + " Parametros Salida:  " + UtilJAXRS.formatJson(rpta));

        if (response.getStatus() == 200) {
          tokenResponse = new GeneraTokenPlumeResponse();
          Gson gson = new Gson();
          tokenResponse = gson.fromJson(rpta, GeneraTokenPlumeResponse.class);

        } else {
          logger.info(mensajeMetodo + " Error en el Servicio WS generaTokenPlume Error:" + response.getStatus());

          Gson gson = new Gson();
          tokenResponse = gson.fromJson(rpta, GeneraTokenPlumeResponse.class);

          logger.info(mensajeMetodo + " Parametros Salida ERROR:  "
            + UtilJAXRS.anyObjectToPrettyJson(tokenResponse.getMessageResponse().getBody().getError()));
        }
      }
    } catch (Exception e) {
      logger.error(
        mensajeMetodo + " Excepcion ocurrida en el WS [" + "generaTokenPlume - WS" + "]: [" + e.getMessage() + "]",
        e);
      ClaroUtil.capturarErrorWS(e, nombreWS, nombreMetodo, prop);
    } finally {
      logger.info(mensajeMetodo + Constantes.TIEMPOTOTALPROCESO + (System.currentTimeMillis() - tiempoInicio)
        + Constantes.MILISEG);
      logger.info(mensajeMetodo + " [FIN] - METODO: [generaTokenPlume - WS] ");
    }

    return tokenResponse;
  }
}
