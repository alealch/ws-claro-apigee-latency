package pe.com.claro.post.activaciones.plume.integration.ws.bean.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.ErrorType;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EliminarRepetidorBodyResponse {

  @JsonProperty("error")
  private ErrorType error;

  public ErrorType getError() {
    return error;
  }

  public void setError(ErrorType error) {
    this.error = error;
  }
}
