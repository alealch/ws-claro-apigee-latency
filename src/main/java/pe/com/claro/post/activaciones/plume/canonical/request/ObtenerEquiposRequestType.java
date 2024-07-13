package pe.com.claro.post.activaciones.plume.canonical.request;

import java.util.List;

import pe.com.claro.post.activaciones.plume.canonical.type.ListaOpcionalType;

public class ObtenerEquiposRequestType {

  private String numeroSot;
  private List<ListaOpcionalType> listaOpcional;
  private String codigoCliente;

  public String getNumeroSot() {
    return numeroSot;
  }

  public void setNumeroSot(String numeroSot) {
    this.numeroSot = numeroSot;
  }

  public List<ListaOpcionalType> getListaOpcional() {
    return listaOpcional;
  }

  public void setListaOpcional(List<ListaOpcionalType> listaOpcional) {
    this.listaOpcional = listaOpcional;
  }

  public String getCodigoCliente() {
    return codigoCliente;
  }

  public void setCodigoCliente(String codigoCliente) {
    this.codigoCliente = codigoCliente;
  }
}
