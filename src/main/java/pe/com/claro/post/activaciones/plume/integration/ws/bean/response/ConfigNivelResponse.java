package pe.com.claro.post.activaciones.plume.integration.ws.bean.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConfigNivelResponse {

  @SerializedName("MessageResponse")
  @Expose
  ConfigNivelMessageResponse messageResponse;

  public ConfigNivelMessageResponse getMessageResponse() {
    return messageResponse;
  }

  public void setMessageResponse(ConfigNivelMessageResponse messageResponse) {
    this.messageResponse = messageResponse;
  }

}
