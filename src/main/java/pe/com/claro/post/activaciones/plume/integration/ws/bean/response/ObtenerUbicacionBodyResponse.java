package pe.com.claro.post.activaciones.plume.integration.ws.bean.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.AppTimeConfig;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.Backhaul;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.BandSteering;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.ClientSteering;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.ControlMode;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.DPPConfiguration;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.ErrorType;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.FeatureConfigIds;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.FreezeTemplate;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.HAAHSConfiguration;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.IPV6;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.ISPSpeedTestConfiguration;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.MonitorMode;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.Optimization;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.Optimizations;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.ServiceLevel;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ObtenerUbicacionBodyResponse {

  @SerializedName("groupIds")
  @Expose
  private List<String> groupIds;
  @SerializedName("partnerId")
  @Expose
  private String partnerId;
  @SerializedName("profile")
  @Expose
  private String profile;
  @SerializedName("id")
  @Expose
  private String id;
  @SerializedName("accountId")
  @Expose
  private String accountId;
  @SerializedName("freezeTemplates")
  @Expose
  private List<FreezeTemplate> freezeTemplates;
  @SerializedName("networkMode")
  @Expose
  private String networkMode;
  @SerializedName("serviceLevel")
  @Expose
  private ServiceLevel serviceLevel;
  @SerializedName("serviceId")
  @Expose
  private String serviceId;
  @SerializedName("ipv6")
  @Expose
  private IPV6 ipv6;
  @SerializedName("homeSecurityPriority")
  @Expose
  private String homeSecurityPriority;
  @SerializedName("createdAt")
  @Expose
  private String createdAt;
  @SerializedName("name")
  @Expose
  private String name;
  @SerializedName("updatedAt")
  @Expose
  private String updatedAt;
  @SerializedName("stepFunctionTimers")
  @Expose
  private List<Object> stepFunctionTimers;
  @SerializedName("groupOfUnassignedDevicesFreezeTemplates")
  @Expose
  private List<Object> groupOfUnassignedDevicesFreezeTemplates;
  @SerializedName("appTime")
  @Expose
  private boolean appTime;
  @SerializedName("groupOfUnassignedDevicesFreezeSchedules")
  @Expose
  private List<Object> groupOfUnassignedDevicesFreezeSchedules;
  @SerializedName("_version")
  @Expose
  private String version;
  @SerializedName("uprise")
  @Expose
  private boolean uprise;
  @SerializedName("mDNSUniqueIdentifier")
  @Expose
  private String mDNSUniqueIdentifier;
  @SerializedName("isUtilizingSharedLocationFreezeSchedules")
  @Expose
  private boolean isUtilizingSharedLocationFreezeSchedules;
  @SerializedName("wifiNetworkImportedFromGateway")
  @Expose
  private boolean wifiNetworkImportedFromGateway;
  @SerializedName("flex")
  @Expose
  private boolean flex;
  @SerializedName("customerId")
  @Expose
  private String customerId;
  @SerializedName("clientSteering")
  @Expose
  private ClientSteering clientSteering;
  @SerializedName("controlMode")
  @Expose
  private ControlMode controlMode;
  @JsonProperty("error")
  private ErrorType error;
  @SerializedName("bandSteering")
  @Expose
  private BandSteering bandSteering;
  @SerializedName("backhaul")
  @Expose
  private Backhaul backhaul;
  @SerializedName("monitorMode")
  @Expose
  private MonitorMode monitorMode;
  @SerializedName("ispSpeedTestConfiguration")
  @Expose
  private ISPSpeedTestConfiguration ispSpeedTestConfiguration;
  @SerializedName("dppConfiguration")
  @Expose
  private DPPConfiguration dppConfiguration;
  @SerializedName("optimizations")
  @Expose
  private Optimizations optimizations;
  @SerializedName("optimization")
  @Expose
  private Optimization optimization;
  @SerializedName("haahsConfiguration")
  @Expose
  private HAAHSConfiguration haahsConfiguration;
  @SerializedName("onboardedAt")
  @Expose
  private String onboardedAt;
  @SerializedName("ssid")
  @Expose
  private Object ssid;
  @SerializedName("basicServiceNodeClaimCount")
  @Expose
  private int basicServiceNodeClaimCount;
  @SerializedName("partnerName")
  @Expose
  private String partnerName;
  @SerializedName("featureConfigIds")
  @Expose
  private List<FeatureConfigIds> featureConfigIds;
  @SerializedName("appTimeConfig")
  @Expose
  private AppTimeConfig appTimeConfig;

  public List<String> getGroupIds() {
    return groupIds;
  }

  public void setGroupIds(List<String> groupIds) {
    this.groupIds = groupIds;
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

  public List<FreezeTemplate> getFreezeTemplates() {
    return freezeTemplates;
  }

  public void setFreezeTemplates(List<FreezeTemplate> freezeTemplates) {
    this.freezeTemplates = freezeTemplates;
  }

  public String getNetworkMode() {
    return networkMode;
  }

  public void setNetworkMode(String networkMode) {
    this.networkMode = networkMode;
  }

  public ServiceLevel getServiceLevel() {
    return serviceLevel;
  }

  public void setServiceLevel(ServiceLevel serviceLevel) {
    this.serviceLevel = serviceLevel;
  }

  public String getServiceId() {
    return serviceId;
  }

  public void setServiceId(String serviceId) {
    this.serviceId = serviceId;
  }

  public IPV6 getIpv6() {
    return ipv6;
  }

  public void setIpv6(IPV6 ipv6) {
    this.ipv6 = ipv6;
  }

  public String getHomeSecurityPriority() {
    return homeSecurityPriority;
  }

  public void setHomeSecurityPriority(String homeSecurityPriority) {
    this.homeSecurityPriority = homeSecurityPriority;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public List<Object> getGroupOfUnassignedDevicesFreezeTemplates() {
    return groupOfUnassignedDevicesFreezeTemplates;
  }

  public void setGroupOfUnassignedDevicesFreezeTemplates(List<Object> groupOfUnassignedDevicesFreezeTemplates) {
    this.groupOfUnassignedDevicesFreezeTemplates = groupOfUnassignedDevicesFreezeTemplates;
  }

  public boolean isAppTime() {
    return appTime;
  }

  public void setAppTime(boolean appTime) {
    this.appTime = appTime;
  }

  public List<Object> getGroupOfUnassignedDevicesFreezeSchedules() {
    return groupOfUnassignedDevicesFreezeSchedules;
  }

  public void setGroupOfUnassignedDevicesFreezeSchedules(List<Object> groupOfUnassignedDevicesFreezeSchedules) {
    this.groupOfUnassignedDevicesFreezeSchedules = groupOfUnassignedDevicesFreezeSchedules;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public boolean isUprise() {
    return uprise;
  }

  public void setUprise(boolean uprise) {
    this.uprise = uprise;
  }

  public String getmDNSUniqueIdentifier() {
    return mDNSUniqueIdentifier;
  }

  public void setmDNSUniqueIdentifier(String mDNSUniqueIdentifier) {
    this.mDNSUniqueIdentifier = mDNSUniqueIdentifier;
  }

  public boolean isUtilizingSharedLocationFreezeSchedules() {
    return isUtilizingSharedLocationFreezeSchedules;
  }

  public void setUtilizingSharedLocationFreezeSchedules(boolean utilizingSharedLocationFreezeSchedules) {
    isUtilizingSharedLocationFreezeSchedules = utilizingSharedLocationFreezeSchedules;
  }

  public boolean isWifiNetworkImportedFromGateway() {
    return wifiNetworkImportedFromGateway;
  }

  public void setWifiNetworkImportedFromGateway(boolean wifiNetworkImportedFromGateway) {
    this.wifiNetworkImportedFromGateway = wifiNetworkImportedFromGateway;
  }

  public boolean isFlex() {
    return flex;
  }

  public void setFlex(boolean flex) {
    this.flex = flex;
  }

  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
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

  public ErrorType getError() {
    return error;
  }

  public void setError(ErrorType error) {
    this.error = error;
  }

  public BandSteering getBandSteering() {
    return bandSteering;
  }

  public void setBandSteering(BandSteering bandSteering) {
    this.bandSteering = bandSteering;
  }

  public Backhaul getBackhaul() {
    return backhaul;
  }

  public void setBackhaul(Backhaul backhaul) {
    this.backhaul = backhaul;
  }

  public MonitorMode getMonitorMode() {
    return monitorMode;
  }

  public void setMonitorMode(MonitorMode monitorMode) {
    this.monitorMode = monitorMode;
  }

  public ISPSpeedTestConfiguration getIspSpeedTestConfiguration() {
    return ispSpeedTestConfiguration;
  }

  public void setIspSpeedTestConfiguration(ISPSpeedTestConfiguration ispSpeedTestConfiguration) {
    this.ispSpeedTestConfiguration = ispSpeedTestConfiguration;
  }

  public DPPConfiguration getDppConfiguration() {
    return dppConfiguration;
  }

  public void setDppConfiguration(DPPConfiguration dppConfiguration) {
    this.dppConfiguration = dppConfiguration;
  }

  public Optimizations getOptimizations() {
    return optimizations;
  }

  public void setOptimizations(Optimizations optimizations) {
    this.optimizations = optimizations;
  }

  public Optimization getOptimization() {
    return optimization;
  }

  public void setOptimization(Optimization optimization) {
    this.optimization = optimization;
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

  public Object getSsid() {
    return ssid;
  }

  public void setSsid(Object ssid) {
    this.ssid = ssid;
  }

  public int getBasicServiceNodeClaimCount() {
    return basicServiceNodeClaimCount;
  }

  public void setBasicServiceNodeClaimCount(int basicServiceNodeClaimCount) {
    this.basicServiceNodeClaimCount = basicServiceNodeClaimCount;
  }

  public String getPartnerName() {
    return partnerName;
  }

  public void setPartnerName(String partnerName) {
    this.partnerName = partnerName;
  }

  public List<FeatureConfigIds> getFeatureConfigIds() {
    return featureConfigIds;
  }

  public void setFeatureConfigIds(List<FeatureConfigIds> featureConfigIds) {
    this.featureConfigIds = featureConfigIds;
  }

  public AppTimeConfig getAppTimeConfig() {
    return appTimeConfig;
  }

  public void setAppTimeConfig(AppTimeConfig appTimeConfig) {
    this.appTimeConfig = appTimeConfig;
  }
}
