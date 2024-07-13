package pe.com.claro.post.activaciones.plume.integration.ws.bean.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import pe.com.claro.post.activaciones.plume.canonical.type.EquipoCEType;

public class ConsultarRepetidorMessageResponse {

  @SerializedName("Header")
  @Expose
  private Header header;

  @SerializedName("Body")
  @Expose
  private EquipoCEType body;

  public Header getHeader() {
    return header;
  }

  public void setHeader(Header header) {
    this.header = header;
  }

  public EquipoCEType getBody() {
    return body;
  }

  public void setBody(EquipoCEType body) {
    this.body = body;
  }

}
