package pe.com.claro.post.activaciones.plume.integration.ws.bean.type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Fault {
  @SerializedName("detail")
  @Expose
  private DetailType detail;
  @SerializedName("faultactor")
  @Expose
  private String faultactor;
  @SerializedName("faultcode")
  @Expose
  private String faultcode;
  @SerializedName("faultstring")
  @Expose
  private String faultstring;

  public DetailType getDetail() {
    return detail;
  }

  public void setDetail(DetailType detail) {
    this.detail = detail;
  }

  public String getFaultactor() {
    return faultactor;
  }

  public void setFaultactor(String faultactor) {
    this.faultactor = faultactor;
  }

  public String getFaultcode() {
    return faultcode;
  }

  public void setFaultcode(String faultcode) {
    this.faultcode = faultcode;
  }

  public String getFaultstring() {
    return faultstring;
  }

  public void setFaultstring(String faultstring) {
    this.faultstring = faultstring;
  }

}
