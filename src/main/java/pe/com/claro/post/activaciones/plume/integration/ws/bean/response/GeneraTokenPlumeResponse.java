package pe.com.claro.post.activaciones.plume.integration.ws.bean.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeneraTokenPlumeResponse {

  @SerializedName("MessageResponse")
  @Expose
  private GeneraTokenPlumeMessageResponse messageResponse;

  public GeneraTokenPlumeMessageResponse getMessageResponse() {
    return messageResponse;
  }

  public void setMessageResponse(GeneraTokenPlumeMessageResponse messageResponse) {
    this.messageResponse = messageResponse;
  }

}
