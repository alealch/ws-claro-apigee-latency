package pe.com.claro.post.activaciones.plume.integration.ws.bean.response;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ObtenerUbicacionMessageResponse {

  @SerializedName("Header")
  @Expose
  private Header header;

  @SerializedName("Body")
  @Expose
  private List<ObtenerUbicacionBodyResponse> body;

  public Header getHeader() {
    return header;
  }

  public void setHeader(Header header) {
    this.header = header;
  }

  public List<ObtenerUbicacionBodyResponse> getBody() {
    return body;
  }

  public void setBody(List<ObtenerUbicacionBodyResponse> body) {
    this.body = body;
  }

}
