package pe.com.claro.post.activaciones.plume.integration.ws.bean.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CrearCuentaRequest {

  @SerializedName("MessageRequest")
  @Expose
  private CrearCuentaMessageRequest messageRequest;

  public CrearCuentaMessageRequest getMessageRequest() {
    return messageRequest;
  }

  public void setMessageRequest(CrearCuentaMessageRequest messageRequest) {
    this.messageRequest = messageRequest;
  }

}
