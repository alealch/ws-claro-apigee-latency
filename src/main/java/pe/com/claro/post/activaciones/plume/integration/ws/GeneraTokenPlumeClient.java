package pe.com.claro.post.activaciones.plume.integration.ws;

import pe.com.claro.post.activaciones.plume.common.exceptions.WSException;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.request.GeneraTokenPlumeRequest;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.GeneraTokenPlumeResponse;

public interface GeneraTokenPlumeClient {

  GeneraTokenPlumeResponse generaTokenPlume(String idTransaccion, GeneraTokenPlumeRequest request, String token,
                                            String tokeClaro, String scope, String granType) throws WSException;

}
