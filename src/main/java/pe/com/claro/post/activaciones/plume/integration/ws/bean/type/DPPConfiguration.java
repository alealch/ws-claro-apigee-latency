package pe.com.claro.post.activaciones.plume.integration.ws.bean.type;

import java.util.List;

public class DPPConfiguration {
  private String mode;
  private List<Object> enrollments;
  private String modeRealized;

  public String getMode() {
    return mode;
  }

  public void setMode(String mode) {
    this.mode = mode;
  }

  public List<Object> getEnrollments() {
    return enrollments;
  }

  public void setEnrollments(List<Object> enrollments) {
    this.enrollments = enrollments;
  }

  public String getModeRealized() {
    return modeRealized;
  }

  public void setModeRealized(String modeRealized) {
    this.modeRealized = modeRealized;
  }

}
