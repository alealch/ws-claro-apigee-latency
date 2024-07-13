package pe.com.claro.post.activaciones.plume.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.com.claro.post.activaciones.plume.canonical.header.HeaderRequest;
import pe.com.claro.post.activaciones.plume.canonical.request.AprovisionarPlumeRequest;
import pe.com.claro.post.activaciones.plume.canonical.request.ConsultarEquipoRequest;
import pe.com.claro.post.activaciones.plume.canonical.request.ObtenerEquiposRequest;
import pe.com.claro.post.activaciones.plume.canonical.request.ObtenerLocacionesRequest;
import pe.com.claro.post.activaciones.plume.canonical.response.AprovisionarPlumeResponse;
import pe.com.claro.post.activaciones.plume.canonical.response.ConsultarEquipoResponse;
import pe.com.claro.post.activaciones.plume.canonical.response.ObtenerEquiposResponse;
import pe.com.claro.post.activaciones.plume.canonical.response.ObtenerLocacionesResponse;
import pe.com.claro.post.activaciones.plume.common.constants.Constantes;
import pe.com.claro.post.activaciones.plume.common.property.PropertiesExterno;
import pe.com.claro.post.activaciones.plume.domain.service.AprovisionarPlumeService;
import pe.com.claro.post.activaciones.plume.domain.service.ConsultarEquipoPlumeService;
import pe.com.claro.post.activaciones.plume.domain.service.ObtenerEquiposPlumeService;
import pe.com.claro.post.activaciones.plume.domain.service.ObtenerLocacionesPlumeService;

@RestController
@RequestMapping(Constantes.BASEPATH_CONTROLLER)
public class ActivacionesPlumeController {

  @Autowired
  private AprovisionarPlumeService aprovisionarPlumeService;
  @Autowired
  private ObtenerLocacionesPlumeService obtenerLocacionesService;
  @Autowired
  private ObtenerEquiposPlumeService obtenerEquiposService;
  @Autowired
  private ConsultarEquipoPlumeService consultarEquipoPlumeService;
  @Autowired
  private PropertiesExterno prop;

  @PostMapping(value = Constantes.METODO_UNO, consumes = Constantes.APPLICATION_JSON, produces = Constantes.APPLICATION_JSON)
  public ResponseEntity<AprovisionarPlumeResponse> aprovisionarPlume(
    @RequestHeader(name = "idTransaccion", required = true) String idTransaccion,
    @RequestHeader(name = "msgid", required = true) String msgid,
    @RequestHeader(name = "timestamp", required = true) String timestamp,
    @RequestHeader(name = "userId", required = true) String userId,
    @RequestHeader(name = "canal", required = true) String canal,
    @RequestHeader(name = "accept", required = true) String accept, @RequestBody AprovisionarPlumeRequest request) {

    HeaderRequest headerRequest = new HeaderRequest();
    headerRequest.setIdTransaccion(idTransaccion);
    headerRequest.setUserId(userId);
    headerRequest.setMsgid(msgid);
    headerRequest.setCanal(canal);
    headerRequest.setTimestamp(timestamp);
    headerRequest.setAccept(accept);
    return new ResponseEntity<AprovisionarPlumeResponse>(
      aprovisionarPlumeService.aprovisionarPlume(idTransaccion, headerRequest, request, prop), HttpStatus.OK);
  }

  @PostMapping(value = Constantes.METODO_DOS, consumes = Constantes.APPLICATION_JSON, produces = Constantes.APPLICATION_JSON)
  public ResponseEntity<ConsultarEquipoResponse> consultarEquipo(
    @RequestHeader(name = "idTransaccion", required = true) String idTransaccion,
    @RequestHeader(name = "msgid", required = true) String msgid,
    @RequestHeader(name = "timestamp", required = true) String timestamp,
    @RequestHeader(name = "userId", required = true) String userId,
    @RequestHeader(name = "canal", required = true) String canal,
    @RequestHeader(name = "accept", required = true) String accept, @RequestBody ConsultarEquipoRequest request) {

    HeaderRequest headerRequest = new HeaderRequest();
    headerRequest.setIdTransaccion(idTransaccion);
    headerRequest.setUserId(userId);
    headerRequest.setMsgid(msgid);
    headerRequest.setCanal(canal);
    headerRequest.setTimestamp(timestamp);
    headerRequest.setAccept(accept);
    return new ResponseEntity<ConsultarEquipoResponse>(
      consultarEquipoPlumeService.consultarEquipo(idTransaccion, headerRequest, request, prop), HttpStatus.OK);
  }

  @PostMapping(value = Constantes.METODO_TRES, consumes = Constantes.APPLICATION_JSON, produces = Constantes.APPLICATION_JSON)
  public ResponseEntity<ObtenerLocacionesResponse> obtenerLocaciones(
    @RequestHeader(name = "idTransaccion", required = true) String idTransaccion,
    @RequestHeader(name = "msgid", required = true) String msgid,
    @RequestHeader(name = "timestamp", required = true) String timestamp,
    @RequestHeader(name = "userId", required = true) String userId,
    @RequestHeader(name = "canal", required = true) String canal,
    @RequestHeader(name = "accept", required = true) String accept, @RequestBody ObtenerLocacionesRequest request) {

    HeaderRequest headerRequest = new HeaderRequest();
    headerRequest.setIdTransaccion(idTransaccion);
    headerRequest.setUserId(userId);
    headerRequest.setMsgid(msgid);
    headerRequest.setCanal(canal);
    headerRequest.setTimestamp(timestamp);
    headerRequest.setAccept(accept);
    return new ResponseEntity<ObtenerLocacionesResponse>(
      obtenerLocacionesService.obtenerLocaciones(idTransaccion, headerRequest, request), HttpStatus.OK);
  }

  @PostMapping(value = Constantes.METODO_CUATRO, consumes = Constantes.APPLICATION_JSON, produces = Constantes.APPLICATION_JSON)
  public ResponseEntity<ObtenerEquiposResponse> obtenerEquipos(
    @RequestHeader(name = "idTransaccion", required = true) String idTransaccion,
    @RequestHeader(name = "msgid", required = true) String msgid,
    @RequestHeader(name = "timestamp", required = true) String timestamp,
    @RequestHeader(name = "userId", required = true) String userId,
    @RequestHeader(name = "canal", required = true) String canal,
    @RequestHeader(name = "accept", required = true) String accept, @RequestBody ObtenerEquiposRequest request) {

    HeaderRequest headerRequest = new HeaderRequest();
    headerRequest.setIdTransaccion(idTransaccion);
    headerRequest.setUserId(userId);
    headerRequest.setMsgid(msgid);
    headerRequest.setCanal(canal);
    headerRequest.setTimestamp(timestamp);
    headerRequest.setAccept(accept);
    return new ResponseEntity<ObtenerEquiposResponse>(
      obtenerEquiposService.obtenerEquipos(idTransaccion, headerRequest, request), HttpStatus.OK);
  }
}
