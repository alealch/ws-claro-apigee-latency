package pe.com.claro.post.activaciones.plume.integration.ws.bean.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.ErrorType;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EliminarCuentaBodyResponse {

  @SerializedName("count")
  @Expose
  private String count;
  @SerializedName("error")
  @Expose
  private ErrorType error;

  public String getCount() {
    return count;
  }

  public void setCount(String count) {
    this.count = count;
  }

  public ErrorType getError() {
    return error;
  }

  public void setError(ErrorType error) {
    this.error = error;
  }


}
