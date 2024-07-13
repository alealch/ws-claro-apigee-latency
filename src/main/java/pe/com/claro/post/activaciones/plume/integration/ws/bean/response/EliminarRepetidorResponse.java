package pe.com.claro.post.activaciones.plume.integration.ws.bean.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EliminarRepetidorResponse {

  @SerializedName("MessageResponse")
  @Expose
  private EliminarRepetidorMessageResponse messageResponse;

  public EliminarRepetidorMessageResponse getMessageResponse() {
    return messageResponse;
  }

  public void setMessageResponse(EliminarRepetidorMessageResponse messageResponse) {
    this.messageResponse = messageResponse;
  }

}
