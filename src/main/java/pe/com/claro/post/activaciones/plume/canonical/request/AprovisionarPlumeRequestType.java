package pe.com.claro.post.activaciones.plume.canonical.request;

import java.util.List;

import pe.com.claro.post.activaciones.plume.canonical.type.ListaEquiposAPType;
import pe.com.claro.post.activaciones.plume.canonical.type.ListaOpcionalType;

public class AprovisionarPlumeRequestType {

  private String coId;
  private String idTipo;
  private String numeroSot;
  private List<ListaEquiposAPType> equipos;
  private List<ListaOpcionalType> listaOpcional;

  public String getCoId() {
    return coId;
  }

  public void setCoId(String coId) {
    this.coId = coId;
  }

  public String getIdTipo() {
    return idTipo;
  }

  public void setIdTipo(String idTipo) {
    this.idTipo = idTipo;
  }

  public String getNumeroSot() {
    return numeroSot;
  }

  public void setNumeroSot(String numeroSot) {
    this.numeroSot = numeroSot;
  }

  public List<ListaEquiposAPType> getEquipos() {
    return equipos;
  }

  public void setEquipos(List<ListaEquiposAPType> equipos) {
    this.equipos = equipos;
  }

  public List<ListaOpcionalType> getListaOpcional() {
    return listaOpcional;
  }

  public void setListaOpcional(List<ListaOpcionalType> listaOpcional) {
    this.listaOpcional = listaOpcional;
  }

}
