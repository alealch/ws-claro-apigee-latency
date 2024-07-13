package pe.com.claro.post.activaciones.plume.integration.ws.bean.type;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ObtenerUbicacionResponseType {

  private String id;
  private IPV6 ipv6;
  private String networkMode;
  private String name;
  private String accountId;
  private String partnerId;
  private List<String> groupIds;
  private String serviceId;
  private String profile;
  private String mDNSUniqueIdentifier;
  private List<FreezeTemplate> freezeTemplates;
  private ServiceLevel serviceLevel;
  private String homeSecurityPriority;
  private boolean appTime;
  private String createdAt;
  private String updatedAt;
  private List<Object> stepFunctionTimers;
  @SerializedName("_version")
  @Expose(serialize = true, deserialize = true)
  private String version;
  private List<Object> groupOfUnassignedDevicesFreezeTemplates;
  private List<Object> groupOfUnassignedDevicesFreezeSchedules;
  private boolean isUtilizingSharedLocationFreezeSchedules;
  private boolean uprise;
  private boolean flex;
  private boolean wifiNetworkImportedFromGateway;
  private String customerId;
  private ISPSpeedTestConfiguration ispSpeedTestConfiguration;
  private BandSteering bandSteering;
  private ClientSteering clientSteering;
  private ControlMode controlMode;
  private MonitorMode monitorMode;
  private Backhaul backhaul;
  private Optimizations optimizations;
  private DPPConfiguration dppConfiguration;
  private HAAHSConfiguration haahsConfiguration;
  private String onboardedAt;
  private int basicServiceNodeClaimCount;
  private List<FeatureConfigIds> featureConfigIds;
  private String partnerName;
  private AppTimeConfig appTimeConfig;
  private Optimization optimization;
  private Object ssid;
  @JsonProperty("error")
  private ErrorType error;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public IPV6 getIpv6() {
    return ipv6;
  }

  public void setIpv6(IPV6 ipv6) {
    this.ipv6 = ipv6;
  }

  public String getNetworkMode() {
    return networkMode;
  }

  public void setNetworkMode(String networkMode) {
    this.networkMode = networkMode;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  public String getPartnerId() {
    return partnerId;
  }

  public void setPartnerId(String partnerId) {
    this.partnerId = partnerId;
  }

  public List<String> getGroupIds() {
    return groupIds;
  }

  public void setGroupIds(List<String> groupIds) {
    this.groupIds = groupIds;
  }

  public String getServiceId() {
    return serviceId;
  }

  public void setServiceId(String serviceId) {
    this.serviceId = serviceId;
  }

  public String getProfile() {
    return profile;
  }

  public void setProfile(String profile) {
    this.profile = profile;
  }

  public String getmDNSUniqueIdentifier() {
    return mDNSUniqueIdentifier;
  }

  public void setmDNSUniqueIdentifier(String mDNSUniqueIdentifier) {
    this.mDNSUniqueIdentifier = mDNSUniqueIdentifier;
  }

  public List<FreezeTemplate> getFreezeTemplates() {
    return freezeTemplates;
  }

  public void setFreezeTemplates(List<FreezeTemplate> freezeTemplates) {
    this.freezeTemplates = freezeTemplates;
  }

  public ServiceLevel getServiceLevel() {
    return serviceLevel;
  }

  public void setServiceLevel(ServiceLevel serviceLevel) {
    this.serviceLevel = serviceLevel;
  }

  public String getHomeSecurityPriority() {
    return homeSecurityPriority;
  }

  public void setHomeSecurityPriority(String homeSecurityPriority) {
    this.homeSecurityPriority = homeSecurityPriority;
  }

  public boolean isAppTime() {
    return appTime;
  }

  public void setAppTime(boolean appTime) {
    this.appTime = appTime;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  public List<Object> getStepFunctionTimers() {
    return stepFunctionTimers;
  }

  public void setStepFunctionTimers(List<Object> stepFunctionTimers) {
    this.stepFunctionTimers = stepFunctionTimers;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public List<Object> getGroupOfUnassignedDevicesFreezeTemplates() {
    return groupOfUnassignedDevicesFreezeTemplates;
  }

  public void setGroupOfUnassignedDevicesFreezeTemplates(List<Object> groupOfUnassignedDevicesFreezeTemplates) {
    this.groupOfUnassignedDevicesFreezeTemplates = groupOfUnassignedDevicesFreezeTemplates;
  }

  public List<Object> getGroupOfUnassignedDevicesFreezeSchedules() {
    return groupOfUnassignedDevicesFreezeSchedules;
  }

  public void setGroupOfUnassignedDevicesFreezeSchedules(List<Object> groupOfUnassignedDevicesFreezeSchedules) {
    this.groupOfUnassignedDevicesFreezeSchedules = groupOfUnassignedDevicesFreezeSchedules;
  }

  public boolean isUtilizingSharedLocationFreezeSchedules() {
    return isUtilizingSharedLocationFreezeSchedules;
  }

  public void setUtilizingSharedLocationFreezeSchedules(boolean isUtilizingSharedLocationFreezeSchedules) {
    this.isUtilizingSharedLocationFreezeSchedules = isUtilizingSharedLocationFreezeSchedules;
  }

  public boolean isUprise() {
    return uprise;
  }

  public void setUprise(boolean uprise) {
    this.uprise = uprise;
  }

  public boolean isFlex() {
    return flex;
  }

  public void setFlex(boolean flex) {
    this.flex = flex;
  }

  public boolean isWifiNetworkImportedFromGateway() {
    return wifiNetworkImportedFromGateway;
  }

  public void setWifiNetworkImportedFromGateway(boolean wifiNetworkImportedFromGateway) {
    this.wifiNetworkImportedFromGateway = wifiNetworkImportedFromGateway;
  }

  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public ISPSpeedTestConfiguration getIspSpeedTestConfiguration() {
    return ispSpeedTestConfiguration;
  }

  public void setIspSpeedTestConfiguration(ISPSpeedTestConfiguration ispSpeedTestConfiguration) {
    this.ispSpeedTestConfiguration = ispSpeedTestConfiguration;
  }

  public BandSteering getBandSteering() {
    return bandSteering;
  }

  public void setBandSteering(BandSteering bandSteering) {
    this.bandSteering = bandSteering;
  }

  public ClientSteering getClientSteering() {
    return clientSteering;
  }

  public void setClientSteering(ClientSteering clientSteering) {
    this.clientSteering = clientSteering;
  }

  public ControlMode getControlMode() {
    return controlMode;
  }

  public void setControlMode(ControlMode controlMode) {
    this.controlMode = controlMode;
  }

  public MonitorMode getMonitorMode() {
    return monitorMode;
  }

  public void setMonitorMode(MonitorMode monitorMode) {
    this.monitorMode = monitorMode;
  }

  public Backhaul getBackhaul() {
    return backhaul;
  }

  public void setBackhaul(Backhaul backhaul) {
    this.backhaul = backhaul;
  }

  public Optimizations getOptimizations() {
    return optimizations;
  }

  public void setOptimizations(Optimizations optimizations) {
    this.optimizations = optimizations;
  }

  public DPPConfiguration getDppConfiguration() {
    return dppConfiguration;
  }

  public void setDppConfiguration(DPPConfiguration dppConfiguration) {
    this.dppConfiguration = dppConfiguration;
  }

  public HAAHSConfiguration getHaahsConfiguration() {
    return haahsConfiguration;
  }

  public void setHaahsConfiguration(HAAHSConfiguration haahsConfiguration) {
    this.haahsConfiguration = haahsConfiguration;
  }

  public String getOnboardedAt() {
    return onboardedAt;
  }

  public void setOnboardedAt(String onboardedAt) {
    this.onboardedAt = onboardedAt;
  }

  public int getBasicServiceNodeClaimCount() {
    return basicServiceNodeClaimCount;
  }

  public void setBasicServiceNodeClaimCount(int basicServiceNodeClaimCount) {
    this.basicServiceNodeClaimCount = basicServiceNodeClaimCount;
  }

  public List<FeatureConfigIds> getFeatureConfigIds() {
    return featureConfigIds;
  }

  public void setFeatureConfigIds(List<FeatureConfigIds> featureConfigIds) {
    this.featureConfigIds = featureConfigIds;
  }

  public String getPartnerName() {
    return partnerName;
  }

  public void setPartnerName(String partnerName) {
    this.partnerName = partnerName;
  }

  public AppTimeConfig getAppTimeConfig() {
    return appTimeConfig;
  }

  public void setAppTimeConfig(AppTimeConfig appTimeConfig) {
    this.appTimeConfig = appTimeConfig;
  }

  public Optimization getOptimization() {
    return optimization;
  }

  public void setOptimization(Optimization optimization) {
    this.optimization = optimization;
  }

  public Object getSsid() {
    return ssid;
  }

  public void setSsid(Object ssid) {
    this.ssid = ssid;
  }

  public ErrorType getError() {
    return error;
  }

  public void setError(ErrorType error) {
    this.error = error;
  }


}
