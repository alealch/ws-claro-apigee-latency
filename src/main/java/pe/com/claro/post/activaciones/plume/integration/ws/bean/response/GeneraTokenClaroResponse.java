package pe.com.claro.post.activaciones.plume.integration.ws.bean.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeneraTokenClaroResponse {

  @SerializedName("MessageResponse")
  @Expose
  private GeneraTokenClaroMessageResponse messageResponse;

  public GeneraTokenClaroMessageResponse getMessageResponse() {
    return messageResponse;
  }

  public void setMessageResponse(GeneraTokenClaroMessageResponse messageResponse) {
    this.messageResponse = messageResponse;
  }

}
