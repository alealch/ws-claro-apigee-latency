package pe.com.claro.post.activaciones.plume.integration.dao.bean.request;

import java.util.List;

import pe.com.claro.post.activaciones.plume.integration.dao.bean.type.CursorEquipoType;

public class RegistrarClieteReq {

  private String codigoCliente;
  private String codigoClientePlm;
  private String nombreCliente;
  private String codigoSuc;
  private String codigoSucPlm;
  private String usuarioReg;
  private List<CursorEquipoType> cursorEquipo;

  public String getCodigoCliente() {
    return codigoCliente;
  }

  public void setCodigoCliente(String codigoCliente) {
    this.codigoCliente = codigoCliente;
  }

  public String getCodigoClientePlm() {
    return codigoClientePlm;
  }

  public void setCodigoClientePlm(String codigoClientePlm) {
    this.codigoClientePlm = codigoClientePlm;
  }

  public String getNombreCliente() {
    return nombreCliente;
  }

  public void setNombreCliente(String nombreCliente) {
    this.nombreCliente = nombreCliente;
  }

  public String getCodigoSuc() {
    return codigoSuc;
  }

  public void setCodigoSuc(String codigoSuc) {
    this.codigoSuc = codigoSuc;
  }

  public String getCodigoSucPlm() {
    return codigoSucPlm;
  }

  public void setCodigoSucPlm(String codigoSucPlm) {
    this.codigoSucPlm = codigoSucPlm;
  }

  public String getUsuarioReg() {
    return usuarioReg;
  }

  public void setUsuarioReg(String usuarioReg) {
    this.usuarioReg = usuarioReg;
  }

  public List<CursorEquipoType> getCursorEquipo() {
    return cursorEquipo;
  }

  public void setCursorEquipo(List<CursorEquipoType> cursorEquipo) {
    this.cursorEquipo = cursorEquipo;
  }

}
