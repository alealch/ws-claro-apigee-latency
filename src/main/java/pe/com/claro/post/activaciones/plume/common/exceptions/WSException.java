package pe.com.claro.post.activaciones.plume.common.exceptions;

public class WSException extends BaseException {

  private static final long serialVersionUID = 1L;

  private String nombreWS;

  public WSException(String string) {
    super(string);
  }

  public WSException(Exception exception) {
    super(exception);
  }

  public WSException(String string, Exception exception) {
    super(string, exception);
  }

  public WSException(String code, String message, Exception exception) {
    super(code, message, exception);
  }

  public WSException(String code, String message, String messageDeveloper, Exception exception, int status) {
    super(code, message, messageDeveloper, exception, status);
  }

  public WSException(String code, String message) {
    super(code, message);
  }

  public WSException(String codError, String nombreWS, String msjError, Exception objException) {
    super(codError, msjError, objException);
    this.nombreWS = nombreWS;
  }

  public String getNombreWS() {
    return nombreWS;
  }

  public void setNombreWS(String nombreWS) {
    this.nombreWS = nombreWS;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

}