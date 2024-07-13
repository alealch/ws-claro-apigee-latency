package pe.com.claro.post.activaciones.plume.integration.ws.bean.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CrearUbicacionMessageRequest {

  @SerializedName("Header")
  @Expose
  private HeaderDatapower header;

  @SerializedName("Body")
  @Expose
  private BodyCrearUbicacionRequest body;

  public HeaderDatapower getHeader() {
    return header;
  }

  public void setHeader(HeaderDatapower header) {
    this.header = header;
  }

  public BodyCrearUbicacionRequest getBody() {
    return body;
  }

  public void setBody(BodyCrearUbicacionRequest body) {
    this.body = body;
  }

}
