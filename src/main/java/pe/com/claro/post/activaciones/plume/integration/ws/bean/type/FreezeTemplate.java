package pe.com.claro.post.activaciones.plume.integration.ws.bean.type;

import java.util.List;

public class FreezeTemplate {
  private String id;
  private String startTime;
  private String endTime;
  private List<Integer> daysOfWeek;
  private List<String> macs;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public List<Integer> getDaysOfWeek() {
    return daysOfWeek;
  }

  public void setDaysOfWeek(List<Integer> daysOfWeek) {
    this.daysOfWeek = daysOfWeek;
  }

  public List<String> getMacs() {
    return macs;
  }

  public void setMacs(List<String> macs) {
    this.macs = macs;
  }

}
