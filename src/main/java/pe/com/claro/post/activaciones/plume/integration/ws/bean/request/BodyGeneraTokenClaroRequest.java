package pe.com.claro.post.activaciones.plume.integration.ws.bean.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BodyGeneraTokenClaroRequest {

  @SerializedName("client_id")
  @Expose
  private String clientId;

  @SerializedName("client_secret")
  @Expose
  private String clientSecret;

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public String getClientSecret() {
    return clientSecret;
  }

  public void setClientSecret(String clientSecret) {
    this.clientSecret = clientSecret;
  }


}
