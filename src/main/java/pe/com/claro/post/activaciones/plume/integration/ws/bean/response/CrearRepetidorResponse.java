package pe.com.claro.post.activaciones.plume.integration.ws.bean.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CrearRepetidorResponse {

  @SerializedName("MessageResponse")
  @Expose
  private CrearRepetidorMessageResponse messageResponse;

  public CrearRepetidorMessageResponse getMessageResponse() {
    return messageResponse;
  }

  public void setMessageResponse(CrearRepetidorMessageResponse messageResponse) {
    this.messageResponse = messageResponse;
  }


}
