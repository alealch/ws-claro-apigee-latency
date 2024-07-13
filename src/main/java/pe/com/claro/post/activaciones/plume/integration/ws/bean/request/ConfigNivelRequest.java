package pe.com.claro.post.activaciones.plume.integration.ws.bean.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfigNivelRequest {

  @SerializedName("MessageRequest")
  @Expose
  private ConfigNivelMessageRequest messageRequest;

  public ConfigNivelMessageRequest getMessageRequest() {
    return messageRequest;
  }

  public void setMessageRequest(ConfigNivelMessageRequest messageRequest) {
    this.messageRequest = messageRequest;
  }

}
