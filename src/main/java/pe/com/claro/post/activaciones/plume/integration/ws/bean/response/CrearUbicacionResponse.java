package pe.com.claro.post.activaciones.plume.integration.ws.bean.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CrearUbicacionResponse {

  @SerializedName("MessageResponse")
  @Expose
  private CrearUbicacionMessageResponse messageResponse;

  public CrearUbicacionMessageResponse getMessageResponse() {
    return messageResponse;
  }

  public void setMessageResponse(CrearUbicacionMessageResponse messageResponse) {
    this.messageResponse = messageResponse;
  }

}
