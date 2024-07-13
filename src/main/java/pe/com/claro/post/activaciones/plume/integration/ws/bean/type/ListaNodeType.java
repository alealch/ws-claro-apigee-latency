package pe.com.claro.post.activaciones.plume.integration.ws.bean.type;

public class ListaNodeType {
  private String id;
  private String numeroSerie;
  private String modelo;
  private String mac;
  private String estadoConexion;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getNumeroSerie() {
    return numeroSerie;
  }

  public void setNumeroSerie(String numeroSerie) {
    this.numeroSerie = numeroSerie;
  }

  public String getModelo() {
    return modelo;
  }

  public void setModelo(String modelo) {
    this.modelo = modelo;
  }

  public String getMac() {
    return mac;
  }

  public void setMac(String mac) {
    this.mac = mac;
  }

  public String getEstadoConexion() {
    return estadoConexion;
  }

  public void setEstadoConexion(String estadoConexion) {
    this.estadoConexion = estadoConexion;
  }

}
