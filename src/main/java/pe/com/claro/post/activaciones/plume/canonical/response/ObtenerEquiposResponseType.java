package pe.com.claro.post.activaciones.plume.canonical.response;

import pe.com.claro.post.activaciones.plume.canonical.type.ObtenerEquiposResponseDataType;
import pe.com.claro.post.activaciones.plume.canonical.type.ResponseAuditType;

public class ObtenerEquiposResponseType {
  private ResponseAuditType responseAudit;
  private ObtenerEquiposResponseDataType responseData;

  public ResponseAuditType getResponseAudit() {
    return responseAudit;
  }

  public void setResponseAudit(ResponseAuditType responseAudit) {
    this.responseAudit = responseAudit;
  }

  public ObtenerEquiposResponseDataType getResponseData() {
    return responseData;
  }

  public void setResponseData(ObtenerEquiposResponseDataType responseData) {
    this.responseData = responseData;
  }

}
