package pe.com.claro.post.activaciones.plume.canonical.response;

import pe.com.claro.post.activaciones.plume.canonical.type.ResponseAuditType;
import pe.com.claro.post.activaciones.plume.canonical.type.ResponseDataType;

public class ConsultadatosfacturaResponseType {

  private ResponseAuditType responseAudit;
  private ResponseDataType responseData;

  public ResponseAuditType getResponseAudit() {
    return responseAudit;
  }

  public void setResponseAudit(ResponseAuditType responseAudit) {
    this.responseAudit = responseAudit;
  }

  public ResponseDataType getResponseData() {
    return responseData;
  }

  public void setResponseData(ResponseDataType responseData) {
    this.responseData = responseData;
  }

}
