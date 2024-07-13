package pe.com.claro.post.activaciones.plume.integration.ws.bean.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CrearRepetidorRequest {

  @SerializedName("MessageRequest")
  @Expose
  private CrearRepetidorMessageRequest messageRequest;

  public CrearRepetidorMessageRequest getMessageRequest() {
    return messageRequest;
  }

  public void setMessageRequest(CrearRepetidorMessageRequest messageRequest) {
    this.messageRequest = messageRequest;
  }

}
