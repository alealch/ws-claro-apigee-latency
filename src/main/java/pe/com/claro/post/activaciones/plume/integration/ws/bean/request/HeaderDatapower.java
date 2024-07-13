package pe.com.claro.post.activaciones.plume.integration.ws.bean.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HeaderDatapower {

  @SerializedName("HeaderRequest")
  @Expose
  private HeaderDPRequest headerRequest;

  public HeaderDPRequest getHeaderRequest() {
    return headerRequest;
  }

  public void setHeaderRequest(HeaderDPRequest headerRequest) {
    this.headerRequest = headerRequest;
  }

}
