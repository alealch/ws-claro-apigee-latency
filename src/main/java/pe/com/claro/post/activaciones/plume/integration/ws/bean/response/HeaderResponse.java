package pe.com.claro.post.activaciones.plume.integration.ws.bean.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.Status;

public class HeaderResponse {

  @SerializedName("consumer")
  @Expose
  private String consumer;

  @SerializedName("pid")
  @Expose
  private String pid;

  @SerializedName("timestamp")
  @Expose
  private String timestamp;

  @SerializedName("VarArg")
  @Expose
  private String varArg;

  @SerializedName("status")
  @Expose
  private Status status;

  public String getConsumer() {
    return consumer;
  }

  public void setConsumer(String consumer) {
    this.consumer = consumer;
  }

  public String getPid() {
    return pid;
  }

  public void setPid(String pid) {
    this.pid = pid;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public String getVarArg() {
    return varArg;
  }

  public void setVarArg(String varArg) {
    this.varArg = varArg;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }


}
