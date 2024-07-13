package pe.com.claro.post.activaciones.plume.integration.ws;

import pe.com.claro.post.activaciones.plume.common.exceptions.WSException;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.request.ConfigNivelRequest;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.request.CrearUbicacionRequest;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.ConfigNivelResponse;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.CrearUbicacionResponse;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.EliminarUbicacionResponse;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.ObtenerRepetidorResponse;

public interface PlumeLocationClient {

  CrearUbicacionResponse crearUbicacion(String idTransaccion, CrearUbicacionRequest request, String tokenBasic,
                                        String tokenPlume, String tokenClaro, String accountId) throws WSException;

  ConfigNivelResponse configNivelServicio(String idTransaccion, ConfigNivelRequest request, String tokenBasic,
                                          String tokenPlume, String tokenClaro, String accountId, String idLocation) throws WSException;

  ObtenerRepetidorResponse obtenerRepetidor(String idTransaccion, String tokenBasic, String tokenPlume,
                                            String tokenClaro, String accountId, String idLocation) throws WSException;

  EliminarUbicacionResponse eliminarUbicacion(String idTransaccion, String tokenBasic, String tokenPlume,
                                              String tokenClaro, String accountId, String idLocation) throws WSException;

}
