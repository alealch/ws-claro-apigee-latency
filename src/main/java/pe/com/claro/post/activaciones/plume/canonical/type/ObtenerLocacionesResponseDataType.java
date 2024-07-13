package pe.com.claro.post.activaciones.plume.canonical.type;

import java.util.List;

public class ObtenerLocacionesResponseDataType {

  private String accountId;
  private String idCustPlume;
  private List<ListaLocacionesOLType> locaciones;
  private List<ListaOpcionalType> listaOpcional;

  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  public String getIdCustPlume() {
    return idCustPlume;
  }

  public void setIdCustPlume(String idCustPlume) {
    this.idCustPlume = idCustPlume;
  }

  public List<ListaLocacionesOLType> getLocaciones() {
    return locaciones;
  }

  public void setLocaciones(List<ListaLocacionesOLType> locaciones) {
    this.locaciones = locaciones;
  }

  public List<ListaOpcionalType> getListaOpcional() {
    return listaOpcional;
  }

  public void setListaOpcional(List<ListaOpcionalType> listaOpcional) {
    this.listaOpcional = listaOpcional;
  }

}
