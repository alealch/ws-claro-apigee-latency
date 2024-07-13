package pe.com.claro.post.activaciones.plume.canonical.type;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EthernetLanCEType {

  @SerializedName("default")
  @Expose
  private DefaultCEType defaultCE;

  public DefaultCEType getDefaultCE() {
    return defaultCE;
  }

  public void setDefaultCE(DefaultCEType defaultCE) {
    this.defaultCE = defaultCE;
  }

}
