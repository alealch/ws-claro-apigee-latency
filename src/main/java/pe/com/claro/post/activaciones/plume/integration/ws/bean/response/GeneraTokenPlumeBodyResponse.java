package pe.com.claro.post.activaciones.plume.integration.ws.bean.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.ErrorType;

public class GeneraTokenPlumeBodyResponse {
  @SerializedName("access_token")
  @Expose
  private String accessToken;
  @SerializedName("expires_in")
  @Expose
  private Integer expiresIn;
  @SerializedName("token_type")
  @Expose
  private String tokenType;
  @SerializedName("scope")
  @Expose
  private String scope;
  @SerializedName("error")
  @Expose
  private ErrorType error;

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public Integer getExpiresIn() {
    return expiresIn;
  }

  public void setExpiresIn(Integer expiresIn) {
    this.expiresIn = expiresIn;
  }

  public String getTokenType() {
    return tokenType;
  }

  public void setTokenType(String tokenType) {
    this.tokenType = tokenType;
  }

  public String getScope() {
    return scope;
  }

  public void setScope(String scope) {
    this.scope = scope;
  }

  public ErrorType getError() {
    return error;
  }

  public void setError(ErrorType error) {
    this.error = error;
  }


}
