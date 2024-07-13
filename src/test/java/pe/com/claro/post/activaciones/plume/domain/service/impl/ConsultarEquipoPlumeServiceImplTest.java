package pe.com.claro.post.activaciones.plume.domain.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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
import pe.com.claro.post.activaciones.plume.canonical.request.ConsultarEquipoRequest;
import pe.com.claro.post.activaciones.plume.canonical.request.ConsultarEquipoRequestType;
import pe.com.claro.post.activaciones.plume.canonical.response.ConsultarEquipoResponse;
import pe.com.claro.post.activaciones.plume.canonical.response.ConsultarEquipoResponseType;
import pe.com.claro.post.activaciones.plume.canonical.type.ConsultarEquipoResponseDataType;
import pe.com.claro.post.activaciones.plume.canonical.type.DefaultCEType;
import pe.com.claro.post.activaciones.plume.canonical.type.EquipoCEType;
import pe.com.claro.post.activaciones.plume.canonical.type.EthernetLanCEType;
import pe.com.claro.post.activaciones.plume.canonical.type.ResponseAuditType;
import pe.com.claro.post.activaciones.plume.common.exceptions.BDException;
import pe.com.claro.post.activaciones.plume.common.exceptions.BaseException;
import pe.com.claro.post.activaciones.plume.common.exceptions.WSException;
import pe.com.claro.post.activaciones.plume.common.property.PropertiesExterno;
import pe.com.claro.post.activaciones.plume.integration.dao.SgaDao;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.request.DatosClienteBeanReq;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.response.DatosClienteBeanRes;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.response.EquipoRes;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.type.POCursorType;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.utiltarios.UtilTokens;
import pe.com.claro.post.activaciones.plume.integration.ws.PlumeNodeClient;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.ConsultarRepetidorMessageResponse;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.ConsultarRepetidorResponse;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.Header;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.HeaderResponse;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.ErrorType;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.Status;

@ContextConfiguration(classes = {ConsultarEquipoPlumeServiceImpl.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class ConsultarEquipoPlumeServiceImplTest {
  @Autowired
  @InjectMocks
  private ConsultarEquipoPlumeServiceImpl consultarEquipoPlumeServiceImpl;

  @MockBean
  private PlumeNodeClient plumeNodeClient;

  @MockBean
  private SgaDao sgaDao;

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
  public void testConsultarEquipo() throws BDException {
    DatosClienteBeanRes datosClienteBeanRes = new DatosClienteBeanRes();
    datosClienteBeanRes.setAccountId("42");
    datosClienteBeanRes.setCodigoCliente("123");
    datosClienteBeanRes.setCodigoClientePlm("1234");
    datosClienteBeanRes.setCodigoRespuesta("0");
    datosClienteBeanRes.setCodigoSucursal("1");
    datosClienteBeanRes.setCursor(new ArrayList<>());
    datosClienteBeanRes.setEmail("jane.doe@example.org");
    datosClienteBeanRes.setMensajeRespuesta("Exito");
    datosClienteBeanRes.setNombreSurcursal("Nombre Surcursal");
    datosClienteBeanRes.setNombres("Nombres");
    datosClienteBeanRes.setTipoTrabajo("1");
    datosClienteBeanRes.setValidaCuenta("Valida Cuenta");
    when(sgaDao.datosCliente(any(), any())).thenReturn(datosClienteBeanRes);

    HeaderRequest headerRequest = new HeaderRequest();
    headerRequest.setAccept("Accept");
    headerRequest.setCanal("Canal");
    headerRequest.setIdTransaccion("Id Transaccion");
    headerRequest.setMsgid("Msgid");
    headerRequest.setTimestamp("Timestamp");
    headerRequest.setUserId("42");

    ConsultarEquipoRequestType consultarEquipoRequestType = new ConsultarEquipoRequestType();
    consultarEquipoRequestType.setCodigoCliente("123456");
    consultarEquipoRequestType.setCodigoSucursal("Codigo Sucursal");
    consultarEquipoRequestType.setListaOpcional(new ArrayList<>());
    consultarEquipoRequestType.setNumeroSerie("123456");
    consultarEquipoRequestType.setNumeroSot("Numero Sot");

    ConsultarEquipoRequest consultarEquipoRequest = new ConsultarEquipoRequest();
    consultarEquipoRequest.setConsultarEquipoRequest(consultarEquipoRequestType);

    EquipoRes responseEquipoSGA = new EquipoRes();
    responseEquipoSGA.setCodigoClientePlm("123456");
    responseEquipoSGA.setCodigoRespuesta("0");
    List<POCursorType> listaEquipoSGA = new ArrayList<POCursorType>();
    POCursorType cursorPO = new POCursorType();
    cursorPO.setMatvNroSerie("123456");
    listaEquipoSGA.add(cursorPO);
    responseEquipoSGA.setPoCursor(listaEquipoSGA);

    when(sgaDao.listarEquipos(Mockito.any(String.class), Mockito.any(String.class), Mockito.any(String.class))).thenReturn(responseEquipoSGA);

    ResponseAuditType responseAudit = consultarEquipoPlumeServiceImpl
      .consultarEquipo("1234", headerRequest, consultarEquipoRequest, propertiesExterno)
      .getConsultarEquipoResponse()
      .getResponseAudit();
    assertEquals("Idt3 Mensaje", responseAudit.getMensajeRespuesta());
    assertEquals("Id Transaccion", responseAudit.getIdTransaccion());
    assertEquals("3", responseAudit.getCodigoRespuesta());
    verify(sgaDao).datosCliente(any(), any());
  }

  @Test
  public void testConsultarEquipoUtil() throws BaseException, SQLException {
    DatosClienteBeanRes datosClienteBeanRes = new DatosClienteBeanRes();
    datosClienteBeanRes.setAccountId("42");
    datosClienteBeanRes.setCodigoCliente("123");
    datosClienteBeanRes.setCodigoClientePlm("1234");
    datosClienteBeanRes.setCodigoRespuesta("0");
    datosClienteBeanRes.setCodigoSucursal("1");
    datosClienteBeanRes.setCursor(new ArrayList<>());
    datosClienteBeanRes.setEmail("jane.doe@example.org");
    datosClienteBeanRes.setMensajeRespuesta("Exito");
    datosClienteBeanRes.setNombreSurcursal("Nombre Surcursal");
    datosClienteBeanRes.setNombres("Nombres");
    datosClienteBeanRes.setTipoTrabajo("1");
    datosClienteBeanRes.setValidaCuenta("Valida Cuenta");

    HeaderRequest headerRequest = new HeaderRequest();
    headerRequest.setAccept("Accept");
    headerRequest.setCanal("Canal");
    headerRequest.setIdTransaccion("Id Transaccion");
    headerRequest.setMsgid("Msgid");
    headerRequest.setTimestamp("Timestamp");
    headerRequest.setUserId("42");

    ConsultarEquipoRequestType consultarEquipoRequestType = new ConsultarEquipoRequestType();
    consultarEquipoRequestType.setCodigoCliente("123456");
    consultarEquipoRequestType.setCodigoSucursal("Codigo Sucursal");
    consultarEquipoRequestType.setListaOpcional(new ArrayList<>());
    consultarEquipoRequestType.setNumeroSerie("123456");
    consultarEquipoRequestType.setNumeroSot("Numero Sot");

    ConsultarEquipoRequest consultarEquipoRequest = new ConsultarEquipoRequest();
    consultarEquipoRequest.setConsultarEquipoRequest(consultarEquipoRequestType);

    EquipoRes responseEquipoSGA = new EquipoRes();
    responseEquipoSGA.setCodigoClientePlm("123456");
    responseEquipoSGA.setCodigoRespuesta("0");
    List<POCursorType> listaEquipoSGA = new ArrayList<POCursorType>();
    POCursorType cursorPO = new POCursorType();
    cursorPO.setMatvNroSerie("123456");
    listaEquipoSGA.add(cursorPO);
    responseEquipoSGA.setPoCursor(listaEquipoSGA);

    when(sgaDao.datosCliente(any(), any())).thenReturn(datosClienteBeanRes);

    when(sgaDao.listarEquipos(any(), any(), any())).thenReturn(responseEquipoSGA);

    when(UtilTokens.subprocesoTokens(any(), any(), any()
      , any(), any(), any()
      , any())).thenReturn("eyJraWQiOiJfTWpXMDF6dnFvZjZuVpzWG5zIiwiYWxnIjoiUlMyNTYifQ.eyJ2ZXIFFP2QKtvrh3XKnSjw\n" +
      "3a5d987d-6b9e-449a-83f3-e2ca718df557");
    ResponseAuditType responseAudit = consultarEquipoPlumeServiceImpl
      .consultarEquipo("1234", headerRequest, consultarEquipoRequest, propertiesExterno)
      .getConsultarEquipoResponse()
      .getResponseAudit();
    assertEquals("Idt3 Mensaje", responseAudit.getMensajeRespuesta());
    assertEquals("3", responseAudit.getCodigoRespuesta());
    verify(sgaDao).datosCliente(any(), any());
  }

  @Test
  public void testConsultarEquipoDatosClienteSGAFail() throws BDException {
    DatosClienteBeanRes datosClienteBeanRes = new DatosClienteBeanRes();
    datosClienteBeanRes.setAccountId("42");
    datosClienteBeanRes.setCodigoCliente("123");
    datosClienteBeanRes.setCodigoClientePlm("1234");
    datosClienteBeanRes.setCodigoRespuesta("1");
    datosClienteBeanRes.setCodigoSucursal("1");
    datosClienteBeanRes.setCursor(new ArrayList<>());
    datosClienteBeanRes.setEmail("jane.doe@example.org");
    datosClienteBeanRes.setMensajeRespuesta("Exito");
    datosClienteBeanRes.setNombreSurcursal("Nombre Surcursal");
    datosClienteBeanRes.setNombres("Nombres");
    datosClienteBeanRes.setTipoTrabajo("1");
    datosClienteBeanRes.setValidaCuenta("Valida Cuenta");
    when(sgaDao.datosCliente(any(), any())).thenReturn(datosClienteBeanRes);

    HeaderRequest headerRequest = new HeaderRequest();
    headerRequest.setAccept("Accept");
    headerRequest.setCanal("Canal");
    headerRequest.setIdTransaccion("Id Transaccion");
    headerRequest.setMsgid("Msgid");
    headerRequest.setTimestamp("Timestamp");
    headerRequest.setUserId("42");

    ConsultarEquipoRequestType consultarEquipoRequestType = new ConsultarEquipoRequestType();
    consultarEquipoRequestType.setCodigoCliente("123456");
    consultarEquipoRequestType.setCodigoSucursal("Codigo Sucursal");
    consultarEquipoRequestType.setListaOpcional(new ArrayList<>());
    consultarEquipoRequestType.setNumeroSerie("123456");
    consultarEquipoRequestType.setNumeroSot("Numero Sot");

    ConsultarEquipoRequest consultarEquipoRequest = new ConsultarEquipoRequest();
    consultarEquipoRequest.setConsultarEquipoRequest(consultarEquipoRequestType);

    ResponseAuditType responseAudit = consultarEquipoPlumeServiceImpl
      .consultarEquipo("1234", headerRequest, consultarEquipoRequest, propertiesExterno)
      .getConsultarEquipoResponse()
      .getResponseAudit();
    assertEquals("No se pudo validar la información de la cuenta. 1 - Exito", responseAudit.getMensajeRespuesta());
    assertEquals("2", responseAudit.getCodigoRespuesta());
    verify(sgaDao).datosCliente(any(), any());
  }

  @Test
  public void testConsultarEquipoListarEquipFail() throws BDException {
    DatosClienteBeanRes datosClienteBeanRes = new DatosClienteBeanRes();
    datosClienteBeanRes.setAccountId("42");
    datosClienteBeanRes.setCodigoCliente("123");
    datosClienteBeanRes.setCodigoClientePlm("1234");
    datosClienteBeanRes.setCodigoRespuesta("0");
    datosClienteBeanRes.setCodigoSucursal("1");
    datosClienteBeanRes.setCursor(new ArrayList<>());
    datosClienteBeanRes.setEmail("jane.doe@example.org");
    datosClienteBeanRes.setMensajeRespuesta("Exito");
    datosClienteBeanRes.setNombreSurcursal("Nombre Surcursal");
    datosClienteBeanRes.setNombres("Nombres");
    datosClienteBeanRes.setTipoTrabajo("1");
    datosClienteBeanRes.setValidaCuenta("Valida Cuenta");
    when(sgaDao.datosCliente(any(), any())).thenReturn(datosClienteBeanRes);

    HeaderRequest headerRequest = new HeaderRequest();
    headerRequest.setAccept("Accept");
    headerRequest.setCanal("Canal");
    headerRequest.setIdTransaccion("Id Transaccion");
    headerRequest.setMsgid("Msgid");
    headerRequest.setTimestamp("Timestamp");
    headerRequest.setUserId("42");

    ConsultarEquipoRequestType consultarEquipoRequestType = new ConsultarEquipoRequestType();
    consultarEquipoRequestType.setCodigoCliente("123456");
    consultarEquipoRequestType.setCodigoSucursal("Codigo Sucursal");
    consultarEquipoRequestType.setListaOpcional(new ArrayList<>());
    consultarEquipoRequestType.setNumeroSerie("123456");
    consultarEquipoRequestType.setNumeroSot("Numero Sot");

    ConsultarEquipoRequest consultarEquipoRequest = new ConsultarEquipoRequest();
    consultarEquipoRequest.setConsultarEquipoRequest(consultarEquipoRequestType);

    EquipoRes responseEquipoSGA = new EquipoRes();
    responseEquipoSGA.setCodigoClientePlm("123456");
    responseEquipoSGA.setCodigoRespuesta("1");
    List<POCursorType> listaEquipoSGA = new ArrayList<POCursorType>();
    POCursorType cursorPO = new POCursorType();
    cursorPO.setMatvNroSerie("123456");
    listaEquipoSGA.add(cursorPO);
    responseEquipoSGA.setPoCursor(listaEquipoSGA);

    when(sgaDao.listarEquipos(Mockito.any(String.class), Mockito.any(String.class), Mockito.any(String.class))).thenReturn(responseEquipoSGA);

    ResponseAuditType responseAudit = consultarEquipoPlumeServiceImpl
      .consultarEquipo("1234", headerRequest, consultarEquipoRequest, propertiesExterno)
      .getConsultarEquipoResponse()
      .getResponseAudit();
    assertEquals("Idt3 Mensaje", responseAudit.getMensajeRespuesta());
    assertEquals("3", responseAudit.getCodigoRespuesta());
    verify(sgaDao).datosCliente(any(), any());
  }

  @Test
  public void testConsultarEquipoReq1() throws BDException {
    HeaderRequest headerRequest = new HeaderRequest();

    ConsultarEquipoRequestType consultarEquipoRequestType = new ConsultarEquipoRequestType();
    consultarEquipoRequestType.setCodigoCliente("");
    consultarEquipoRequestType.setCodigoSucursal("Codigo Sucursal");
    consultarEquipoRequestType.setListaOpcional(new ArrayList<>());
    consultarEquipoRequestType.setNumeroSerie("123456");
    consultarEquipoRequestType.setNumeroSot("Numero Sot");

    ConsultarEquipoRequest consultarEquipoRequest = new ConsultarEquipoRequest();
    consultarEquipoRequest.setConsultarEquipoRequest(consultarEquipoRequestType);

    ResponseAuditType responseAudit = consultarEquipoPlumeServiceImpl
      .consultarEquipo("1234", headerRequest, consultarEquipoRequest, propertiesExterno)
      .getConsultarEquipoResponse()
      .getResponseAudit();
    assertEquals("Parametros obligatorios incompletos: codigoCliente", responseAudit.getMensajeRespuesta());
    assertEquals("1", responseAudit.getCodigoRespuesta());
  }

  @Test
  public void testConsultarEquipoReq2() throws BDException {
    HeaderRequest headerRequest = new HeaderRequest();

    ConsultarEquipoRequestType consultarEquipoRequestType = new ConsultarEquipoRequestType();
    consultarEquipoRequestType.setCodigoCliente("123");
    consultarEquipoRequestType.setCodigoSucursal("");
    consultarEquipoRequestType.setListaOpcional(new ArrayList<>());
    consultarEquipoRequestType.setNumeroSerie("123456");
    consultarEquipoRequestType.setNumeroSot("Numero Sot");

    ConsultarEquipoRequest consultarEquipoRequest = new ConsultarEquipoRequest();
    consultarEquipoRequest.setConsultarEquipoRequest(consultarEquipoRequestType);

    ResponseAuditType responseAudit = consultarEquipoPlumeServiceImpl
      .consultarEquipo("1234", headerRequest, consultarEquipoRequest, propertiesExterno)
      .getConsultarEquipoResponse()
      .getResponseAudit();
    assertEquals("Parametros obligatorios incompletos: codigoSucursal", responseAudit.getMensajeRespuesta());
    assertEquals("1", responseAudit.getCodigoRespuesta());
  }

  @Test
  public void testConsultarEquipoReq3() throws BDException {
    HeaderRequest headerRequest = new HeaderRequest();

    ConsultarEquipoRequestType consultarEquipoRequestType = new ConsultarEquipoRequestType();
    consultarEquipoRequestType.setCodigoCliente("123");
    consultarEquipoRequestType.setCodigoSucursal("123");
    consultarEquipoRequestType.setListaOpcional(new ArrayList<>());
    consultarEquipoRequestType.setNumeroSerie("");
    consultarEquipoRequestType.setNumeroSot("Numero Sot");

    ConsultarEquipoRequest consultarEquipoRequest = new ConsultarEquipoRequest();
    consultarEquipoRequest.setConsultarEquipoRequest(consultarEquipoRequestType);

    ResponseAuditType responseAudit = consultarEquipoPlumeServiceImpl
      .consultarEquipo("1234", headerRequest, consultarEquipoRequest, propertiesExterno)
      .getConsultarEquipoResponse()
      .getResponseAudit();
    assertEquals("Parametros obligatorios incompletos: numeroSerie", responseAudit.getMensajeRespuesta());
    assertEquals("1", responseAudit.getCodigoRespuesta());
  }

  @Test
  public void testConsultarEquipoReq4() throws BDException {
    HeaderRequest headerRequest = new HeaderRequest();

    ConsultarEquipoRequestType consultarEquipoRequestType = new ConsultarEquipoRequestType();
    consultarEquipoRequestType.setCodigoCliente("123");
    consultarEquipoRequestType.setCodigoSucursal("123");
    consultarEquipoRequestType.setListaOpcional(new ArrayList<>());
    consultarEquipoRequestType.setNumeroSerie("123");
    consultarEquipoRequestType.setNumeroSot("");

    ConsultarEquipoRequest consultarEquipoRequest = new ConsultarEquipoRequest();
    consultarEquipoRequest.setConsultarEquipoRequest(consultarEquipoRequestType);

    ResponseAuditType responseAudit = consultarEquipoPlumeServiceImpl
      .consultarEquipo("1234", headerRequest, consultarEquipoRequest, propertiesExterno)
      .getConsultarEquipoResponse()
      .getResponseAudit();
    assertEquals("Parametros obligatorios incompletos: numeroSot", responseAudit.getMensajeRespuesta());
    assertEquals("1", responseAudit.getCodigoRespuesta());
  }

  @Test
  public void testArmarResponse() {
    ResponseAuditType responseAuditType = new ResponseAuditType();
    responseAuditType.setCodigoRespuesta("Codigo Respuesta");
    responseAuditType.setIdTransaccion("Id Transaccion");
    responseAuditType.setMensajeRespuesta("Mensaje Respuesta");

    EquipoCEType equipoCEType = new EquipoCEType();
    equipoCEType.setBackhaulDhcpPoolIdx("Backhaul Dhcp Pool Idx");
    equipoCEType.setBoxSerialNumber("42");
    equipoCEType.setCartonId("42");
    equipoCEType.setClaimKeyRequired(true);
    equipoCEType.setCreatedAt("Jan 1, 2020 8:00am GMT+0100");
    equipoCEType.setCustomerId("42");
    equipoCEType.setDeployment("Deployment");
    equipoCEType.setError(new ErrorType());
    equipoCEType.setEthernet1Mac("Ethernet1 Mac");
    equipoCEType.setEthernetLan(new EthernetLanCEType());
    equipoCEType.setEthernetMac("Ethernet Mac");
    equipoCEType.setFirmwareVersion("1.0.2");
    equipoCEType.setId("42");
    equipoCEType.setKvConfigs(new ArrayList<>());
    equipoCEType.setLocationId("42");
    equipoCEType.setMac("Mac");
    equipoCEType.setModelo("Modelo");
    equipoCEType.setNumeroSerie("Numero Serie");
    equipoCEType.setPackId("42");
    equipoCEType.setPartNumber("42");
    equipoCEType.setPartnerId("42");
    equipoCEType.setPurchaseOrderNumber("42");
    equipoCEType.setRadioMac24("Radio Mac24");
    equipoCEType.setRadioMac50("Radio Mac50");
    equipoCEType.setResidentialGateway(true);
    equipoCEType.setShardNumber("42");
    equipoCEType.setShipDate("2020-03-01");
    equipoCEType.setSubscriptionRequired(true);
    equipoCEType.setSubscriptionRequiredTerm("Subscription Required Term");
    equipoCEType.setTimestamp("Timestamp");
    equipoCEType.setUnclaimable(true);
    equipoCEType.setUpdatedAt("2020-03-01");
    equipoCEType.setVersion("1.0.2");

    ConsultarEquipoResponseDataType consultarEquipoResponseDataType = new ConsultarEquipoResponseDataType();
    consultarEquipoResponseDataType.setEquipo(equipoCEType);
    consultarEquipoResponseDataType.setIdCustPlume("Id Cust Plume");
    consultarEquipoResponseDataType.setIdLocPlume("Id Loc Plume");
    consultarEquipoResponseDataType.setListaOpcional(new ArrayList<>());

    ConsultarEquipoResponseType consultarEquipoResponseType = new ConsultarEquipoResponseType();
    consultarEquipoResponseType.setResponseAudit(responseAuditType);
    consultarEquipoResponseType.setResponseData(consultarEquipoResponseDataType);

    ConsultarEquipoResponse consultarEquipoResponse = new ConsultarEquipoResponse();
    consultarEquipoResponse.setConsultarEquipoResponse(consultarEquipoResponseType);

    ResponseAuditType responseAuditType1 = new ResponseAuditType();
    responseAuditType1.setCodigoRespuesta("Codigo Respuesta");
    responseAuditType1.setIdTransaccion("Id Transaccion");
    responseAuditType1.setMensajeRespuesta("Mensaje Respuesta");

    ErrorType errorType = new ErrorType();
    errorType.setMessage("Not all who wander are lost");
    errorType.setName("Name");
    errorType.setStatusCode("Status Code");

    EthernetLanCEType ethernetLanCEType = new EthernetLanCEType();
    ethernetLanCEType.setDefaultCE(new DefaultCEType());

    EquipoCEType equipoCEType1 = new EquipoCEType();
    equipoCEType1.setBackhaulDhcpPoolIdx("Backhaul Dhcp Pool Idx");
    equipoCEType1.setBoxSerialNumber("42");
    equipoCEType1.setCartonId("42");
    equipoCEType1.setClaimKeyRequired(true);
    equipoCEType1.setCreatedAt("Jan 1, 2020 8:00am GMT+0100");
    equipoCEType1.setCustomerId("42");
    equipoCEType1.setDeployment("Deployment");
    equipoCEType1.setError(errorType);
    equipoCEType1.setEthernet1Mac("Ethernet1 Mac");
    equipoCEType1.setEthernetLan(ethernetLanCEType);
    equipoCEType1.setEthernetMac("Ethernet Mac");
    equipoCEType1.setFirmwareVersion("1.0.2");
    equipoCEType1.setId("42");
    equipoCEType1.setKvConfigs(new ArrayList<>());
    equipoCEType1.setLocationId("42");
    equipoCEType1.setMac("Mac");
    equipoCEType1.setModelo("Modelo");
    equipoCEType1.setNumeroSerie("Numero Serie");
    equipoCEType1.setPackId("42");
    equipoCEType1.setPartNumber("42");
    equipoCEType1.setPartnerId("42");
    equipoCEType1.setPurchaseOrderNumber("42");
    equipoCEType1.setRadioMac24("Radio Mac24");
    equipoCEType1.setRadioMac50("Radio Mac50");
    equipoCEType1.setResidentialGateway(true);
    equipoCEType1.setShardNumber("42");
    equipoCEType1.setShipDate("2020-03-01");
    equipoCEType1.setSubscriptionRequired(true);
    equipoCEType1.setSubscriptionRequiredTerm("Subscription Required Term");
    equipoCEType1.setTimestamp("Timestamp");
    equipoCEType1.setUnclaimable(true);
    equipoCEType1.setUpdatedAt("2020-03-01");
    equipoCEType1.setVersion("1.0.2");

    ConsultarEquipoResponseDataType consultarEquipoResponseDataType1 = new ConsultarEquipoResponseDataType();
    consultarEquipoResponseDataType1.setEquipo(equipoCEType1);
    consultarEquipoResponseDataType1.setIdCustPlume("Id Cust Plume");
    consultarEquipoResponseDataType1.setIdLocPlume("Id Loc Plume");
    consultarEquipoResponseDataType1.setListaOpcional(new ArrayList<>());

    ConsultarEquipoResponseType consultarEquipoResponseType1 = new ConsultarEquipoResponseType();
    consultarEquipoResponseType1.setResponseAudit(responseAuditType1);
    consultarEquipoResponseType1.setResponseData(consultarEquipoResponseDataType1);

    ResponseAuditType responseAuditType2 = new ResponseAuditType();
    responseAuditType2.setCodigoRespuesta("Codigo Respuesta");
    responseAuditType2.setIdTransaccion("Id Transaccion");
    responseAuditType2.setMensajeRespuesta("Mensaje Respuesta");

    ErrorType errorType1 = new ErrorType();
    errorType1.setMessage("Not all who wander are lost");
    errorType1.setName("Name");
    errorType1.setStatusCode("Status Code");

    DefaultCEType defaultCEType = new DefaultCEType();
    defaultCEType.setMode("Mode");

    EthernetLanCEType ethernetLanCEType1 = new EthernetLanCEType();
    ethernetLanCEType1.setDefaultCE(defaultCEType);

    EquipoCEType equipoCEType2 = new EquipoCEType();
    equipoCEType2.setBackhaulDhcpPoolIdx("Backhaul Dhcp Pool Idx");
    equipoCEType2.setBoxSerialNumber("42");
    equipoCEType2.setCartonId("42");
    equipoCEType2.setClaimKeyRequired(true);
    equipoCEType2.setCreatedAt("Jan 1, 2020 8:00am GMT+0100");
    equipoCEType2.setCustomerId("42");
    equipoCEType2.setDeployment("Deployment");
    equipoCEType2.setError(errorType1);
    equipoCEType2.setEthernet1Mac("Ethernet1 Mac");
    equipoCEType2.setEthernetLan(ethernetLanCEType1);
    equipoCEType2.setEthernetMac("Ethernet Mac");
    equipoCEType2.setFirmwareVersion("1.0.2");
    equipoCEType2.setId("42");
    equipoCEType2.setKvConfigs(new ArrayList<>());
    equipoCEType2.setLocationId("42");
    equipoCEType2.setMac("Mac");
    equipoCEType2.setModelo("Modelo");
    equipoCEType2.setNumeroSerie("Numero Serie");
    equipoCEType2.setPackId("42");
    equipoCEType2.setPartNumber("42");
    equipoCEType2.setPartnerId("42");
    equipoCEType2.setPurchaseOrderNumber("42");
    equipoCEType2.setRadioMac24("Radio Mac24");
    equipoCEType2.setRadioMac50("Radio Mac50");
    equipoCEType2.setResidentialGateway(true);
    equipoCEType2.setShardNumber("42");
    equipoCEType2.setShipDate("2020-03-01");
    equipoCEType2.setSubscriptionRequired(true);
    equipoCEType2.setSubscriptionRequiredTerm("Subscription Required Term");
    equipoCEType2.setTimestamp("Timestamp");
    equipoCEType2.setUnclaimable(true);
    equipoCEType2.setUpdatedAt("2020-03-01");
    equipoCEType2.setVersion("1.0.2");

    ConsultarEquipoResponseDataType consultarEquipoResponseDataType2 = new ConsultarEquipoResponseDataType();
    consultarEquipoResponseDataType2.setEquipo(equipoCEType2);
    consultarEquipoResponseDataType2.setIdCustPlume("Id Cust Plume");
    consultarEquipoResponseDataType2.setIdLocPlume("Id Loc Plume");
    consultarEquipoResponseDataType2.setListaOpcional(new ArrayList<>());

    ErrorType errorType2 = new ErrorType();
    errorType2.setMessage("Not all who wander are lost");
    errorType2.setName("Name");
    errorType2.setStatusCode("Status Code");

    EthernetLanCEType ethernetLanCEType2 = new EthernetLanCEType();
    ethernetLanCEType2.setDefaultCE(new DefaultCEType());

    EquipoCEType equipoCEType3 = new EquipoCEType();
    equipoCEType3.setBackhaulDhcpPoolIdx("Backhaul Dhcp Pool Idx");
    equipoCEType3.setBoxSerialNumber("42");
    equipoCEType3.setCartonId("42");
    equipoCEType3.setClaimKeyRequired(true);
    equipoCEType3.setCreatedAt("Jan 1, 2020 8:00am GMT+0100");
    equipoCEType3.setCustomerId("42");
    equipoCEType3.setDeployment("Deployment");
    equipoCEType3.setError(errorType2);
    equipoCEType3.setEthernet1Mac("Ethernet1 Mac");
    equipoCEType3.setEthernetLan(ethernetLanCEType2);
    equipoCEType3.setEthernetMac("Ethernet Mac");
    equipoCEType3.setFirmwareVersion("1.0.2");
    equipoCEType3.setId("42");
    equipoCEType3.setKvConfigs(new ArrayList<>());
    equipoCEType3.setLocationId("42");
    equipoCEType3.setMac("Mac");
    equipoCEType3.setModelo("Modelo");
    equipoCEType3.setNumeroSerie("Numero Serie");
    equipoCEType3.setPackId("42");
    equipoCEType3.setPartNumber("42");
    equipoCEType3.setPartnerId("42");
    equipoCEType3.setPurchaseOrderNumber("42");
    equipoCEType3.setRadioMac24("Radio Mac24");
    equipoCEType3.setRadioMac50("Radio Mac50");
    equipoCEType3.setResidentialGateway(true);
    equipoCEType3.setShardNumber("42");
    equipoCEType3.setShipDate("2020-03-01");
    equipoCEType3.setSubscriptionRequired(true);
    equipoCEType3.setSubscriptionRequiredTerm("Subscription Required Term");
    equipoCEType3.setTimestamp("Timestamp");
    equipoCEType3.setUnclaimable(true);
    equipoCEType3.setUpdatedAt("2020-03-01");
    equipoCEType3.setVersion("1.0.2");

    HeaderResponse headerResponse = new HeaderResponse();
    headerResponse.setConsumer("Consumer");
    headerResponse.setPid("Pid");
    headerResponse.setStatus(new Status());
    headerResponse.setTimestamp("Timestamp");
    headerResponse.setVarArg("Var Arg");

    Header header = new Header();
    header.setHeaderResponse(headerResponse);

    ConsultarRepetidorMessageResponse consultarRepetidorMessageResponse = new ConsultarRepetidorMessageResponse();
    consultarRepetidorMessageResponse.setBody(equipoCEType3);
    consultarRepetidorMessageResponse.setHeader(header);

    ConsultarRepetidorResponse consultarRepetidorResponse = new ConsultarRepetidorResponse();
    consultarRepetidorResponse.setMessageResponse(consultarRepetidorMessageResponse);

    consultarEquipoPlumeServiceImpl.armarResponse(consultarEquipoResponse, consultarEquipoResponseType1,
      responseAuditType2, consultarEquipoResponseDataType2, consultarRepetidorResponse, propertiesExterno);
    ConsultarEquipoResponseType consultarEquipoResponse1 = consultarEquipoResponse.getConsultarEquipoResponse();
    assertSame(consultarEquipoResponseType1, consultarEquipoResponse1);
    ResponseAuditType responseAudit = consultarEquipoResponse1.getResponseAudit();
    assertSame(responseAuditType2, responseAudit);
    ConsultarEquipoResponseDataType responseData = consultarEquipoResponse1.getResponseData();
    assertSame(consultarEquipoResponseDataType2, responseData);
    assertEquals("Exito", responseAudit.getMensajeRespuesta());
    assertEquals("42", responseData.getIdLocPlume());
    assertEquals("42", responseData.getIdCustPlume());
    assertSame(equipoCEType3, responseData.getEquipo());
    assertEquals("0", responseAudit.getCodigoRespuesta());
  }
}
