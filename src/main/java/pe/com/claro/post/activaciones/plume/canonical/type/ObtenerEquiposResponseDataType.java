package pe.com.claro.post.activaciones.plume.canonical.type;

import java.util.List;

public class ObtenerEquiposResponseDataType {
  private String accountId;
  private String codigoSucursal;
  private String nombreSucursal;
  private List<ListaEquiposOEType> equipos;
  private List<ListaOpcionalType> listaOpcional;

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

  public String getNombreSucursal() {
    return nombreSucursal;
  }

  public void setNombreSucursal(String nombreSucursal) {
    this.nombreSucursal = nombreSucursal;
  }

  public List<ListaEquiposOEType> getEquipos() {
    return equipos;
  }

  public void setEquipos(List<ListaEquiposOEType> equipos) {
    this.equipos = equipos;
  }

  public List<ListaOpcionalType> getListaOpcional() {
    return listaOpcional;
  }

  public void setListaOpcional(List<ListaOpcionalType> listaOpcional) {
    this.listaOpcional = listaOpcional;
  }

}
