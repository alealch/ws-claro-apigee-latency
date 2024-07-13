package pe.com.claro.post.activaciones.plume.canonical.response;

import pe.com.claro.post.activaciones.plume.canonical.type.ObtenerLocacionesResponseDataType;
import pe.com.claro.post.activaciones.plume.canonical.type.ResponseAuditType;

public class ObtenerLocacionesResponseType {

  private ResponseAuditType responseAudit;
  private ObtenerLocacionesResponseDataType responseData;

  public ResponseAuditType getResponseAudit() {
    return responseAudit;
  }

  public void setResponseAudit(ResponseAuditType responseAudit) {
    this.responseAudit = responseAudit;
  }

  public ObtenerLocacionesResponseDataType getResponseData() {
    return responseData;
  }

  public void setResponseData(ObtenerLocacionesResponseDataType responseData) {
    this.responseData = responseData;
  }

}
