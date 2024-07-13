package pe.com.claro.post.activaciones.plume.domain.service;

import pe.com.claro.post.activaciones.plume.canonical.header.HeaderRequest;
import pe.com.claro.post.activaciones.plume.canonical.request.AprovisionarPlumeRequest;
import pe.com.claro.post.activaciones.plume.canonical.response.AprovisionarPlumeResponse;
import pe.com.claro.post.activaciones.plume.common.property.PropertiesExterno;

public interface AprovisionarPlumeService {

  AprovisionarPlumeResponse aprovisionarPlume(String idTransaccion, HeaderRequest header,
                                              AprovisionarPlumeRequest request, PropertiesExterno prop);

}
