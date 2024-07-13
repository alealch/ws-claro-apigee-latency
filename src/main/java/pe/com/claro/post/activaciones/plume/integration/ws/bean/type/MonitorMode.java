package pe.com.claro.post.activaciones.plume.integration.ws.bean.type;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MonitorMode {
  @SerializedName("_version")
  @Expose(serialize = true, deserialize = true)
  private String version;
  private boolean enable;
  private String updatedAt;

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public boolean isEnable() {
    return enable;
  }

  public void setEnable(boolean enable) {
    this.enable = enable;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

}
