package pe.com.claro.post.activaciones.plume.integration.dao.bean.response;

import java.util.List;

import pe.com.claro.post.activaciones.plume.integration.dao.bean.type.POCursorType;

public class EquipoRes {
  private String codigoRespuesta;
  private String mensajeRespuesta;
  private String codigoClientePlm;
  private List<POCursorType> poCursor;

  public String getCodigoRespuesta() {
    return codigoRespuesta;
  }

  public void setCodigoRespuesta(String codigoRespuesta) {
    this.codigoRespuesta = codigoRespuesta;
  }

  public String getMensajeRespuesta() {
    return mensajeRespuesta;
  }

  public void setMensajeRespuesta(String mensajeRespuesta) {
    this.mensajeRespuesta = mensajeRespuesta;
  }

  public String getCodigoClientePlm() {
    return codigoClientePlm;
  }

  public void setCodigoClientePlm(String codigoClientePlm) {
    this.codigoClientePlm = codigoClientePlm;
  }

  public List<POCursorType> getPoCursor() {
    return poCursor;
  }

  public void setPoCursor(List<POCursorType> poCursor) {
    this.poCursor = poCursor;
  }

}
