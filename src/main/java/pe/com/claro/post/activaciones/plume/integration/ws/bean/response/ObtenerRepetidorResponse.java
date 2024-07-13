package pe.com.claro.post.activaciones.plume.integration.ws.bean.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ObtenerRepetidorResponse {

  @SerializedName("MessageResponse")
  @Expose
  private ObtenerRepetidorMessageResponse messageResponse;

  public ObtenerRepetidorMessageResponse getMessageResponse() {
    return messageResponse;
  }

  public void setMessageResponse(ObtenerRepetidorMessageResponse messageResponse) {
    this.messageResponse = messageResponse;
  }

}
