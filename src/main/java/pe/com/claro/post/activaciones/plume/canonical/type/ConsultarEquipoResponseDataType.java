package pe.com.claro.post.activaciones.plume.canonical.type;

import java.util.List;

public class ConsultarEquipoResponseDataType {

  private String idCustPlume;
  private String idLocPlume;
  private EquipoCEType equipo;
  private List<ListaOpcionalType> listaOpcional;

  public EquipoCEType getEquipo() {
    return equipo;
  }

  public void setEquipo(EquipoCEType equipo) {
    this.equipo = equipo;
  }

  public String getIdCustPlume() {
    return idCustPlume;
  }

  public void setIdCustPlume(String idCustPlume) {
    this.idCustPlume = idCustPlume;
  }

  public String getIdLocPlume() {
    return idLocPlume;
  }

  public void setIdLocPlume(String idLocPlume) {
    this.idLocPlume = idLocPlume;
  }

  public List<ListaOpcionalType> getListaOpcional() {
    return listaOpcional;
  }

  public void setListaOpcional(List<ListaOpcionalType> listaOpcional) {
    this.listaOpcional = listaOpcional;
  }

}
