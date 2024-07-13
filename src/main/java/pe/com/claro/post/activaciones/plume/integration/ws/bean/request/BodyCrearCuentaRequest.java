package pe.com.claro.post.activaciones.plume.integration.ws.bean.request;

public class BodyCrearCuentaRequest {

  private String accountId;
  private String email;
  private String name;
  private String partnerId;
  private String profile;
  private String acceptLanguage;
  private String onboardingCheckpoint;

  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPartnerId() {
    return partnerId;
  }

  public void setPartnerId(String partnerId) {
    this.partnerId = partnerId;
  }

  public String getProfile() {
    return profile;
  }

  public void setProfile(String profile) {
    this.profile = profile;
  }

  public String getAcceptLanguage() {
    return acceptLanguage;
  }

  public void setAcceptLanguage(String acceptLanguage) {
    this.acceptLanguage = acceptLanguage;
  }

  public String getOnboardingCheckpoint() {
    return onboardingCheckpoint;
  }

  public void setOnboardingCheckpoint(String onboardingCheckpoint) {
    this.onboardingCheckpoint = onboardingCheckpoint;
  }

}
