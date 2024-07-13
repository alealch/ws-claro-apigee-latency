package pe.com.claro.post.activaciones.plume.integration.ws.bean.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EliminarUbicacionResponse {

  @SerializedName("MessageResponse")
  @Expose
  private EliminarUbicacionMessageResponse messageResponse;

  public EliminarUbicacionMessageResponse getMessageResponse() {
    return messageResponse;
  }

  public void setMessageResponse(EliminarUbicacionMessageResponse messageResponse) {
    this.messageResponse = messageResponse;
  }

}
