package pe.com.claro.post.activaciones.plume.canonical.response;

import pe.com.claro.post.activaciones.plume.canonical.type.AprovisionarPlumeResponseDataType;
import pe.com.claro.post.activaciones.plume.canonical.type.ResponseAuditType;

public class AprovisionarPlumeResponseType {

  private ResponseAuditType responseAudit;
  private AprovisionarPlumeResponseDataType responseData;

  public ResponseAuditType getResponseAudit() {
    return responseAudit;
  }

  public void setResponseAudit(ResponseAuditType responseAudit) {
    this.responseAudit = responseAudit;
  }

  public AprovisionarPlumeResponseDataType getResponseData() {
    return responseData;
  }

  public void setResponseData(AprovisionarPlumeResponseDataType responseData) {
    this.responseData = responseData;
  }

}
