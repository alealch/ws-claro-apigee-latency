package pe.com.claro.post.activaciones.plume.integration.ws.bean.type;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ISPSpeedTestConfiguration {
  @SerializedName("_version")
  @Expose(serialize = true, deserialize = true)
  private String version;
  private boolean enable;
  private boolean enableAllNodes;
  private String createdAt;

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

  public boolean isEnableAllNodes() {
    return enableAllNodes;
  }

  public void setEnableAllNodes(boolean enableAllNodes) {
    this.enableAllNodes = enableAllNodes;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

}
