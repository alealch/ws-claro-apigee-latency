package pe.com.claro.post.activaciones.plume.integration.ws.bean.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeneraTokenPlumeRequest {

  @SerializedName("MessageRequest")
  @Expose
  private GeneraTokenPlumeMessageRequest messageRequest;

  public GeneraTokenPlumeMessageRequest getMessageRequest() {
    return messageRequest;
  }

  public void setMessageRequest(GeneraTokenPlumeMessageRequest messageRequest) {
    this.messageRequest = messageRequest;
  }

}
