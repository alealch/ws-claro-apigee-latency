package pe.com.claro.post.activaciones.plume.canonical.type;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.ErrorType;

public class EquipoCEType {

  private String backhaulDhcpPoolIdx;
  private String createdAt;
  private String boxSerialNumber;
  private String cartonId;
  private boolean claimKeyRequired;
  private String locationId;
  private String deployment;
  private String customerId;
  private List<KvConfigsCEType> kvConfigs;
  private String ethernet1Mac;
  private EthernetLanCEType ethernetLan;
  private String mac;
  private String ethernetMac;
  @SerializedName("model")
  @Expose
  private String modelo;
  private String firmwareVersion;
  private String partnerId;
  @SerializedName("serialNumber")
  @Expose
  private String numeroSerie;
  private String partNumber;
  private String purchaseOrderNumber;
  @SerializedName("_version")
  @Expose
  private String version;
  private boolean subscriptionRequired;
  private String radioMac24;
  private String id;
  private String radioMac50;
  private boolean residentialGateway;
  private String shardNumber;
  private String packId;
  private String shipDate;
  private String subscriptionRequiredTerm;
  private String timestamp;
  private boolean unclaimable;
  private String updatedAt;
  private ErrorType error;

  public String getBackhaulDhcpPoolIdx() {
    return backhaulDhcpPoolIdx;
  }

  public void setBackhaulDhcpPoolIdx(String backhaulDhcpPoolIdx) {
    this.backhaulDhcpPoolIdx = backhaulDhcpPoolIdx;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public String getBoxSerialNumber() {
    return boxSerialNumber;
  }

  public void setBoxSerialNumber(String boxSerialNumber) {
    this.boxSerialNumber = boxSerialNumber;
  }

  public String getCartonId() {
    return cartonId;
  }

  public void setCartonId(String cartonId) {
    this.cartonId = cartonId;
  }

  public boolean isClaimKeyRequired() {
    return claimKeyRequired;
  }

  public void setClaimKeyRequired(boolean claimKeyRequired) {
    this.claimKeyRequired = claimKeyRequired;
  }

  public String getLocationId() {
    return locationId;
  }

  public void setLocationId(String locationId) {
    this.locationId = locationId;
  }

  public String getDeployment() {
    return deployment;
  }

  public void setDeployment(String deployment) {
    this.deployment = deployment;
  }

  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public List<KvConfigsCEType> getKvConfigs() {
    return kvConfigs;
  }

  public void setKvConfigs(List<KvConfigsCEType> kvConfigs) {
    this.kvConfigs = kvConfigs;
  }

  public String getEthernet1Mac() {
    return ethernet1Mac;
  }

  public void setEthernet1Mac(String ethernet1Mac) {
    this.ethernet1Mac = ethernet1Mac;
  }

  public EthernetLanCEType getEthernetLan() {
    return ethernetLan;
  }

  public void setEthernetLan(EthernetLanCEType ethernetLan) {
    this.ethernetLan = ethernetLan;
  }

  public String getMac() {
    return mac;
  }

  public void setMac(String mac) {
    this.mac = mac;
  }

  public String getEthernetMac() {
    return ethernetMac;
  }

  public void setEthernetMac(String ethernetMac) {
    this.ethernetMac = ethernetMac;
  }

  public String getModelo() {
    return modelo;
  }

  public void setModelo(String modelo) {
    this.modelo = modelo;
  }

  public String getFirmwareVersion() {
    return firmwareVersion;
  }

  public void setFirmwareVersion(String firmwareVersion) {
    this.firmwareVersion = firmwareVersion;
  }

  public String getPartnerId() {
    return partnerId;
  }

  public void setPartnerId(String partnerId) {
    this.partnerId = partnerId;
  }

  public String getNumeroSerie() {
    return numeroSerie;
  }

  public void setNumeroSerie(String numeroSerie) {
    this.numeroSerie = numeroSerie;
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

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public boolean isSubscriptionRequired() {
    return subscriptionRequired;
  }

  public void setSubscriptionRequired(boolean subscriptionRequired) {
    this.subscriptionRequired = subscriptionRequired;
  }

  public String getRadioMac24() {
    return radioMac24;
  }

  public void setRadioMac24(String radioMac24) {
    this.radioMac24 = radioMac24;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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

  public String getShardNumber() {
    return shardNumber;
  }

  public void setShardNumber(String shardNumber) {
    this.shardNumber = shardNumber;
  }

  public String getPackId() {
    return packId;
  }

  public void setPackId(String packId) {
    this.packId = packId;
  }

  public String getShipDate() {
    return shipDate;
  }

  public void setShipDate(String shipDate) {
    this.shipDate = shipDate;
  }

  public String getSubscriptionRequiredTerm() {
    return subscriptionRequiredTerm;
  }

  public void setSubscriptionRequiredTerm(String subscriptionRequiredTerm) {
    this.subscriptionRequiredTerm = subscriptionRequiredTerm;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public boolean isUnclaimable() {
    return unclaimable;
  }

  public void setUnclaimable(boolean unclaimable) {
    this.unclaimable = unclaimable;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  public ErrorType getError() {
    return error;
  }

  public void setError(ErrorType error) {
    this.error = error;
  }
}
