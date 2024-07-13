package pe.com.claro.post.activaciones.plume.integration.ws.bean.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CrearCuentaResponse {

  @SerializedName("MessageResponse")
  @Expose
  private CrearCuentaMessageResponse messageResponse;

  public CrearCuentaMessageResponse getMessageResponse() {
    return messageResponse;
  }

  public void setMessageResponse(CrearCuentaMessageResponse messageResponse) {
    this.messageResponse = messageResponse;
  }

}
