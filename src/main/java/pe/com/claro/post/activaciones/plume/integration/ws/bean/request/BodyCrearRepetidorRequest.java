package pe.com.claro.post.activaciones.plume.integration.ws.bean.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BodyCrearRepetidorRequest {

  @SerializedName("serialNumber")
  @Expose
  private String serialNumber;
  @SerializedName("nickname")
  @Expose
  private String nickName;

  public String getSerialNumber() {
    return serialNumber;
  }

  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }
}
