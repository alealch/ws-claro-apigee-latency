package pe.com.claro.post.activaciones.plume.integration.ws.bean.response;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.ErrorType;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.KvConfigType;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.VendorType;

public class ConsultarRepetidorBodyResponse {

  @SerializedName("cartonId")
  @Expose
  private String cartonId;
  @SerializedName("boxSerialNumber")
  @Expose
  private String boxSerialNumber;
  @SerializedName("kvConfigs")
  @Expose
  private List<KvConfigType> kvConfigs;
  @SerializedName("ethernetMac")
  @Expose
  private String ethernetMac;
  @SerializedName("deployment")
  @Expose
  private String deployment;
  @SerializedName("groupIds")
  @Expose
  private List<String> groupIds;
  @SerializedName("firmwareVersion")
  @Expose
  private String firmwareVersion;
  @SerializedName("customerId")
  @Expose
  private String customerId;
  @SerializedName("locationId")
  @Expose
  private String locationId;
  @SerializedName("id")
  @Expose
  private String id;
  @SerializedName("packId")
  @Expose
  private String packId;
  @SerializedName("model")
  @Expose
  private String model;
  @SerializedName("claimKeyRequired")
  @Expose
  private boolean claimKeyRequired;
  @SerializedName("partNumber")
  @Expose
  private String partNumber;
  @SerializedName("radioMac50")
  @Expose
  private String radioMac50;
  @SerializedName("partnerId")
  @Expose
  private String partnerId;
  @SerializedName("purchaseOrderNumber")
  @Expose
  private String purchaseOrderNumber;
  @SerializedName("radioMac24")
  @Expose
  private String radioMac24;
  @SerializedName("residentialGateway")
  @Expose
  private boolean residentialGateway;
  @SerializedName("shipDate")
  @Expose
  private String shipDate;
  @SerializedName("serialNumber")
  @Expose
  private String serialNumber;
  @SerializedName("shardNumber")
  @Expose
  private String shardNumber;
  @SerializedName("subscriptionRequired")
  @Expose
  private boolean subscriptionRequired;
  @SerializedName("updatedAt")
  @Expose
  private String updatedAt;
  @SerializedName("subscriptionRequiredTerm")
  @Expose
  private String subscriptionRequiredTerm;
  @SerializedName("unclaimable")
  @Expose
  private boolean unclaimable;
  @SerializedName("vendor")
  @Expose
  private VendorType vendor;
  @SerializedName("error")
  @Expose
  private ErrorType error;
  @SerializedName("createdAt")
  @Expose
  private String createdAt;

  public String getCartonId() {
    return cartonId;
  }

  public void setCartonId(String cartonId) {
    this.cartonId = cartonId;
  }

  public String getBoxSerialNumber() {
    return boxSerialNumber;
  }

  public void setBoxSerialNumber(String boxSerialNumber) {
    this.boxSerialNumber = boxSerialNumber;
  }

  public List<KvConfigType> getKvConfigs() {
    return kvConfigs;
  }

  public void setKvConfigs(List<KvConfigType> kvConfigs) {
    this.kvConfigs = kvConfigs;
  }

  public String getEthernetMac() {
    return ethernetMac;
  }

  public void setEthernetMac(String ethernetMac) {
    this.ethernetMac = ethernetMac;
  }

  public String getDeployment() {
    return deployment;
  }

  public void setDeployment(String deployment) {
    this.deployment = deployment;
  }

  public List<String> getGroupIds() {
    return groupIds;
  }

  public void setGroupIds(List<String> groupIds) {
    this.groupIds = groupIds;
  }

  public String getFirmwareVersion() {
    return firmwareVersion;
  }

  public void setFirmwareVersion(String firmwareVersion) {
    this.firmwareVersion = firmwareVersion;
  }

  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public String getLocationId() {
    return locationId;
  }

  public void setLocationId(String locationId) {
    this.locationId = locationId;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getPackId() {
    return packId;
  }

  public void setPackId(String packId) {
    this.packId = packId;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public boolean isClaimKeyRequired() {
    return claimKeyRequired;
  }

  public void setClaimKeyRequired(boolean claimKeyRequired) {
    this.claimKeyRequired = claimKeyRequired;
  }

  public String getPartNumber() {
    return partNumber;
  }

  public void setPartNumber(String partNumber) {
    this.partNumber = partNumber;
  }

  public String getRadioMac50() {
    return radioMac50;
  }

  public void setRadioMac50(String radioMac50) {
    this.radioMac50 = radioMac50;
  }

  public String getPartnerId() {
    return partnerId;
  }

  public void setPartnerId(String partnerId) {
    this.partnerId = partnerId;
  }

  public String getPurchaseOrderNumber() {
    return purchaseOrderNumber;
  }

  public void setPurchaseOrderNumber(String purchaseOrderNumber) {
    this.purchaseOrderNumber = purchaseOrderNumber;
  }

  public String getRadioMac24() {
    return radioMac24;
  }

  public void setRadioMac24(String radioMac24) {
    this.radioMac24 = radioMac24;
  }

  public boolean isResidentialGateway() {
    return residentialGateway;
  }

  public void setResidentialGateway(boolean residentialGateway) {
    this.residentialGateway = residentialGateway;
  }

  public String getShipDate() {
    return shipDate;
  }

  public void setShipDate(String shipDate) {
    this.shipDate = shipDate;
  }

  public String getSerialNumber() {
    return serialNumber;
  }

  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  public String getShardNumber() {
    return shardNumber;
  }

  public void setShardNumber(String shardNumber) {
    this.shardNumber = shardNumber;
  }

  public boolean isSubscriptionRequired() {
    return subscriptionRequired;
  }

  public void setSubscriptionRequired(boolean subscriptionRequired) {
    this.subscriptionRequired = subscriptionRequired;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  public String getSubscriptionRequiredTerm() {
    return subscriptionRequiredTerm;
  }

  public void setSubscriptionRequiredTerm(String subscriptionRequiredTerm) {
    this.subscriptionRequiredTerm = subscriptionRequiredTerm;
  }

  public boolean isUnclaimable() {
    return unclaimable;
  }

  public void setUnclaimable(boolean unclaimable) {
    this.unclaimable = unclaimable;
  }

  public VendorType getVendor() {
    return vendor;
  }

  public void setVendor(VendorType vendor) {
    this.vendor = vendor;
  }

  public ErrorType getError() {
    return error;
  }

  public void setError(ErrorType error) {
    this.error = error;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }
}
