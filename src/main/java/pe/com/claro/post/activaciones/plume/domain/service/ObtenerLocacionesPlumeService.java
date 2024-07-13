package pe.com.claro.post.activaciones.plume.domain.service;

import pe.com.claro.post.activaciones.plume.canonical.header.HeaderRequest;
import pe.com.claro.post.activaciones.plume.canonical.request.ObtenerLocacionesRequest;
import pe.com.claro.post.activaciones.plume.canonical.response.ObtenerLocacionesResponse;

public interface ObtenerLocacionesPlumeService {

  ObtenerLocacionesResponse obtenerLocaciones(String idTransaccion, HeaderRequest header, ObtenerLocacionesRequest request);

}
