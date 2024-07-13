package pe.com.claro.post.activaciones.plume.integration.ws.bean.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.Fault;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.Node;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ObtenerRepetidorBodyResponse {

  @SerializedName("nodes")
  @Expose
  private List<Node> nodes;
  @SerializedName("Fault")
  @Expose
  private Fault fault;

  public List<Node> getNodes() {
    return nodes;
  }

  public void setNodes(List<Node> nodes) {
    this.nodes = nodes;
  }

  public Fault getFault() {
    return fault;
  }

  public void setFault(Fault fault) {
    this.fault = fault;
  }

}
