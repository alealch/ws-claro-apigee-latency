package pe.com.claro.post.activaciones.plume.integration.ws.bean.type;

import java.util.List;

import pe.com.claro.post.activaciones.plume.canonical.type.EthernetLanCEType;

public class Node {
  private String id;
  private String connectionState;
  private String model;
  private String mac;
  private String nickname;
  private String backhaulType;
  private String defaultName;
  private String radioMac24;
  private String radioMac50;
  private String serialNumber;
  private String firmwareVersion;
  private int connectedDeviceCount;
  private String packId;
  private boolean subscriptionRequired;
  private String claimedAt;
  private boolean residentialGateway;
  private boolean hybrid;
  private EthernetLanCEType ethernetLan;
  private String shipDate;
  private String partNumber;
  private List<String> alerts;
  private List<String> puncturedChannels;
  private List<String> leafToRoot;
  private String health;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getConnectionState() {
    return connectionState;
  }

  public void setConnectionState(String connectionState) {
    this.connectionState = connectionState;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public String getMac() {
    return mac;
  }

  public void setMac(String mac) {
    this.mac = mac;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getBackhaulType() {
    return backhaulType;
  }

  public void setBackhaulType(String backhaulType) {
    this.backhaulType = backhaulType;
  }

  public String getDefaultName() {
    return defaultName;
  }

  public void setDefaultName(String defaultName) {
    this.defaultName = defaultName;
  }

  public String getRadioMac24() {
    return radioMac24;
  }

  public void setRadioMac24(String radioMac24) {
    this.radioMac24 = radioMac24;
  }

  public String getRadioMac50() {
    return radioMac50;
  }

  public void setRadioMac50(String radioMac50) {
    this.radioMac50 = radioMac50;
  }

  public String getSerialNumber() {
    return serialNumber;
  }

  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  public String getFirmwareVersion() {
    return firmwareVersion;
  }

  public void setFirmwareVersion(String firmwareVersion) {
    this.firmwareVersion = firmwareVersion;
  }

  public int getConnectedDeviceCount() {
    return connectedDeviceCount;
  }

  public void setConnectedDeviceCount(int connectedDeviceCount) {
    this.connectedDeviceCount = connectedDeviceCount;
  }

  public String getPackId() {
    return packId;
  }

  public void setPackId(String packId) {
    this.packId = packId;
  }

  public boolean isSubscriptionRequired() {
    return subscriptionRequired;
  }

  public void setSubscriptionRequired(boolean subscriptionRequired) {
    this.subscriptionRequired = subscriptionRequired;
  }

  public String getClaimedAt() {
    return claimedAt;
  }

  public void setClaimedAt(String claimedAt) {
    this.claimedAt = claimedAt;
  }

  public boolean isResidentialGateway() {
    return residentialGateway;
  }

  public void setResidentialGateway(boolean residentialGateway) {
    this.residentialGateway = residentialGateway;
  }

  public boolean isHybrid() {
    return hybrid;
  }

  public void setHybrid(boolean hybrid) {
    this.hybrid = hybrid;
  }

  public EthernetLanCEType getEthernetLan() {
    return ethernetLan;
  }

  public void setEthernetLan(EthernetLanCEType ethernetLan) {
    this.ethernetLan = ethernetLan;
  }

  public String getShipDate() {
    return shipDate;
  }

  public void setShipDate(String shipDate) {
    this.shipDate = shipDate;
  }

  public String getPartNumber() {
    return partNumber;
  }

  public void setPartNumber(String partNumber) {
    this.partNumber = partNumber;
  }

  public List<String> getAlerts() {
    return alerts;
  }

  public void setAlerts(List<String> alerts) {
    this.alerts = alerts;
  }

  public List<String> getPuncturedChannels() {
    return puncturedChannels;
  }

  public void setPuncturedChannels(List<String> puncturedChannels) {
    this.puncturedChannels = puncturedChannels;
  }

  public List<String> getLeafToRoot() {
    return leafToRoot;
  }

  public void setLeafToRoot(List<String> leafToRoot) {
    this.leafToRoot = leafToRoot;
  }

  public String getHealth() {
    return health;
  }

  public void setHealth(String health) {
    this.health = health;
  }

}
