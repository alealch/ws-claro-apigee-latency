package pe.com.claro.post.activaciones.plume.integration.ws.bean.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeneraTokenClaroMessageRequest {

  @SerializedName("Header")
  @Expose
  private HeaderDatapower header;

  @SerializedName("Body")
  @Expose
  private BodyGeneraTokenClaroRequest body;

  public HeaderDatapower getHeader() {
    return header;
  }

  public void setHeader(HeaderDatapower header) {
    this.header = header;
  }

  public BodyGeneraTokenClaroRequest getBody() {
    return body;
  }

  public void setBody(BodyGeneraTokenClaroRequest body) {
    this.body = body;
  }


}
