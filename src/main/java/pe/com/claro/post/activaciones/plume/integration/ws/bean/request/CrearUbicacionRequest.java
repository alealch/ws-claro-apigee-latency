package pe.com.claro.post.activaciones.plume.integration.ws.bean.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CrearUbicacionRequest {

  @SerializedName("MessageRequest")
  @Expose
  private CrearUbicacionMessageRequest messageRequest;

  public CrearUbicacionMessageRequest getMessageRequest() {
    return messageRequest;
  }

  public void setMessageRequest(CrearUbicacionMessageRequest messageRequest) {
    this.messageRequest = messageRequest;
  }

}
