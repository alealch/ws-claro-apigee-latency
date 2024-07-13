package pe.com.claro.post.activaciones.plume.integration.ws.bean.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ObtenerUbicacionResponse {

  @SerializedName("MessageResponse")
  @Expose
  private ObtenerUbicacionMessageResponse messageResponse;

  public ObtenerUbicacionMessageResponse getMessageResponse() {
    return messageResponse;
  }

  public void setMessageResponse(ObtenerUbicacionMessageResponse messageResponse) {
    this.messageResponse = messageResponse;
  }

}
