package pe.com.claro.post.activaciones.plume.integration.ws.bean.type;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BandSteering {
  private boolean auto;
  @SerializedName("_version")
  @Expose(serialize = true, deserialize = true)
  private String version;
  private String updatedAt;

  public boolean isAuto() {
    return auto;
  }

  public void setAuto(boolean auto) {
    this.auto = auto;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }
}
