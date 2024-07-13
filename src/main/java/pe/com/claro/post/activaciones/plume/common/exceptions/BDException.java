package pe.com.claro.post.activaciones.plume.common.exceptions;

public class BDException extends BaseException {

  private static final long serialVersionUID = 1L;

  public BDException(String string) {
    super(string);
  }

  public BDException(Exception exception) {
    super(exception);
  }

  public BDException(String string, Exception exception) {
    super(string, exception);
  }

  public BDException(String code, String message, Exception exception) {
    super(code, message, exception);
  }

  public BDException(String code, String message, String messageDeveloper, Exception exception, int status) {
    super(code, message, messageDeveloper, exception, status);
  }

  public BDException(String code, String message, String messageDeveloper, Exception exception) {
    super(code, message, messageDeveloper, exception);
  }

  public BDException(String code, String message) {
    super(code, message);
  }

}
