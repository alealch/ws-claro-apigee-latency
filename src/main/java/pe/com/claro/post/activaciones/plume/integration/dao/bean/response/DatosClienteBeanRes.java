package pe.com.claro.post.activaciones.plume.integration.dao.bean.response;

import java.util.List;

import pe.com.claro.post.activaciones.plume.integration.dao.bean.type.CursorValidaCuenta;

public class DatosClienteBeanRes {

  private String codigoRespuesta;
  private String mensajeRespuesta;
  private String codigoCliente;
  private String email;
  private String nombres;
  private String accountId;
  private String codigoSucursal;
  private String nombreSurcursal;
  private String tipoTrabajo;
  private String validaCuenta;
  private String codigoClientePlm;
  private List<CursorValidaCuenta> cursor;

  public String getCodigoRespuesta() {
    return codigoRespuesta;
  }

  public void setCodigoRespuesta(String codigoRespuesta) {
    this.codigoRespuesta = codigoRespuesta;
  }

  public String getMensajeRespuesta() {
    return mensajeRespuesta;
  }

  public void setMensajeRespuesta(String mensajeRespuesta) {
    this.mensajeRespuesta = mensajeRespuesta;
  }

  public String getCodigoCliente() {
    return codigoCliente;
  }

  public void setCodigoCliente(String codigoCliente) {
    this.codigoCliente = codigoCliente;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getNombres() {
    return nombres;
  }

  public void setNombres(String nombres) {
    this.nombres = nombres;
  }

  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  public String getCodigoSucursal() {
    return codigoSucursal;
  }

  public void setCodigoSucursal(String codigoSucursal) {
    this.codigoSucursal = codigoSucursal;
  }

  public String getNombreSurcursal() {
    return nombreSurcursal;
  }

  public void setNombreSurcursal(String nombreSurcursal) {
    this.nombreSurcursal = nombreSurcursal;
  }

  public String getTipoTrabajo() {
    return tipoTrabajo;
  }

  public void setTipoTrabajo(String tipoTrabajo) {
    this.tipoTrabajo = tipoTrabajo;
  }

  public String getValidaCuenta() {
    return validaCuenta;
  }

  public void setValidaCuenta(String validaCuenta) {
    this.validaCuenta = validaCuenta;
  }

  public String getCodigoClientePlm() {
    return codigoClientePlm;
  }

  public void setCodigoClientePlm(String codigoClientePlm) {
    this.codigoClientePlm = codigoClientePlm;
  }

  public List<CursorValidaCuenta> getCursor() {
    return cursor;
  }

  public void setCursor(List<CursorValidaCuenta> cursor) {
    this.cursor = cursor;
  }

}
