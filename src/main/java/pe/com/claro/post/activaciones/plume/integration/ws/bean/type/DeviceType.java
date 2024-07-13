package pe.com.claro.post.activaciones.plume.integration.ws.bean.type;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeviceType {
  private String cartonId;
  private String boxSerialNumber;
  private String customerId;
  private boolean claimKeyRequired;
  private List<String> groupIds;
  private String ethernetMac;
  private String firmwareVersion;
  private String deployment;
  private String id;
  private VendorType vendor;
  private String locationId;
  private List<KvConfigType> kvConfigs;
  private String model;
  private String packId;
  private String partnerId;
  private boolean unclaimable;
  private String partNumber;
  private String purchaseOrderNumber;
  private String radioMac24;
  private String updatedAt;
  private boolean subscriptionRequired;
  private String radioMac50;
  private boolean residentialGateway;
  private String serialNumber;
  private String shipDate;
  private String shardNumber;
  private String subscriptionRequiredTerm;
  @SerializedName("_id")
  @Expose(serialize = true, deserialize = true)
  private String id2;
  private String createdAt;
  @SerializedName("_version")
  @Expose(serialize = true, deserialize = true)
  private String version;
  private int backhaulDhcpPoolIdx;
  @SerializedName("__v")
  @Expose(serialize = true, deserialize = true)
  private int v;

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

  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public boolean isClaimKeyRequired() {
    return claimKeyRequired;
  }

  public void setClaimKeyRequired(boolean claimKeyRequired) {
    this.claimKeyRequired = claimKeyRequired;
  }

  public List<String> getGroupIds() {
    return groupIds;
  }

  public void setGroupIds(List<String> groupIds) {
    this.groupIds = groupIds;
  }

  public String getEthernetMac() {
    return ethernetMac;
  }

  public void setEthernetMac(String ethernetMac) {
    this.ethernetMac = ethernetMac;
  }

  public String getFirmwareVersion() {
    return firmwareVersion;
  }

  public void setFirmwareVersion(String firmwareVersion) {
    this.firmwareVersion = firmwareVersion;
  }

  public String getDeployment() {
    return deployment;
  }

  public void setDeployment(String deployment) {
    this.deployment = deployment;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public VendorType getVendor() {
    return vendor;
  }

  public void setVendor(VendorType vendor) {
    this.vendor = vendor;
  }

  public String getLocationId() {
    return locationId;
  }

  public void setLocationId(String locationId) {
    this.locationId = locationId;
  }

  public List<KvConfigType> getKvConfigs() {
    return kvConfigs;
  }

  public void setKvConfigs(List<KvConfigType> kvConfigs) {
    this.kvConfigs = kvConfigs;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public String getPackId() {
    return packId;
  }

  public void setPackId(String packId) {
    this.packId = packId;
  }

  public String getPartnerId() {
    return partnerId;
  }

  public void setPartnerId(String partnerId) {
    this.partnerId = partnerId;
  }

  public boolean isUnclaimable() {
    return unclaimable;
  }

  public void setUnclaimable(boolean unclaimable) {
    this.unclaimable = unclaimable;
  }

  public String getPartNumber() {
    return partNumber;
  }

  public void setPartNumber(String partNumber) {
    this.partNumber = partNumber;
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

  public String getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  public boolean isSubscriptionRequired() {
    return subscriptionRequired;
  }

  public void setSubscriptionRequired(boolean subscriptionRequired) {
    this.subscriptionRequired = subscriptionRequired;
  }

  public String getRadioMac50() {
    return radioMac50;
  }

  public void setRadioMac50(String radioMac50) {
    this.radioMac50 = radioMac50;
  }

  public boolean isResidentialGateway() {
    return residentialGateway;
  }

  public void setResidentialGateway(boolean residentialGateway) {
    this.residentialGateway = residentialGateway;
  }

  public String getSerialNumber() {
    return serialNumber;
  }

  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  public String getShipDate() {
    return shipDate;
  }

  public void setShipDate(String shipDate) {
    this.shipDate = shipDate;
  }

  public String getShardNumber() {
    return shardNumber;
  }

  public void setShardNumber(String shardNumber) {
    this.shardNumber = shardNumber;
  }

  public String getSubscriptionRequiredTerm() {
    return subscriptionRequiredTerm;
  }

  public void setSubscriptionRequiredTerm(String subscriptionRequiredTerm) {
    this.subscriptionRequiredTerm = subscriptionRequiredTerm;
  }

  public String getId2() {
    return id2;
  }

  public void setId2(String id2) {
    this.id2 = id2;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public int getBackhaulDhcpPoolIdx() {
    return backhaulDhcpPoolIdx;
  }

  public void setBackhaulDhcpPoolIdx(int backhaulDhcpPoolIdx) {
    this.backhaulDhcpPoolIdx = backhaulDhcpPoolIdx;
  }

  public int getV() {
    return v;
  }

  public void setV(int v) {
    this.v = v;
  }
}
