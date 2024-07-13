package pe.com.claro.post.activaciones.plume.canonical.request;

import java.util.List;

import pe.com.claro.post.activaciones.plume.canonical.type.ListaOpcionalType;

public class ConsultadatosfacturaRequestType {

  private String msisdn;
  private String spCode;
  private List<ListaOpcionalType> listaOpcional;

  public String getMsisdn() {
    return msisdn;
  }

  public void setMsisdn(String msisdn) {
    this.msisdn = msisdn;
  }

  public String getSpCode() {
    return spCode;
  }

  public void setSpCode(String spCode) {
    this.spCode = spCode;
  }

  public List<ListaOpcionalType> getListaOpcional() {
    return listaOpcional;
  }

  public void setListaOpcional(List<ListaOpcionalType> listaOpcional) {
    this.listaOpcional = listaOpcional;
  }

}
