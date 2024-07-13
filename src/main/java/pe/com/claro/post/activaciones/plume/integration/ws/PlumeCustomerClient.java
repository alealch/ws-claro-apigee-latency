package pe.com.claro.post.activaciones.plume.integration.ws;

import pe.com.claro.post.activaciones.plume.common.exceptions.WSException;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.request.CrearCuentaRequest;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.CrearCuentaResponse;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.EliminarCuentaResponse;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.ObtenerUbicacionResponse;

public interface PlumeCustomerClient {

  CrearCuentaResponse crearCuentaCliente(String idTransaccion, CrearCuentaRequest request, String tokenBasic,
                                         String tokenPlume, String tokenClaro) throws WSException;

  EliminarCuentaResponse eliminarCuentaCliente(String idTransaccion, String tokenBasic, String tokenPlume,
                                               String tokenClaro, String accountId) throws WSException;

  ObtenerUbicacionResponse obtenerUbicacion(String idTransaccion, String tokenBasic, String tokenPlume,
                                            String tokenClaro, String accountId)
    throws WSException;
}
