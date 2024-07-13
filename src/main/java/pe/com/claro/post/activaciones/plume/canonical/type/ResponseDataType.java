package pe.com.claro.post.activaciones.plume.canonical.type;

import java.util.List;

public class ResponseDataType {

  private String nroFact;
  private String cfAct;
  private String cfMen;
  private List<ListaOpcionalType> listaOpcional;

  public String getNroFact() {
    return nroFact;
  }

  public void setNroFact(String nroFact) {
    this.nroFact = nroFact;
  }

  public String getCfAct() {
    return cfAct;
  }

  public void setCfAct(String cfAct) {
    this.cfAct = cfAct;
  }

  public String getCfMen() {
    return cfMen;
  }

  public void setCfMen(String cfMen) {
    this.cfMen = cfMen;
  }

  public List<ListaOpcionalType> getListaOpcional() {
    return listaOpcional;
  }

  public void setListaOpcional(List<ListaOpcionalType> listaOpcional) {
    this.listaOpcional = listaOpcional;
  }

}
