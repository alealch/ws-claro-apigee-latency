package pe.com.claro.post.activaciones.plume.integration.ws.bean.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Header {
  @SerializedName("HeaderResponse")
  @Expose
  private HeaderResponse headerResponse;

  public HeaderResponse getHeaderResponse() {
    return headerResponse;
  }

  public void setHeaderResponse(HeaderResponse headerResponse) {
    this.headerResponse = headerResponse;
  }

}
