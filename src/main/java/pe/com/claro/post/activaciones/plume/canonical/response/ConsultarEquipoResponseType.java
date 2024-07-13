package pe.com.claro.post.activaciones.plume.canonical.response;

import pe.com.claro.post.activaciones.plume.canonical.type.ConsultarEquipoResponseDataType;
import pe.com.claro.post.activaciones.plume.canonical.type.ResponseAuditType;

public class ConsultarEquipoResponseType {

  private ResponseAuditType responseAudit;
  private ConsultarEquipoResponseDataType responseData;

  public ResponseAuditType getResponseAudit() {
    return responseAudit;
  }

  public void setResponseAudit(ResponseAuditType responseAudit) {
    this.responseAudit = responseAudit;
  }

  public ConsultarEquipoResponseDataType getResponseData() {
    return responseData;
  }

  public void setResponseData(ConsultarEquipoResponseDataType responseData) {
    this.responseData = responseData;
  }

}
