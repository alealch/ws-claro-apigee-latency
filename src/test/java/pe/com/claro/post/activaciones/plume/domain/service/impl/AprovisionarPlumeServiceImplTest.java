package pe.com.claro.post.activaciones.plume.domain.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pe.com.claro.post.activaciones.plume.canonical.header.HeaderRequest;
import pe.com.claro.post.activaciones.plume.canonical.request.AprovisionarPlumeRequest;
import pe.com.claro.post.activaciones.plume.canonical.request.AprovisionarPlumeRequestType;
import pe.com.claro.post.activaciones.plume.canonical.response.AprovisionarPlumeResponse;
import pe.com.claro.post.activaciones.plume.canonical.response.AprovisionarPlumeResponseType;
import pe.com.claro.post.activaciones.plume.canonical.type.AprovisionarPlumeResponseDataType;
import pe.com.claro.post.activaciones.plume.canonical.type.ListaEquiposAPType;
import pe.com.claro.post.activaciones.plume.canonical.type.ResponseAuditType;
import pe.com.claro.post.activaciones.plume.common.exceptions.BaseException;
import pe.com.claro.post.activaciones.plume.common.exceptions.IDFException;
import pe.com.claro.post.activaciones.plume.common.exceptions.WSException;
import pe.com.claro.post.activaciones.plume.common.property.PropertiesExterno;
import pe.com.claro.post.activaciones.plume.integration.dao.SgaDao;
import pe.com.claro.post.activaciones.plume.integration.dao.TimeaiDao;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.request.DatosClienteBeanReq;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.request.RegistrarClieteReq;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.response.DatosClienteBeanRes;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.response.RegistrarClieteRes;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.type.CursorValidaCuenta;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.type.ListaEquiposProvisionados;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.utiltarios.UtilTokens;
import pe.com.claro.post.activaciones.plume.integration.ws.PlumeCustomerClient;
import pe.com.claro.post.activaciones.plume.integration.ws.PlumeLocationClient;
import pe.com.claro.post.activaciones.plume.integration.ws.PlumeNodeClient;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.request.BodyCrearRepetidorRequest;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.request.ConfigNivelRequest;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.request.CrearRepetidorRequest;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.request.CrearUbicacionRequest;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.request.GeneraTokenClaroRequest;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.request.HeaderDPRequest;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.request.HeaderDatapower;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.ConfigNivelMessageResponse;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.ConfigNivelResponse;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.CrearCuentaBodyResponse;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.CrearCuentaMessageResponse;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.CrearCuentaResponse;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.CrearRepetidorBodyResponse;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.CrearRepetidorMessageResponse;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.CrearRepetidorResponse;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.CrearUbicacionBodyResponse;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.CrearUbicacionMessageResponse;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.CrearUbicacionResponse;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.GeneraTokenClaroResponse;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.Header;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.HeaderResponse;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.AppTime;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.Backhaul;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.BandSteering;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.ClientSteering;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.ControlMode;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.DPPConfiguration;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.DetailType;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.Fault;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.HAAHSConfiguration;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.IPV6;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.ISPSpeedTestConfiguration;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.MonitorMode;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.Optimizations;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.Status;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.Subscription;
import pe.com.claro.post.activaciones.plume.integration.ws.impl.GeneraTokenClaroClientImpl;

@ContextConfiguration(classes = {AprovisionarPlumeServiceImpl.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class AprovisionarPlumeServiceImplTest {

  @Autowired
  @InjectMocks
  private AprovisionarPlumeServiceImpl aprovisionarPlumeServiceImpl;

  @MockBean
  private PlumeCustomerClient plumeCustomerClient;
  @MockBean
  private PlumeLocationClient plumeLocationClient;
  @MockBean
  private PlumeNodeClient plumeNodeClient;
  @MockBean
  private SgaDao sgaDao;
  @MockBean
  private TimeaiDao timeaiDao;
  @MockBean
  private GeneraTokenClaroClientImpl generaTokenClaroClientImpl;
  @MockBean
  private UtilTokens UtilTokens;

  @Mock
  PropertiesExterno propertiesExterno = new PropertiesExterno();

  @Before
  public void init() throws Exception {
    propertiesExterno.dbSgaJndi = "pe.com.claro.esb.jdbc.dataSources.NoXAsgaDev";
    propertiesExterno.dbSgaNombre = "SGA";
    propertiesExterno.dbSgaOwner = "OPERACION";
    propertiesExterno.dbSgaPkgProvPlume = "PKG_PROV_PLUME";
    propertiesExterno.dbSgaSpDatosCliente = "PLMSS_DATOSCLIENTE";
    propertiesExterno.dbSgaSpDatosClienteEjecucion = "1";
    propertiesExterno.dbSgaSpDatosClienteTimeout = "1";
    propertiesExterno.dbSgaSpEquipos = "PLMSS_EQUIPO";
    propertiesExterno.dbSgaSpEquiposEjecucion = "1";
    propertiesExterno.dbSgaSpEquiposTimeout = "1";
    propertiesExterno.dbSgaSpRegistrarCliente = "PLMSI_REGISTRA_CLIENTE";
    propertiesExterno.dbSgaSpRegistrarClienteEjecucion = "1";
    propertiesExterno.dbSgaSpRegistrarClienteTimeout = "1";
    propertiesExterno.dbSgaSpRegistrarClienteUsuario = "USRPLM";
    propertiesExterno.dbTimeaiJndi = "pe.com.claro.esb.jdbc.dataSources.timeaiDS";
    propertiesExterno.dbTimeaiNombre = "TIMEAI";
    propertiesExterno.dbTimeaiOwner = "USRACT";
    propertiesExterno.dbTimeaiPkgGestionPlume = "PKG_GESTION_PLUME";
    propertiesExterno.dbTimeaiSpRegistrarTrazaEjecucion = "1";
    propertiesExterno.dbTimeaiSpRegistrarTrazaTimeout = "1";
    propertiesExterno.dbTimeaiSpRegistrarTrazabilidad = "PLMTI_OPERACIONES";
    propertiesExterno.idf0Codigo = "0";
    propertiesExterno.idf0Mensaje = "Exito";
    propertiesExterno.idf10Codigo = "10";
    propertiesExterno.idf10Mensaje = "Error almacenar datos en SGA. [DETALLE]";
    propertiesExterno.idf1Codigo = "1";
    propertiesExterno.idf1Mensaje = "Parametros obligatorios incompletos: [DETALLE]";
    propertiesExterno.idf2Codigo = "2";
    propertiesExterno.idf2CodigoLocaciones = "2";
    propertiesExterno.idf2CodigoObtenerEquipos = "2";
    propertiesExterno.idf2Mensaje = "[FIEL_NAME] no válido";
    propertiesExterno.idf2MensajeLocaciones = "No se pudo validar la información de la cuenta. [DETALLE]";
    propertiesExterno.idf2MensajeObtenerEquipos = "No se pudo obtener información del cliente. [DETALLE]";
    propertiesExterno.idf2MensajeConsultarEquipos = "No se pudo validar la información de la cuenta. [DETALLE]";
    propertiesExterno.idf3Codigo = "3";
    propertiesExterno.idf3CodigoLocaciones = "3";
    propertiesExterno.idf3CodigoConsultarEquipos = "3";
    propertiesExterno.idf3Mensaje = "Error al obtener información del cliente. [DETALLE]";
    propertiesExterno.idf3MensajeLocaciones = "Ocurrió un error al autenticar. [PLATAFORMA] : [DETALLE]";
    propertiesExterno.idf3MensajeConsultarEquipos = "Cliente no presenta equipos.";
    propertiesExterno.idf4CodigoConsultarEquipos = "4";
    propertiesExterno.idf4CodigoLocaciones = "4";
    propertiesExterno.idf4CodigoObtenerEquipos = "4";
    propertiesExterno.idf4MensajeConsultarEquipos = "El equipo a consultar no existe.";
    propertiesExterno.idf4MensajeLocaciones = "Ocurrió un error al obtener locaciones en plume. [DETALLE]";
    propertiesExterno.idf4MensajeObtenerEquipos = "Cliente no presenta locaciones.";
    propertiesExterno.idf5Codigo = "5";
    propertiesExterno.idf5CodigoLocaciones = "5";
    propertiesExterno.idf5CodigoConsultarEquipos = "5";
    propertiesExterno.idf5Mensaje = "Error al autenticar. [PLATAFORMA] : [DETALLE]";
    propertiesExterno.idf5MensajeLocaciones = "No se encontraron locaciones.";
    propertiesExterno.idf5MensajeConsultarEquipos = "Ocurrió un error al autenticar. [PLATAFORMA] : [DETALLE]";
    propertiesExterno.idf6Codigo = "6";
    propertiesExterno.idf6CodigoConsultarEquipos = "6";
    propertiesExterno.idf6CodigoObtenerEquipos = "6";
    propertiesExterno.idf6Mensaje = "Error al crear cuenta en PLUME. [DETALLE]";
    propertiesExterno.idf6MensajeConsultarEquipos = "No se encontro el equipo en plume.";
    propertiesExterno.idf6MensajeObtenerEquipos = "Idf6 Mensaje Obtener Equipos";
    propertiesExterno.idf7Codigo = "Idf7 Codigo";
    propertiesExterno.idf7CodigoObtenerEquipos = "Idf7 Codigo Obtener Equipos";
    propertiesExterno.idf7Mensaje = "Idf7 Mensaje";
    propertiesExterno.idf7MensajeObtenerEquipos = "Idf7 Mensaje Obtener Equipos";
    propertiesExterno.idf8Codigo = "Idf8 Codigo";
    propertiesExterno.idf8CodigoObtenerEquipos = "Idf8 Codigo Obtener Equipos";
    propertiesExterno.idf8Mensaje = "Idf8 Mensaje";
    propertiesExterno.idf8MensajeObtenerEquipos = "Idf8 Mensaje Obtener Equipos";
    propertiesExterno.idf9Codigo = "9";
    propertiesExterno.idf9Mensaje = "Error al registrar equipo en PLUME. [DETALLE]";
    propertiesExterno.idt1Codigo = "1";
    propertiesExterno.idt1Mensaje = "Idt1 Mensaje";
    propertiesExterno.idt2Codigo = "2";
    propertiesExterno.idt2Mensaje = "Idt2 Mensaje";
    propertiesExterno.idt3Codigo = "3";
    propertiesExterno.idt3Mensaje = "Idt3 Mensaje";
    propertiesExterno.nombreBearer = "Nombre Bearer";
    propertiesExterno.registrarClientelistaObject = "Registrar Clientelista Object";
    propertiesExterno.registrarClientelistaType = "Registrar Clientelista Type";
    propertiesExterno.registrarTrazabilidadUserId = "42";
    propertiesExterno.wsConfigNivelServiAuthorization = "JaneDoe";
    propertiesExterno.wsCrearConfigNivelHeaderIp = "Ws Crear Config Nivel Header Ip";
    propertiesExterno.wsCrearCuentaHeaderIp = "Ws Crear Cuenta Header Ip";
    propertiesExterno.wsCrearRepetidorHeaderIp = "Bella";
    propertiesExterno.wsCrearUbicacionAuthorization = "JaneDoe";
    propertiesExterno.wsCrearUbicacionHeaderIp = "Ws Crear Ubicacion Header Ip";
    propertiesExterno.wsDpPlumeContentType = "text/plain";
    propertiesExterno.wsGeneraTokenClaroAuthorization = "Basic dXNyU2VydlNnYTpRQHZlMTIzNDU2";
    propertiesExterno.wsGeneraTokenClaroClientId = "42";
    propertiesExterno.wsGeneraTokenClaroClientSecret = "ABC123";
    propertiesExterno.wsGeneraTokenClaroContentType = "ABC123";
    propertiesExterno.wsGeneraTokenClaroHeaderIp = "ABC123";
    propertiesExterno.wsGeneraTokenClaroMetodo = "ABC123";
    propertiesExterno.wsGeneraTokenClaroNombre = "ABC123";
    propertiesExterno.wsGeneraTokenClaroTimeoutConnect = "ABC123";
    propertiesExterno.wsGeneraTokenClaroTimeoutEjecucion = "ABC123";
    propertiesExterno.wsGeneraTokenGenericoHeaderCountry = "GB";
    propertiesExterno.wsGeneraTokenGenericoHeaderDispositivo = "ABC123";
    propertiesExterno.wsGeneraTokenGenericoHeaderLeanguaje = "ABC123";
    propertiesExterno.wsGeneraTokenGenericoHeaderModulo = "ABC123";
    propertiesExterno.wsGeneraTokenGenericoHeaderMsgType = "ABC123";
    propertiesExterno.wsGeneraTokenGenericoHeaderOperation = "ABC123";
    propertiesExterno.wsGeneraTokenPlumeAuthorization = "ABC123";
    propertiesExterno.wsGeneraTokenPlumeGrantType = "ABC123";
    propertiesExterno.wsGeneraTokenPlumeHeaderIp = "ABC123";
    propertiesExterno.wsGeneraTokenPlumeMetodo = "ABC123";
    propertiesExterno.wsGeneraTokenPlumeNombre = "ABC123";
    propertiesExterno.wsGeneraTokenPlumeScope = "ABC123";
    propertiesExterno.wsGeneraTokenPlumeTimeoutConnect = "ABC123";
    propertiesExterno.wsGeneraTokenPlumeTimeoutEjecucion = "ABC123";
    propertiesExterno.wsPlumeCustomerCrearCuentaAcceptLeng = "Ws Plume Customer Crear Cuenta Accept Leng";
    propertiesExterno.wsPlumeCustomerCrearCuentaAuthorization = "Basic dXNyU2VydlNnYTpRQHZlMTIzNDU2";
    propertiesExterno.wsPlumeCustomerCrearCuentaMetodo = "Ws Plume Customer Crear Cuenta Metodo";
    propertiesExterno.wsPlumeCustomerCrearCuentaNombre = "Ws Plume Customer Crear Cuenta Nombre";
    propertiesExterno.wsPlumeCustomerCrearCuentaOnboardingCheck = "Ws Plume Customer Crear Cuenta Onboarding Check";
    propertiesExterno.wsPlumeCustomerCrearCuentaPartnerId = "42";
    propertiesExterno.wsPlumeCustomerCrearCuentaProfile = "Ws Plume Customer Crear Cuenta Profile";
    propertiesExterno.wsPlumeCustomerCrearCuentaTimeoutConnect = "5000";
    propertiesExterno.wsPlumeCustomerCrearCuentaTimeoutEjecucion = "5000";
    propertiesExterno.wsPlumeCustomerEliminarCuentaAuthorization = "Basic dXNyU2VydlNnYTpRQHZlMTIzNDU2";
    propertiesExterno.wsPlumeCustomerEliminarCuentaMetodo = "Ws Plume Customer Eliminar Cuenta Metodo";
    propertiesExterno.wsPlumeCustomerEliminarCuentaNombre = "Ws Plume Customer Eliminar Cuenta Nombre";
    propertiesExterno.wsPlumeCustomerEliminarCuentaTimeoutConnect = "2000";
    propertiesExterno.wsPlumeCustomerEliminarCuentaTimeoutEjecucion = "5000";
    propertiesExterno.wsPlumeCustomerObtenerUbicacionMetodo = "Ws Plume Customer Obtener Ubicacion Metodo";
    propertiesExterno.wsPlumeCustomerObtenerUbicacionNombre = "Ws Plume Customer Obtener Ubicacion Nombre";
    propertiesExterno.wsPlumeCustomerObtenerUbicacionTimeoutConnect = "5000";
    propertiesExterno.wsPlumeCustomerObtenerUbicacionTimeoutEjecucion = "5000";
    propertiesExterno.wsPlumeLocationConfigNivelBasicService = "Ws Plume Location Config Nivel Basic Service";
    propertiesExterno.wsPlumeLocationConfigNivelMetodo = "Ws Plume Location Config Nivel Metodo";
    propertiesExterno.wsPlumeLocationConfigNivelNombre = "Ws Plume Location Config Nivel Nombre";
    propertiesExterno.wsPlumeLocationConfigNivelTimeoutConnect = "5000";
    propertiesExterno.wsPlumeLocationConfigNivelTimeoutEjecucion = "5000";
    propertiesExterno.wsPlumeLocationConsultarRepetidorAuthorization = "Basic dXNyU2VydlNnYTpRQHZlMTIzNDU2";
    propertiesExterno.wsPlumeLocationCrearRepetidorAuthorization = "Basic dXNyU2VydlNnYTpRQHZlMTIzNDU2";
    propertiesExterno.wsPlumeLocationCrearUbiMetodo = "Ws Plume Location Crear Ubi Metodo";
    propertiesExterno.wsPlumeLocationCrearUbiNombre = "Ws Plume Location Crear Ubi Nombre";
    propertiesExterno.wsPlumeLocationCrearUbiTimeoutConnect = "2000";
    propertiesExterno.wsPlumeLocationCrearUbiTimeoutEjecucion = "5000";
    propertiesExterno.wsPlumeLocationEliminarRepetidorAuthorization = "Basic dXNyU2VydlNnYTpRQHZlMTIzNDU2";
    propertiesExterno.wsPlumeLocationEliminarUbiAuthorization = "JaneDoe";
    propertiesExterno.wsPlumeLocationEliminarUbiEjecucion = "5000";
    propertiesExterno.wsPlumeLocationEliminarUbiTimeoutConnect = "5000";
    propertiesExterno.wsPlumeLocationObtenerRepetidorAuthorization = "Basic dXNyU2VydlNnYTpRQHZlMTIzNDU2";
    propertiesExterno.wsPlumeLocationObtenerRepetidorEjecucion = "5000";
    propertiesExterno.wsPlumeLocationObtenerRepetidorMetodo = "obtenerRepetidores";
    propertiesExterno.wsPlumeLocationObtenerRepetidorNombre = "plumelocation";
    propertiesExterno.wsPlumeLocationObtenerRepetidorTimeoutConnect = "2000";
    propertiesExterno.wsPlumeLocationObtenerUbicaAuthorization = "JaneDoe";
    propertiesExterno.wsPlumeNodeConsultarRepetidorMetodo = "consultarRepetidor";
    propertiesExterno.wsPlumeNodeConsultarRepetidorNombre = "plumenode";
    propertiesExterno.wsPlumeNodeConsultarRepetidorTimeoutConnect = "2000";
    propertiesExterno.wsPlumeNodeConsultarRepetidorTimeoutEjecucion = "5000";
    propertiesExterno.wsPlumeNodeCrearRepetidorMetodo = "crearRepetidor";
    propertiesExterno.wsPlumeNodeCrearRepetidorNombre = "plumenode";
    propertiesExterno.wsPlumeNodeCrearRepetidorTimeoutConnect = "2000";
    propertiesExterno.wsPlumeNodeCrearRepetidorTimeoutEjecucion = "5000";
    propertiesExterno.wsPlumeNodeEliminarRepetidorMetodo = "eliminarRepetidor";
    propertiesExterno.wsPlumeNodeEliminarRepetidorNombre = "plumenode";
    propertiesExterno.wsPlumeNodeEliminarRepetidorTimeoutConnect = "2000";
    propertiesExterno.wsPlumeNodeEliminarRepetidorTimeoutEjecucion = "5000";
    propertiesExterno.wsUrlGeneraTokenClaro = "http://172.19.172.37/v1.0/MIG1_postventa/integration_Infrastructure_Domain/api/seguridadTokenV2/generarTokenV2";
    propertiesExterno.wsUrlGeneraTokenPlume = "http://172.19.172.37/v1.0/MIG1_postventa/customer_Domain/custInfo/plumesecurity/generarToken";
    propertiesExterno.wsUrlPlumeCustomerCrearCuenta = "http://172.19.172.37/v1.0/MIG1_postventa/customer_Domain/custInfo/plumecustomer/crearCuentaCliente";
    propertiesExterno.wsUrlPlumeCustomerEliminarCuenta = "http://172.19.172.37/v1.0/MIG1_postventa/customer_Domain/custInfo/plumecustomer/eliminarCuentaCliente/{id_custPlume}";
    propertiesExterno.wsUrlPlumeCustomerObtenerUbicacion = "http://172.19.172.37/v1.0/MIG1_postventa/customer_Domain/custInfo/plumecustomer/obtenerUbicaciones/{idCustPlume}";
    propertiesExterno.wsUrlPlumeLocationConfigNivel = "http://172.19.172.37/v1.0/MIG1_postventa/resource_Domain/location/plumelocation/configNivelServicio/{idCustPlume}/{idLocPlume}";
    propertiesExterno.wsUrlPlumeLocationCrearUbi = "http://172.19.172.37/v1.0/MIG1_postventa/resource_Domain/location/plumelocation/crearUbicacion/{idCustPlume}";
    propertiesExterno.wsUrlPlumeLocationEliminarUbi = "http://172.19.172.37/v1.0/MIG1_postventa/resource_Domain/location/plumelocation/eliminarUbicacion/{idCustPlume}/{idLocPlume}";
    propertiesExterno.wsUrlPlumeLocationEliminarUbiMetodo = "eliminarUbicacion";
    propertiesExterno.wsUrlPlumeLocationEliminarUbiNombre = "plumelocation";
    propertiesExterno.wsUrlPlumeLocationObtenerRepetidor = "http://172.19.172.37/v1.0/MIG1_postventa/resource_Domain/location/plumelocation/obtenerRepetidores/{idCustPlume}/{idLocPlume}";
    propertiesExterno.wsUrlPlumeNodeConsultarRepetidor = "http://172.19.172.37/v1.0/MIG1_postventa/resource_Domain/resourceInv/plumenode/consultarRepetidor/{serialNumber}";
    propertiesExterno.wsUrlPlumeNodeCrearRepetidor = "http://172.19.172.37/v1.0/MIG1_postventa/resource_Domain/resourceInv/plumenode/crearRepetidor/{idCustPlume}/{idLocPlume}";
    propertiesExterno.wsUrlPlumeNodeEliminarRepetidor = "http://172.19.172.37/v1.0/MIG1_postventa/resource_Domain/resourceInv/plumenode/eliminarRepetidor/{idCustPlume}/{idLocPlume}/{serialNumber}";
  }

  @Test
  public void testAprovisionarPlume() throws BaseException, SQLException {
    HeaderRequest headerRequest = new HeaderRequest();
    headerRequest.setAccept("Accept");
    headerRequest.setCanal("Canal");
    headerRequest.setIdTransaccion("123456");
    headerRequest.setMsgid("Msgid");
    headerRequest.setTimestamp("Timestamp");
    headerRequest.setUserId("42");

    AprovisionarPlumeRequestType aprovisionarPlumeRequestType = new AprovisionarPlumeRequestType();
    aprovisionarPlumeRequestType.setCoId("42");
    aprovisionarPlumeRequestType.setEquipos(new ArrayList<>());
    aprovisionarPlumeRequestType.setIdTipo("1");
    aprovisionarPlumeRequestType.setListaOpcional(new ArrayList<>());
    aprovisionarPlumeRequestType.setNumeroSot("123456");

    AprovisionarPlumeRequest aprovisionarPlumeRequest = new AprovisionarPlumeRequest();
    aprovisionarPlumeRequest.setAprovisionarPlumeRequest(aprovisionarPlumeRequestType);

    DatosClienteBeanRes resDatosCliente = new DatosClienteBeanRes();
    resDatosCliente.setCodigoRespuesta("0");
    resDatosCliente.setValidaCuenta("1");
    resDatosCliente.setCodigoSucursal("123");
    resDatosCliente.setAccountId("123");
    resDatosCliente.setEmail("asda@mail.test");
    resDatosCliente.setNombres("tester");
    List<CursorValidaCuenta> cursorDatos = new ArrayList<CursorValidaCuenta>();
    CursorValidaCuenta cursorValC = new CursorValidaCuenta();
    cursorValC.setCodSuc("123");
    cursorDatos.add(cursorValC);
    resDatosCliente.setCursor(cursorDatos);

    GeneraTokenClaroResponse tokenClaroResponse = new GeneraTokenClaroResponse();

    when(sgaDao.datosCliente(Mockito.any(String.class), Mockito.any(DatosClienteBeanReq.class))).thenReturn(resDatosCliente);
    when(generaTokenClaroClientImpl.generarToken(Mockito.any(String.class), Mockito.any(String.class),
      Mockito.any(GeneraTokenClaroRequest.class), Mockito.any(PropertiesExterno.class))).thenReturn(tokenClaroResponse);

    ResponseAuditType responseAudit = aprovisionarPlumeServiceImpl
      .aprovisionarPlume("123456", headerRequest, aprovisionarPlumeRequest, propertiesExterno)
      .getAprovisionarPlumeResponse()
      .getResponseAudit();
    assertEquals("Idt3 Mensaje", responseAudit.getMensajeRespuesta());
    assertEquals("123456", responseAudit.getIdTransaccion());
    assertEquals("3", responseAudit.getCodigoRespuesta());
  }

  @Test
  public void testAprovisionarPlume2() {
    HeaderRequest headerRequest = new HeaderRequest();
    headerRequest.setAccept("Accept");
    headerRequest.setCanal("Canal");
    headerRequest.setIdTransaccion("123456");
    headerRequest.setMsgid("Msgid");
    headerRequest.setTimestamp("Timestamp");
    headerRequest.setUserId("42");

    AprovisionarPlumeRequestType aprovisionarPlumeRequestType = new AprovisionarPlumeRequestType();
    aprovisionarPlumeRequestType.setCoId("42");
    aprovisionarPlumeRequestType.setEquipos(new ArrayList<>());
    aprovisionarPlumeRequestType.setIdTipo("Id Tipo");
    aprovisionarPlumeRequestType.setListaOpcional(new ArrayList<>());
    aprovisionarPlumeRequestType.setNumeroSot("Numero Sot");

    AprovisionarPlumeRequest aprovisionarPlumeRequest = new AprovisionarPlumeRequest();
    aprovisionarPlumeRequest.setAprovisionarPlumeRequest(aprovisionarPlumeRequestType);

    ResponseAuditType responseAudit = aprovisionarPlumeServiceImpl
      .aprovisionarPlume("[aprovisionarPlume idTx=", headerRequest, aprovisionarPlumeRequest, propertiesExterno)
      .getAprovisionarPlumeResponse()
      .getResponseAudit();
    assertEquals("idTipo no válido", responseAudit.getMensajeRespuesta());
    assertEquals("123456", responseAudit.getIdTransaccion());
    assertEquals("2", responseAudit.getCodigoRespuesta());
  }

  @Test
  public void testAprovisionarPlumeEQP() throws BaseException, SQLException {
    HeaderRequest headerRequest = new HeaderRequest();
    headerRequest.setAccept("Accept");
    headerRequest.setCanal("Canal");
    headerRequest.setIdTransaccion("123456");
    headerRequest.setMsgid("Msgid");
    headerRequest.setTimestamp("Timestamp");
    headerRequest.setUserId("42");

    AprovisionarPlumeRequestType aprovisionarPlumeRequestType = new AprovisionarPlumeRequestType();
    aprovisionarPlumeRequestType.setCoId("42");
    aprovisionarPlumeRequestType.setEquipos(new ArrayList<>());
    aprovisionarPlumeRequestType.setIdTipo("1");
    aprovisionarPlumeRequestType.setListaOpcional(new ArrayList<>());
    aprovisionarPlumeRequestType.setNumeroSot("123456");

    AprovisionarPlumeRequest aprovisionarPlumeRequest = new AprovisionarPlumeRequest();
    aprovisionarPlumeRequest.setAprovisionarPlumeRequest(aprovisionarPlumeRequestType);

    DatosClienteBeanRes resDatosCliente = new DatosClienteBeanRes();
    resDatosCliente.setCodigoRespuesta("0");
    resDatosCliente.setValidaCuenta("1");
    resDatosCliente.setCodigoSucursal("123");
    resDatosCliente.setAccountId("123");
    resDatosCliente.setEmail("asda@mail.test");
    resDatosCliente.setNombres("tester");
    List<CursorValidaCuenta> cursorDatos = new ArrayList<CursorValidaCuenta>();
    CursorValidaCuenta cursorValC = new CursorValidaCuenta();
    cursorValC.setCodSuc("123");
    cursorDatos.add(cursorValC);
    resDatosCliente.setCursor(cursorDatos);

    when(sgaDao.datosCliente(any(), any())).thenReturn(resDatosCliente);
    when(UtilTokens.subprocesoTokens(Mockito.any(String.class), Mockito.any(HeaderRequest.class), Mockito.any(String.class)
      , Mockito.any(PropertiesExterno.class), Mockito.any(String.class), Mockito.any(AprovisionarPlumeRequest.class)
      , Mockito.any(DatosClienteBeanRes.class))).thenReturn("eyJraWQiOiJfTWpXMDF6dnFvZjZuY0plVW41YVVTOFpQeUtEellHSVJLN1Q2VVpzWG5zIiwiYWxnIjoiUlMyNTYifQ.eyJ2ZXIiOjEsImp0aSI6IkFULk5CZWQ1ZDFIYzJmd0p3OER2WXp4NHZQOEdJXzVFcG1kZ2NWa0stQTZTUG8iLCJpc3MiOiJodHRwczovL2V4dGVybmFsLnNzby5wbHVtZS5jb20vb2F1dGgyL2F1c2MwMzZmZTJvVllHMWVYMzU3IiwiYXVkIjoiYXBpOi8vbTJtX2JldGFfdXN3Ml9wcm9kIiwiaWF0IjoxNzEwOTcxMTc3LCJleHAiOjE3MTEwNTc1NzcsImNpZCI6IjBvYXlsYjR4MDVPODVINlRDMzU3Iiwic2NwIjpbInBhcnRuZXJJZDo1ZWU4MGEyMWY5NDc5NDJjYzYzNDkyNDQiLCJyb2xlOnBhcnRuZXJJZEFkbWluIl0sInN1YiI6IjBvYXlsYjR4MDVPODVINlRDMzU3In0.mPcImlu6f4JeF2kG-JT5YZWoAtiYehP0IVcpobCHjt2LA_vikCp8rMWInEVry2ac1sVTcumy2unPfbYpYpLq0YvCGBS2hr37pONLwWI0mPan7JOpq1OzBhSB_twZ9wyxbQ9OgaBmU--mJB6b_iESwzdFCTQc6D1uZp8OQT_ms-Bqe_K8BJuh46mcIW6fvktIkb9pBgHgeqRZlhFoZQLsDsnKTeH8sHNdv4wIsfAQgV3Qzb2BCK6Yotjqbg-kh6N3f8L3nh2yOpqLSwjglKfcNZ5MzODx06JLNxHKmj_ZeuU-zb8WQUCX5k626IuvYYgC7TAW1FFP2QKtvrh3XKnSjw\n" +
      "3a5d987d-6b9e-449a-83f3-e2ca718df557");

    ResponseAuditType responseAudit = aprovisionarPlumeServiceImpl
      .aprovisionarPlume("[aprovisionarPlume idTx=", headerRequest, aprovisionarPlumeRequest, propertiesExterno)
      .getAprovisionarPlumeResponse()
      .getResponseAudit();
    assertEquals("Idt3 Mensaje", responseAudit.getMensajeRespuesta());
    assertEquals("123456", responseAudit.getIdTransaccion());
    assertEquals("3", responseAudit.getCodigoRespuesta());
  }

  @Test
  public void testAprovisionarPlumeLOC() throws BaseException, SQLException {
    HeaderRequest headerRequest = new HeaderRequest();
    headerRequest.setAccept("Accept");
    headerRequest.setCanal("Canal");
    headerRequest.setIdTransaccion("123456");
    headerRequest.setMsgid("Msgid");
    headerRequest.setTimestamp("Timestamp");
    headerRequest.setUserId("42");

    AprovisionarPlumeRequestType aprovisionarPlumeRequestType = new AprovisionarPlumeRequestType();
    aprovisionarPlumeRequestType.setCoId("42");
    aprovisionarPlumeRequestType.setEquipos(new ArrayList<>());
    aprovisionarPlumeRequestType.setIdTipo("1");
    aprovisionarPlumeRequestType.setListaOpcional(new ArrayList<>());
    aprovisionarPlumeRequestType.setNumeroSot("123456");

    AprovisionarPlumeRequest aprovisionarPlumeRequest = new AprovisionarPlumeRequest();
    aprovisionarPlumeRequest.setAprovisionarPlumeRequest(aprovisionarPlumeRequestType);

    DatosClienteBeanRes resDatosCliente = new DatosClienteBeanRes();
    resDatosCliente.setCodigoRespuesta("0");
    resDatosCliente.setValidaCuenta("1");
    resDatosCliente.setCodigoSucursal("123");
    resDatosCliente.setAccountId("123");
    resDatosCliente.setEmail("asda@mail.test");
    resDatosCliente.setNombres("tester");
    List<CursorValidaCuenta> cursorDatos = new ArrayList<CursorValidaCuenta>();
    CursorValidaCuenta cursorValC = new CursorValidaCuenta();
    cursorValC.setCodSuc("1234");
    cursorDatos.add(cursorValC);
    resDatosCliente.setCursor(cursorDatos);

    when(sgaDao.datosCliente(any(), any())).thenReturn(resDatosCliente);
    when(UtilTokens.subprocesoTokens(Mockito.any(String.class), Mockito.any(HeaderRequest.class), Mockito.any(String.class)
      , Mockito.any(PropertiesExterno.class), Mockito.any(String.class), Mockito.any(AprovisionarPlumeRequest.class)
      , Mockito.any(DatosClienteBeanRes.class))).thenReturn("eyJraWQiOiJfTWpXMDF6dnFvZjZuY0plVW41YVVTOFpQeUtEellHSVJLN1Q2VVpzWG5zIiwiYWxnIjoiUlMyNTYifQ.eyJ2ZXIiOjEsImp0aSI6IkFULk5CZWQ1ZDFIYzJmd0p3OER2WXp4NHZQOEdJXzVFcG1kZ2NWa0stQTZTUG8iLCJpc3MiOiJodHRwczovL2V4dGVybmFsLnNzby5wbHVtZS5jb20vb2F1dGgyL2F1c2MwMzZmZTJvVllHMWVYMzU3IiwiYXVkIjoiYXBpOi8vbTJtX2JldGFfdXN3Ml9wcm9kIiwiaWF0IjoxNzEwOTcxMTc3LCJleHAiOjE3MTEwNTc1NzcsImNpZCI6IjBvYXlsYjR4MDVPODVINlRDMzU3Iiwic2NwIjpbInBhcnRuZXJJZDo1ZWU4MGEyMWY5NDc5NDJjYzYzNDkyNDQiLCJyb2xlOnBhcnRuZXJJZEFkbWluIl0sInN1YiI6IjBvYXlsYjR4MDVPODVINlRDMzU3In0.mPcImlu6f4JeF2kG-JT5YZWoAtiYehP0IVcpobCHjt2LA_vikCp8rMWInEVry2ac1sVTcumy2unPfbYpYpLq0YvCGBS2hr37pONLwWI0mPan7JOpq1OzBhSB_twZ9wyxbQ9OgaBmU--mJB6b_iESwzdFCTQc6D1uZp8OQT_ms-Bqe_K8BJuh46mcIW6fvktIkb9pBgHgeqRZlhFoZQLsDsnKTeH8sHNdv4wIsfAQgV3Qzb2BCK6Yotjqbg-kh6N3f8L3nh2yOpqLSwjglKfcNZ5MzODx06JLNxHKmj_ZeuU-zb8WQUCX5k626IuvYYgC7TAW1FFP2QKtvrh3XKnSjw\n" +
      "3a5d987d-6b9e-449a-83f3-e2ca718df557");

    ResponseAuditType responseAudit = aprovisionarPlumeServiceImpl
      .aprovisionarPlume("[aprovisionarPlume idTx=", headerRequest, aprovisionarPlumeRequest, propertiesExterno)
      .getAprovisionarPlumeResponse()
      .getResponseAudit();
    assertEquals("Idt3 Mensaje", responseAudit.getMensajeRespuesta());
    assertEquals("123456", responseAudit.getIdTransaccion());
    assertEquals("3", responseAudit.getCodigoRespuesta());
  }

  @Test
  public void testAprovisionarPlumeCTA() throws BaseException, SQLException {
    HeaderRequest headerRequest = new HeaderRequest();
    headerRequest.setAccept("Accept");
    headerRequest.setCanal("Canal");
    headerRequest.setIdTransaccion("123456");
    headerRequest.setMsgid("Msgid");
    headerRequest.setTimestamp("Timestamp");
    headerRequest.setUserId("42");

    AprovisionarPlumeRequestType aprovisionarPlumeRequestType = new AprovisionarPlumeRequestType();
    aprovisionarPlumeRequestType.setCoId("42");
    aprovisionarPlumeRequestType.setEquipos(new ArrayList<>());
    aprovisionarPlumeRequestType.setIdTipo("1");
    aprovisionarPlumeRequestType.setListaOpcional(new ArrayList<>());
    aprovisionarPlumeRequestType.setNumeroSot("123456");

    AprovisionarPlumeRequest aprovisionarPlumeRequest = new AprovisionarPlumeRequest();
    aprovisionarPlumeRequest.setAprovisionarPlumeRequest(aprovisionarPlumeRequestType);

    DatosClienteBeanRes resDatosCliente = new DatosClienteBeanRes();
    resDatosCliente.setCodigoRespuesta("0");
    resDatosCliente.setValidaCuenta("0");
    resDatosCliente.setCodigoSucursal("123");
    resDatosCliente.setAccountId("123");
    resDatosCliente.setEmail("asda@mail.test");
    resDatosCliente.setNombres("tester");
    List<CursorValidaCuenta> cursorDatos = new ArrayList<CursorValidaCuenta>();
    CursorValidaCuenta cursorValC = new CursorValidaCuenta();
    cursorValC.setCodSuc("1234");
    cursorDatos.add(cursorValC);
    resDatosCliente.setCursor(cursorDatos);

    when(sgaDao.datosCliente(any(), any())).thenReturn(resDatosCliente);
    when(UtilTokens.subprocesoTokens(Mockito.any(String.class), Mockito.any(HeaderRequest.class), Mockito.any(String.class)
      , Mockito.any(PropertiesExterno.class), Mockito.any(String.class), Mockito.any(AprovisionarPlumeRequest.class)
      , Mockito.any(DatosClienteBeanRes.class))).thenReturn("eyJraWQiOiJfTWpXMDF6dnFvZjZuY0plVW41YVVTOFpQeUtEellHSVJLN1Q2VVpzWG5zIiwiYWxnIjoiUlMyNTYifQ.eyJ2ZXIiOjEsImp0aSI6IkFULk5CZWQ1ZDFIYzJmd0p3OER2WXp4NHZQOEdJXzVFcG1kZ2NWa0stQTZTUG8iLCJpc3MiOiJodHRwczovL2V4dGVybmFsLnNzby5wbHVtZS5jb20vb2F1dGgyL2F1c2MwMzZmZTJvVllHMWVYMzU3IiwiYXVkIjoiYXBpOi8vbTJtX2JldGFfdXN3Ml9wcm9kIiwiaWF0IjoxNzEwOTcxMTc3LCJleHAiOjE3MTEwNTc1NzcsImNpZCI6IjBvYXlsYjR4MDVPODVINlRDMzU3Iiwic2NwIjpbInBhcnRuZXJJZDo1ZWU4MGEyMWY5NDc5NDJjYzYzNDkyNDQiLCJyb2xlOnBhcnRuZXJJZEFkbWluIl0sInN1YiI6IjBvYXlsYjR4MDVPODVINlRDMzU3In0.mPcImlu6f4JeF2kG-JT5YZWoAtiYehP0IVcpobCHjt2LA_vikCp8rMWInEVry2ac1sVTcumy2unPfbYpYpLq0YvCGBS2hr37pONLwWI0mPan7JOpq1OzBhSB_twZ9wyxbQ9OgaBmU--mJB6b_iESwzdFCTQc6D1uZp8OQT_ms-Bqe_K8BJuh46mcIW6fvktIkb9pBgHgeqRZlhFoZQLsDsnKTeH8sHNdv4wIsfAQgV3Qzb2BCK6Yotjqbg-kh6N3f8L3nh2yOpqLSwjglKfcNZ5MzODx06JLNxHKmj_ZeuU-zb8WQUCX5k626IuvYYgC7TAW1FFP2QKtvrh3XKnSjw\n" +
      "3a5d987d-6b9e-449a-83f3-e2ca718df557");

    ResponseAuditType responseAudit = aprovisionarPlumeServiceImpl
      .aprovisionarPlume("[aprovisionarPlume idTx=", headerRequest, aprovisionarPlumeRequest, propertiesExterno)
      .getAprovisionarPlumeResponse()
      .getResponseAudit();
    assertEquals("Idt3 Mensaje", responseAudit.getMensajeRespuesta());
    assertEquals("123456", responseAudit.getIdTransaccion());
    assertEquals("3", responseAudit.getCodigoRespuesta());
  }

  @Test
  public void testAprovisionarPlumeIDFProc() throws BaseException, SQLException {
    HeaderRequest headerRequest = new HeaderRequest();
    headerRequest.setAccept("Accept");
    headerRequest.setCanal("Canal");
    headerRequest.setIdTransaccion("123456");
    headerRequest.setMsgid("Msgid");
    headerRequest.setTimestamp("Timestamp");
    headerRequest.setUserId("42");

    AprovisionarPlumeRequestType aprovisionarPlumeRequestType = new AprovisionarPlumeRequestType();
    aprovisionarPlumeRequestType.setCoId("42");
    aprovisionarPlumeRequestType.setEquipos(new ArrayList<>());
    aprovisionarPlumeRequestType.setIdTipo("3");
    aprovisionarPlumeRequestType.setListaOpcional(new ArrayList<>());
    aprovisionarPlumeRequestType.setNumeroSot("123456");

    AprovisionarPlumeRequest aprovisionarPlumeRequest = new AprovisionarPlumeRequest();
    aprovisionarPlumeRequest.setAprovisionarPlumeRequest(aprovisionarPlumeRequestType);

    when(UtilTokens.subprocesoTokens(Mockito.any(String.class), Mockito.any(HeaderRequest.class), Mockito.any(String.class)
        , Mockito.any(PropertiesExterno.class), Mockito.any(String.class), Mockito.any(AprovisionarPlumeRequest.class)
        , Mockito.any(DatosClienteBeanRes.class))).thenReturn("eyJraWQiOiJfTWpXMDF6dnFvZjZuY0plVW41YVVTOFpQeUtEellHSVJLN1Q2VVpzWG5zIiwiYWxnIjoiUlMyNTYifQ.eyJ2ZXIiOjEsImp0aSI6IkFULk5CZWQ1ZDFIYzJmd0p3OER2WXp4NHZQOEdJXzVFcG1kZ2NWa0stQTZTUG8iLCJpc3MiOiJodHRwczovL2V4dGVybmFsLnNzby5wbHVtZS5jb20vb2F1dGgyL2F1c2MwMzZmZTJvVllHMWVYMzU3IiwiYXVkIjoiYXBpOi8vbTJtX2JldGFfdXN3Ml9wcm9kIiwiaWF0IjoxNzEwOTcxMTc3LCJleHAiOjE3MTEwNTc1NzcsImNpZCI6IjBvYXlsYjR4MDVPODVINlRDMzU3Iiwic2NwIjpbInBhcnRuZXJJZDo1ZWU4MGEyMWY5NDc5NDJjYzYzNDkyNDQiLCJyb2xlOnBhcnRuZXJJZEFkbWluIl0sInN1YiI6IjBvYXlsYjR4MDVPODVINlRDMzU3In0.mPcImlu6f4JeF2kG-JT5YZWoAtiYehP0IVcpobCHjt2LA_vikCp8rMWInEVry2ac1sVTcumy2unPfbYpYpLq0YvCGBS2hr37pONLwWI0mPan7JOpq1OzBhSB_twZ9wyxbQ9OgaBmU--mJB6b_iESwzdFCTQc6D1uZp8OQT_ms-Bqe_K8BJuh46mcIW6fvktIkb9pBgHgeqRZlhFoZQLsDsnKTeH8sHNdv4wIsfAQgV3Qzb2BCK6Yotjqbg-kh6N3f8L3nh2yOpqLSwjglKfcNZ5MzODx06JLNxHKmj_ZeuU-zb8WQUCX5k626IuvYYgC7TAW1FFP2QKtvrh3XKnSjw\n" +
        "3a5d987d-6b9e-449a-83f3-e2ca718df557");

    ResponseAuditType responseAudit = aprovisionarPlumeServiceImpl
        .aprovisionarPlume("[aprovisionarPlume idTx=", headerRequest, aprovisionarPlumeRequest, propertiesExterno)
        .getAprovisionarPlumeResponse()
        .getResponseAudit();
    assertEquals("idTipo no válido", responseAudit.getMensajeRespuesta());
    assertEquals("123456", responseAudit.getIdTransaccion());
    assertEquals("2", responseAudit.getCodigoRespuesta());
  }

  @Test
  public void testValidarRegistrarClienteSGA() throws WSException {
    RegistrarClieteRes registrarClieteRes = new RegistrarClieteRes();
    registrarClieteRes.setCodigoRespuesta("123456");
    registrarClieteRes.setMensajeRespuesta("Mensaje Respuesta");

    assertThrows(WSException.class,
      () -> aprovisionarPlumeServiceImpl.validarRegistrarClienteSGA(registrarClieteRes, propertiesExterno));
  }

  @Test
  public void testValidarRegistrarClienteSGA2() throws WSException {
    RegistrarClieteRes registrarClieteRes = mock(RegistrarClieteRes.class);
    when(registrarClieteRes.getCodigoRespuesta()).thenReturn("123456");
    when(registrarClieteRes.getMensajeRespuesta()).thenReturn("Mensaje Respuesta");
    doNothing().when(registrarClieteRes).setCodigoRespuesta(any());
    doNothing().when(registrarClieteRes).setMensajeRespuesta(any());
    registrarClieteRes.setCodigoRespuesta("123456");
    registrarClieteRes.setMensajeRespuesta("Mensaje Respuesta");
    assertThrows(WSException.class,
      () -> aprovisionarPlumeServiceImpl.validarRegistrarClienteSGA(registrarClieteRes, propertiesExterno));
    verify(registrarClieteRes).getCodigoRespuesta();
    verify(registrarClieteRes).getMensajeRespuesta();
    verify(registrarClieteRes).setCodigoRespuesta(any());
    verify(registrarClieteRes).setMensajeRespuesta(any());
  }

  @Test
  public void testActividad9() throws WSException, IDFException {
    HeaderRequest headerRequest = new HeaderRequest();
    headerRequest.setAccept("Accept");
    headerRequest.setCanal("Canal");
    headerRequest.setIdTransaccion("123456");
    headerRequest.setMsgid("Msgid");
    headerRequest.setTimestamp("Timestamp");
    headerRequest.setUserId("42");

    List<ListaEquiposAPType> equipos = new ArrayList<>();
    ListaEquiposAPType eqp1 = new ListaEquiposAPType();
    eqp1.setAlias("test");
    eqp1.setModelo("test");
    eqp1.setNumeroSerie("12345");
    equipos.add(eqp1);

    AprovisionarPlumeRequestType aprovisionarPlumeRequestType = new AprovisionarPlumeRequestType();
    aprovisionarPlumeRequestType.setCoId("42");
    aprovisionarPlumeRequestType.setEquipos(equipos);
    aprovisionarPlumeRequestType.setIdTipo("Id Tipo");
    aprovisionarPlumeRequestType.setListaOpcional(new ArrayList<>());
    aprovisionarPlumeRequestType.setNumeroSot("Numero Sot");

    AprovisionarPlumeRequest aprovisionarPlumeRequest = new AprovisionarPlumeRequest();
    aprovisionarPlumeRequest.setAprovisionarPlumeRequest(aprovisionarPlumeRequestType);

    Fault fault = new Fault();
    fault.setDetail(new DetailType());
    fault.setFaultactor("Faultactor");
    fault.setFaultcode("Faultcode");
    fault.setFaultstring("Faultstring");

    CrearCuentaBodyResponse crearCuentaBodyResponse = new CrearCuentaBodyResponse();
    crearCuentaBodyResponse.setAcceptLanguage("en");
    crearCuentaBodyResponse.setAccountId("42");
    crearCuentaBodyResponse.setAnonymous(true);
    crearCuentaBodyResponse.setId("42");
    crearCuentaBodyResponse.setLocationId("42");
    crearCuentaBodyResponse.setPartnerId("42");

    HeaderResponse headerResponse = new HeaderResponse();
    headerResponse.setConsumer("Consumer");
    headerResponse.setPid("Pid");
    headerResponse.setStatus(new Status());
    headerResponse.setTimestamp("Timestamp");
    headerResponse.setVarArg("Var Arg");

    Header header = new Header();
    header.setHeaderResponse(headerResponse);

    CrearCuentaMessageResponse crearCuentaMessageResponse = new CrearCuentaMessageResponse();
    crearCuentaMessageResponse.setBody(crearCuentaBodyResponse);
    crearCuentaMessageResponse.setHeader(header);

    CrearCuentaResponse crearCuentaResponse = new CrearCuentaResponse();
    crearCuentaResponse.setMessageResponse(crearCuentaMessageResponse);

    AppTime appTime = new AppTime();
    appTime.setEnable(true);
    appTime.setUpdatedAt("2020-03-01");

    Backhaul backhaul = new Backhaul();
    backhaul.setCreatedAt("Jan 1, 2020 8:00am GMT+0100");
    backhaul.setDynamicBeacon("Dynamic Beacon");
    backhaul.setHitlessTopology("Hitless Topology");
    backhaul.setMode("Mode");
    backhaul.setVersion("1.0.2");
    backhaul.setWds("Wds");
    backhaul.setWpaMode("Wpa Mode");

    BandSteering bandSteering = new BandSteering();
    bandSteering.setAuto(true);
    bandSteering.setUpdatedAt("2020-03-01");
    bandSteering.setVersion("1.0.2");

    ClientSteering clientSteering = new ClientSteering();
    clientSteering.setAuto(true);
    clientSteering.setUpdatedAt("2020-03-01");
    clientSteering.setVersion("1.0.2");

    ControlMode controlMode = new ControlMode();
    controlMode.setMode("Mode");
    controlMode.setUpdatedAt("2020-03-01");

    DPPConfiguration dppConfiguration = new DPPConfiguration();
    dppConfiguration.setEnrollments(new ArrayList<>());
    dppConfiguration.setMode("Mode");
    dppConfiguration.setModeRealized("Mode Realized");

    HAAHSConfiguration haahsConfiguration = new HAAHSConfiguration();
    haahsConfiguration.setMode("Mode");
    haahsConfiguration.setSubscription(new Subscription());

    IPV6 ipv6 = new IPV6();
    ipv6.setAddressingConfig("42 Main St");
    ipv6.setMode("Mode");

    ISPSpeedTestConfiguration ispSpeedTestConfiguration = new ISPSpeedTestConfiguration();
    ispSpeedTestConfiguration.setCreatedAt("Jan 1, 2020 8:00am GMT+0100");
    ispSpeedTestConfiguration.setEnable(true);
    ispSpeedTestConfiguration.setEnableAllNodes(true);
    ispSpeedTestConfiguration.setVersion("1.0.2");

    MonitorMode monitorMode = new MonitorMode();
    monitorMode.setEnable(true);
    monitorMode.setUpdatedAt("2020-03-01");
    monitorMode.setVersion("1.0.2");

    Optimizations optimizations = new Optimizations();
    optimizations.setAuto(true);
    optimizations.setDfsMode("Dfs Mode");
    optimizations.setEnable(true);
    optimizations.setHopPenalty("Hop Penalty");
    optimizations.setPreCACScheduler("Pre CACScheduler");
    optimizations.setPrefer160MhzMode("Prefer160 Mhz Mode");
    optimizations.setUpdatedAt("2020-03-01");
    optimizations.setVersion("1.0.2");
    optimizations.setZeroWaitDfsMode("Zero Wait Dfs Mode");

    CrearUbicacionBodyResponse crearUbicacionBodyResponse = new CrearUbicacionBodyResponse();
    crearUbicacionBodyResponse.setAccountId("42");
    crearUbicacionBodyResponse.setCustomerId("42");
    crearUbicacionBodyResponse.setDppConfiguration(dppConfiguration);
    crearUbicacionBodyResponse.setError(null);
    crearUbicacionBodyResponse.setFlex(true);
    crearUbicacionBodyResponse.setFreezeTemplates(new ArrayList<>());
    crearUbicacionBodyResponse.setGroupIds(new ArrayList<>());
    crearUbicacionBodyResponse.setGroupOfUnassignedDevicesFreezeSchedules(new ArrayList<>());
    crearUbicacionBodyResponse.setGroupOfUnassignedDevicesFreezeTemplates(new ArrayList<>());
    crearUbicacionBodyResponse.setHaahsConfiguration(haahsConfiguration);
    crearUbicacionBodyResponse.setHomeSecurityPriority("Home Security Priority");
    crearUbicacionBodyResponse.setId("42");

    HeaderResponse headerResponse1 = new HeaderResponse();
    headerResponse1.setConsumer("Consumer");
    headerResponse1.setPid("Pid");
    headerResponse1.setStatus(new Status());
    headerResponse1.setTimestamp("Timestamp");
    headerResponse1.setVarArg("Var Arg");

    Header header1 = new Header();
    header1.setHeaderResponse(headerResponse1);

    CrearUbicacionMessageResponse crearUbicacionMessageResponse = new CrearUbicacionMessageResponse();
    crearUbicacionMessageResponse.setBody(crearUbicacionBodyResponse);
    crearUbicacionMessageResponse.setHeader(header1);

    CrearUbicacionResponse crearUbicacionResponse = new CrearUbicacionResponse();
    crearUbicacionResponse.setMessageResponse(crearUbicacionMessageResponse);

    DatosClienteBeanRes datosClienteBeanRes = new DatosClienteBeanRes();
    datosClienteBeanRes.setAccountId("42");
    ArrayList<ListaEquiposProvisionados> listaEquiposProvisionadosList = new ArrayList<>();
    List<ListaEquiposProvisionados> actualActividad9Result = aprovisionarPlumeServiceImpl.actividad9("123456",
      headerRequest, aprovisionarPlumeRequest, "Mensaje Trx", "ABC123", "ABC123", "Id Loc Plume", "Proceso",
      crearCuentaResponse, crearUbicacionResponse, datosClienteBeanRes, listaEquiposProvisionadosList, true,
      propertiesExterno);
    assertSame(listaEquiposProvisionadosList, actualActividad9Result);
    assertTrue(actualActividad9Result.isEmpty());
  }

  @Test
  public void testActividad9False() throws WSException {

    HeaderRequest headerRequest = new HeaderRequest();
    headerRequest.setAccept("Accept");
    headerRequest.setCanal("Canal");
    headerRequest.setIdTransaccion("123456");
    headerRequest.setMsgid("Msgid");
    headerRequest.setTimestamp("Timestamp");
    headerRequest.setUserId("42");

    List<ListaEquiposAPType> equipos = new ArrayList<>();
    ListaEquiposAPType eqp1 = new ListaEquiposAPType();
    eqp1.setAlias("test");
    eqp1.setModelo("test");
    eqp1.setNumeroSerie("12345");
    equipos.add(eqp1);

    AprovisionarPlumeRequestType aprovisionarPlumeRequestType = new AprovisionarPlumeRequestType();
    aprovisionarPlumeRequestType.setCoId("42");
    aprovisionarPlumeRequestType.setEquipos(equipos);
    aprovisionarPlumeRequestType.setIdTipo("Id Tipo");
    aprovisionarPlumeRequestType.setListaOpcional(new ArrayList<>());
    aprovisionarPlumeRequestType.setNumeroSot("Numero Sot");

    AprovisionarPlumeRequest aprovisionarPlumeRequest = new AprovisionarPlumeRequest();
    aprovisionarPlumeRequest.setAprovisionarPlumeRequest(aprovisionarPlumeRequestType);

    Fault fault = new Fault();
    fault.setDetail(new DetailType());
    fault.setFaultactor("Faultactor");
    fault.setFaultcode("Faultcode");
    fault.setFaultstring("Faultstring");

    CrearCuentaBodyResponse crearCuentaBodyResponse = new CrearCuentaBodyResponse();
    crearCuentaBodyResponse.setAcceptLanguage("en");
    crearCuentaBodyResponse.setAccountId("42");
    crearCuentaBodyResponse.setAnonymous(true);
    crearCuentaBodyResponse.setCreatedAt("Jan 1, 2020 8:00am GMT+0100");
    crearCuentaBodyResponse.setEmail("jane.doe@example.org");
    crearCuentaBodyResponse.setError(null);
    crearCuentaBodyResponse.setId("42");
    crearCuentaBodyResponse.setLocationId("424");
    crearCuentaBodyResponse.setLocked(true);
    crearCuentaBodyResponse.setName("Name");
    crearCuentaBodyResponse.setPartnerId("42");
    crearCuentaBodyResponse.setProvisioningSsoAuditTrail("Provisioning Sso Audit Trail");
    crearCuentaBodyResponse.setSource("Source");
    crearCuentaBodyResponse.setVersion("1.0.2");

    HeaderResponse headerResponse = new HeaderResponse();
    headerResponse.setConsumer("Consumer");
    headerResponse.setPid("Pid");
    headerResponse.setStatus(new Status());
    headerResponse.setTimestamp("Timestamp");
    headerResponse.setVarArg("Var Arg");

    Header header = new Header();
    header.setHeaderResponse(headerResponse);

    CrearCuentaMessageResponse crearCuentaMessageResponse = new CrearCuentaMessageResponse();
    crearCuentaMessageResponse.setBody(crearCuentaBodyResponse);
    crearCuentaMessageResponse.setHeader(header);

    CrearCuentaResponse crearCuentaResponse = new CrearCuentaResponse();
    crearCuentaResponse.setMessageResponse(crearCuentaMessageResponse);

    AppTime appTime = new AppTime();
    appTime.setEnable(true);
    appTime.setUpdatedAt("2020-03-01");

    Backhaul backhaul = new Backhaul();
    backhaul.setCreatedAt("Jan 1, 2020 8:00am GMT+0100");
    backhaul.setDynamicBeacon("Dynamic Beacon");
    backhaul.setHitlessTopology("Hitless Topology");
    backhaul.setMode("Mode");
    backhaul.setVersion("1.0.2");
    backhaul.setWds("Wds");
    backhaul.setWpaMode("Wpa Mode");

    BandSteering bandSteering = new BandSteering();
    bandSteering.setAuto(true);
    bandSteering.setUpdatedAt("2020-03-01");
    bandSteering.setVersion("1.0.2");

    ClientSteering clientSteering = new ClientSteering();
    clientSteering.setAuto(true);
    clientSteering.setUpdatedAt("2020-03-01");
    clientSteering.setVersion("1.0.2");

    ControlMode controlMode = new ControlMode();
    controlMode.setMode("Mode");
    controlMode.setUpdatedAt("2020-03-01");

    DPPConfiguration dppConfiguration = new DPPConfiguration();
    dppConfiguration.setEnrollments(new ArrayList<>());
    dppConfiguration.setMode("Mode");
    dppConfiguration.setModeRealized("Mode Realized");

    Fault fault1 = new Fault();
    fault1.setDetail(new DetailType());
    fault1.setFaultactor("Faultactor");
    fault1.setFaultcode("Faultcode");
    fault1.setFaultstring("Faultstring");

    HAAHSConfiguration haahsConfiguration = new HAAHSConfiguration();
    haahsConfiguration.setMode("Mode");
    haahsConfiguration.setSubscription(new Subscription());

    IPV6 ipv6 = new IPV6();
    ipv6.setAddressingConfig("42 Main St");
    ipv6.setMode("Mode");

    ISPSpeedTestConfiguration ispSpeedTestConfiguration = new ISPSpeedTestConfiguration();
    ispSpeedTestConfiguration.setCreatedAt("Jan 1, 2020 8:00am GMT+0100");
    ispSpeedTestConfiguration.setEnable(true);
    ispSpeedTestConfiguration.setEnableAllNodes(true);
    ispSpeedTestConfiguration.setVersion("1.0.2");

    MonitorMode monitorMode = new MonitorMode();
    monitorMode.setEnable(true);
    monitorMode.setUpdatedAt("2020-03-01");
    monitorMode.setVersion("1.0.2");

    Optimizations optimizations = new Optimizations();
    optimizations.setAuto(true);
    optimizations.setDfsMode("Dfs Mode");
    optimizations.setEnable(true);
    optimizations.setHopPenalty("Hop Penalty");
    optimizations.setPreCACScheduler("Pre CACScheduler");
    optimizations.setPrefer160MhzMode("Prefer160 Mhz Mode");
    optimizations.setUpdatedAt("2020-03-01");
    optimizations.setVersion("1.0.2");
    optimizations.setZeroWaitDfsMode("Zero Wait Dfs Mode");

    CrearUbicacionBodyResponse crearUbicacionBodyResponse = new CrearUbicacionBodyResponse();
    crearUbicacionBodyResponse.setAccountId("42");
    crearUbicacionBodyResponse.setId("42");

    Status status = new Status();
    status.setMessage("Error");
    status.setCode("1234");

    HeaderResponse headerResponse1 = new HeaderResponse();
    headerResponse1.setConsumer("Consumer");
    headerResponse1.setPid("Pid");
    headerResponse1.setStatus(status);
    headerResponse1.setTimestamp("Timestamp");
    headerResponse1.setVarArg("Var Arg");

    Header header1 = new Header();
    header1.setHeaderResponse(headerResponse1);

    CrearUbicacionMessageResponse crearUbicacionMessageResponse = new CrearUbicacionMessageResponse();
    crearUbicacionMessageResponse.setBody(crearUbicacionBodyResponse);
    crearUbicacionMessageResponse.setHeader(header1);

    CrearUbicacionResponse crearUbicacionResponse = new CrearUbicacionResponse();
    crearUbicacionResponse.setMessageResponse(crearUbicacionMessageResponse);

    DatosClienteBeanRes datosClienteBeanRes = new DatosClienteBeanRes();
    datosClienteBeanRes.setAccountId("42");
    ArrayList<ListaEquiposProvisionados> listaEquiposProvisionadosList = new ArrayList<>();

    CrearRepetidorResponse responseMock = new CrearRepetidorResponse();

    CrearRepetidorMessageResponse messageResponse = new CrearRepetidorMessageResponse();
    CrearRepetidorBodyResponse bodyCR = new CrearRepetidorBodyResponse();
    bodyCR.setError(null);
    messageResponse.setBody(bodyCR);
    messageResponse.setHeader(header1);
    responseMock.setMessageResponse(messageResponse);


    when(plumeNodeClient.crearRepetidor(Mockito.any(String.class), any(CrearRepetidorRequest.class),
      any(String.class), any(String.class), any(String.class), any(String.class), any(String.class))).thenReturn(responseMock);

    assertThrows(NullPointerException.class,
      () -> aprovisionarPlumeServiceImpl.actividad9("1234",
        headerRequest, aprovisionarPlumeRequest, "Mensaje Trx", "ABC123", "ABC123", "Id Loc Plume", "CTA",
        crearCuentaResponse, crearUbicacionResponse, datosClienteBeanRes, listaEquiposProvisionadosList, false,
        propertiesExterno));
  }

  @Test
  public void testSubProcesoAgregarEquipoPlume() throws WSException,IDFException {
    CrearRepetidorResponse responseMock = new CrearRepetidorResponse();

    CrearRepetidorMessageResponse messageResponse = new CrearRepetidorMessageResponse();
    CrearRepetidorBodyResponse bodyCR = new CrearRepetidorBodyResponse();

    HeaderResponse headerResponse = new HeaderResponse();
    headerResponse.setConsumer("Consumer");
    headerResponse.setPid("Pid");
    headerResponse.setStatus(new Status());
    headerResponse.setTimestamp("Timestamp");
    headerResponse.setVarArg("Var Arg");

    Header header = new Header();
    header.setHeaderResponse(headerResponse);

    bodyCR.setError(null);
    messageResponse.setBody(bodyCR);
    messageResponse.setHeader(header);
    responseMock.setMessageResponse(messageResponse);

    when(plumeNodeClient.crearRepetidor(Mockito.any(String.class), any(CrearRepetidorRequest.class),
      any(String.class), any(String.class), any(String.class), any(String.class), any(String.class))).thenReturn(responseMock);

    AprovisionarPlumeRequest request = new AprovisionarPlumeRequest();
    AprovisionarPlumeRequestType requestType = new AprovisionarPlumeRequestType();
    List<ListaEquiposAPType> equipos = new ArrayList<>();
    requestType.setEquipos(equipos);
    request.setAprovisionarPlumeRequest(requestType);
    HeaderRequest headerRequest = new HeaderRequest();
    headerRequest.setAccept("Accept");
    headerRequest.setCanal("Canal");
    headerRequest.setIdTransaccion("123456");
    headerRequest.setMsgid("Msgid");
    headerRequest.setTimestamp("Timestamp");
    headerRequest.setUserId("42");
    DatosClienteBeanRes resDatosClienteSGA = null;
    CrearCuentaResponse resCrearCliente = null;
    CrearUbicacionResponse resCrearUbi = null;

    List<ListaEquiposProvisionados> response = new ArrayList<ListaEquiposProvisionados>();

    assertEquals(response, aprovisionarPlumeServiceImpl.subProcesoAgregarEquipoPlume("12345", request, headerRequest, "12345", "12345", "12345",
      resDatosClienteSGA, "12345", "12345", resCrearCliente, resCrearUbi, propertiesExterno));
  }

  @Test
  public void testSubProcesoAgregarEquipoPlumeError() throws WSException, IDFException {
    CrearRepetidorResponse responseMock = mock(CrearRepetidorResponse.class);

    CrearRepetidorMessageResponse messageResponse = new CrearRepetidorMessageResponse();
    CrearRepetidorBodyResponse bodyCR = new CrearRepetidorBodyResponse();

    HeaderResponse headerResponse = new HeaderResponse();
    headerResponse.setConsumer("Consumer");
    headerResponse.setPid("Pid");
    headerResponse.setStatus(new Status());
    headerResponse.setTimestamp("Timestamp");
    headerResponse.setVarArg("Var Arg");

    Header header = new Header();
    header.setHeaderResponse(headerResponse);

    bodyCR.setError(null);
    messageResponse.setBody(bodyCR);
    messageResponse.setHeader(header);
    doNothing().when(responseMock).setMessageResponse(any());
    responseMock.setMessageResponse(messageResponse);

    when(plumeNodeClient.crearRepetidor(Mockito.any(String.class), any(CrearRepetidorRequest.class),
      any(String.class), any(String.class), any(String.class), any(String.class), any(String.class))).thenReturn(responseMock);

    AprovisionarPlumeRequest request = new AprovisionarPlumeRequest();
    AprovisionarPlumeRequestType requestType = new AprovisionarPlumeRequestType();
    List<ListaEquiposAPType> equipos = new ArrayList<>();
    ListaEquiposAPType equiAP = new ListaEquiposAPType();
    equiAP.setAlias("");
    equiAP.setModelo("");
    equiAP.setNumeroSerie("");
    equipos.add(equiAP);
    requestType.setEquipos(equipos);
    request.setAprovisionarPlumeRequest(requestType);
    HeaderRequest headerRequest = new HeaderRequest();
    headerRequest.setAccept("Accept");
    headerRequest.setCanal("Canal");
    headerRequest.setIdTransaccion("123456");
    headerRequest.setMsgid("Msgid");
    headerRequest.setTimestamp("Timestamp");
    headerRequest.setUserId("42");
    DatosClienteBeanRes resDatosClienteSGA = null;
    CrearCuentaResponse resCrearCliente = null;
    CrearUbicacionResponse resCrearUbi = null;

    aprovisionarPlumeServiceImpl.subProcesoAgregarEquipoPlume("12345", request, headerRequest, "12345", "12345", "12345",
      resDatosClienteSGA, "12345", "12345", resCrearCliente, resCrearUbi, propertiesExterno);

    verify(responseMock).setMessageResponse(any());
  }

  @Test
  public void testValidarDatosSGA() throws WSException {
    DatosClienteBeanRes datosClienteBeanRes = new DatosClienteBeanRes();
    datosClienteBeanRes.setAccountId("42");
    datosClienteBeanRes.setCodigoCliente("123456");
    datosClienteBeanRes.setCodigoClientePlm("123456");
    datosClienteBeanRes.setCodigoRespuesta("123456");
    datosClienteBeanRes.setCodigoSucursal("Codigo Sucursal");
    datosClienteBeanRes.setCursor(new ArrayList<>());
    datosClienteBeanRes.setEmail("jane.doe@example.org");
    datosClienteBeanRes.setMensajeRespuesta("Mensaje Respuesta");
    datosClienteBeanRes.setNombreSurcursal("Nombre Surcursal");
    datosClienteBeanRes.setNombres("Nombres");
    datosClienteBeanRes.setTipoTrabajo("Tipo Trabajo");
    datosClienteBeanRes.setValidaCuenta("Valida Cuenta");
    assertThrows(WSException.class,
      () -> aprovisionarPlumeServiceImpl.validarDatosSGA(datosClienteBeanRes, propertiesExterno));
  }

  @Test
  public void testEjecutarRollbackCTA() throws WSException {

    DatosClienteBeanRes datosClienteBeanRes = new DatosClienteBeanRes();
    CrearCuentaResponse crearCuentaResponse = new CrearCuentaResponse();
    CrearCuentaBodyResponse crearCuentaBodyResponse = new CrearCuentaBodyResponse();
    crearCuentaBodyResponse.setAcceptLanguage("en");
    crearCuentaBodyResponse.setAccountId("42");
    crearCuentaBodyResponse.setError(null);
    crearCuentaBodyResponse.setId("42");
    crearCuentaBodyResponse.setLocationId("42");

    Status status = new Status();
    status.setCode("Code");
    status.setMessage("Not all who wander are lost");
    status.setMsgid("Msgid");
    status.setType("Type");

    HeaderResponse headerResponse = new HeaderResponse();
    headerResponse.setConsumer("Consumer");
    headerResponse.setPid("Pid");
    headerResponse.setStatus(status);
    headerResponse.setTimestamp("Timestamp");
    headerResponse.setVarArg("123456");

    Header header = new Header();
    header.setHeaderResponse(headerResponse);

    CrearCuentaMessageResponse crearCuentaMessageResponse = new CrearCuentaMessageResponse();
    crearCuentaMessageResponse.setBody(crearCuentaBodyResponse);
    crearCuentaMessageResponse.setHeader(header);
    crearCuentaResponse.setMessageResponse(crearCuentaMessageResponse);

    CrearUbicacionResponse crearUbicacionResponse = new CrearUbicacionResponse();
    List<ListaEquiposProvisionados> equiProvisionados = new ArrayList<>();
    HeaderRequest headerRequest = new HeaderRequest();
    AprovisionarPlumeRequest aprovisionarPlumeRequest = new AprovisionarPlumeRequest();

    assertThrows(NullPointerException.class,
      () -> aprovisionarPlumeServiceImpl.ejecutarRollback("12345", "Mensaje Trx", "ABC123", "ABC123",
        datosClienteBeanRes, "CTA", crearCuentaResponse, crearUbicacionResponse, equiProvisionados, "Codigo",
        "Mensaje", headerRequest, aprovisionarPlumeRequest, propertiesExterno));
  }

  @Test
  public void testEjecutarRollbackLOC() throws WSException {
    DatosClienteBeanRes datosClienteBeanRes = new DatosClienteBeanRes();
    datosClienteBeanRes.setCodigoClientePlm("12345");
    CrearCuentaResponse crearCuentaResponse = new CrearCuentaResponse();
    CrearUbicacionResponse crearUbicacionResponse = new CrearUbicacionResponse();
    CrearUbicacionMessageResponse messageResponse = new CrearUbicacionMessageResponse();
    CrearUbicacionBodyResponse body = new CrearUbicacionBodyResponse();
    body.setId("123456");
    messageResponse.setBody(body);
    crearUbicacionResponse.setMessageResponse(messageResponse);
    List<ListaEquiposProvisionados> equiProvisionados = new ArrayList<>();
    HeaderRequest headerRequest = new HeaderRequest();
    AprovisionarPlumeRequest aprovisionarPlumeRequest = new AprovisionarPlumeRequest();

    assertThrows(NullPointerException.class,
      () -> aprovisionarPlumeServiceImpl.ejecutarRollback("12345", "Mensaje Trx", "ABC123", "ABC123",
        datosClienteBeanRes, "LOC", crearCuentaResponse, crearUbicacionResponse, equiProvisionados, "Codigo",
        "Mensaje", headerRequest, aprovisionarPlumeRequest, propertiesExterno));
  }

  @Test
  public void testEjecutarRollbackEQP() throws WSException {

    DatosClienteBeanRes datosClienteBeanRes = new DatosClienteBeanRes();
    datosClienteBeanRes.setAccountId("4234562");

    CrearCuentaBodyResponse crearCuentaBodyResponse = new CrearCuentaBodyResponse();

    Header header = new Header();
    CrearCuentaMessageResponse crearCuentaMessageResponse = new CrearCuentaMessageResponse();
    crearCuentaMessageResponse.setBody(crearCuentaBodyResponse);
    crearCuentaMessageResponse.setHeader(header);
    CrearCuentaResponse crearCuentaResponse = mock(CrearCuentaResponse.class);
    doNothing().when(crearCuentaResponse).setMessageResponse(any());
    crearCuentaResponse.setMessageResponse(crearCuentaMessageResponse);

    AprovisionarPlumeRequest aprovisionarPlumeRequest = new AprovisionarPlumeRequest();
    ListaEquiposProvisionados listaEquiposProvisionados = new ListaEquiposProvisionados();
    listaEquiposProvisionados.setCodSeriePLM("123456");
    listaEquiposProvisionados.setCodigoCliPLM("123");
    listaEquiposProvisionados.setCodigoSucPLM("123");
    listaEquiposProvisionados.setModelo("123");
    listaEquiposProvisionados.setNickName("123");
    listaEquiposProvisionados.setSerialNumber("42");
    ArrayList<ListaEquiposProvisionados> listaEquiposProvisionadosList = new ArrayList<>();
    listaEquiposProvisionadosList.add(listaEquiposProvisionados);
    CrearUbicacionResponse crearUbicacionResponse = new CrearUbicacionResponse();
    HeaderRequest headerRequest = new HeaderRequest();
    assertThrows(NullPointerException.class,
      () -> aprovisionarPlumeServiceImpl.ejecutarRollback("12345", "Mensaje Trx", "ABC123", "ABC123",
        datosClienteBeanRes, "EQP", crearCuentaResponse, crearUbicacionResponse, listaEquiposProvisionadosList, "Codigo",
        "Mensaje", headerRequest, aprovisionarPlumeRequest, propertiesExterno));
  }

  @Test
  public void testEjecutarRollbackDEF() throws WSException {

    DatosClienteBeanRes datosClienteBeanRes = new DatosClienteBeanRes();
    datosClienteBeanRes.setAccountId("4234562");

    CrearCuentaBodyResponse crearCuentaBodyResponse = new CrearCuentaBodyResponse();

    Header header = new Header();
    CrearCuentaMessageResponse crearCuentaMessageResponse = new CrearCuentaMessageResponse();
    crearCuentaMessageResponse.setBody(crearCuentaBodyResponse);
    crearCuentaMessageResponse.setHeader(header);
    CrearCuentaResponse crearCuentaResponse = mock(CrearCuentaResponse.class);
    doNothing().when(crearCuentaResponse).setMessageResponse(any());
    crearCuentaResponse.setMessageResponse(crearCuentaMessageResponse);

    AprovisionarPlumeRequest aprovisionarPlumeRequest = new AprovisionarPlumeRequest();
    ListaEquiposProvisionados listaEquiposProvisionados = new ListaEquiposProvisionados();
    listaEquiposProvisionados.setCodSeriePLM("123456");
    listaEquiposProvisionados.setCodigoCliPLM("123");
    listaEquiposProvisionados.setCodigoSucPLM("123");
    listaEquiposProvisionados.setModelo("123");
    listaEquiposProvisionados.setNickName("123");
    listaEquiposProvisionados.setSerialNumber("42");
    ArrayList<ListaEquiposProvisionados> listaEquiposProvisionadosList = new ArrayList<>();
    listaEquiposProvisionadosList.add(listaEquiposProvisionados);
    CrearUbicacionResponse crearUbicacionResponse = new CrearUbicacionResponse();
    HeaderRequest headerRequest = new HeaderRequest();
    aprovisionarPlumeServiceImpl.ejecutarRollback("12345", "Mensaje Trx", "ABC123", "ABC123",
      datosClienteBeanRes, "DEFAULT", crearCuentaResponse, crearUbicacionResponse, listaEquiposProvisionadosList, "Codigo",
      "Mensaje", headerRequest, aprovisionarPlumeRequest, propertiesExterno);
    verify(crearCuentaResponse).setMessageResponse(any());
  }

  @Test
  public void testEjecutarRollbackDEFlse() throws WSException {

    DatosClienteBeanRes datosClienteBeanRes = new DatosClienteBeanRes();
    datosClienteBeanRes.setAccountId("4234562");
    datosClienteBeanRes.setCodigoCliente("12345");

    CrearCuentaBodyResponse crearCuentaBodyResponse = new CrearCuentaBodyResponse();

    Header header = new Header();
    CrearCuentaMessageResponse crearCuentaMessageResponse = new CrearCuentaMessageResponse();
    crearCuentaMessageResponse.setBody(crearCuentaBodyResponse);
    crearCuentaMessageResponse.setHeader(header);
    CrearCuentaResponse crearCuentaResponse = mock(CrearCuentaResponse.class);
    doNothing().when(crearCuentaResponse).setMessageResponse(any());
    crearCuentaResponse.setMessageResponse(crearCuentaMessageResponse);

    AprovisionarPlumeRequest aprovisionarPlumeRequest = new AprovisionarPlumeRequest();
    ArrayList<ListaEquiposProvisionados> listaEquiposProvisionadosList = new ArrayList<>();
    CrearUbicacionResponse crearUbicacionResponse = new CrearUbicacionResponse();
    HeaderRequest headerRequest = new HeaderRequest();
    aprovisionarPlumeServiceImpl.ejecutarRollback("12345", "Mensaje Trx", "ABC123", "ABC123",
      datosClienteBeanRes, "DEFAULT", crearCuentaResponse, crearUbicacionResponse, listaEquiposProvisionadosList, "Codigo",
      "Mensaje", headerRequest, aprovisionarPlumeRequest, propertiesExterno);
    verify(crearCuentaResponse).setMessageResponse(any());
  }

  @Test
  public void testObtenerLocationIdPlumeEQP() {
    CrearUbicacionResponse crearUbicacionResponse = new CrearUbicacionResponse();
    CrearCuentaMessageResponse crearCuentaMessageResponse = new CrearCuentaMessageResponse();
    CrearCuentaResponse crearCuentaResponse = new CrearCuentaResponse();
    crearCuentaResponse.setMessageResponse(crearCuentaMessageResponse);
    assertEquals("123", aprovisionarPlumeServiceImpl.obtenerLocationIdPlume("EQP", "123",
      crearUbicacionResponse, crearCuentaResponse));
  }

  @Test
  public void testObtenerLocationIdPlumeLOC() {

    CrearUbicacionBodyResponse crearUbicacionBodyResponse = new CrearUbicacionBodyResponse();
    crearUbicacionBodyResponse.setAccountId("42");
    crearUbicacionBodyResponse.setCustomerId("42");
    crearUbicacionBodyResponse.setId("42");


    HeaderResponse headerResponse = new HeaderResponse();
    headerResponse.setConsumer("Consumer");
    headerResponse.setPid("Pid");
    headerResponse.setStatus(new Status());
    headerResponse.setTimestamp("Timestamp");
    headerResponse.setVarArg("Var Arg");

    Header header = new Header();
    header.setHeaderResponse(headerResponse);

    CrearUbicacionMessageResponse crearUbicacionMessageResponse = new CrearUbicacionMessageResponse();
    crearUbicacionMessageResponse.setBody(crearUbicacionBodyResponse);
    crearUbicacionMessageResponse.setHeader(header);
    CrearUbicacionResponse crearUbicacionResponse = new CrearUbicacionResponse();
    crearUbicacionResponse.setMessageResponse(crearUbicacionMessageResponse);

    CrearCuentaBodyResponse crearCuentaBodyResponse = new CrearCuentaBodyResponse();
    Header header1 = new Header();

    CrearCuentaMessageResponse crearCuentaMessageResponse = new CrearCuentaMessageResponse();
    crearCuentaMessageResponse.setBody(crearCuentaBodyResponse);
    crearCuentaMessageResponse.setHeader(header1);

    CrearCuentaResponse crearCuentaResponse = new CrearCuentaResponse();
    crearCuentaResponse.setMessageResponse(crearCuentaMessageResponse);
    assertEquals("42", aprovisionarPlumeServiceImpl.obtenerLocationIdPlume("LOC", "123",
      crearUbicacionResponse, crearCuentaResponse));
  }

  @Test
  public void testObtenerCodigoClientePlmLOC() {
    DatosClienteBeanRes datosClienteBeanRes = new DatosClienteBeanRes();
    datosClienteBeanRes.setCodigoClientePlm("42");
    CrearCuentaResponse crearCuentaResponse = new CrearCuentaResponse();
    assertEquals("42",
      aprovisionarPlumeServiceImpl.obtenerCodigoClientePlm(datosClienteBeanRes, "LOC", crearCuentaResponse));
  }

  @Test
  public void testRegistrarClienteSGA() throws BaseException {
    AprovisionarPlumeServiceImpl aprovisionarPlumeServiceImpl = new AprovisionarPlumeServiceImpl();

    DatosClienteBeanRes datosClienteBeanRes = new DatosClienteBeanRes();
    datosClienteBeanRes.setAccountId("42");
    datosClienteBeanRes.setCodigoCliente("123456");
    datosClienteBeanRes.setCodigoClientePlm("123456");
    datosClienteBeanRes.setCodigoRespuesta("123456");
    datosClienteBeanRes.setCodigoSucursal("Codigo Sucursal");
    datosClienteBeanRes.setCursor(new ArrayList<>());
    datosClienteBeanRes.setEmail("jane.doe@example.org");
    datosClienteBeanRes.setMensajeRespuesta("Mensaje Respuesta");
    datosClienteBeanRes.setNombreSurcursal("Nombre Surcursal");
    datosClienteBeanRes.setNombres("Nombres");
    datosClienteBeanRes.setTipoTrabajo("Tipo Trabajo");
    datosClienteBeanRes.setValidaCuenta("Valida Cuenta");
    ArrayList<ListaEquiposProvisionados> equiProvisionados = new ArrayList<>();
    ListaEquiposProvisionados listEqProv = new ListaEquiposProvisionados();
    listEqProv.setCodigoCliPLM("123");
    listEqProv.setCodigoSucPLM("123");
    listEqProv.setSerialNumber("12346");
    listEqProv.setModelo("test");
    equiProvisionados.add(listEqProv);

    DetailType detailType = new DetailType();
    detailType.setIntegrationError("An error occurred");

    Fault fault = new Fault();
    fault.setDetail(detailType);
    fault.setFaultactor("Faultactor");
    fault.setFaultcode("Faultcode");
    fault.setFaultstring("Faultstring");

    CrearCuentaBodyResponse crearCuentaBodyResponse = new CrearCuentaBodyResponse();
    crearCuentaBodyResponse.setAcceptLanguage("en");
    crearCuentaBodyResponse.setAccountId("42");
    crearCuentaBodyResponse.setAnonymous(true);
    crearCuentaBodyResponse.setCreatedAt("Jan 1, 2020 8:00am GMT+0100");
    crearCuentaBodyResponse.setEmail("jane.doe@example.org");
    crearCuentaBodyResponse.setError(null);
    crearCuentaBodyResponse.setId("42");
    crearCuentaBodyResponse.setLocationId("42");
    crearCuentaBodyResponse.setLocked(true);
    crearCuentaBodyResponse.setName("Name");
    crearCuentaBodyResponse.setPartnerId("42");
    crearCuentaBodyResponse.setProvisioningSsoAuditTrail("Provisioning Sso Audit Trail");
    crearCuentaBodyResponse.setSource("Source");
    crearCuentaBodyResponse.setVersion("1.0.2");

    Status status = new Status();
    status.setCode("Code");
    status.setMessage("Not all who wander are lost");
    status.setMsgid("Msgid");
    status.setType("Type");

    HeaderResponse headerResponse = new HeaderResponse();
    headerResponse.setConsumer("Consumer");
    headerResponse.setPid("Pid");
    headerResponse.setStatus(status);
    headerResponse.setTimestamp("Timestamp");
    headerResponse.setVarArg("Var Arg");
    Header header = new Header();
    header.setHeaderResponse(headerResponse);
    CrearCuentaMessageResponse crearCuentaMessageResponse = new CrearCuentaMessageResponse();
    crearCuentaMessageResponse.setBody(crearCuentaBodyResponse);
    crearCuentaMessageResponse.setHeader(header);
    CrearCuentaResponse crearCuentaResponse = new CrearCuentaResponse();
    crearCuentaResponse.setMessageResponse(crearCuentaMessageResponse);
    HeaderResponse headerResponse1 = new HeaderResponse();
    headerResponse1.setConsumer("Consumer");
    headerResponse1.setPid("Pid");
    headerResponse1.setTimestamp("Timestamp");
    headerResponse1.setVarArg("Var Arg");

    Header header1 = new Header();
    header1.setHeaderResponse(headerResponse1);

    CrearUbicacionMessageResponse crearUbicacionMessageResponse = new CrearUbicacionMessageResponse();
    crearUbicacionMessageResponse.setHeader(header1);

    CrearUbicacionResponse crearUbicacionResponse = new CrearUbicacionResponse();
    crearUbicacionResponse.setMessageResponse(crearUbicacionMessageResponse);

    RegistrarClieteRes respRegistrarClient = new RegistrarClieteRes();
    respRegistrarClient.setCodigoRespuesta("0");
    respRegistrarClient.setMensajeRespuesta("Test");
    when(sgaDao.registrarCliente(Mockito.any(String.class), any(RegistrarClieteReq.class))).thenReturn(respRegistrarClient);

    assertThrows(NullPointerException.class,
      () -> aprovisionarPlumeServiceImpl.registrarClienteSGA("12345", "Mensaje Trx", datosClienteBeanRes,
        equiProvisionados, "Proceso", crearCuentaResponse, "Id Loc Plume", crearUbicacionResponse, propertiesExterno, null, null, null, null));
  }

  @Test
  public void testObtenerCodigoSucPlmEQP() {
    CrearCuentaBodyResponse crearCuentaBodyResponse = new CrearCuentaBodyResponse();

    Header header = new Header();

    CrearCuentaMessageResponse crearCuentaMessageResponse = new CrearCuentaMessageResponse();
    crearCuentaMessageResponse.setBody(crearCuentaBodyResponse);
    crearCuentaMessageResponse.setHeader(header);
    CrearCuentaResponse crearCuentaResponse = mock(CrearCuentaResponse.class);
    doNothing().when(crearCuentaResponse).setMessageResponse(any());
    crearCuentaResponse.setMessageResponse(crearCuentaMessageResponse);

    CrearUbicacionBodyResponse crearUbicacionBodyResponse = new CrearUbicacionBodyResponse();
    crearUbicacionBodyResponse.setId("42");

    HeaderResponse headerResponse1 = new HeaderResponse();
    headerResponse1.setConsumer("Consumer");
    headerResponse1.setPid("Pid");
    headerResponse1.setStatus(new Status());
    headerResponse1.setTimestamp("Timestamp");
    headerResponse1.setVarArg("Var Arg");

    Header header1 = new Header();
    header1.setHeaderResponse(headerResponse1);

    CrearUbicacionMessageResponse crearUbicacionMessageResponse = new CrearUbicacionMessageResponse();
    crearUbicacionMessageResponse.setBody(crearUbicacionBodyResponse);
    crearUbicacionMessageResponse.setHeader(header1);

    CrearUbicacionResponse crearUbicacionResponse = new CrearUbicacionResponse();
    crearUbicacionResponse.setMessageResponse(crearUbicacionMessageResponse);
    assertEquals("42", aprovisionarPlumeServiceImpl.obtenerCodigoSucPlm("EQP", crearCuentaResponse, "42",
      crearUbicacionResponse));
    verify(crearCuentaResponse).setMessageResponse(any());
  }

  @Test
  public void testObtenerCodigoSucPlmLOC() {

    CrearCuentaBodyResponse crearCuentaBodyResponse = new CrearCuentaBodyResponse();
    Header header = new Header();
    CrearCuentaMessageResponse crearCuentaMessageResponse = new CrearCuentaMessageResponse();
    crearCuentaMessageResponse.setBody(crearCuentaBodyResponse);
    crearCuentaMessageResponse.setHeader(header);
    CrearCuentaResponse crearCuentaResponse = mock(CrearCuentaResponse.class);
    doNothing().when(crearCuentaResponse).setMessageResponse(any());
    crearCuentaResponse.setMessageResponse(crearCuentaMessageResponse);
    CrearUbicacionBodyResponse crearUbicacionBodyResponse = new CrearUbicacionBodyResponse();
    crearUbicacionBodyResponse.setId("42");
    HeaderResponse headerResponse1 = new HeaderResponse();
    headerResponse1.setConsumer("Consumer");
    headerResponse1.setPid("Pid");
    headerResponse1.setStatus(new Status());
    headerResponse1.setTimestamp("Timestamp");
    headerResponse1.setVarArg("Var Arg");
    Header header1 = new Header();
    header1.setHeaderResponse(headerResponse1);
    CrearUbicacionMessageResponse crearUbicacionMessageResponse = new CrearUbicacionMessageResponse();
    crearUbicacionMessageResponse.setBody(crearUbicacionBodyResponse);
    crearUbicacionMessageResponse.setHeader(header1);
    CrearUbicacionResponse crearUbicacionResponse = new CrearUbicacionResponse();
    crearUbicacionResponse.setMessageResponse(crearUbicacionMessageResponse);
    assertEquals("42", aprovisionarPlumeServiceImpl.obtenerCodigoSucPlm("LOC", crearCuentaResponse, "123",
      crearUbicacionResponse));
    verify(crearCuentaResponse).setMessageResponse(any());
  }

  @Test
  public void testObtenerCodigoSucPlmCTA() {

    CrearCuentaBodyResponse crearCuentaBodyResponse = new CrearCuentaBodyResponse();
    crearCuentaBodyResponse.setId("42");
    Header header = new Header();
    CrearCuentaMessageResponse crearCuentaMessageResponse = new CrearCuentaMessageResponse();
    crearCuentaMessageResponse.setBody(crearCuentaBodyResponse);
    crearCuentaMessageResponse.setHeader(header);
    CrearCuentaResponse crearCuentaResponse = new CrearCuentaResponse();
    crearCuentaResponse.setMessageResponse(crearCuentaMessageResponse);
    Header header1 = new Header();
    CrearUbicacionBodyResponse crearUbicacionBodyResponse = new CrearUbicacionBodyResponse();
    CrearUbicacionMessageResponse crearUbicacionMessageResponse = new CrearUbicacionMessageResponse();
    crearUbicacionMessageResponse.setBody(crearUbicacionBodyResponse);
    crearUbicacionMessageResponse.setHeader(header1);
    CrearUbicacionResponse crearUbicacionResponse = new CrearUbicacionResponse();
    crearUbicacionResponse.setMessageResponse(crearUbicacionMessageResponse);
    assertEquals(null, aprovisionarPlumeServiceImpl.obtenerCodigoSucPlm("CTA", crearCuentaResponse, "123",
      crearUbicacionResponse));
  }

  @Test
  public void testObtenerDatosSGA() throws BaseException {
    DatosClienteBeanRes datosClienteBeanRes = new DatosClienteBeanRes();
    datosClienteBeanRes.setAccountId("42");
    datosClienteBeanRes.setCodigoCliente("123456");
    datosClienteBeanRes.setCodigoClientePlm("123456");
    datosClienteBeanRes.setCodigoRespuesta("123456");
    datosClienteBeanRes.setCodigoSucursal("Codigo Sucursal");
    datosClienteBeanRes.setCursor(new ArrayList<>());
    datosClienteBeanRes.setEmail("jane.doe@example.org");
    datosClienteBeanRes.setMensajeRespuesta("Mensaje Respuesta");
    datosClienteBeanRes.setNombreSurcursal("Nombre Surcursal");
    datosClienteBeanRes.setNombres("Nombres");
    datosClienteBeanRes.setTipoTrabajo("Tipo Trabajo");
    datosClienteBeanRes.setValidaCuenta("Valida Cuenta");
    when(sgaDao.datosCliente(any(), any())).thenReturn(datosClienteBeanRes);

    AprovisionarPlumeRequestType aprovisionarPlumeRequestType = new AprovisionarPlumeRequestType();
    aprovisionarPlumeRequestType.setCoId("42");
    aprovisionarPlumeRequestType.setEquipos(new ArrayList<>());
    aprovisionarPlumeRequestType.setIdTipo("Id Tipo");
    aprovisionarPlumeRequestType.setListaOpcional(new ArrayList<>());
    aprovisionarPlumeRequestType.setNumeroSot("Numero Sot");

    AprovisionarPlumeRequest aprovisionarPlumeRequest = new AprovisionarPlumeRequest();
    aprovisionarPlumeRequest.setAprovisionarPlumeRequest(aprovisionarPlumeRequestType);
    assertSame(datosClienteBeanRes,
      aprovisionarPlumeServiceImpl.obtenerDatosSGA("123456", aprovisionarPlumeRequest, "Mensaje Trx"));
    verify(sgaDao).datosCliente(any(), any());
  }

  @Test
  public void testCrearUbicacion() throws SQLException, BaseException {

    AprovisionarPlumeServiceImpl aprovisionarPlumeServiceImpl = new AprovisionarPlumeServiceImpl();

    HeaderRequest headerRequest = new HeaderRequest();
    headerRequest.setAccept("Accept");
    headerRequest.setCanal("Canal");
    headerRequest.setIdTransaccion("123456");
    headerRequest.setMsgid("Msgid");
    headerRequest.setTimestamp("Timestamp");
    headerRequest.setUserId("42");

    DatosClienteBeanRes datosClienteBeanRes = new DatosClienteBeanRes();
    datosClienteBeanRes.setAccountId("42");
    datosClienteBeanRes.setCodigoCliente("123456");
    datosClienteBeanRes.setCodigoClientePlm("123456");
    datosClienteBeanRes.setCodigoRespuesta("123456");
    datosClienteBeanRes.setCodigoSucursal("Codigo Sucursal");
    datosClienteBeanRes.setCursor(new ArrayList<>());
    datosClienteBeanRes.setEmail("jane.doe@example.org");
    datosClienteBeanRes.setMensajeRespuesta("Mensaje Respuesta");
    datosClienteBeanRes.setNombreSurcursal("Nombre Surcursal");
    datosClienteBeanRes.setNombres("Nombres");
    datosClienteBeanRes.setTipoTrabajo("Tipo Trabajo");
    datosClienteBeanRes.setValidaCuenta("Valida Cuenta");

    AprovisionarPlumeRequestType aprovisionarPlumeRequestType = new AprovisionarPlumeRequestType();
    aprovisionarPlumeRequestType.setCoId("42");
    aprovisionarPlumeRequestType.setEquipos(new ArrayList<>());
    aprovisionarPlumeRequestType.setIdTipo("Id Tipo");
    aprovisionarPlumeRequestType.setListaOpcional(new ArrayList<>());
    aprovisionarPlumeRequestType.setNumeroSot("Numero Sot");

    AprovisionarPlumeRequest aprovisionarPlumeRequest = new AprovisionarPlumeRequest();
    aprovisionarPlumeRequest.setAprovisionarPlumeRequest(aprovisionarPlumeRequestType);

    CrearUbicacionResponse resCrearUbi = new CrearUbicacionResponse();
    CrearUbicacionMessageResponse messageResponse = new CrearUbicacionMessageResponse();
    Header headerResponse = new Header();
    HeaderResponse headerResponseBody = new HeaderResponse();
    Status status = new Status();
    status.setCode("1");
    status.setMessage("Test");
    headerResponseBody.setStatus(status);
    headerResponse.setHeaderResponse(headerResponseBody);
    messageResponse.setHeader(headerResponse);
    resCrearUbi.setMessageResponse(messageResponse);

    when(plumeLocationClient.crearUbicacion(Mockito.any(String.class), any(CrearUbicacionRequest.class), Mockito.any(String.class),
      Mockito.any(String.class), Mockito.any(String.class), Mockito.any(String.class))).thenReturn(resCrearUbi);

    assertThrows(NullPointerException.class,
      () -> aprovisionarPlumeServiceImpl.crearUbicacion("123456", headerRequest, "Mensaje Trx", "ABC123", "ABC123",
        datosClienteBeanRes, aprovisionarPlumeRequest, "Proceso", propertiesExterno));
  }

  @Test
  public void testConfigNivelServicio() throws WSException {
    HeaderRequest headerRequest = new HeaderRequest();
    headerRequest.setAccept("Accept");
    headerRequest.setCanal("Canal");
    headerRequest.setIdTransaccion("123456");
    headerRequest.setMsgid("Msgid");
    headerRequest.setTimestamp("Timestamp");
    headerRequest.setUserId("42");

    Status status1 = new Status();
    status1.setCode("1");
    status1.setMessage("Not all who wander are lost");
    status1.setMsgid("Msgid");
    status1.setType("Type");

    HeaderResponse headerResponse1 = new HeaderResponse();
    headerResponse1.setConsumer("Consumer");
    headerResponse1.setPid("Pid");
    headerResponse1.setStatus(status1);
    headerResponse1.setTimestamp("Timestamp");
    headerResponse1.setVarArg("Var Arg");

    Header header1 = new Header();
    header1.setHeaderResponse(headerResponse1);

    CrearUbicacionBodyResponse crearUbicacionBodyResponse = new CrearUbicacionBodyResponse();
    crearUbicacionBodyResponse.setCustomerId("42");
    crearUbicacionBodyResponse.setId("42");

    CrearUbicacionMessageResponse crearUbicacionMessageResponse = new CrearUbicacionMessageResponse();
    crearUbicacionMessageResponse.setBody(crearUbicacionBodyResponse);
    crearUbicacionMessageResponse.setHeader(header1);

    ConfigNivelResponse response = new ConfigNivelResponse();
    ConfigNivelMessageResponse messageResponse = new ConfigNivelMessageResponse();
    messageResponse.setHeader(header1);
    response.setMessageResponse(messageResponse);

    CrearCuentaResponse crearCuentaResponse = new CrearCuentaResponse();
    DatosClienteBeanRes datosClienteBeanRes = new DatosClienteBeanRes();
    CrearUbicacionResponse crearUbicacionResponse = new CrearUbicacionResponse();
    AprovisionarPlumeRequest aprovisionarPlumeRequest = new AprovisionarPlumeRequest();

    HeaderDatapower headerDp = new HeaderDatapower();
    HeaderDPRequest headerRequestDP = new HeaderDPRequest();
    headerDp.setHeaderRequest(headerRequestDP);

    when(UtilTokens.headerDP(Mockito.any(String.class), Mockito.any(HeaderRequest.class),
        Mockito.any(PropertiesExterno.class), Mockito.any(String.class))).thenReturn(headerDp);
    when(plumeLocationClient.configNivelServicio(Mockito.any(String.class), Mockito.any(ConfigNivelRequest.class),
        Mockito.any(String.class), Mockito.any(String.class), Mockito.any(String.class), Mockito.any(String.class), Mockito.any(String.class))).thenReturn(response);

    assertThrows(NullPointerException.class,
        () -> aprovisionarPlumeServiceImpl.configNivelServicioSub("123456", headerRequest, "Mensaje Trx", "ABC123",
          "ABC123", crearCuentaResponse, datosClienteBeanRes, crearUbicacionResponse, "CTA", aprovisionarPlumeRequest,
          propertiesExterno));
  }

  @Test
  public void testConfigNivelServicioSubCTA() throws WSException {

    HeaderRequest headerRequest = new HeaderRequest();
    headerRequest.setAccept("Accept");
    headerRequest.setCanal("Canal");
    headerRequest.setIdTransaccion("123456");
    headerRequest.setMsgid("Msgid");
    headerRequest.setTimestamp("Timestamp");
    headerRequest.setUserId("42");

    DetailType detailType = new DetailType();
    detailType.setIntegrationError("An error occurred");

    CrearCuentaBodyResponse crearCuentaBodyResponse = new CrearCuentaBodyResponse();
    crearCuentaBodyResponse.setId("42");
    crearCuentaBodyResponse.setLocationId("42");

    Status status = new Status();
    status.setCode("Code");
    status.setMessage("Not all who wander are lost");
    status.setMsgid("Msgid");
    status.setType("Type");

    HeaderResponse headerResponse = new HeaderResponse();
    headerResponse.setConsumer("Consumer");
    headerResponse.setPid("Pid");
    headerResponse.setStatus(status);
    headerResponse.setTimestamp("Timestamp");
    headerResponse.setVarArg("Var Arg");

    Header header = new Header();
    header.setHeaderResponse(headerResponse);

    CrearCuentaMessageResponse crearCuentaMessageResponse = new CrearCuentaMessageResponse();
    crearCuentaMessageResponse.setBody(crearCuentaBodyResponse);
    crearCuentaMessageResponse.setHeader(header);

    CrearCuentaResponse crearCuentaResponse = new CrearCuentaResponse();
    crearCuentaResponse.setMessageResponse(crearCuentaMessageResponse);

    DatosClienteBeanRes datosClienteBeanRes = new DatosClienteBeanRes();

    CrearUbicacionBodyResponse crearUbicacionBodyResponse = new CrearUbicacionBodyResponse();
    crearUbicacionBodyResponse.setCustomerId("42");
    crearUbicacionBodyResponse.setId("42");

    Status status1 = new Status();
    status1.setCode("1");
    status1.setMessage("Not all who wander are lost");
    status1.setMsgid("Msgid");
    status1.setType("Type");

    HeaderResponse headerResponse1 = new HeaderResponse();
    headerResponse1.setConsumer("Consumer");
    headerResponse1.setPid("Pid");
    headerResponse1.setStatus(status1);
    headerResponse1.setTimestamp("Timestamp");
    headerResponse1.setVarArg("Var Arg");

    Header header1 = new Header();
    header1.setHeaderResponse(headerResponse1);

    CrearUbicacionMessageResponse crearUbicacionMessageResponse = new CrearUbicacionMessageResponse();
    crearUbicacionMessageResponse.setBody(crearUbicacionBodyResponse);
    crearUbicacionMessageResponse.setHeader(header1);

    CrearUbicacionResponse crearUbicacionResponse = new CrearUbicacionResponse();
    crearUbicacionResponse.setMessageResponse(crearUbicacionMessageResponse);

    AprovisionarPlumeRequestType aprovisionarPlumeRequestType = new AprovisionarPlumeRequestType();
    aprovisionarPlumeRequestType.setCoId("42");
    aprovisionarPlumeRequestType.setEquipos(new ArrayList<>());
    aprovisionarPlumeRequestType.setIdTipo("Id Tipo");
    aprovisionarPlumeRequestType.setListaOpcional(new ArrayList<>());
    aprovisionarPlumeRequestType.setNumeroSot("Numero Sot");

    AprovisionarPlumeRequest aprovisionarPlumeRequest = new AprovisionarPlumeRequest();
    aprovisionarPlumeRequest.setAprovisionarPlumeRequest(aprovisionarPlumeRequestType);

    ConfigNivelResponse response = new ConfigNivelResponse();
    ConfigNivelMessageResponse messageResponse = new ConfigNivelMessageResponse();
    messageResponse.setHeader(header1);
    response.setMessageResponse(messageResponse);

    HeaderDatapower headerDp = new HeaderDatapower();
    HeaderDPRequest headerRequestDP = new HeaderDPRequest();
    headerDp.setHeaderRequest(headerRequestDP);

    when(plumeLocationClient.configNivelServicio(Mockito.any(String.class), Mockito.any(ConfigNivelRequest.class),
      Mockito.any(String.class), Mockito.any(String.class), Mockito.any(String.class), Mockito.any(String.class), Mockito.any(String.class))).thenReturn(response);

    assertThrows(NullPointerException.class,
      () -> aprovisionarPlumeServiceImpl.configNivelServicioSub("123456", headerRequest, "Mensaje Trx", "ABC123",
        "ABC123", crearCuentaResponse, datosClienteBeanRes, crearUbicacionResponse, "CTA", aprovisionarPlumeRequest,
        propertiesExterno));
  }

  @Test
  public void testConfigNivelServicioSubLoc() throws WSException {
    AprovisionarPlumeServiceImpl aprovisionarPlumeServiceImpl = new AprovisionarPlumeServiceImpl();

    HeaderRequest headerRequest = new HeaderRequest();
    headerRequest.setAccept("Accept");
    headerRequest.setCanal("Canal");
    headerRequest.setIdTransaccion("123456");
    headerRequest.setMsgid("Msgid");
    headerRequest.setTimestamp("Timestamp");
    headerRequest.setUserId("42");

    CrearCuentaBodyResponse crearCuentaBodyResponse = new CrearCuentaBodyResponse();
    crearCuentaBodyResponse.setId("42");
    crearCuentaBodyResponse.setLocationId("42");

    Status status = new Status();
    status.setCode("Code");
    status.setMessage("Not all who wander are lost");
    status.setMsgid("Msgid");
    status.setType("Type");

    HeaderResponse headerResponse = new HeaderResponse();
    headerResponse.setConsumer("Consumer");
    headerResponse.setPid("Pid");
    headerResponse.setStatus(status);
    headerResponse.setTimestamp("Timestamp");
    headerResponse.setVarArg("Var Arg");

    Header header = new Header();
    header.setHeaderResponse(headerResponse);

    CrearCuentaMessageResponse crearCuentaMessageResponse = new CrearCuentaMessageResponse();
    crearCuentaMessageResponse.setBody(crearCuentaBodyResponse);
    crearCuentaMessageResponse.setHeader(header);

    CrearCuentaResponse crearCuentaResponse = new CrearCuentaResponse();
    crearCuentaResponse.setMessageResponse(crearCuentaMessageResponse);

    DatosClienteBeanRes datosClienteBeanRes = new DatosClienteBeanRes();
    datosClienteBeanRes.setCodigoClientePlm("123456");

    CrearUbicacionBodyResponse crearUbicacionBodyResponse = new CrearUbicacionBodyResponse();
    crearUbicacionBodyResponse.setAccountId("42");
    crearUbicacionBodyResponse.setId("42");

    Status status1 = new Status();
    status1.setCode("1");
    status1.setMessage("Not all who wander are lost");
    status1.setMsgid("Msgid");
    status1.setType("Type");

    HeaderResponse headerResponse1 = new HeaderResponse();
    headerResponse1.setConsumer("Consumer");
    headerResponse1.setPid("Pid");
    headerResponse1.setStatus(status1);
    headerResponse1.setTimestamp("Timestamp");
    headerResponse1.setVarArg("Var Arg");

    Header header1 = new Header();
    header1.setHeaderResponse(headerResponse1);

    CrearUbicacionMessageResponse crearUbicacionMessageResponse = new CrearUbicacionMessageResponse();
    crearUbicacionMessageResponse.setBody(crearUbicacionBodyResponse);
    crearUbicacionMessageResponse.setHeader(header1);

    CrearUbicacionResponse crearUbicacionResponse = new CrearUbicacionResponse();
    crearUbicacionResponse.setMessageResponse(crearUbicacionMessageResponse);

    AprovisionarPlumeRequestType aprovisionarPlumeRequestType = new AprovisionarPlumeRequestType();
    aprovisionarPlumeRequestType.setCoId("42");
    aprovisionarPlumeRequestType.setEquipos(new ArrayList<>());
    aprovisionarPlumeRequestType.setIdTipo("Id Tipo");
    aprovisionarPlumeRequestType.setListaOpcional(new ArrayList<>());
    aprovisionarPlumeRequestType.setNumeroSot("Numero Sot");

    AprovisionarPlumeRequest aprovisionarPlumeRequest = new AprovisionarPlumeRequest();
    aprovisionarPlumeRequest.setAprovisionarPlumeRequest(aprovisionarPlumeRequestType);

    ConfigNivelResponse response = new ConfigNivelResponse();
    ConfigNivelMessageResponse messageResponse = new ConfigNivelMessageResponse();
    messageResponse.setHeader(header1);
    response.setMessageResponse(messageResponse);

    when(plumeLocationClient.configNivelServicio(Mockito.any(String.class), Mockito.any(ConfigNivelRequest.class),
      Mockito.any(String.class), Mockito.any(String.class), Mockito.any(String.class), Mockito.any(String.class), Mockito.any(String.class))).thenReturn(response);

    assertThrows(NullPointerException.class,
      () -> aprovisionarPlumeServiceImpl.configNivelServicioSub("123456", headerRequest, "Mensaje Trx", "ABC123",
        "ABC123", crearCuentaResponse, datosClienteBeanRes, crearUbicacionResponse, "LOC", aprovisionarPlumeRequest,
        propertiesExterno));
  }

  @Test
  public void testCrearCuentaCliente() throws SQLException, BaseException {

    AprovisionarPlumeServiceImpl aprovisionarPlumeServiceImpl = new AprovisionarPlumeServiceImpl();

    HeaderRequest headerRequest = new HeaderRequest();
    headerRequest.setAccept("Accept");
    headerRequest.setCanal("Canal");
    headerRequest.setIdTransaccion("123456");
    headerRequest.setMsgid("Msgid");
    headerRequest.setTimestamp("Timestamp");
    headerRequest.setUserId("42");

    DatosClienteBeanRes datosClienteBeanRes = new DatosClienteBeanRes();
    datosClienteBeanRes.setAccountId("00290076697");
    datosClienteBeanRes.setCodigoCliente("123456");
    datosClienteBeanRes.setCodigoClientePlm("123456");
    datosClienteBeanRes.setCodigoRespuesta("123456");
    datosClienteBeanRes.setCodigoSucursal("Codigo Sucursal");
    datosClienteBeanRes.setCursor(new ArrayList<>());
    datosClienteBeanRes.setEmail("jane.doe@example.org");
    datosClienteBeanRes.setMensajeRespuesta("Mensaje Respuesta");
    datosClienteBeanRes.setNombreSurcursal("Nombre Surcursal");
    datosClienteBeanRes.setNombres("Nombres");
    datosClienteBeanRes.setTipoTrabajo("Tipo Trabajo");
    datosClienteBeanRes.setValidaCuenta("Valida Cuenta");

    AprovisionarPlumeRequestType aprovisionarPlumeRequestType = new AprovisionarPlumeRequestType();
    aprovisionarPlumeRequestType.setCoId("42");
    aprovisionarPlumeRequestType.setEquipos(new ArrayList<>());
    aprovisionarPlumeRequestType.setIdTipo("Id Tipo");
    aprovisionarPlumeRequestType.setListaOpcional(new ArrayList<>());
    aprovisionarPlumeRequestType.setNumeroSot("Numero Sot");

    AprovisionarPlumeRequest aprovisionarPlumeRequest = new AprovisionarPlumeRequest();
    aprovisionarPlumeRequest.setAprovisionarPlumeRequest(aprovisionarPlumeRequestType);
    assertThrows(NullPointerException.class,
      () -> aprovisionarPlumeServiceImpl.crearCuentaCliente("Mensaje Trx", headerRequest, "ABC123", "ABC123",
        datosClienteBeanRes, aprovisionarPlumeRequest, "Proceso", propertiesExterno));
  }

  @Test
  public void testArmarResponse() {
    ResponseAuditType responseAuditType = new ResponseAuditType();
    responseAuditType.setCodigoRespuesta("123456");
    responseAuditType.setIdTransaccion("123456");
    responseAuditType.setMensajeRespuesta("Mensaje Respuesta");

    AprovisionarPlumeResponseDataType aprovisionarPlumeResponseDataType = new AprovisionarPlumeResponseDataType();
    aprovisionarPlumeResponseDataType.setListaOpcional(new ArrayList<>());

    AprovisionarPlumeResponseType aprovisionarPlumeResponseType = new AprovisionarPlumeResponseType();
    aprovisionarPlumeResponseType.setResponseAudit(responseAuditType);
    aprovisionarPlumeResponseType.setResponseData(aprovisionarPlumeResponseDataType);

    AprovisionarPlumeResponse aprovisionarPlumeResponse = new AprovisionarPlumeResponse();
    aprovisionarPlumeResponse.setAprovisionarPlumeResponse(aprovisionarPlumeResponseType);

    ResponseAuditType responseAuditType1 = new ResponseAuditType();
    responseAuditType1.setCodigoRespuesta("123456");
    responseAuditType1.setIdTransaccion("123456");
    responseAuditType1.setMensajeRespuesta("Mensaje Respuesta");

    AprovisionarPlumeResponseDataType aprovisionarPlumeResponseDataType1 = new AprovisionarPlumeResponseDataType();
    aprovisionarPlumeResponseDataType1.setListaOpcional(new ArrayList<>());

    AprovisionarPlumeResponseType aprovisionarPlumeResponseType1 = new AprovisionarPlumeResponseType();
    aprovisionarPlumeResponseType1.setResponseAudit(responseAuditType1);
    aprovisionarPlumeResponseType1.setResponseData(aprovisionarPlumeResponseDataType1);

    ResponseAuditType responseAuditType2 = new ResponseAuditType();
    responseAuditType2.setCodigoRespuesta("123456");
    responseAuditType2.setIdTransaccion("123456");
    responseAuditType2.setMensajeRespuesta("Mensaje Respuesta");

    AprovisionarPlumeResponseDataType aprovisionarPlumeResponseDataType2 = new AprovisionarPlumeResponseDataType();
    aprovisionarPlumeResponseDataType2.setListaOpcional(new ArrayList<>());
    aprovisionarPlumeServiceImpl.armarResponse(aprovisionarPlumeResponse, aprovisionarPlumeResponseType1,
      responseAuditType2, aprovisionarPlumeResponseDataType2, propertiesExterno);
    AprovisionarPlumeResponseType aprovisionarPlumeResponse1 = aprovisionarPlumeResponse.getAprovisionarPlumeResponse();
    assertSame(aprovisionarPlumeResponseType1, aprovisionarPlumeResponse1);
    ResponseAuditType responseAudit = aprovisionarPlumeResponse1.getResponseAudit();
    assertSame(responseAuditType2, responseAudit);
    assertSame(aprovisionarPlumeResponseDataType2, aprovisionarPlumeResponse1.getResponseData());
    assertEquals("Exito", responseAudit.getMensajeRespuesta());
    assertEquals("0", responseAudit.getCodigoRespuesta());
  }

  @Test
  public void testArmarResponse2() {
    ResponseAuditType responseAuditType = new ResponseAuditType();
    responseAuditType.setCodigoRespuesta("123456");
    responseAuditType.setIdTransaccion("123456");
    responseAuditType.setMensajeRespuesta("Mensaje Respuesta");

    AprovisionarPlumeResponseDataType aprovisionarPlumeResponseDataType = new AprovisionarPlumeResponseDataType();
    aprovisionarPlumeResponseDataType.setListaOpcional(new ArrayList<>());

    AprovisionarPlumeResponseType aprovisionarPlumeResponseType = new AprovisionarPlumeResponseType();
    aprovisionarPlumeResponseType.setResponseAudit(responseAuditType);
    aprovisionarPlumeResponseType.setResponseData(aprovisionarPlumeResponseDataType);
    AprovisionarPlumeResponse aprovisionarPlumeResponse = mock(AprovisionarPlumeResponse.class);
    doNothing().when(aprovisionarPlumeResponse).setAprovisionarPlumeResponse(any());
    aprovisionarPlumeResponse.setAprovisionarPlumeResponse(aprovisionarPlumeResponseType);

    ResponseAuditType responseAuditType1 = new ResponseAuditType();
    responseAuditType1.setCodigoRespuesta("123456");
    responseAuditType1.setIdTransaccion("123456");
    responseAuditType1.setMensajeRespuesta("Mensaje Respuesta");

    AprovisionarPlumeResponseDataType aprovisionarPlumeResponseDataType1 = new AprovisionarPlumeResponseDataType();
    aprovisionarPlumeResponseDataType1.setListaOpcional(new ArrayList<>());

    AprovisionarPlumeResponseType aprovisionarPlumeResponseType1 = new AprovisionarPlumeResponseType();
    aprovisionarPlumeResponseType1.setResponseAudit(responseAuditType1);
    aprovisionarPlumeResponseType1.setResponseData(aprovisionarPlumeResponseDataType1);

    ResponseAuditType responseAuditType2 = new ResponseAuditType();
    responseAuditType2.setCodigoRespuesta("123456");
    responseAuditType2.setIdTransaccion("123456");
    responseAuditType2.setMensajeRespuesta("Mensaje Respuesta");

    AprovisionarPlumeResponseDataType aprovisionarPlumeResponseDataType2 = new AprovisionarPlumeResponseDataType();
    aprovisionarPlumeResponseDataType2.setListaOpcional(new ArrayList<>());
    aprovisionarPlumeServiceImpl.armarResponse(aprovisionarPlumeResponse, aprovisionarPlumeResponseType1,
      responseAuditType2, aprovisionarPlumeResponseDataType2, propertiesExterno);
    verify(aprovisionarPlumeResponse, atLeast(1)).setAprovisionarPlumeResponse(any());
    ResponseAuditType responseAudit = aprovisionarPlumeResponseType1.getResponseAudit();
    assertSame(responseAuditType2, responseAudit);
    assertSame(aprovisionarPlumeResponseDataType2, aprovisionarPlumeResponseType1.getResponseData());
    assertEquals("Exito", responseAudit.getMensajeRespuesta());
    assertEquals("0", responseAudit.getCodigoRespuesta());
  }

  @Test
  public void testValidateInputBlank() throws IDFException {
    AprovisionarPlumeRequestType aprovisionarPlumeRequestType = new AprovisionarPlumeRequestType();
    aprovisionarPlumeRequestType.setCoId("42");
    aprovisionarPlumeRequestType.setEquipos(new ArrayList<>());
    aprovisionarPlumeRequestType.setIdTipo("");
    aprovisionarPlumeRequestType.setListaOpcional(new ArrayList<>());
    aprovisionarPlumeRequestType.setNumeroSot("Numero Sot");

    AprovisionarPlumeRequest aprovisionarPlumeRequest = new AprovisionarPlumeRequest();
    aprovisionarPlumeRequest.setAprovisionarPlumeRequest(aprovisionarPlumeRequestType);
    assertThrows(IDFException.class,
      () -> aprovisionarPlumeServiceImpl.validateInput(aprovisionarPlumeRequest, propertiesExterno));
  }

  @Test
  public void testValidateInputIdNotBlank() throws IDFException {
    AprovisionarPlumeRequestType aprovisionarPlumeRequestType = new AprovisionarPlumeRequestType();
    aprovisionarPlumeRequestType.setCoId("42");
    aprovisionarPlumeRequestType.setEquipos(new ArrayList<>());
    aprovisionarPlumeRequestType.setIdTipo("Id Tipo");
    aprovisionarPlumeRequestType.setListaOpcional(new ArrayList<>());
    aprovisionarPlumeRequestType.setNumeroSot("Numero Sot");

    AprovisionarPlumeRequest aprovisionarPlumeRequest = new AprovisionarPlumeRequest();
    aprovisionarPlumeRequest.setAprovisionarPlumeRequest(aprovisionarPlumeRequestType);
    assertThrows(IDFException.class,
      () -> aprovisionarPlumeServiceImpl.validateInput(aprovisionarPlumeRequest, propertiesExterno));
  }

  @Test
  public void testValidateInputSotBlank1() throws IDFException {
    AprovisionarPlumeRequestType aprovisionarPlumeRequestType = new AprovisionarPlumeRequestType();
    aprovisionarPlumeRequestType.setCoId("42");
    aprovisionarPlumeRequestType.setEquipos(new ArrayList<>());
    aprovisionarPlumeRequestType.setIdTipo("1");
    aprovisionarPlumeRequestType.setListaOpcional(new ArrayList<>());
    aprovisionarPlumeRequestType.setNumeroSot("");

    AprovisionarPlumeRequest aprovisionarPlumeRequest = new AprovisionarPlumeRequest();
    aprovisionarPlumeRequest.setAprovisionarPlumeRequest(aprovisionarPlumeRequestType);
    assertThrows(IDFException.class,
      () -> aprovisionarPlumeServiceImpl.validateInput(aprovisionarPlumeRequest, propertiesExterno));
  }

  @Test
  public void testValidateInputSotBlank2() throws IDFException {
    AprovisionarPlumeRequestType aprovisionarPlumeRequestType = new AprovisionarPlumeRequestType();
    aprovisionarPlumeRequestType.setCoId("");
    aprovisionarPlumeRequestType.setEquipos(new ArrayList<>());
    aprovisionarPlumeRequestType.setIdTipo("2");
    aprovisionarPlumeRequestType.setListaOpcional(new ArrayList<>());
    aprovisionarPlumeRequestType.setNumeroSot("");

    AprovisionarPlumeRequest aprovisionarPlumeRequest = new AprovisionarPlumeRequest();
    aprovisionarPlumeRequest.setAprovisionarPlumeRequest(aprovisionarPlumeRequestType);
    assertThrows(IDFException.class,
      () -> aprovisionarPlumeServiceImpl.validateInput(aprovisionarPlumeRequest, propertiesExterno));
  }

  @Test
  public void testGetEquiposProvisionados() throws Exception {
    BodyCrearRepetidorRequest body = new BodyCrearRepetidorRequest();
    String idAccount = "123";
    String idLocation = "123";
    String idKvConfig  = "123";

    AprovisionarPlumeRequestType aprovisionarPlumeRequest = new AprovisionarPlumeRequestType();
    List<ListaEquiposAPType> equipos = new ArrayList<ListaEquiposAPType>();
    ListaEquiposAPType listap = new ListaEquiposAPType();
    listap.setModelo("tst");
    listap.setNumeroSerie("asdasdasd");
    equipos.add(listap);
    aprovisionarPlumeRequest.setEquipos(equipos);

    AprovisionarPlumeRequest request = new AprovisionarPlumeRequest();
    request.setAprovisionarPlumeRequest(aprovisionarPlumeRequest);

    ListaEquiposProvisionados obj = new ListaEquiposProvisionados();
    obj.setCodigoCliPLM(idAccount);
    obj.setCodigoSucPLM(idLocation);
    obj.setNickName(body.getNickName());
    obj.setModelo(request.getAprovisionarPlumeRequest().getEquipos().get(0).getModelo());
    obj.setSerialNumber(request.getAprovisionarPlumeRequest().getEquipos().get(0).getNumeroSerie());
    obj.setCodSeriePLM(idKvConfig);

    ListaEquiposProvisionados obj2 = aprovisionarPlumeServiceImpl.getEquiposProvisionados(request, body, idAccount, idLocation, 0, idKvConfig);

    assertEquals(idAccount, obj2.getCodigoCliPLM());
  }

  @Test
  public void testValidarIdfIdtCrearRepetidor() throws WSException, IDFException {
    String idTransaccion = "123";
    AprovisionarPlumeRequest request = new AprovisionarPlumeRequest();
    HeaderRequest headerWs = new HeaderRequest();
    String mensajeTrx = "123";
    String tokenPlume = "123";
    String tokenClaro = "123";
    DatosClienteBeanRes resDatosClienteSGA = new DatosClienteBeanRes();
    String proceso = "123";
    CrearCuentaResponse resCrearCliente = new CrearCuentaResponse();
    CrearUbicacionResponse resCrearUbi = new CrearUbicacionResponse();
    List<ListaEquiposProvisionados> objListaEquiposProvisionados = new ArrayList<ListaEquiposProvisionados>();

    CrearRepetidorResponse responseCrear = new CrearRepetidorResponse();
    CrearRepetidorMessageResponse messageResponse = new CrearRepetidorMessageResponse();
    Header header = new Header();
    Status status = new Status();
    status.setCode("2011");
    status.setMessage("No se encontro");
    HeaderResponse headerResponse = new HeaderResponse();
    headerResponse.setStatus(status);
    header.setHeaderResponse(headerResponse);
    messageResponse.setHeader(header);
    responseCrear.setMessageResponse(messageResponse);

    assertThrows(WSException.class,
        () -> aprovisionarPlumeServiceImpl.validarIdfIdtCrearRepetidor(idTransaccion, request, headerWs, mensajeTrx, tokenPlume, tokenClaro,
        resDatosClienteSGA, proceso, resCrearCliente, resCrearUbi, propertiesExterno, objListaEquiposProvisionados, responseCrear));
  }
}
