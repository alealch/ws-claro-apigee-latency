package pe.com.claro.post.activaciones.plume.domain.service;

import pe.com.claro.post.activaciones.plume.canonical.header.HeaderRequest;
import pe.com.claro.post.activaciones.plume.canonical.request.ObtenerEquiposRequest;
import pe.com.claro.post.activaciones.plume.canonical.response.ObtenerEquiposResponse;

public interface ObtenerEquiposPlumeService {
  ObtenerEquiposResponse obtenerEquipos(String idTransaccion, HeaderRequest header, ObtenerEquiposRequest request);

}
