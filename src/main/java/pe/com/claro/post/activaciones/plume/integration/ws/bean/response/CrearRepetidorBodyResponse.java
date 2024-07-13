package pe.com.claro.post.activaciones.plume.integration.ws.bean.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.DeviceType;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.ErrorType;

public class CrearRepetidorBodyResponse {

  @JsonProperty("claimed")
  private List<DeviceType> claimed;

  @SerializedName("error")
  @Expose
  private ErrorType error;


  public List<DeviceType> getClaimed() {
    return claimed;
  }

  public void setClaimed(List<DeviceType> claimed) {
    this.claimed = claimed;
  }

  public ErrorType getError() {
    return error;
  }

  public void setError(ErrorType error) {
    this.error = error;
  }

}
