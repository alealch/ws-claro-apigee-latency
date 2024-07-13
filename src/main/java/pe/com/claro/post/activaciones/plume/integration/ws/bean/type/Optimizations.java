package pe.com.claro.post.activaciones.plume.integration.ws.bean.type;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Optimizations {
  @SerializedName("_version")
  @Expose(serialize = true, deserialize = true)
  private String version;
  private boolean auto;
  private boolean enable;
  private String dfsMode;
  private String prefer160MhzMode;
  private String hopPenalty;
  private String preCACScheduler;
  private String updatedAt;
  private String zeroWaitDfsMode;

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public boolean isAuto() {
    return auto;
  }

  public void setAuto(boolean auto) {
    this.auto = auto;
  }

  public boolean isEnable() {
    return enable;
  }

  public void setEnable(boolean enable) {
    this.enable = enable;
  }

  public String getDfsMode() {
    return dfsMode;
  }

  public void setDfsMode(String dfsMode) {
    this.dfsMode = dfsMode;
  }

  public String getPrefer160MhzMode() {
    return prefer160MhzMode;
  }

  public void setPrefer160MhzMode(String prefer160MhzMode) {
    this.prefer160MhzMode = prefer160MhzMode;
  }

  public String getHopPenalty() {
    return hopPenalty;
  }

  public void setHopPenalty(String hopPenalty) {
    this.hopPenalty = hopPenalty;
  }

  public String getPreCACScheduler() {
    return preCACScheduler;
  }

  public void setPreCACScheduler(String preCACScheduler) {
    this.preCACScheduler = preCACScheduler;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  public String getZeroWaitDfsMode() {
    return zeroWaitDfsMode;
  }

  public void setZeroWaitDfsMode(String zeroWaitDfsMode) {
    this.zeroWaitDfsMode = zeroWaitDfsMode;
  }

}
