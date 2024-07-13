package pe.com.claro.post.activaciones.plume.integration.ws.bean.type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DetailType {
  @SerializedName("IntegrationError")
  @Expose
  private String integrationError;

  public String getIntegrationError() {
    return integrationError;
  }

  public void setIntegrationError(String integrationError) {
    this.integrationError = integrationError;
  }

}
