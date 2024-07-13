package pe.com.claro.post.activaciones.plume.integration.dao.bean.type;

import java.io.Serializable;

public class CursorEquipoType implements Serializable {
  private static final long serialVersionUID = 1L;

  private String nroSerie;
  private String codSerie;
  private String mdlEquipo;

  public String getNroSerie() {
    return nroSerie;
  }

  public void setNroSerie(String nroSerie) {
    this.nroSerie = nroSerie;
  }

  public String getCodSerie() {
    return codSerie;
  }

  public void setCodSerie(String codSerie) {
    this.codSerie = codSerie;
  }

  public String getMdlEquipo() {
    return mdlEquipo;
  }

  public void setMdlEquipo(String mdlEquipo) {
    this.mdlEquipo = mdlEquipo;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

}
