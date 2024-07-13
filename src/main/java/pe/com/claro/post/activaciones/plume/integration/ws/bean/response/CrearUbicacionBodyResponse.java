package pe.com.claro.post.activaciones.plume.integration.ws.bean.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.AppTime;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.Backhaul;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.BandSteering;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.ClientSteering;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.ControlMode;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.DPPConfiguration;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.ErrorType;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.FreezeTemplate;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.HAAHSConfiguration;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.IPV6;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.ISPSpeedTestConfiguration;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.MonitorMode;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.Optimizations;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CrearUbicacionBodyResponse {

  @SerializedName("ipv6")
  @Expose
  private IPV6 ipv6;
  @SerializedName("mDNSUniqueIdentifier")
  @Expose
  private String mDNSUniqueIdentifier;
  @SerializedName("id")
  @Expose
  private String id;
  @SerializedName("name")
  @Expose
  private String name;
  @SerializedName("accountId")
  @Expose
  private String accountId;
  @SerializedName("serviceId")
  @Expose
  private String serviceId;
  @SerializedName("profile")
  @Expose
  private String profile;
  @SerializedName("networkMode")
  @Expose
  private String networkMode;
  @SerializedName("freezeTemplates")
  @Expose
  private List<FreezeTemplate> freezeTemplates;
  @SerializedName("homeSecurityPriority")
  @Expose
  private String homeSecurityPriority;
  @SerializedName("appTime")
  @Expose
  private AppTime appTime;
  @SerializedName("createdAt")
  @Expose
  private String createdAt;
  @SerializedName("stepFunctionTimers")
  @Expose
  private List<Object> stepFunctionTimers;
  @SerializedName("_version")
  @Expose
  private String version;
  @SerializedName("partnerId")
  @Expose
  private String partnerId;
  @SerializedName("groupIds")
  @Expose
  private List<String> groupIds;
  @SerializedName("uprise")
  @Expose
  private boolean uprise;
  @SerializedName("groupOfUnassignedDevicesFreezeTemplates")
  @Expose
  private List<Object> groupOfUnassignedDevicesFreezeTemplates;
  @SerializedName("flex")
  @Expose
  private boolean flex;
  @SerializedName("ispSpeedTestConfiguration")
  @Expose
  private ISPSpeedTestConfiguration ispSpeedTestConfiguration;
  @SerializedName("clientSteering")
  @Expose
  private ClientSteering clientSteering;
  @SerializedName("wifiNetworkImportedFromGateway")
  @Expose
  private boolean wifiNetworkImportedFromGateway;
  @SerializedName("groupOfUnassignedDevicesFreezeSchedules")
  @Expose
  private List<Object> groupOfUnassignedDevicesFreezeSchedules;
  @SerializedName("isUtilizingSharedLocationFreezeSchedules")
  @Expose
  private boolean isUtilizingSharedLocationFreezeSchedules;
  @SerializedName("backhaul")
  @Expose
  private Backhaul backhaul;
  @SerializedName("bandSteering")
  @Expose
  private BandSteering bandSteering;
  @SerializedName("optimizations")
  @Expose
  private Optimizations optimizations;
  @SerializedName("error")
  @Expose
  private ErrorType error;
  @SerializedName("dppConfiguration")
  @Expose
  private DPPConfiguration dppConfiguration;
  @SerializedName("haahsConfiguration")
  @Expose
  private HAAHSConfiguration haahsConfiguration;
  @SerializedName("controlMode")
  @Expose
  private ControlMode controlMode;
  @SerializedName("customerId")
  @Expose
  private String customerId;
  @SerializedName("monitorMode")
  @Expose
  private MonitorMode monitorMode;

  public IPV6 getIpv6() {
    return ipv6;
  }

  public void setIpv6(IPV6 ipv6) {
    this.ipv6 = ipv6;
  }

  public String getmDNSUniqueIdentifier() {
    return mDNSUniqueIdentifier;
  }

  public void setmDNSUniqueIdentifier(String mDNSUniqueIdentifier) {
    this.mDNSUniqueIdentifier = mDNSUniqueIdentifier;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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

  public String getNetworkMode() {
    return networkMode;
  }

  public void setNetworkMode(String networkMode) {
    this.networkMode = networkMode;
  }

  public List<FreezeTemplate> getFreezeTemplates() {
    return freezeTemplates;
  }

  public void setFreezeTemplates(List<FreezeTemplate> freezeTemplates) {
    this.freezeTemplates = freezeTemplates;
  }

  public String getHomeSecurityPriority() {
    return homeSecurityPriority;
  }

  public void setHomeSecurityPriority(String homeSecurityPriority) {
    this.homeSecurityPriority = homeSecurityPriority;
  }

  public AppTime getAppTime() {
    return appTime;
  }

  public void setAppTime(AppTime appTime) {
    this.appTime = appTime;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
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

  public boolean isUprise() {
    return uprise;
  }

  public void setUprise(boolean uprise) {
    this.uprise = uprise;
  }

  public List<Object> getGroupOfUnassignedDevicesFreezeTemplates() {
    return groupOfUnassignedDevicesFreezeTemplates;
  }

  public void setGroupOfUnassignedDevicesFreezeTemplates(List<Object> groupOfUnassignedDevicesFreezeTemplates) {
    this.groupOfUnassignedDevicesFreezeTemplates = groupOfUnassignedDevicesFreezeTemplates;
  }

  public boolean isFlex() {
    return flex;
  }

  public void setFlex(boolean flex) {
    this.flex = flex;
  }

  public ISPSpeedTestConfiguration getIspSpeedTestConfiguration() {
    return ispSpeedTestConfiguration;
  }

  public void setIspSpeedTestConfiguration(ISPSpeedTestConfiguration ispSpeedTestConfiguration) {
    this.ispSpeedTestConfiguration = ispSpeedTestConfiguration;
  }

  public ClientSteering getClientSteering() {
    return clientSteering;
  }

  public void setClientSteering(ClientSteering clientSteering) {
    this.clientSteering = clientSteering;
  }

  public boolean isWifiNetworkImportedFromGateway() {
    return wifiNetworkImportedFromGateway;
  }

  public void setWifiNetworkImportedFromGateway(boolean wifiNetworkImportedFromGateway) {
    this.wifiNetworkImportedFromGateway = wifiNetworkImportedFromGateway;
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

  public void setUtilizingSharedLocationFreezeSchedules(boolean utilizingSharedLocationFreezeSchedules) {
    isUtilizingSharedLocationFreezeSchedules = utilizingSharedLocationFreezeSchedules;
  }

  public Backhaul getBackhaul() {
    return backhaul;
  }

  public void setBackhaul(Backhaul backhaul) {
    this.backhaul = backhaul;
  }

  public BandSteering getBandSteering() {
    return bandSteering;
  }

  public void setBandSteering(BandSteering bandSteering) {
    this.bandSteering = bandSteering;
  }

  public Optimizations getOptimizations() {
    return optimizations;
  }

  public void setOptimizations(Optimizations optimizations) {
    this.optimizations = optimizations;
  }

  public ErrorType getError() {
    return error;
  }

  public void setError(ErrorType error) {
    this.error = error;
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

  public ControlMode getControlMode() {
    return controlMode;
  }

  public void setControlMode(ControlMode controlMode) {
    this.controlMode = controlMode;
  }

  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public MonitorMode getMonitorMode() {
    return monitorMode;
  }

  public void setMonitorMode(MonitorMode monitorMode) {
    this.monitorMode = monitorMode;
  }
}
