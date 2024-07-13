package pe.com.claro.post.activaciones.plume.integration.ws.bean.type;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Backhaul {
  @SerializedName("_version")
  @Expose(serialize = true, deserialize = true)
  private String version;
  private String mode;
  private String dynamicBeacon;
  private String wds;
  private String wpaMode;
  private String hitlessTopology;
  private String createdAt;

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getMode() {
    return mode;
  }

  public void setMode(String mode) {
    this.mode = mode;
  }

  public String getDynamicBeacon() {
    return dynamicBeacon;
  }

  public void setDynamicBeacon(String dynamicBeacon) {
    this.dynamicBeacon = dynamicBeacon;
  }

  public String getWds() {
    return wds;
  }

  public void setWds(String wds) {
    this.wds = wds;
  }

  public String getWpaMode() {
    return wpaMode;
  }

  public void setWpaMode(String wpaMode) {
    this.wpaMode = wpaMode;
  }

  public String getHitlessTopology() {
    return hitlessTopology;
  }

  public void setHitlessTopology(String hitlessTopology) {
    this.hitlessTopology = hitlessTopology;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

}
