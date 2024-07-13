package pe.com.claro.post.activaciones.plume.domain.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pe.com.claro.post.activaciones.plume.canonical.header.HeaderRequest;
import pe.com.claro.post.activaciones.plume.canonical.request.AprovisionarPlumeRequest;
import pe.com.claro.post.activaciones.plume.canonical.request.ObtenerEquiposRequest;
import pe.com.claro.post.activaciones.plume.canonical.request.ObtenerEquiposRequestType;
import pe.com.claro.post.activaciones.plume.canonical.response.ObtenerEquiposResponse;
import pe.com.claro.post.activaciones.plume.canonical.response.ObtenerEquiposResponseType;
import pe.com.claro.post.activaciones.plume.canonical.type.DefaultCEType;
import pe.com.claro.post.activaciones.plume.canonical.type.EthernetLanCEType;
import pe.com.claro.post.activaciones.plume.canonical.type.ListaEquiposOEType;
import pe.com.claro.post.activaciones.plume.canonical.type.ObtenerEquiposResponseDataType;
import pe.com.claro.post.activaciones.plume.canonical.type.ResponseAuditType;
import pe.com.claro.post.activaciones.plume.common.exceptions.BDException;
import pe.com.claro.post.activaciones.plume.common.exceptions.BaseException;
import pe.com.claro.post.activaciones.plume.common.exceptions.WSException;
import pe.com.claro.post.activaciones.plume.common.property.PropertiesExterno;
import pe.com.claro.post.activaciones.plume.integration.dao.SgaDao;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.request.DatosClienteBeanReq;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.response.DatosClienteBeanRes;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.response.EquipoRes;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.type.CursorValidaCuenta;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.type.POCursorType;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.utiltarios.UtilTokens;
import pe.com.claro.post.activaciones.plume.integration.ws.PlumeLocationClient;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.Header;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.HeaderResponse;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.ObtenerRepetidorBodyResponse;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.ObtenerRepetidorMessageResponse;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.ObtenerRepetidorResponse;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.DetailType;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.Fault;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.Node;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.type.Status;

@ContextConfiguration(classes = {ObtenerEquiposPlumeServiceImpl.class, PropertiesExterno.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class ObtenerEquiposPlumeServiceImplTest extends PowerMockito {
  @Autowired
  @InjectMocks
  private ObtenerEquiposPlumeServiceImpl obtenerEquiposPlumeServiceImpl;

  @MockBean
  private PlumeLocationClient plumeLocationClient;

  @MockBean
  private SgaDao sgaDao;

  @MockBean
  private UtilTokens UtilTokens;

  @Test
  public void testObtenerEquipos() throws BaseException, SQLException {
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

    ObtenerEquiposRequestType obtenerEquiposRequestType = new ObtenerEquiposRequestType();
    obtenerEquiposRequestType.setCodigoCliente("Codigo Cliente");
    obtenerEquiposRequestType.setListaOpcional(new ArrayList<>());
    obtenerEquiposRequestType.setNumeroSot("Numero Sot");

    ObtenerEquiposRequest obtenerEquiposRequest = new ObtenerEquiposRequest();
    obtenerEquiposRequest.setObtenerEquiposRequest(obtenerEquiposRequestType);

    ResponseAuditType responseAudit = obtenerEquiposPlumeServiceImpl
      .obtenerEquipos("Id Transaccion", headerRequest, obtenerEquiposRequest)
      .getObtenerEquiposResponse()
      .getResponseAudit();
    assertEquals("${obtener.equipos.idf4.mensaje}", responseAudit.getMensajeRespuesta());
    assertEquals("Id Transaccion", responseAudit.getIdTransaccion());
    assertEquals("${obtener.equipos.idf4.codigo}", responseAudit.getCodigoRespuesta());
    verify(sgaDao).datosCliente(any(), any());
  }

  @Test
  public void testObtenerEquiposUtil() throws BaseException, SQLException {
    EquipoRes equipoRes = new EquipoRes();
    equipoRes.setCodigoClientePlm("Codigo Cliente Plm");
    equipoRes.setCodigoRespuesta("0");
    equipoRes.setMensajeRespuesta("Mensaje Respuesta");
    List<POCursorType> poCursor = new ArrayList<POCursorType>();
    POCursorType cursorType = new POCursorType();
    cursorType.setMatvLocation("123");
    cursorType.setMatvNickName("123");
    cursorType.setMatvNroSerie("123");
    cursorType.setMatvRepetidor("test");
    poCursor.add(cursorType);
    equipoRes.setPoCursor(poCursor);
    when(sgaDao.listarEquipos(any(), any(), any())).thenReturn(equipoRes);
    DatosClienteBeanRes datosClienteBeanRes = mock(DatosClienteBeanRes.class);
    when(datosClienteBeanRes.getCodigoCliente()).thenReturn("Codigo Cliente");
    when(datosClienteBeanRes.getCodigoClientePlm()).thenReturn("Codigo Cliente Plm");
    when(datosClienteBeanRes.getCodigoSucursal()).thenReturn("[obtenerEquipos idTx=");

    CursorValidaCuenta cursorValidaCuenta = new CursorValidaCuenta();
    cursorValidaCuenta.setCodSuc("[obtenerEquipos idTx=");
    cursorValidaCuenta.setLocation("[obtenerEquipos idTx=");
    cursorValidaCuenta.setNickName("[obtenerEquipos idTx=");
    cursorValidaCuenta.setNroSerie("[obtenerEquipos idTx=");

    ArrayList<CursorValidaCuenta> cursorValidaCuentaList = new ArrayList<>();
    cursorValidaCuentaList.add(cursorValidaCuenta);
    when(datosClienteBeanRes.getCursor()).thenReturn(cursorValidaCuentaList);
    when(datosClienteBeanRes.getCodigoRespuesta()).thenReturn("Codigo Respuesta");
    doNothing().when(datosClienteBeanRes).setAccountId(any());
    doNothing().when(datosClienteBeanRes).setCodigoCliente(any());
    doNothing().when(datosClienteBeanRes).setCodigoClientePlm(any());
    doNothing().when(datosClienteBeanRes).setCodigoRespuesta(any());
    doNothing().when(datosClienteBeanRes).setCodigoSucursal(any());
    doNothing().when(datosClienteBeanRes).setCursor(any());
    doNothing().when(datosClienteBeanRes).setEmail(any());
    doNothing().when(datosClienteBeanRes).setMensajeRespuesta(any());
    doNothing().when(datosClienteBeanRes).setNombreSurcursal(any());
    doNothing().when(datosClienteBeanRes).setNombres(any());
    doNothing().when(datosClienteBeanRes).setTipoTrabajo(any());
    doNothing().when(datosClienteBeanRes).setValidaCuenta(any());
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
    when(headerRequest.getTimestamp()).thenReturn("Timestamp");
    when(headerRequest.getUserId()).thenReturn("42");
    doNothing().when(headerRequest).setAccept(any());
    doNothing().when(headerRequest).setCanal(any());
    doNothing().when(headerRequest).setIdTransaccion(any());
    doNothing().when(headerRequest).setMsgid(any());
    doNothing().when(headerRequest).setTimestamp(any());
    doNothing().when(headerRequest).setUserId(any());
    headerRequest.setAccept("Accept");
    headerRequest.setCanal("Canal");
    headerRequest.setIdTransaccion("Id Transaccion");
    headerRequest.setMsgid("Msgid");
    headerRequest.setTimestamp("Timestamp");
    headerRequest.setUserId("42");

    ObtenerEquiposRequestType obtenerEquiposRequestType = new ObtenerEquiposRequestType();
    obtenerEquiposRequestType.setCodigoCliente("Codigo Cliente");
    obtenerEquiposRequestType.setListaOpcional(new ArrayList<>());
    obtenerEquiposRequestType.setNumeroSot("Numero Sot");

    ObtenerEquiposRequest obtenerEquiposRequest = new ObtenerEquiposRequest();
    obtenerEquiposRequest.setObtenerEquiposRequest(obtenerEquiposRequestType);

    when(UtilTokens.subprocesoTokens(any(), any(), any()
      , any(), any(), any()
      , any())).thenReturn("eyJraWQiOiJfTWpXMDF6dnFvZjZuVpzWG5zIiwiYWxnIjoiUlMyNTYifQ.eyJ2ZXIFFP2QKtvrh3XKnSjw\n" +
      "3a5d987d-6b9e-449a-83f3-e2ca718df557");

    ObtenerRepetidorResponse obtenerRepRes = new ObtenerRepetidorResponse();
    ObtenerRepetidorMessageResponse messageResponse = new ObtenerRepetidorMessageResponse();
    Header header = new Header();
    HeaderResponse headerResponse = new HeaderResponse();
    Status status = new Status();
    status.setCode("0");
    headerResponse.setStatus(status);
    header.setHeaderResponse(headerResponse);
    messageResponse.setHeader(header);
    ObtenerRepetidorBodyResponse body = new ObtenerRepetidorBodyResponse();
    List<Node> nodes = new ArrayList<Node>();
    Node node = new Node();
    node.setId("123");
    node.setSerialNumber("1234566");
    node.setModel("test");
    node.setMac("12345");
    node.setConnectionState("on");
    nodes.add(node);
    body.setNodes(nodes);
    messageResponse.setBody(body);
    obtenerRepRes.setMessageResponse(messageResponse);

    when(plumeLocationClient.obtenerRepetidor(any(), any(), any(), any(), any(), any())).thenReturn(obtenerRepRes);

    ResponseAuditType responseAudit = obtenerEquiposPlumeServiceImpl
      .obtenerEquipos("Id Transaccion", headerRequest, obtenerEquiposRequest)
      .getObtenerEquiposResponse()
      .getResponseAudit();
    assertEquals("${idf0.mensaje}", responseAudit.getMensajeRespuesta());
    assertEquals("Id Transaccion", responseAudit.getIdTransaccion());
    assertEquals("${idf0.codigo}", responseAudit.getCodigoRespuesta());
    verify(sgaDao).datosCliente(any(), any());
    verify(sgaDao).listarEquipos(any(), any(), any());
    verify(datosClienteBeanRes).getCodigoCliente();
    verify(datosClienteBeanRes).getCodigoClientePlm();
    verify(datosClienteBeanRes).getCodigoRespuesta();
    verify(datosClienteBeanRes, atLeast(1)).getCodigoSucursal();
    verify(datosClienteBeanRes).getCursor();
    verify(datosClienteBeanRes).setAccountId(any());
    verify(datosClienteBeanRes).setCodigoCliente(any());
    verify(datosClienteBeanRes).setCodigoClientePlm(any());
    verify(datosClienteBeanRes).setCodigoRespuesta(any());
    verify(datosClienteBeanRes).setCodigoSucursal(any());
    verify(datosClienteBeanRes).setCursor(any());
    verify(datosClienteBeanRes).setEmail(any());
    verify(datosClienteBeanRes).setMensajeRespuesta(any());
    verify(datosClienteBeanRes).setNombreSurcursal(any());
    verify(datosClienteBeanRes).setNombres(any());
    verify(datosClienteBeanRes).setTipoTrabajo(any());
    verify(datosClienteBeanRes).setValidaCuenta(any());
    verify(headerRequest).getAccept();
    verify(headerRequest).getCanal();
    verify(headerRequest, atLeast(1)).getIdTransaccion();
    verify(headerRequest).getMsgid();
    verify(headerRequest).getTimestamp();
    verify(headerRequest).getUserId();
    verify(headerRequest).setAccept(any());
    verify(headerRequest).setCanal(any());
    verify(headerRequest).setIdTransaccion(any());
    verify(headerRequest).setMsgid(any());
    verify(headerRequest).setTimestamp(any());
    verify(headerRequest).setUserId(any());
  }

  @Test
  public void testObtenerEquipos2() throws BDException {
    DatosClienteBeanRes datosClienteBeanRes = mock(DatosClienteBeanRes.class);
    when(datosClienteBeanRes.getCodigoSucursal()).thenReturn("Codigo Sucursal");

    CursorValidaCuenta cursorValidaCuenta = new CursorValidaCuenta();
    cursorValidaCuenta.setCodSuc("[obtenerEquipos idTx=");
    cursorValidaCuenta.setLocation("[obtenerEquipos idTx=");
    cursorValidaCuenta.setNickName("[obtenerEquipos idTx=");
    cursorValidaCuenta.setNroSerie("[obtenerEquipos idTx=");

    ArrayList<CursorValidaCuenta> cursorValidaCuentaList = new ArrayList<>();
    cursorValidaCuentaList.add(cursorValidaCuenta);
    when(datosClienteBeanRes.getCursor()).thenReturn(cursorValidaCuentaList);
    when(datosClienteBeanRes.getCodigoRespuesta()).thenReturn("Codigo Respuesta");
    doNothing().when(datosClienteBeanRes).setAccountId(any());
    doNothing().when(datosClienteBeanRes).setCodigoCliente(any());
    doNothing().when(datosClienteBeanRes).setCodigoClientePlm(any());
    doNothing().when(datosClienteBeanRes).setCodigoRespuesta(any());
    doNothing().when(datosClienteBeanRes).setCodigoSucursal(any());
    doNothing().when(datosClienteBeanRes).setCursor(any());
    doNothing().when(datosClienteBeanRes).setEmail(any());
    doNothing().when(datosClienteBeanRes).setMensajeRespuesta(any());
    doNothing().when(datosClienteBeanRes).setNombreSurcursal(any());
    doNothing().when(datosClienteBeanRes).setNombres(any());
    doNothing().when(datosClienteBeanRes).setTipoTrabajo(any());
    doNothing().when(datosClienteBeanRes).setValidaCuenta(any());
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
    when(headerRequest.getTimestamp()).thenReturn("Timestamp");
    when(headerRequest.getUserId()).thenReturn("42");
    doNothing().when(headerRequest).setAccept(any());
    doNothing().when(headerRequest).setCanal(any());
    doNothing().when(headerRequest).setIdTransaccion(any());
    doNothing().when(headerRequest).setMsgid(any());
    doNothing().when(headerRequest).setTimestamp(any());
    doNothing().when(headerRequest).setUserId(any());
    headerRequest.setAccept("Accept");
    headerRequest.setCanal("Canal");
    headerRequest.setIdTransaccion("Id Transaccion");
    headerRequest.setMsgid("Msgid");
    headerRequest.setTimestamp("Timestamp");
    headerRequest.setUserId("42");

    ObtenerEquiposRequestType obtenerEquiposRequestType = new ObtenerEquiposRequestType();
    obtenerEquiposRequestType.setCodigoCliente("Codigo Cliente");
    obtenerEquiposRequestType.setListaOpcional(new ArrayList<>());
    obtenerEquiposRequestType.setNumeroSot("Numero Sot");

    ObtenerEquiposRequest obtenerEquiposRequest = new ObtenerEquiposRequest();
    obtenerEquiposRequest.setObtenerEquiposRequest(obtenerEquiposRequestType);
    ResponseAuditType responseAudit = obtenerEquiposPlumeServiceImpl
      .obtenerEquipos("Id Transaccion", headerRequest, obtenerEquiposRequest)
      .getObtenerEquiposResponse()
      .getResponseAudit();
    assertEquals("${obtener.equipos.idf4.mensaje}", responseAudit.getMensajeRespuesta());
    assertEquals("Id Transaccion", responseAudit.getIdTransaccion());
    assertEquals("${obtener.equipos.idf4.codigo}", responseAudit.getCodigoRespuesta());
    verify(sgaDao).datosCliente(any(), any());
    verify(datosClienteBeanRes).getCodigoRespuesta();
    verify(datosClienteBeanRes).getCodigoSucursal();
    verify(datosClienteBeanRes).getCursor();
    verify(datosClienteBeanRes).setAccountId(any());
    verify(datosClienteBeanRes).setCodigoCliente(any());
    verify(datosClienteBeanRes).setCodigoClientePlm(any());
    verify(datosClienteBeanRes).setCodigoRespuesta(any());
    verify(datosClienteBeanRes).setCodigoSucursal(any());
    verify(datosClienteBeanRes).setCursor(any());
    verify(datosClienteBeanRes).setEmail(any());
    verify(datosClienteBeanRes).setMensajeRespuesta(any());
    verify(datosClienteBeanRes).setNombreSurcursal(any());
    verify(datosClienteBeanRes).setNombres(any());
    verify(datosClienteBeanRes).setTipoTrabajo(any());
    verify(datosClienteBeanRes).setValidaCuenta(any());
    verify(headerRequest).getAccept();
    verify(headerRequest).getCanal();
    verify(headerRequest, atLeast(1)).getIdTransaccion();
    verify(headerRequest).getMsgid();
    verify(headerRequest).getTimestamp();
    verify(headerRequest).getUserId();
    verify(headerRequest).setAccept(any());
    verify(headerRequest).setCanal(any());
    verify(headerRequest).setIdTransaccion(any());
    verify(headerRequest).setMsgid(any());
    verify(headerRequest).setTimestamp(any());
    verify(headerRequest).setUserId(any());
  }

  @Test
  public void testObtenerEquipos3() throws BDException {
    EquipoRes equipoRes = new EquipoRes();
    equipoRes.setCodigoClientePlm("Codigo Cliente Plm");
    equipoRes.setCodigoRespuesta("Codigo Respuesta");
    equipoRes.setMensajeRespuesta("Mensaje Respuesta");
    equipoRes.setPoCursor(new ArrayList<>());
    when(sgaDao.listarEquipos(any(), any(), any())).thenReturn(equipoRes);
    DatosClienteBeanRes datosClienteBeanRes = mock(DatosClienteBeanRes.class);
    when(datosClienteBeanRes.getCodigoCliente()).thenReturn("Codigo Cliente");
    when(datosClienteBeanRes.getCodigoClientePlm()).thenReturn("Codigo Cliente Plm");
    when(datosClienteBeanRes.getCodigoSucursal()).thenReturn("[obtenerEquipos idTx=");

    CursorValidaCuenta cursorValidaCuenta = new CursorValidaCuenta();
    cursorValidaCuenta.setCodSuc("[obtenerEquipos idTx=");
    cursorValidaCuenta.setLocation("[obtenerEquipos idTx=");
    cursorValidaCuenta.setNickName("[obtenerEquipos idTx=");
    cursorValidaCuenta.setNroSerie("[obtenerEquipos idTx=");

    ArrayList<CursorValidaCuenta> cursorValidaCuentaList = new ArrayList<>();
    cursorValidaCuentaList.add(cursorValidaCuenta);
    when(datosClienteBeanRes.getCursor()).thenReturn(cursorValidaCuentaList);
    when(datosClienteBeanRes.getCodigoRespuesta()).thenReturn("Codigo Respuesta");
    doNothing().when(datosClienteBeanRes).setAccountId(any());
    doNothing().when(datosClienteBeanRes).setCodigoCliente(any());
    doNothing().when(datosClienteBeanRes).setCodigoClientePlm(any());
    doNothing().when(datosClienteBeanRes).setCodigoRespuesta(any());
    doNothing().when(datosClienteBeanRes).setCodigoSucursal(any());
    doNothing().when(datosClienteBeanRes).setCursor(any());
    doNothing().when(datosClienteBeanRes).setEmail(any());
    doNothing().when(datosClienteBeanRes).setMensajeRespuesta(any());
    doNothing().when(datosClienteBeanRes).setNombreSurcursal(any());
    doNothing().when(datosClienteBeanRes).setNombres(any());
    doNothing().when(datosClienteBeanRes).setTipoTrabajo(any());
    doNothing().when(datosClienteBeanRes).setValidaCuenta(any());
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
    when(headerRequest.getTimestamp()).thenReturn("Timestamp");
    when(headerRequest.getUserId()).thenReturn("42");
    doNothing().when(headerRequest).setAccept(any());
    doNothing().when(headerRequest).setCanal(any());
    doNothing().when(headerRequest).setIdTransaccion(any());
    doNothing().when(headerRequest).setMsgid(any());
    doNothing().when(headerRequest).setTimestamp(any());
    doNothing().when(headerRequest).setUserId(any());
    headerRequest.setAccept("Accept");
    headerRequest.setCanal("Canal");
    headerRequest.setIdTransaccion("Id Transaccion");
    headerRequest.setMsgid("Msgid");
    headerRequest.setTimestamp("Timestamp");
    headerRequest.setUserId("42");

    ObtenerEquiposRequestType obtenerEquiposRequestType = new ObtenerEquiposRequestType();
    obtenerEquiposRequestType.setCodigoCliente("Codigo Cliente");
    obtenerEquiposRequestType.setListaOpcional(new ArrayList<>());
    obtenerEquiposRequestType.setNumeroSot("Numero Sot");

    ObtenerEquiposRequest obtenerEquiposRequest = new ObtenerEquiposRequest();
    obtenerEquiposRequest.setObtenerEquiposRequest(obtenerEquiposRequestType);
    ResponseAuditType responseAudit = obtenerEquiposPlumeServiceImpl
      .obtenerEquipos("Id Transaccion", headerRequest, obtenerEquiposRequest)
      .getObtenerEquiposResponse()
      .getResponseAudit();
    assertEquals("${obtener.equipos.idf5.mensaje}", responseAudit.getMensajeRespuesta());
    assertEquals("Id Transaccion", responseAudit.getIdTransaccion());
    assertEquals("${obtener.equipos.idf5.codigo}", responseAudit.getCodigoRespuesta());
    verify(sgaDao).datosCliente(any(), any());
    verify(sgaDao).listarEquipos(any(), any(), any());
    verify(datosClienteBeanRes).getCodigoCliente();
    verify(datosClienteBeanRes).getCodigoClientePlm();
    verify(datosClienteBeanRes).getCodigoRespuesta();
    verify(datosClienteBeanRes, atLeast(1)).getCodigoSucursal();
    verify(datosClienteBeanRes).getCursor();
    verify(datosClienteBeanRes).setAccountId(any());
    verify(datosClienteBeanRes).setCodigoCliente(any());
    verify(datosClienteBeanRes).setCodigoClientePlm(any());
    verify(datosClienteBeanRes).setCodigoRespuesta(any());
    verify(datosClienteBeanRes).setCodigoSucursal(any());
    verify(datosClienteBeanRes).setCursor(any());
    verify(datosClienteBeanRes).setEmail(any());
    verify(datosClienteBeanRes).setMensajeRespuesta(any());
    verify(datosClienteBeanRes).setNombreSurcursal(any());
    verify(datosClienteBeanRes).setNombres(any());
    verify(datosClienteBeanRes).setTipoTrabajo(any());
    verify(datosClienteBeanRes).setValidaCuenta(any());
    verify(headerRequest).getAccept();
    verify(headerRequest).getCanal();
    verify(headerRequest, atLeast(1)).getIdTransaccion();
    verify(headerRequest).getMsgid();
    verify(headerRequest).getTimestamp();
    verify(headerRequest).getUserId();
    verify(headerRequest).setAccept(any());
    verify(headerRequest).setCanal(any());
    verify(headerRequest).setIdTransaccion(any());
    verify(headerRequest).setMsgid(any());
    verify(headerRequest).setTimestamp(any());
    verify(headerRequest).setUserId(any());
  }

  @Test
  public void testGetListaEquiposOEType4() {
    DefaultCEType defaultCEType = new DefaultCEType();
    defaultCEType.setMode("Mode");

    EthernetLanCEType ethernetLanCEType = new EthernetLanCEType();
    ethernetLanCEType.setDefaultCE(defaultCEType);

    Node node = new Node();
    node.setAlerts(new ArrayList<>());
    node.setBackhaulType("Backhaul Type");
    node.setClaimedAt("Claimed At");
    node.setConnectedDeviceCount(3);
    node.setConnectionState("Connection State");
    node.setDefaultName("Default Name");
    node.setEthernetLan(ethernetLanCEType);
    node.setFirmwareVersion("1.0.2");
    node.setHealth("Health");
    node.setHybrid(true);
    node.setId("Matv Nro Serie");
    node.setLeafToRoot(new ArrayList<>());
    node.setMac("Mac");
    node.setModel("Model");
    node.setNickname("Nickname");
    node.setPackId("42");
    node.setPartNumber("42");
    node.setPuncturedChannels(new ArrayList<>());
    node.setRadioMac24("Radio Mac24");
    node.setRadioMac50("Radio Mac50");
    node.setResidentialGateway(true);
    node.setSerialNumber("42");
    node.setShipDate("2020-03-01");
    node.setSubscriptionRequired(true);

    ArrayList<Node> nodeList = new ArrayList<>();
    nodeList.add(node);

    POCursorType poCursorType = new POCursorType();
    poCursorType.setMatvLocation("Matv Location");
    poCursorType.setMatvNickName("Matv Nick Name");
    poCursorType.setMatvNroSerie("Matv Nro Serie");
    poCursorType.setMatvRepetidor("Bella");

    ArrayList<POCursorType> poCursorTypeList = new ArrayList<>();
    poCursorTypeList.add(poCursorType);
    ArrayList<ListaEquiposOEType> listaEquiposOETypeList = new ArrayList<>();
    ListaEquiposOEType actualListaEquiposOEType = obtenerEquiposPlumeServiceImpl.getListaEquiposOEType(nodeList,
      poCursorTypeList, listaEquiposOETypeList);
    assertEquals("Connection State", actualListaEquiposOEType.getEstadoConexion());
    assertEquals("42", actualListaEquiposOEType.getNumeroSerie());
    assertEquals("Model", actualListaEquiposOEType.getModelo());
    assertEquals("Mac", actualListaEquiposOEType.getMac());
    assertEquals("Matv Nro Serie", actualListaEquiposOEType.getId());
    assertEquals(1, listaEquiposOETypeList.size());
  }

  @Test
  public void testValidarObtenerRepetidorResponse() throws WSException {

    Fault fault = new Fault();
    fault.setDetail(new DetailType());
    fault.setFaultactor("Faultactor");
    fault.setFaultcode("Faultcode");
    fault.setFaultstring("Faultstring");

    ObtenerRepetidorBodyResponse obtenerRepetidorBodyResponse = new ObtenerRepetidorBodyResponse();
    obtenerRepetidorBodyResponse.setFault(fault);
    obtenerRepetidorBodyResponse.setNodes(null);

    Status status = new Status();
    status.setCode("0");
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

    ObtenerRepetidorMessageResponse obtenerRepetidorMessageResponse = new ObtenerRepetidorMessageResponse();
    obtenerRepetidorMessageResponse.setBody(obtenerRepetidorBodyResponse);
    obtenerRepetidorMessageResponse.setHeader(header);

    ObtenerRepetidorResponse obtenerRepetidorResponse = new ObtenerRepetidorResponse();
    obtenerRepetidorResponse.setMessageResponse(obtenerRepetidorMessageResponse);
    assertThrows(WSException.class,
      () -> obtenerEquiposPlumeServiceImpl.validarObtenerRepetidorResponse(obtenerRepetidorResponse));
  }

  @Test
  public void testValidarObtenerRepetidorResponse2() throws WSException {
    Fault fault = new Fault();
    fault.setDetail(new DetailType());
    fault.setFaultactor("Faultactor");
    fault.setFaultcode("Faultcode");
    fault.setFaultstring("Faultstring");

    ObtenerRepetidorBodyResponse obtenerRepetidorBodyResponse = new ObtenerRepetidorBodyResponse();
    obtenerRepetidorBodyResponse.setFault(fault);
    obtenerRepetidorBodyResponse.setNodes(new ArrayList<>());

    HeaderResponse headerResponse = new HeaderResponse();
    headerResponse.setConsumer("Consumer");
    headerResponse.setPid("Pid");
    headerResponse.setStatus(new Status());
    headerResponse.setTimestamp("Timestamp");
    headerResponse.setVarArg("Var Arg");

    Header header = new Header();
    header.setHeaderResponse(headerResponse);

    ObtenerRepetidorMessageResponse obtenerRepetidorMessageResponse = new ObtenerRepetidorMessageResponse();
    obtenerRepetidorMessageResponse.setBody(obtenerRepetidorBodyResponse);
    obtenerRepetidorMessageResponse.setHeader(header);

    DetailType detailType = new DetailType();
    detailType.setIntegrationError("An error occurred");

    Fault fault1 = new Fault();
    fault1.setDetail(detailType);
    fault1.setFaultactor("Faultactor");
    fault1.setFaultcode("Faultcode");
    fault1.setFaultstring("Faultstring");

    ObtenerRepetidorBodyResponse obtenerRepetidorBodyResponse1 = new ObtenerRepetidorBodyResponse();
    obtenerRepetidorBodyResponse1.setFault(fault1);
    obtenerRepetidorBodyResponse1.setNodes(new ArrayList<>());

    Status status = new Status();
    status.setCode("Code");
    status.setMessage("Not all who wander are lost");
    status.setMsgid("Msgid");
    status.setType("Type");

    HeaderResponse headerResponse1 = new HeaderResponse();
    headerResponse1.setConsumer("Consumer");
    headerResponse1.setPid("Pid");
    headerResponse1.setStatus(status);
    headerResponse1.setTimestamp("Timestamp");
    headerResponse1.setVarArg("Var Arg");

    Header header1 = new Header();
    header1.setHeaderResponse(headerResponse1);

    ObtenerRepetidorMessageResponse obtenerRepetidorMessageResponse1 = new ObtenerRepetidorMessageResponse();
    obtenerRepetidorMessageResponse1.setBody(obtenerRepetidorBodyResponse1);
    obtenerRepetidorMessageResponse1.setHeader(header1);
    ObtenerRepetidorResponse obtenerRepetidorResponse = mock(ObtenerRepetidorResponse.class);
    when(obtenerRepetidorResponse.getMessageResponse()).thenReturn(obtenerRepetidorMessageResponse1);
    doNothing().when(obtenerRepetidorResponse).setMessageResponse(any());
    obtenerRepetidorResponse.setMessageResponse(obtenerRepetidorMessageResponse);
    assertThrows(WSException.class,
      () -> obtenerEquiposPlumeServiceImpl.validarObtenerRepetidorResponse(obtenerRepetidorResponse));
    verify(obtenerRepetidorResponse, atLeast(1)).getMessageResponse();
    verify(obtenerRepetidorResponse).setMessageResponse(any());
  }

  @Test
  public void testArmarResponse2() {
    ResponseAuditType responseAuditType = new ResponseAuditType();
    responseAuditType.setCodigoRespuesta("Codigo Respuesta");
    responseAuditType.setIdTransaccion("Id Transaccion");
    responseAuditType.setMensajeRespuesta("Mensaje Respuesta");

    ObtenerEquiposResponseDataType obtenerEquiposResponseDataType = new ObtenerEquiposResponseDataType();
    obtenerEquiposResponseDataType.setAccountId("42");
    obtenerEquiposResponseDataType.setCodigoSucursal("Codigo Sucursal");
    obtenerEquiposResponseDataType.setEquipos(new ArrayList<>());
    obtenerEquiposResponseDataType.setListaOpcional(new ArrayList<>());
    obtenerEquiposResponseDataType.setNombreSucursal("Nombre Sucursal");

    ObtenerEquiposResponseType obtenerEquiposResponseType = new ObtenerEquiposResponseType();
    obtenerEquiposResponseType.setResponseAudit(responseAuditType);
    obtenerEquiposResponseType.setResponseData(obtenerEquiposResponseDataType);
    ObtenerEquiposResponse obtenerEquiposResponse = mock(ObtenerEquiposResponse.class);
    doNothing().when(obtenerEquiposResponse)
      .setObtenerEquiposResponse(Mockito.any());
    obtenerEquiposResponse.setObtenerEquiposResponse(obtenerEquiposResponseType);

    ResponseAuditType responseAuditType1 = new ResponseAuditType();
    responseAuditType1.setCodigoRespuesta("Codigo Respuesta");
    responseAuditType1.setIdTransaccion("Id Transaccion");
    responseAuditType1.setMensajeRespuesta("Mensaje Respuesta");

    ObtenerEquiposResponseDataType obtenerEquiposResponseDataType1 = new ObtenerEquiposResponseDataType();
    obtenerEquiposResponseDataType1.setAccountId("42");
    obtenerEquiposResponseDataType1.setCodigoSucursal("Codigo Sucursal");
    obtenerEquiposResponseDataType1.setEquipos(new ArrayList<>());
    obtenerEquiposResponseDataType1.setListaOpcional(new ArrayList<>());
    obtenerEquiposResponseDataType1.setNombreSucursal("Nombre Sucursal");

    ObtenerEquiposResponseType obtenerEquiposResponseType1 = new ObtenerEquiposResponseType();
    obtenerEquiposResponseType1.setResponseAudit(responseAuditType1);
    obtenerEquiposResponseType1.setResponseData(obtenerEquiposResponseDataType1);

    ResponseAuditType responseAuditType2 = new ResponseAuditType();
    responseAuditType2.setCodigoRespuesta("Codigo Respuesta");
    responseAuditType2.setIdTransaccion("Id Transaccion");
    responseAuditType2.setMensajeRespuesta("Mensaje Respuesta");

    ObtenerEquiposResponseDataType obtenerEquiposResponseDataType2 = new ObtenerEquiposResponseDataType();
    obtenerEquiposResponseDataType2.setAccountId("42");
    obtenerEquiposResponseDataType2.setCodigoSucursal("Codigo Sucursal");
    obtenerEquiposResponseDataType2.setEquipos(new ArrayList<>());
    obtenerEquiposResponseDataType2.setListaOpcional(new ArrayList<>());
    obtenerEquiposResponseDataType2.setNombreSucursal("Nombre Sucursal");

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
    obtenerEquiposPlumeServiceImpl.armarResponse(obtenerEquiposResponse, obtenerEquiposResponseType1,
      responseAuditType2, obtenerEquiposResponseDataType2, datosClienteBeanRes, new ArrayList<>());
    verify(obtenerEquiposResponse, atLeast(1))
      .setObtenerEquiposResponse(Mockito.any());
    ResponseAuditType responseAudit = obtenerEquiposResponseType1.getResponseAudit();
    assertSame(responseAuditType2, responseAudit);
    ObtenerEquiposResponseDataType responseData = obtenerEquiposResponseType1.getResponseData();
    assertSame(obtenerEquiposResponseDataType2, responseData);
    assertEquals("${idf0.mensaje}", responseAudit.getMensajeRespuesta());
    assertEquals("Nombre Surcursal", responseData.getNombreSucursal());
    assertTrue(responseData.getEquipos().isEmpty());
    assertEquals("Codigo Sucursal", responseData.getCodigoSucursal());
    assertEquals("42", responseData.getAccountId());
    assertEquals("${idf0.codigo}", responseAudit.getCodigoRespuesta());
  }

  @Test
  public void testObtenerDatosSGA() throws BaseException {
    DatosClienteBeanRes datosClienteBeanRes = new DatosClienteBeanRes();
    datosClienteBeanRes.setAccountId("42");
    datosClienteBeanRes.setCodigoCliente("123456");
    datosClienteBeanRes.setCodigoClientePlm("123456");
    datosClienteBeanRes.setCodigoRespuesta("100");
    datosClienteBeanRes.setCodigoSucursal("Codigo Sucursal");
    datosClienteBeanRes.setCursor(null);
    datosClienteBeanRes.setEmail("jane.doe@example.org");
    datosClienteBeanRes.setMensajeRespuesta("Mensaje Respuesta");
    datosClienteBeanRes.setNombreSurcursal("Nombre Surcursal");
    datosClienteBeanRes.setNombres("Nombres");
    datosClienteBeanRes.setTipoTrabajo("Tipo Trabajo");
    datosClienteBeanRes.setValidaCuenta("Valida Cuenta");
    when(sgaDao.datosCliente(any(), any())).thenReturn(datosClienteBeanRes);


    ObtenerEquiposRequestType obtenerEquiposRequestType = new ObtenerEquiposRequestType();
    obtenerEquiposRequestType.setCodigoCliente("123");
    obtenerEquiposRequestType.setListaOpcional(null);
    obtenerEquiposRequestType.setNumeroSot("123");

    ObtenerEquiposRequest obtenerEquiposRequest = new ObtenerEquiposRequest();
    obtenerEquiposRequest.setObtenerEquiposRequest(obtenerEquiposRequestType);
    assertThrows(BDException.class,
      () -> obtenerEquiposPlumeServiceImpl.obtenerDatosSGA("123456", obtenerEquiposRequest, "Mensaje Trx"));
    verify(sgaDao).datosCliente(any(), any());
  }
}
