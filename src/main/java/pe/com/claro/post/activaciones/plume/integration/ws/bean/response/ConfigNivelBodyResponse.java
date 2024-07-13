package pe.com.claro.post.activaciones.plume.integration.ws.bean.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.Fault;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfigNivelBodyResponse {

  @SerializedName("Fault")
  @Expose
  private Fault fault;
  private boolean auto;
  private String updatedAt;
  private String status;
  private String createdAt;

  public Fault getFault() {
    return fault;
  }

  public void setFault(Fault fault) {
    this.fault = fault;
  }

  public boolean isAuto() {
    return auto;
  }

  public void setAuto(boolean auto) {
    this.auto = auto;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }
}
