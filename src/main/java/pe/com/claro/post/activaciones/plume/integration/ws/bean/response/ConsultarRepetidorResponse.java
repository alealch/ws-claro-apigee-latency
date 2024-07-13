package pe.com.claro.post.activaciones.plume.integration.ws.bean.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConsultarRepetidorResponse {
  @SerializedName("MessageResponse")
  @Expose
  private ConsultarRepetidorMessageResponse messageResponse;

  public ConsultarRepetidorMessageResponse getMessageResponse() {
    return messageResponse;
  }

  public void setMessageResponse(ConsultarRepetidorMessageResponse messageResponse) {
    this.messageResponse = messageResponse;
  }

}
