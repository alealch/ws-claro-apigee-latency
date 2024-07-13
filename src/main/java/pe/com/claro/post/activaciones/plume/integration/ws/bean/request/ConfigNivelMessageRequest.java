package pe.com.claro.post.activaciones.plume.integration.ws.bean.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.BodyConfigNivelRequest;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfigNivelMessageRequest {

  @SerializedName("Header")
  @Expose
  private HeaderDatapower header;

  @SerializedName("Body")
  @Expose
  private BodyConfigNivelRequest body;

  public HeaderDatapower getHeader() {
    return header;
  }

  public void setHeader(HeaderDatapower header) {
    this.header = header;
  }

  public BodyConfigNivelRequest getBody() {
    return body;
  }

  public void setBody(BodyConfigNivelRequest body) {
    this.body = body;
  }

}
