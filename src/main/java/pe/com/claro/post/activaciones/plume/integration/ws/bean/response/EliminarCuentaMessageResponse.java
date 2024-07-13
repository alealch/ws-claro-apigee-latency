package pe.com.claro.post.activaciones.plume.integration.ws.bean.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EliminarCuentaMessageResponse {
  @SerializedName("Header")
  @Expose
  private Header header;

  @SerializedName("Body")
  @Expose
  private EliminarCuentaBodyResponse body;

  public Header getHeader() {
    return header;
  }

  public void setHeader(Header header) {
    this.header = header;
  }

  public EliminarCuentaBodyResponse getBody() {
    return body;
  }

  public void setBody(EliminarCuentaBodyResponse body) {
    this.body = body;
  }

}
