package pe.com.claro.post.activaciones.plume.integration.ws.bean.request;

public class EliminarRepetidorRequest {

  private String accountId;
  private String idLocation;
  private String serialNumber;

  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  public String getIdLocation() {
    return idLocation;
  }

  public void setIdLocation(String idLocation) {
    this.idLocation = idLocation;
  }

  public String getSerialNumber() {
    return serialNumber;
  }

  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }


}
