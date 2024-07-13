package pe.com.claro.post.activaciones.plume.integration.ws;

import pe.com.claro.post.activaciones.plume.common.exceptions.WSException;
import pe.com.claro.post.activaciones.plume.common.property.PropertiesExterno;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.request.GeneraTokenClaroRequest;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.GeneraTokenClaroResponse;

public interface GeneraTokenClaroClient {

  GeneraTokenClaroResponse generarToken(String mensajeTransaccion, String idTx,
                                        GeneraTokenClaroRequest request, PropertiesExterno prop) throws WSException;

}
