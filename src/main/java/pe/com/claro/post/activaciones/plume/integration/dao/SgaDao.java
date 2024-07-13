package pe.com.claro.post.activaciones.plume.integration.dao;

import pe.com.claro.post.activaciones.plume.common.exceptions.BDException;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.request.DatosClienteBeanReq;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.request.RegistrarClieteReq;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.response.DatosClienteBeanRes;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.response.EquipoRes;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.response.RegistrarClieteRes;

public interface SgaDao {

  DatosClienteBeanRes datosCliente(String mensajeTransaccion, DatosClienteBeanReq request) throws BDException;

  RegistrarClieteRes registrarCliente(String mensajeTransaccion, RegistrarClieteReq request) throws BDException;

  EquipoRes listarEquipos(String mensajeTransaccion, String codigoCliente, String codigoSucurlas) throws BDException;

}
