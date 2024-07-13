package pe.com.claro.post.activaciones.plume.integration.ws;

import pe.com.claro.post.activaciones.plume.common.exceptions.WSException;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.request.CrearRepetidorRequest;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.request.EliminarRepetidorRequest;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.ConsultarRepetidorResponse;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.CrearRepetidorResponse;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.EliminarRepetidorResponse;

public interface PlumeNodeClient {

  CrearRepetidorResponse crearRepetidor(String idTransaccion, CrearRepetidorRequest request, String tokenBasic,
                                        String tokenPlume, String tokenClaro, String accountId, String idLocation) throws WSException;

  EliminarRepetidorResponse eliminarRepetidor(String idTransaccion, EliminarRepetidorRequest request, String tokenBasic,
                                              String tokenPlume, String tokenClaro) throws WSException;

  ConsultarRepetidorResponse consultarRepetidor(String idTransaccion, String tokenBasic,
                                                String tokenPlume, String tokenClaro, String serialNumber) throws WSException;
}
