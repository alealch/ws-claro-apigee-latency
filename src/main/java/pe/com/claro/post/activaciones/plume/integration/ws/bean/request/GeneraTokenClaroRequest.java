package pe.com.claro.post.activaciones.plume.integration.ws.bean.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeneraTokenClaroRequest {

  @SerializedName("MessageRequest")
  @Expose
  private GeneraTokenClaroMessageRequest messageRequest;

  public GeneraTokenClaroMessageRequest getMessageRequest() {
    return messageRequest;
  }

  public void setMessageRequest(GeneraTokenClaroMessageRequest messageRequest) {
    this.messageRequest = messageRequest;
  }


}
