package pe.com.claro.post.activaciones.plume.integration.ws.bean.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CrearCuentaMessageRequest {

  @SerializedName("Header")
  @Expose
  private HeaderDatapower header;

  @SerializedName("Body")
  @Expose
  private BodyCrearCuentaRequest body;

  public HeaderDatapower getHeader() {
    return header;
  }

  public void setHeader(HeaderDatapower header) {
    this.header = header;
  }

  public BodyCrearCuentaRequest getBody() {
    return body;
  }

  public void setBody(BodyCrearCuentaRequest body) {
    this.body = body;
  }

}
