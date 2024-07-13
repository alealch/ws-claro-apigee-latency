package pe.com.claro.post.activaciones.plume.common.exceptions;

public class BaseException extends Exception {

  private static final long serialVersionUID = 1L;

  private Exception exception;
  private String code;
  private String message;
  private String messageDeveloper;
  private int status;
  private String nombreWS;

  public BaseException(String code, String message, Exception exception) {
    super(message);
    this.exception = exception;
    this.code = code;
    this.message = message;
  }

  public BaseException(String code, String message) {
    super(message);
    this.code = code;
    this.message = message;
  }

  public BaseException(String message, Exception exception) {
    super(message);
    this.exception = exception;
    this.message = message;
  }

  public BaseException(String code, String message, String messageDeveloper, Exception exception, int status) {
    super(message);
    this.code = code;
    this.message = message;
    this.messageDeveloper = messageDeveloper;
    this.exception = exception;
    this.status = status;
  }

  public BaseException(String code, String message, String nombreWS, Exception objException) {
    super(message);
    this.code = code;
    this.message = message;
    this.nombreWS = nombreWS;
    this.exception = objException;
  }

  public BaseException(Exception exception) {
    this.exception = exception;
  }

  public BaseException(String message) {
    super(message);
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public Exception getException() {
    return exception;
  }

  public void setException(Exception exception) {
    this.exception = exception;
  }

  public String getCode() {
    return code;
  }

  public String getMessageDeveloper() {
    return messageDeveloper;
  }

  public void setMessageDeveloper(String messageDeveloper) {
    this.messageDeveloper = messageDeveloper;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getNombreWS() {
    return nombreWS;
  }

  public void setNombreWS(String nombreWS) {
    this.nombreWS = nombreWS;
  }
}
