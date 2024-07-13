package pe.com.claro.post.activaciones.plume.integration.ws.bean.response;

import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.ErrorType;

public class EliminarUbicacionBodyResponse {

  private ErrorType error;

  public ErrorType getError() {
    return error;
  }

  public void setError(ErrorType error) {
    this.error = error;
  }

}
