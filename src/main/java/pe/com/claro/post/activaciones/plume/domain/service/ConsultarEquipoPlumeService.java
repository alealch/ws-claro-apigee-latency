package pe.com.claro.post.activaciones.plume.domain.service;

import pe.com.claro.post.activaciones.plume.canonical.header.HeaderRequest;
import pe.com.claro.post.activaciones.plume.canonical.request.ConsultarEquipoRequest;
import pe.com.claro.post.activaciones.plume.canonical.response.ConsultarEquipoResponse;
import pe.com.claro.post.activaciones.plume.common.property.PropertiesExterno;

public interface ConsultarEquipoPlumeService {
  ConsultarEquipoResponse consultarEquipo(String idTransaccion, HeaderRequest header, ConsultarEquipoRequest request, PropertiesExterno prop);

}
