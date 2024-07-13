package pe.com.claro.post.activaciones.plume.common.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertiesExterno {

  @Value("${idt1.codigo}")
  public String idt1Codigo;
  @Value("${idt1.mensaje}")
  public String idt1Mensaje;
  @Value("${idt2.codigo}")
  public String idt2Codigo;
  @Value("${idt2.mensaje}")
  public String idt2Mensaje;
  @Value("${idt3.codigo}")
  public String idt3Codigo;
  @Value("${idt3.mensaje}")
  public String idt3Mensaje;

  @Value("${idf0.codigo}")
  public String idf0Codigo;
  @Value("${idf0.mensaje}")
  public String idf0Mensaje;
  @Value("${idf1.codigo}")
  public String idf1Codigo;
  @Value("${idf1.mensaje}")
  public String idf1Mensaje;
  @Value("${idf2.codigo}")
  public String idf2Codigo;
  @Value("${idf2.mensaje}")
  public String idf2Mensaje;
  @Value("${idf3.codigo}")
  public String idf3Codigo;
  @Value("${idf3.mensaje}")
  public String idf3Mensaje;
  @Value("${idf5.codigo}")
  public String idf5Codigo;
  @Value("${idf5.mensaje}")
  public String idf5Mensaje;
  @Value("${idf6.codigo}")
  public String idf6Codigo;
  @Value("${idf6.mensaje}")
  public String idf6Mensaje;
  @Value("${idf7.codigo}")
  public String idf7Codigo;
  @Value("${idf7.mensaje}")
  public String idf7Mensaje;
  @Value("${idf8.codigo}")
  public String idf8Codigo;
  @Value("${idf8.mensaje}")
  public String idf8Mensaje;
  @Value("${idf9.codigo}")
  public String idf9Codigo;
  @Value("${idf9.mensaje}")
  public String idf9Mensaje;
  @Value("${idf10.codigo}")
  public String idf10Codigo;
  @Value("${idf10.mensaje}")
  public String idf10Mensaje;
  @Value("${obtener.locaciones.idf2.codigo}")
  public String idf2CodigoLocaciones;
  @Value("${obtener.locaciones.idf2.mensaje}")
  public String idf2MensajeLocaciones;
  @Value("${obtener.locaciones.idf3.codigo}")
  public String idf3CodigoLocaciones;
  @Value("${obtener.locaciones.idf3.mensaje}")
  public String idf3MensajeLocaciones;
  @Value("${obtener.locaciones.idf4.codigo}")
  public String idf4CodigoLocaciones;
  @Value("${obtener.locaciones.idf4.mensaje}")
  public String idf4MensajeLocaciones;
  @Value("${obtener.locaciones.idf5.codigo}")
  public String idf5CodigoLocaciones;
  @Value("${obtener.locaciones.idf5.mensaje}")
  public String idf5MensajeLocaciones;
  @Value("${obtener.equipos.idf2.codigo}")
  public String idf2CodigoObtenerEquipos;
  @Value("${obtener.equipos.idf2.mensaje}")
  public String idf2MensajeObtenerEquipos;
  @Value("${obtener.equipos.idf4.codigo}")
  public String idf4CodigoObtenerEquipos;
  @Value("${obtener.equipos.idf4.mensaje}")
  public String idf4MensajeObtenerEquipos;
  @Value("${obtener.equipos.idf5.codigo}")
  public String idf5CodigoObtenerEquipos;
  @Value("${obtener.equipos.idf5.mensaje}")
  public String idf5MensajeObtenerEquipos;
  @Value("${obtener.equipos.idf6.codigo}")
  public String idf6CodigoObtenerEquipos;
  @Value("${obtener.equipos.idf6.mensaje}")
  public String idf6MensajeObtenerEquipos;
  @Value("${obtener.equipos.idf7.codigo}")
  public String idf7CodigoObtenerEquipos;
  @Value("${obtener.equipos.idf7.mensaje}")
  public String idf7MensajeObtenerEquipos;
  @Value("${obtener.equipos.idf8.codigo}")
  public String idf8CodigoObtenerEquipos;
  @Value("${obtener.equipos.idf8.mensaje}")
  public String idf8MensajeObtenerEquipos;
  @Value("${consultar.equipos.idf2.codigo}")
  public String idf2CodigoConsultarEquipos;
  @Value("${consultar.equipos.idf2.mensaje}")
  public String idf2MensajeConsultarEquipos;
  @Value("${consultar.equipos.idf3.codigo}")
  public String idf3CodigoConsultarEquipos;
  @Value("${consultar.equipos.idf3.mensaje}")
  public String idf3MensajeConsultarEquipos;
  @Value("${consultar.equipos.idf4.codigo}")
  public String idf4CodigoConsultarEquipos;
  @Value("${consultar.equipos.idf4.mensaje}")
  public String idf4MensajeConsultarEquipos;
  @Value("${consultar.equipos.idf5.codigo}")
  public String idf5CodigoConsultarEquipos;
  @Value("${consultar.equipos.idf5.mensaje}")
  public String idf5MensajeConsultarEquipos;
  @Value("${consultar.equipos.idf6.codigo}")
  public String idf6CodigoConsultarEquipos;
  @Value("${consultar.equipos.idf6.mensaje}")
  public String idf6MensajeConsultarEquipos;
  @Value("${db.sga.nombre}")
  public String dbSgaNombre;
  @Value("${db.sga.jndi}")
  public String dbSgaJndi;
  @Value("${db.sga.owner}")
  public String dbSgaOwner;
  @Value("${db.sga.pkg.prov.plume}")
  public String dbSgaPkgProvPlume;

  @Value("${db.sga.sp.datos.cliente}")
  public String dbSgaSpDatosCliente;
  @Value("${db.sga.sp.datos.cliente.timeout}")
  public String dbSgaSpDatosClienteTimeout;
  @Value("${db.sga.sp.datos.cliente.ejecucion}")
  public String dbSgaSpDatosClienteEjecucion;

  @Value("${db.sga.sp.registra.cliente}")
  public String dbSgaSpRegistrarCliente;
  @Value("${db.sga.sp.registra.cliente.timeout}")
  public String dbSgaSpRegistrarClienteTimeout;
  @Value("${db.sga.sp.registra.cliente.ejecucion}")
  public String dbSgaSpRegistrarClienteEjecucion;
  @Value("${sp.registra.cliente.valor.usuario}")
  public String dbSgaSpRegistrarClienteUsuario;
  @Value("${registra.cliente.object}")
  public String registrarClientelistaObject;
  @Value("${registra.cliente.type}")
  public String registrarClientelistaType;
  @Value("${db.sga.sp.equipos}")
  public String dbSgaSpEquipos;
  @Value("${db.sga.sp.equipos.timeout}")
  public String dbSgaSpEquiposTimeout;
  @Value("${db.sga.sp.equipos.ejecucion}")
  public String dbSgaSpEquiposEjecucion;

  @Value("${ws.dp.genera.token.url}")
  public String wsUrlGeneraTokenClaro;
  @Value("${ws.dp.genera.token.nombre}")
  public String wsGeneraTokenClaroNombre;
  @Value("${ws.dp.genera.token.metodo}")
  public String wsGeneraTokenClaroMetodo;
  @Value("${ws.dp.genera.token.timeout}")
  public String wsGeneraTokenClaroTimeoutConnect;
  @Value("${ws.dp.genera.token.ejecucion}")
  public String wsGeneraTokenClaroTimeoutEjecucion;
  @Value("${ws.dp.genera.token.content.type}")
  public String wsGeneraTokenClaroContentType;

  @Value("${ws.dp.genera.token.generico.header.country}")
  public String wsGeneraTokenGenericoHeaderCountry;
  @Value("${ws.dp.genera.token.generico.header.dispositivo}")
  public String wsGeneraTokenGenericoHeaderDispositivo;
  @Value("${ws.dp.genera.token.generico.header.modulo}")
  public String wsGeneraTokenGenericoHeaderModulo;
  @Value("${ws.dp.genera.token.generico.header.msgtype}")
  public String wsGeneraTokenGenericoHeaderMsgType;
  @Value("${ws.dp.genera.token.generico.header.lenguaje}")
  public String wsGeneraTokenGenericoHeaderLeanguaje;
  @Value("${ws.dp.genera.token.generico.header.operation}")
  public String wsGeneraTokenGenericoHeaderOperation;
  @Value("${ws.dp.genera.token.claro.header.ip}")
  public String wsGeneraTokenClaroHeaderIp;
  @Value("${ws.dp.genera.token.claro.client.id}")
  public String wsGeneraTokenClaroClientId;
  @Value("${ws.dp.genera.token.claro.client.secret}")
  public String wsGeneraTokenClaroClientSecret;
  @Value("${ws.dp.genera.token.claro.authorization}")
  public String wsGeneraTokenClaroAuthorization;

  @Value("${ws.dp.genera.token.plume.url}")
  public String wsUrlGeneraTokenPlume;
  @Value("${ws.dp.genera.token.plume.nombre}")
  public String wsGeneraTokenPlumeNombre;
  @Value("${ws.dp.genera.token.plume.metodo}")
  public String wsGeneraTokenPlumeMetodo;
  @Value("${ws.dp.genera.token.plume.timeout}")
  public String wsGeneraTokenPlumeTimeoutConnect;
  @Value("${ws.dp.genera.token.plume.ejecucion}")
  public String wsGeneraTokenPlumeTimeoutEjecucion;
  @Value("${ws.dp.genera.token.plume.header.ip}")
  public String wsGeneraTokenPlumeHeaderIp;
  @Value("${ws.dp.genera.token.plume.content.type}")
  public String wsDpPlumeContentType;
  @Value("${ws.dp.genera.token.plume.grant.type}")
  public String wsGeneraTokenPlumeGrantType;
  @Value("${ws.dp.genera.token.plume.scope}")
  public String wsGeneraTokenPlumeScope;
  @Value("${ws.dp.genera.token.plume.authorization}")
  public String wsGeneraTokenPlumeAuthorization;
  @Value("${ws.plume.customer.crear.cuenta.url}")
  public String wsUrlPlumeCustomerCrearCuenta;
  @Value("${ws.plume.customer.crear.cuenta.nommbre}")
  public String wsPlumeCustomerCrearCuentaNombre;
  @Value("${ws.plume.customer.crear.cuenta.metodo}")
  public String wsPlumeCustomerCrearCuentaMetodo;
  @Value("${ws.plume.customer.crear.cuenta.timeout}")
  public String wsPlumeCustomerCrearCuentaTimeoutConnect;
  @Value("${ws.plume.customer.crear.cuenta.ejecucion}")
  public String wsPlumeCustomerCrearCuentaTimeoutEjecucion;
  @Value("${ws.plume.customer.crear.cuenta.authorization}")
  public String wsPlumeCustomerCrearCuentaAuthorization;
  @Value("${ws.plume.customer.crear.cuenta.partner.id}")
  public String wsPlumeCustomerCrearCuentaPartnerId;
  @Value("${ws.plume.customer.crear.cuenta.profile}")
  public String wsPlumeCustomerCrearCuentaProfile;
  @Value("${ws.plume.customer.crear.cuenta.accept.lenguaje}")
  public String wsPlumeCustomerCrearCuentaAcceptLeng;
  @Value("${ws.plume.customer.crear.cuenta.onboarding.check}")
  public String wsPlumeCustomerCrearCuentaOnboardingCheck;
  @Value("${ws.plume.customer.crear.cuenta.header.ip}")
  public String wsCrearCuentaHeaderIp;
  @Value("${ws.plume.location.crear.ubicacion.url}")
  public String wsUrlPlumeLocationCrearUbi;
  @Value("${ws.plume.location.crear.ubicacion.nombre}")
  public String wsPlumeLocationCrearUbiNombre;
  @Value("${ws.plume.location.crear.ubicacion.metodo}")
  public String wsPlumeLocationCrearUbiMetodo;
  @Value("${ws.plume.location.crear.ubicacion.timeout}")
  public String wsPlumeLocationCrearUbiTimeoutConnect;
  @Value("${ws.plume.location.crear.ubicacion.ejecucion}")
  public String wsPlumeLocationCrearUbiTimeoutEjecucion;

  @Value("${ws.plume.location.crear.ubicacion.header.ip}")
  public String wsCrearUbicacionHeaderIp;
  @Value("${ws.plume.location.crear.ubicacion.authorization}")
  public String wsCrearUbicacionAuthorization;
  @Value("${ws.plume.location.config.nivel.url}")
  public String wsUrlPlumeLocationConfigNivel;
  @Value("${ws.plume.location.config.nivel.nombre}")
  public String wsPlumeLocationConfigNivelNombre;
  @Value("${ws.plume.location.config.nivel.metodo}")
  public String wsPlumeLocationConfigNivelMetodo;
  @Value("${ws.plume.location.config.nivel.timeout}")
  public String wsPlumeLocationConfigNivelTimeoutConnect;
  @Value("${ws.plume.location.config.nivel.ejecucion}")
  public String wsPlumeLocationConfigNivelTimeoutEjecucion;
  @Value("${ws.plume.location.config.nivel.basic.service}")
  public String wsPlumeLocationConfigNivelBasicService;
  @Value("${ws.plume.location.config.nivel.authorization}")
  public String wsConfigNivelServiAuthorization;
  @Value("${ws.plume.location.config.nivel.header.ip}")
  public String wsCrearConfigNivelHeaderIp;
  @Value("${ws.plume.node.crear.repetidor.url}")
  public String wsUrlPlumeNodeCrearRepetidor;
  @Value("${ws.plume.node.crear.repetidor.nombre}")
  public String wsPlumeNodeCrearRepetidorNombre;
  @Value("${ws.plume.node.crear.repetidor.metodo}")
  public String wsPlumeNodeCrearRepetidorMetodo;
  @Value("${ws.plume.node.crear.repetidor.timeout}")
  public String wsPlumeNodeCrearRepetidorTimeoutConnect;
  @Value("${ws.plume.node.crear.repetidor.ejecucion}")
  public String wsPlumeNodeCrearRepetidorTimeoutEjecucion;
  @Value("${ws.plume.node.crear.repetidor.authorization}")
  public String wsPlumeLocationCrearRepetidorAuthorization;
  @Value("${ws.plume.node.crear.repetidor.header.ip}")
  public String wsCrearRepetidorHeaderIp;
  @Value("${ws.plume.node.consultar.repetidor.url}")
  public String wsUrlPlumeNodeConsultarRepetidor;
  @Value("${ws.plume.node.consultar.repetidor.nombre}")
  public String wsPlumeNodeConsultarRepetidorNombre;
  @Value("${ws.plume.node.consultar.repetidor.metodo}")
  public String wsPlumeNodeConsultarRepetidorMetodo;
  @Value("${ws.plume.node.consultar.repetidor.timeout}")
  public String wsPlumeNodeConsultarRepetidorTimeoutConnect;
  @Value("${ws.plume.node.consultar.repetidor.ejecucion}")
  public String wsPlumeNodeConsultarRepetidorTimeoutEjecucion;
  @Value("${ws.plume.node.consultar.repetidor.authorization}")
  public String wsPlumeLocationConsultarRepetidorAuthorization;
  @Value("${ws.plume.node.eliminar.repetidor.url}")
  public String wsUrlPlumeNodeEliminarRepetidor;
  @Value("${ws.plume.node.eliminar.repetidor.nombre}")
  public String wsPlumeNodeEliminarRepetidorNombre;
  @Value("${ws.plume.node.eliminar.repetidor.metodo}")
  public String wsPlumeNodeEliminarRepetidorMetodo;
  @Value("${ws.plume.node.eliminar.repetidor.timeout}")
  public String wsPlumeNodeEliminarRepetidorTimeoutConnect;
  @Value("${ws.plume.node.eliminar.repetidor.ejecucion}")
  public String wsPlumeNodeEliminarRepetidorTimeoutEjecucion;
  @Value("${ws.plume.node.eliminar.repetidor.authorization}")
  public String wsPlumeLocationEliminarRepetidorAuthorization;
  @Value("${db.timeai.jndi}")
  public String dbTimeaiJndi;
  @Value("${db.timeai.nombre}")
  public String dbTimeaiNombre;
  @Value("${db.timeai.owner}")
  public String dbTimeaiOwner;
  @Value("${db.timeai.pkg.gestion.plume}")
  public String dbTimeaiPkgGestionPlume;
  @Value("${db.timeai.sp.registrar.trazabilidad}")
  public String dbTimeaiSpRegistrarTrazabilidad;
  @Value("${sp.registrar.trazabilidad.user}")
  public String registrarTrazabilidadUserId;
  @Value("${db.timeai.sp.registrar.trazabilidad.timeout}")
  public String dbTimeaiSpRegistrarTrazaTimeout;
  @Value("${db.timeai.sp.registrar.trazabilidad.ejecucion}")
  public String dbTimeaiSpRegistrarTrazaEjecucion;

  @Value("${ws.plume.customer.eliminar.cuenta.url}")
  public String wsUrlPlumeCustomerEliminarCuenta;
  @Value("${ws.plume.customer.eliminar.cuenta.nombre}")
  public String wsPlumeCustomerEliminarCuentaNombre;
  @Value("${ws.plume.customer.eliminar.cuenta.metodo}")
  public String wsPlumeCustomerEliminarCuentaMetodo;
  @Value("${ws.plume.customer.eliminar.cuenta.timeout}")
  public String wsPlumeCustomerEliminarCuentaTimeoutConnect;
  @Value("${ws.plume.customer.eliminar.cuenta.ejecucion}")
  public String wsPlumeCustomerEliminarCuentaTimeoutEjecucion;
  @Value("${ws.plume.customer.eliminar.cuenta.authorization}")
  public String wsPlumeCustomerEliminarCuentaAuthorization;
  @Value("${ws.plume.location.eliminar.ubicacion.url}")
  public String wsUrlPlumeLocationEliminarUbi;
  @Value("${ws.plume.location.eliminar.ubicacion.nombre}")
  public String wsUrlPlumeLocationEliminarUbiNombre;
  @Value("${ws.plume.location.eliminar.ubicacion.metodo}")
  public String wsUrlPlumeLocationEliminarUbiMetodo;
  @Value("${ws.plume.location.eliminar.ubicacion.timeout}")
  public String wsPlumeLocationEliminarUbiTimeoutConnect;
  @Value("${ws.plume.location.eliminar.ubicacion.ejecucion}")
  public String wsPlumeLocationEliminarUbiEjecucion;
  @Value("${ws.plume.location.eliminar.ubicacion.authorization}")
  public String wsPlumeLocationEliminarUbiAuthorization;
  @Value("${ws.plume.customer.obtener.ubicacion.url}")
  public String wsUrlPlumeCustomerObtenerUbicacion;
  @Value("${ws.plume.customer.obtener.ubicacion.nombre}")
  public String wsPlumeCustomerObtenerUbicacionNombre;
  @Value("${ws.plume.customer.obtener.ubicacion.metodo}")
  public String wsPlumeCustomerObtenerUbicacionMetodo;
  @Value("${ws.plume.customer.obtener.ubicacion.timeout}")
  public String wsPlumeCustomerObtenerUbicacionTimeoutConnect;
  @Value("${ws.plume.customer.obtener.ubicacion.ejecucion}")
  public String wsPlumeCustomerObtenerUbicacionTimeoutEjecucion;
  @Value("${ws.plume.location.obtener.ubicacion.authorization}")
  public String wsPlumeLocationObtenerUbicaAuthorization;
  @Value("${ws.plume.location.obtener.repetidor.url}")
  public String wsUrlPlumeLocationObtenerRepetidor;
  @Value("${ws.plume.location.obtener.repetidor.nombre}")
  public String wsPlumeLocationObtenerRepetidorNombre;
  @Value("${ws.plume.location.obtener.repetidor.metodo}")
  public String wsPlumeLocationObtenerRepetidorMetodo;
  @Value("${ws.plume.location.obtener.repetidor.timeout}")
  public String wsPlumeLocationObtenerRepetidorTimeoutConnect;
  @Value("${ws.plume.location.obtener.repetidor.ejecucion}")
  public String wsPlumeLocationObtenerRepetidorEjecucion;
  @Value("${ws.plume.location.obtener.repetidor.authorization}")
  public String wsPlumeLocationObtenerRepetidorAuthorization;
  @Value("${nombre.bearer}")
  public String nombreBearer;

}
