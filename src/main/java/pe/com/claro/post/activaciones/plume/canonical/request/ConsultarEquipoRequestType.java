package pe.com.claro.post.activaciones.plume.canonical.request;

import java.util.List;

import pe.com.claro.post.activaciones.plume.canonical.type.ListaOpcionalType;

public class ConsultarEquipoRequestType {

  private String codigoCliente;
  private String codigoSucursal;
  private String numeroSerie;
  private String numeroSot;
  private List<ListaOpcionalType> listaOpcional;

  public String getCodigoCliente() {
    return codigoCliente;
  }

  public void setCodigoCliente(String codigoCliente) {
    this.codigoCliente = codigoCliente;
  }

  public String getCodigoSucursal() {
    return codigoSucursal;
  }

  public void setCodigoSucursal(String codigoSucursal) {
    this.codigoSucursal = codigoSucursal;
  }

  public String getNumeroSerie() {
    return numeroSerie;
  }

  public void setNumeroSerie(String numeroSerie) {
    this.numeroSerie = numeroSerie;
  }

  public List<ListaOpcionalType> getListaOpcional() {
    return listaOpcional;
  }

  public void setListaOpcional(List<ListaOpcionalType> listaOpcional) {
    this.listaOpcional = listaOpcional;
  }

  public String getNumeroSot() {
    return numeroSot;
  }

  public void setNumeroSot(String numeroSot) {
    this.numeroSot = numeroSot;
  }

}