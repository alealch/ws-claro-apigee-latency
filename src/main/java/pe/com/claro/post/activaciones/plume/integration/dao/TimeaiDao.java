package pe.com.claro.post.activaciones.plume.integration.dao;

import java.sql.SQLException;

import pe.com.claro.post.activaciones.plume.common.exceptions.BaseException;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.request.RegistrarTrazabilidadRequest;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.response.RegistrarTrazabilidadResponse;

public interface TimeaiDao {
  RegistrarTrazabilidadResponse registrarTrazabilidad(String mensajeTransaccion, RegistrarTrazabilidadRequest request)
    throws BaseException, SQLException;

}
