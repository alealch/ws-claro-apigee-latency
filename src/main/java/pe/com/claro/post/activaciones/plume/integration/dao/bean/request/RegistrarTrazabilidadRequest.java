package pe.com.claro.post.activaciones.plume.integration.dao.bean.request;

import java.sql.Timestamp;

public class RegistrarTrazabilidadRequest {

  private String idTransaccion;
  private int estado;
  private Timestamp feExec;
  private String codigoCliente;
  private String canal;
  private String accountId;
  private String codigoClientePlm;
  private String codigoSucursalPlm;
  private String codigoSeriePlm;
  private String request;
  private String codigoError;
  private String mensajeError;
  private String tipoOperacion;
  private String userRegistro;

  public String getIdTransaccion() {
    return idTransaccion;
  }

  public void setIdTransaccion(String idTransaccion) {
    this.idTransaccion = idTransaccion;
  }

  public int getEstado() {
    return estado;
  }

  public void setEstado(int estado) {
    this.estado = estado;
  }

  public Timestamp getFeExec() {
    return feExec;
  }

  public void setFeExec(Timestamp timestamp) {
    this.feExec = timestamp;
  }

  public String getCodigoCliente() {
    return codigoCliente;
  }

  public void setCodigoCliente(String codigoCliente) {
    this.codigoCliente = codigoCliente;
  }

  public String getCanal() {
    return canal;
  }

  public void setCanal(String canal) {
    this.canal = canal;
  }

  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  public String getCodigoClientePlm() {
    return codigoClientePlm;
  }

  public void setCodigoClientePlm(String codigoClientePlm) {
    this.codigoClientePlm = codigoClientePlm;
  }

  public String getCodigoSucursalPlm() {
    return codigoSucursalPlm;
  }

  public void setCodigoSucursalPlm(String codigoSucursalPlm) {
    this.codigoSucursalPlm = codigoSucursalPlm;
  }

  public String getCodigoSeriePlm() {
    return codigoSeriePlm;
  }

  public void setCodigoSeriePlm(String codigoSeriePlm) {
    this.codigoSeriePlm = codigoSeriePlm;
  }

  public String getRequest() {
    return request;
  }

  public void setRequest(String request) {
    this.request = request;
  }

  public String getCodigoError() {
    return codigoError;
  }

  public void setCodigoError(String codigoError) {
    this.codigoError = codigoError;
  }

  public String getMensajeError() {
    return mensajeError;
  }

  public void setMensajeError(String mensajeError) {
    this.mensajeError = mensajeError;
  }

  public String getTipoOperacion() {
    return tipoOperacion;
  }

  public void setTipoOperacion(String tipoOperacion) {
    this.tipoOperacion = tipoOperacion;
  }

  public String getUserRegistro() {
    return userRegistro;
  }

  public void setUserRegistro(String userRegistro) {
    this.userRegistro = userRegistro;
  }
}
