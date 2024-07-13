package pe.com.claro.post.activaciones.plume.integration.ws.bean.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EliminarRepetidorMessageResponse {

  @SerializedName("Header")
  @Expose
  private Header header;

  @SerializedName("Body")
  @Expose
  private EliminarRepetidorBodyResponse body;

  public Header getHeader() {
    return header;
  }

  public void setHeader(Header header) {
    this.header = header;
  }

  public EliminarRepetidorBodyResponse getBody() {
    return body;
  }

  public void setBody(EliminarRepetidorBodyResponse body) {
    this.body = body;
  }

}
