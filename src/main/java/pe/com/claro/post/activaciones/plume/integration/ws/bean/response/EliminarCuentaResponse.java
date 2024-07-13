package pe.com.claro.post.activaciones.plume.integration.ws.bean.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class EliminarCuentaResponse {

  @SerializedName("MessageResponse")
  @Expose
  private EliminarCuentaMessageResponse messageResponse;

  public EliminarCuentaMessageResponse getMessageResponse() {
    return messageResponse;
  }

  public void setMessageResponse(EliminarCuentaMessageResponse messageResponse) {
    this.messageResponse = messageResponse;
  }

}
