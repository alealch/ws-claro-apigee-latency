package pe.com.claro.post.activaciones.plume.integration.ws.bean.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.ErrorType;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.Fault;

public class CrearCuentaBodyResponse {
  @SerializedName("id")
  @Expose(serialize = true, deserialize = true)
  private String id;

  @SerializedName("accountId")
  @Expose(serialize = true, deserialize = true)
  private String accountId;

  private boolean anonymous;
  private boolean autoProvisioned;
  private String name;
  private boolean locked;

  @SerializedName("partnerId")
  @Expose(serialize = true, deserialize = true)
  private String partnerId;

  private String acceptLanguage;
  private String source;
  private String createdAt;
  private String provisioningSsoAuditTrail;
  @SerializedName("_version")
  @Expose(serialize = true, deserialize = true)
  private String version;
  private String email;

  @SerializedName("locationId")
  @Expose(serialize = true, deserialize = true)
  private String locationId;

  @SerializedName("error")
  @Expose
  private ErrorType error;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  public boolean isAnonymous() {
    return anonymous;
  }

  public void setAnonymous(boolean anonymous) {
    this.anonymous = anonymous;
  }

  public boolean isAutoProvisioned() {
    return autoProvisioned;
  }

  public void setAutoProvisioned(boolean autoProvisioned) {
    this.autoProvisioned = autoProvisioned;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isLocked() {
    return locked;
  }

  public void setLocked(boolean locked) {
    this.locked = locked;
  }

  public String getPartnerId() {
    return partnerId;
  }

  public void setPartnerId(String partnerId) {
    this.partnerId = partnerId;
  }

  public String getAcceptLanguage() {
    return acceptLanguage;
  }

  public void setAcceptLanguage(String acceptLanguage) {
    this.acceptLanguage = acceptLanguage;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public String getProvisioningSsoAuditTrail() {
    return provisioningSsoAuditTrail;
  }

  public void setProvisioningSsoAuditTrail(String provisioningSsoAuditTrail) {
    this.provisioningSsoAuditTrail = provisioningSsoAuditTrail;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getLocationId() {
    return locationId;
  }

  public void setLocationId(String locationId) {
    this.locationId = locationId;
  }

  public ErrorType getError() {
    return error;
  }

  public void setError(ErrorType error) {
    this.error = error;
  }


}
