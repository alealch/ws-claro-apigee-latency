package pe.com.claro.post.activaciones.plume.domain.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pe.com.claro.post.activaciones.plume.canonical.header.HeaderRequest;
import pe.com.claro.post.activaciones.plume.canonical.request.AprovisionarPlumeRequest;
import pe.com.claro.post.activaciones.plume.canonical.request.ObtenerLocacionesRequest;
import pe.com.claro.post.activaciones.plume.canonical.request.ObtenerLocacionesRequestType;
import pe.com.claro.post.activaciones.plume.canonical.response.ObtenerLocacionesResponse;
import pe.com.claro.post.activaciones.plume.canonical.response.ObtenerLocacionesResponseType;
import pe.com.claro.post.activaciones.plume.canonical.type.ListaLocacionesOLType;
import pe.com.claro.post.activaciones.plume.canonical.type.ObtenerLocacionesResponseDataType;
import pe.com.claro.post.activaciones.plume.canonical.type.ResponseAuditType;
import pe.com.claro.post.activaciones.plume.common.exceptions.BDException;
import pe.com.claro.post.activaciones.plume.common.exceptions.BaseException;
import pe.com.claro.post.activaciones.plume.common.exceptions.WSException;
import pe.com.claro.post.activaciones.plume.common.property.PropertiesExterno;
import pe.com.claro.post.activaciones.plume.integration.dao.SgaDao;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.request.DatosClienteBeanReq;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.response.DatosClienteBeanRes;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.type.CursorValidaCuenta;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.utiltarios.UtilTokens;
import pe.com.claro.post.activaciones.plume.integration.ws.PlumeCustomerClient;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.Header;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.HeaderResponse;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.ObtenerUbicacionBodyResponse;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.ObtenerUbicacionMessageResponse;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.ObtenerUbicacionResponse;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.ErrorType;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.ServiceLevel;

@ContextConfiguration(classes = {ObtenerLocacionesPlumeServiceImpl.class, PropertiesExterno.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class ObtenerLocacionesPlumeServiceImplTest {
  @Autowired
  private ObtenerLocacionesPlumeServiceImpl obtenerLocacionesPlumeServiceImpl;

  @MockBean
  private PlumeCustomerClient plumeCustomerClient;

  @MockBean
  private SgaDao sgaDao;

  @MockBean
  private UtilTokens UtilTokens;

  @Test
  public void testObtenerLocacionesList() {
    ArrayList<ListaLocacionesOLType> listaLocacionesOLTypeList = new ArrayList<>();

    CursorValidaCuenta cursorValidaCuenta = new CursorValidaCuenta();
    cursorValidaCuenta.setCodSuc("Cod Suc");
    cursorValidaCuenta.setLocation("Location");
    cursorValidaCuenta.setNickName("Nick Name");
    cursorValidaCuenta.setNroSerie("Nro Serie");

    ArrayList<CursorValidaCuenta> cursorValidaCuentaList = new ArrayList<>();
    cursorValidaCuentaList.add(cursorValidaCuenta);
    obtenerLocacionesPlumeServiceImpl.obtenerLocacionesList(listaLocacionesOLTypeList, cursorValidaCuentaList,
      new ArrayList<>());
    assertEquals(1, listaLocacionesOLTypeList.size());
    assertEquals("Cod Suc", listaLocacionesOLTypeList.get(0).getCodigoSucursal());
  }

  @Test
  public void testObtenerLocacionesList2() {
    ArrayList<ListaLocacionesOLType> listaLocacionesOLTypeList = new ArrayList<>();

    CursorValidaCuenta cursorValidaCuenta = new CursorValidaCuenta();
    cursorValidaCuenta.setCodSuc("123");
    cursorValidaCuenta.setLocation("123");
    cursorValidaCuenta.setNickName("Test");
    cursorValidaCuenta.setNroSerie("123456");

    ArrayList<CursorValidaCuenta> cursorValidaCuentaList = new ArrayList<>();
    cursorValidaCuentaList.add(cursorValidaCuenta);
    List<ObtenerUbicacionBodyResponse> listaPlume = new ArrayList<>();
    ObtenerUbicacionBodyResponse bodyResp = new ObtenerUbicacionBodyResponse();
    ServiceLevel serviceLevel = new ServiceLevel();
    serviceLevel.setStatus("Ok");
    bodyResp.setId("123");
    bodyResp.setName("Test");
    bodyResp.setServiceLevel(serviceLevel);
    listaPlume.add(bodyResp);

    obtenerLocacionesPlumeServiceImpl.obtenerLocacionesList(listaLocacionesOLTypeList, cursorValidaCuentaList,
      listaPlume);
    assertEquals(1, listaLocacionesOLTypeList.size());
    assertEquals("123", listaLocacionesOLTypeList.get(0).getCodigoSucursal());
  }

  @Test
  public void testObtenerLocaciones() throws BaseException, SQLException {
    DatosClienteBeanRes datosClienteBeanRes = new DatosClienteBeanRes();
    datosClienteBeanRes.setAccountId("42");
    datosClienteBeanRes.setCodigoCliente("Codigo Cliente");
    datosClienteBeanRes.setCodigoClientePlm("Codigo Cliente Plm");
    datosClienteBeanRes.setCodigoRespuesta("Codigo Respuesta");
    datosClienteBeanRes.setCodigoSucursal("Codigo Sucursal");
    datosClienteBeanRes.setCursor(new ArrayList<>());
    datosClienteBeanRes.setEmail("jane.doe@example.org");
    datosClienteBeanRes.setMensajeRespuesta("Mensaje Respuesta");
    datosClienteBeanRes.setNombreSurcursal("Nombre Surcursal");
    datosClienteBeanRes.setNombres("Nombres");
    datosClienteBeanRes.setTipoTrabajo("Tipo Trabajo");
    datosClienteBeanRes.setValidaCuenta("Valida Cuenta");
    when(sgaDao.datosCliente(any(), any())).thenReturn(datosClienteBeanRes);

    HeaderRequest headerRequest = new HeaderRequest();
    headerRequest.setAccept("Accept");
    headerRequest.setCanal("Canal");
    headerRequest.setIdTransaccion("Id Transaccion");
    headerRequest.setMsgid("Msgid");
    headerRequest.setTimestamp("Timestamp");
    headerRequest.setUserId("42");

    ObtenerLocacionesRequestType obtenerLocacionesRequestType = new ObtenerLocacionesRequestType();
    obtenerLocacionesRequestType.setCodigoCliente("Codigo Cliente");
    obtenerLocacionesRequestType.setListaOpcional(new ArrayList<>());
    obtenerLocacionesRequestType.setNumeroSot("Numero Sot");

    ObtenerLocacionesRequest obtenerLocacionesRequest = new ObtenerLocacionesRequest();
    obtenerLocacionesRequest.setObtenerLocacionesRequest(obtenerLocacionesRequestType);

    when(UtilTokens.subprocesoTokens(any(), any(), any()
      , any(), any(), any()
      , any())).thenReturn("eyJraWQiOiJfTWpXMDF6dnFvZjZuVpzWG5zIiwiYWxnIjoiUlMyNTYifQ.eyJ2ZXIFFP2QKtvrh3XKnSjw\n" +
      "3a5d987d-6b9e-449a-83f3-e2ca718df557");

    ResponseAuditType responseAudit = obtenerLocacionesPlumeServiceImpl
      .obtenerLocaciones("Id Transaccion", headerRequest, obtenerLocacionesRequest)
      .getObtenerLocacionesResponse()
      .getResponseAudit();
    assertEquals("${obtener.locaciones.idf2.mensaje}", responseAudit.getMensajeRespuesta());
    assertEquals("Id Transaccion", responseAudit.getIdTransaccion());
    assertEquals("${obtener.locaciones.idf2.codigo}", responseAudit.getCodigoRespuesta());
    verify(sgaDao).datosCliente(any(), any());
  }

  @Test
  public void testObtenerLocaciones3() throws BaseException, SQLException {
    DatosClienteBeanRes datosClienteBeanRes = mock(DatosClienteBeanRes.class);
    when(datosClienteBeanRes.getMensajeRespuesta()).thenReturn("Exito");
    when(datosClienteBeanRes.getCodigoRespuesta()).thenReturn("0");
    datosClienteBeanRes.setAccountId("42");
    datosClienteBeanRes.setCodigoCliente("Codigo Cliente");
    datosClienteBeanRes.setCodigoClientePlm("Codigo Cliente Plm");
    datosClienteBeanRes.setCodigoRespuesta("Codigo Respuesta");
    datosClienteBeanRes.setCodigoSucursal("Codigo Sucursal");
    datosClienteBeanRes.setCursor(new ArrayList<>());
    datosClienteBeanRes.setEmail("jane.doe@example.org");
    datosClienteBeanRes.setMensajeRespuesta("Mensaje Respuesta");
    datosClienteBeanRes.setNombreSurcursal("Nombre Surcursal");
    datosClienteBeanRes.setNombres("Nombres");
    datosClienteBeanRes.setTipoTrabajo("Tipo Trabajo");
    datosClienteBeanRes.setValidaCuenta("Valida Cuenta");
    when(sgaDao.datosCliente(any(), any())).thenReturn(datosClienteBeanRes);
    HeaderRequest headerRequest = mock(HeaderRequest.class);
    when(headerRequest.getAccept()).thenReturn("Accept");
    when(headerRequest.getCanal()).thenReturn("Canal");
    when(headerRequest.getIdTransaccion()).thenReturn("Id Transaccion");
    when(headerRequest.getMsgid()).thenReturn("Msgid");
    headerRequest.setAccept("Accept");
    headerRequest.setCanal("Canal");
    headerRequest.setIdTransaccion("Id Transaccion");
    headerRequest.setMsgid("Msgid");
    headerRequest.setTimestamp("Timestamp");
    headerRequest.setUserId("42");

    ObtenerLocacionesRequestType obtenerLocacionesRequestType = new ObtenerLocacionesRequestType();
    obtenerLocacionesRequestType.setCodigoCliente("Codigo Cliente");
    obtenerLocacionesRequestType.setListaOpcional(new ArrayList<>());
    obtenerLocacionesRequestType.setNumeroSot("Numero Sot");

    ObtenerLocacionesRequest obtenerLocacionesRequest = new ObtenerLocacionesRequest();
    obtenerLocacionesRequest.setObtenerLocacionesRequest(obtenerLocacionesRequestType);

    when(UtilTokens.subprocesoTokens(any(), any(), any()
      , any(), any(), any()
      , any())).thenReturn("eyJraWQiOiJfTWpXMDF6dnFvZjZuVpzWG5zIiwiYWxnIjoiUlMyNTYifQ.eyJ2ZXIFFP2QKtvrh3XKnSjw\n" +
      "3a5d987d-6b9e-449a-83f3-e2ca718df557");
    ObtenerUbicacionResponse obtenerResponse = new ObtenerUbicacionResponse();
    ObtenerUbicacionMessageResponse messageResponse = new ObtenerUbicacionMessageResponse();
    List<ObtenerUbicacionBodyResponse> body = new ArrayList<ObtenerUbicacionBodyResponse>();
    ObtenerUbicacionBodyResponse bodyResp = new ObtenerUbicacionBodyResponse();
    ErrorType error = new ErrorType();
    error.setMessage("error");
    error.setName("erear");
    error.setStatusCode("0");
    bodyResp.setError(null);
    body.add(bodyResp);
    messageResponse.setBody(body);
    obtenerResponse.setMessageResponse(messageResponse);
    when(plumeCustomerClient.obtenerUbicacion(any(), any(), any(),
      any(), any())).thenReturn(obtenerResponse);

    ResponseAuditType responseAudit = obtenerLocacionesPlumeServiceImpl
      .obtenerLocaciones("Id Transaccion", headerRequest, obtenerLocacionesRequest)
      .getObtenerLocacionesResponse()
      .getResponseAudit();
    assertEquals("${obtener.locaciones.idf5.mensaje}", responseAudit.getMensajeRespuesta());
    assertEquals("Id Transaccion", responseAudit.getIdTransaccion());
    assertEquals("${obtener.locaciones.idf5.codigo}", responseAudit.getCodigoRespuesta());
    verify(sgaDao).datosCliente(any(), any());
  }

  @Test
  public void testArmarResponse() {
    ResponseAuditType responseAuditType = new ResponseAuditType();
    responseAuditType.setCodigoRespuesta("Codigo Respuesta");
    responseAuditType.setIdTransaccion("Id Transaccion");
    responseAuditType.setMensajeRespuesta("Mensaje Respuesta");

    ObtenerLocacionesResponseDataType obtenerLocacionesResponseDataType = new ObtenerLocacionesResponseDataType();
    obtenerLocacionesResponseDataType.setAccountId("42");
    obtenerLocacionesResponseDataType.setIdCustPlume("Id Cust Plume");
    obtenerLocacionesResponseDataType.setListaOpcional(new ArrayList<>());
    obtenerLocacionesResponseDataType.setLocaciones(new ArrayList<>());

    ObtenerLocacionesResponseType obtenerLocacionesResponseType = new ObtenerLocacionesResponseType();
    obtenerLocacionesResponseType.setResponseAudit(responseAuditType);
    obtenerLocacionesResponseType.setResponseData(obtenerLocacionesResponseDataType);

    ObtenerLocacionesResponse obtenerLocacionesResponse = new ObtenerLocacionesResponse();
    obtenerLocacionesResponse.setObtenerLocacionesResponse(obtenerLocacionesResponseType);

    ResponseAuditType responseAuditType1 = new ResponseAuditType();
    responseAuditType1.setCodigoRespuesta("Codigo Respuesta");
    responseAuditType1.setIdTransaccion("Id Transaccion");
    responseAuditType1.setMensajeRespuesta("Mensaje Respuesta");

    ObtenerLocacionesResponseDataType obtenerLocacionesResponseDataType1 = new ObtenerLocacionesResponseDataType();
    obtenerLocacionesResponseDataType1.setAccountId("42");
    obtenerLocacionesResponseDataType1.setIdCustPlume("Id Cust Plume");
    obtenerLocacionesResponseDataType1.setListaOpcional(new ArrayList<>());
    obtenerLocacionesResponseDataType1.setLocaciones(new ArrayList<>());

    ObtenerLocacionesResponseType obtenerLocacionesResponseType1 = new ObtenerLocacionesResponseType();
    obtenerLocacionesResponseType1.setResponseAudit(responseAuditType1);
    obtenerLocacionesResponseType1.setResponseData(obtenerLocacionesResponseDataType1);
    ArrayList<ListaLocacionesOLType> locaciones = new ArrayList<>();

    ResponseAuditType responseAuditType2 = new ResponseAuditType();
    responseAuditType2.setCodigoRespuesta("Codigo Respuesta");
    responseAuditType2.setIdTransaccion("Id Transaccion");
    responseAuditType2.setMensajeRespuesta("Mensaje Respuesta");

    ObtenerLocacionesResponseDataType obtenerLocacionesResponseDataType2 = new ObtenerLocacionesResponseDataType();
    obtenerLocacionesResponseDataType2.setAccountId("42");
    obtenerLocacionesResponseDataType2.setIdCustPlume("Id Cust Plume");
    obtenerLocacionesResponseDataType2.setListaOpcional(new ArrayList<>());
    obtenerLocacionesResponseDataType2.setLocaciones(new ArrayList<>());

    DatosClienteBeanRes datosClienteBeanRes = new DatosClienteBeanRes();
    datosClienteBeanRes.setAccountId("42");
    datosClienteBeanRes.setCodigoCliente("Codigo Cliente");
    datosClienteBeanRes.setCodigoClientePlm("Codigo Cliente Plm");
    datosClienteBeanRes.setCodigoRespuesta("Codigo Respuesta");
    datosClienteBeanRes.setCodigoSucursal("Codigo Sucursal");
    datosClienteBeanRes.setCursor(new ArrayList<>());
    datosClienteBeanRes.setEmail("jane.doe@example.org");
    datosClienteBeanRes.setMensajeRespuesta("Mensaje Respuesta");
    datosClienteBeanRes.setNombreSurcursal("Nombre Surcursal");
    datosClienteBeanRes.setNombres("Nombres");
    datosClienteBeanRes.setTipoTrabajo("Tipo Trabajo");
    datosClienteBeanRes.setValidaCuenta("Valida Cuenta");

    HeaderResponse headerResponse = new HeaderResponse();
    headerResponse.setConsumer("Consumer");
    headerResponse.setPid("Pid");
    headerResponse.setTimestamp("Timestamp");
    headerResponse.setVarArg("Var Arg");

    Header header = new Header();
    header.setHeaderResponse(headerResponse);

    List<ObtenerUbicacionBodyResponse> bodyListResp = new ArrayList<ObtenerUbicacionBodyResponse>();
    ObtenerUbicacionBodyResponse obtenerUbicacionBodyResponseType = new ObtenerUbicacionBodyResponse();
    obtenerUbicacionBodyResponseType.setAccountId("1234");
    bodyListResp.add(obtenerUbicacionBodyResponseType);

    ObtenerUbicacionMessageResponse obtenerUbicacionMessageResponse = new ObtenerUbicacionMessageResponse();
    obtenerUbicacionMessageResponse.setBody(bodyListResp);
    obtenerUbicacionMessageResponse.setHeader(header);

    ObtenerUbicacionResponse obtenerUbicacionResponse = new ObtenerUbicacionResponse();
    obtenerUbicacionResponse.setMessageResponse(obtenerUbicacionMessageResponse);
    obtenerLocacionesPlumeServiceImpl.armarResponse(obtenerLocacionesResponse, obtenerLocacionesResponseType1,
      locaciones, responseAuditType2, obtenerLocacionesResponseDataType2, datosClienteBeanRes,
      obtenerUbicacionResponse);
    ResponseAuditType responseAudit = obtenerLocacionesResponseType1.getResponseAudit();
    assertEquals("${idf0.mensaje}", responseAudit.getMensajeRespuesta());
    assertEquals("${idf0.codigo}", responseAudit.getCodigoRespuesta());
  }

  @Test
  public void testValidateInputEmpty() {
    ObtenerLocacionesRequest request = new ObtenerLocacionesRequest();
    ObtenerLocacionesRequestType requestType = new ObtenerLocacionesRequestType();
    requestType.setCodigoCliente("");
    requestType.setNumeroSot("");
    request.setObtenerLocacionesRequest(requestType);
    assertThrows(WSException.class,
      () -> obtenerLocacionesPlumeServiceImpl.validateInput(request));
  }

  @Test
  public void testValidateInputNotEmpty() {
    ObtenerLocacionesRequest request = new ObtenerLocacionesRequest();
    ObtenerLocacionesRequestType requestType = new ObtenerLocacionesRequestType();
    requestType.setCodigoCliente("");
    requestType.setNumeroSot("123");
    request.setObtenerLocacionesRequest(requestType);
    assertThrows(WSException.class,
      () -> obtenerLocacionesPlumeServiceImpl.validateInput(request));
  }
}

