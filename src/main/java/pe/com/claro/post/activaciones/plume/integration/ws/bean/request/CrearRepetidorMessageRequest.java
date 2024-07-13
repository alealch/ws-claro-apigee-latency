package pe.com.claro.post.activaciones.plume.integration.ws.bean.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CrearRepetidorMessageRequest {

  @SerializedName("Header")
  @Expose
  private HeaderDatapower header;

  @SerializedName("Body")
  @Expose
  private BodyCrearRepetidorRequest body;

  public HeaderDatapower getHeader() {
    return header;
  }

  public void setHeader(HeaderDatapower header) {
    this.header = header;
  }

  public BodyCrearRepetidorRequest getBody() {
    return body;
  }

  public void setBody(BodyCrearRepetidorRequest body) {
    this.body = body;
  }

}
