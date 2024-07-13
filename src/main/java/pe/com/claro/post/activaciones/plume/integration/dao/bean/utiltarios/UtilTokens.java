package pe.com.claro.post.activaciones.plume.integration.dao.bean.utiltarios;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pe.com.claro.post.activaciones.plume.canonical.header.HeaderRequest;
import pe.com.claro.post.activaciones.plume.canonical.request.AprovisionarPlumeRequest;
import pe.com.claro.post.activaciones.plume.common.constants.Constantes;
import pe.com.claro.post.activaciones.plume.common.exceptions.BaseException;
import pe.com.claro.post.activaciones.plume.common.exceptions.WSException;
import pe.com.claro.post.activaciones.plume.common.property.PropertiesExterno;
import pe.com.claro.post.activaciones.plume.common.util.ClaroUtil;
import pe.com.claro.post.activaciones.plume.common.util.UtilJAXRS;
import pe.com.claro.post.activaciones.plume.integration.dao.TimeaiDao;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.request.RegistrarTrazabilidadRequest;
import pe.com.claro.post.activaciones.plume.integration.dao.bean.response.DatosClienteBeanRes;
import pe.com.claro.post.activaciones.plume.integration.ws.GeneraTokenClaroClient;
import pe.com.claro.post.activaciones.plume.integration.ws.GeneraTokenPlumeClient;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.request.BodyGeneraTokenClaroRequest;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.request.GeneraTokenClaroMessageRequest;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.request.GeneraTokenClaroRequest;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.request.GeneraTokenPlumeMessageRequest;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.request.GeneraTokenPlumeRequest;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.request.HeaderDPRequest;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.request.HeaderDatapower;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.GeneraTokenClaroResponse;
import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.GeneraTokenPlumeResponse;

@Component
public class UtilTokens {
  private final Logger logger = LoggerFactory.getLogger(UtilTokens.class);

  @Autowired
  private GeneraTokenClaroClient generaTokenClaro;

  @Autowired
  private GeneraTokenPlumeClient generaTokenPlume;

  @Autowired
  private TimeaiDao timeaiDao;

  public String subprocesoTokens(String idTransaccion, HeaderRequest header, String mensajeTrx,
                                 PropertiesExterno prop, String numeroActividad, AprovisionarPlumeRequest request,
                                 DatosClienteBeanRes responseAct2) throws BaseException, SQLException {
    String token = Constantes.VACIO;
    String tokenPlume = Constantes.VACIO;

    logger.info("ENTRO.subprocesoTokens  :" + ClaroUtil.printJSONString(responseAct2));

    token = generarToken(mensajeTrx, idTransaccion, header, prop, Constantes.NAMETOKENCLARO, numeroActividad, request,
      responseAct2);
    logger.info(mensajeTrx + " TOKEN_CLARO: " + token);
    tokenPlume = generarTokenPlume(mensajeTrx, idTransaccion, header, token, prop, Constantes.NAMETOKENPLUME,
      numeroActividad, request, responseAct2);
    logger.info(mensajeTrx + " TOKEN_PLUME: " + tokenPlume);

    return tokenPlume + Constantes.SALTO_LINEA + token;
  }

  public String generarTokenPlume(String msjTx, String idTransaccion, HeaderRequest headearRequest,
                                  String tokenClaro, PropertiesExterno prop, String tipoToken, String numeroActividad,
                                  AprovisionarPlumeRequest request, DatosClienteBeanRes responseAct2) throws BaseException, SQLException {
    String numeroActividadReald = numeroActividad;

    String nroActividad = Constantes.VACIO;
    numeroActividad = numeroActividad.substring(0, numeroActividad.length() - numeroActividad.length() + 1);
    nroActividad = obtenerNroActividad(numeroActividad, nroActividad);

    logger.info(Constantes.LOG_PARAM, msjTx, Constantes.INICIO_ACTIVIDAD_LOG,
      nroActividad.replace(Constantes.S_UNO, Constantes.S_DOS) + " Obtener Token PLUME");

    HeaderDatapower headerGenerarToken = headerDP(idTransaccion, headearRequest, prop, tipoToken);

    GeneraTokenPlumeMessageRequest mesgRequestGenerarToken = new GeneraTokenPlumeMessageRequest();
    mesgRequestGenerarToken.setHeader(headerGenerarToken);

    GeneraTokenPlumeRequest reqPlume = new GeneraTokenPlumeRequest();
    reqPlume.setMessageRequest(mesgRequestGenerarToken);

    String token = Constantes.STRING_VACIO;
    GeneraTokenPlumeResponse tokenResponse = null;
    tokenResponse = generaTokenPlume.generaTokenPlume(idTransaccion, reqPlume, prop.wsGeneraTokenPlumeAuthorization,
      tokenClaro, prop.wsGeneraTokenPlumeScope, prop.wsGeneraTokenPlumeGrantType);

    if (tokenResponse != null
      && !Constantes.STRING_VACIO.equals(tokenResponse.getMessageResponse().getBody().getAccessToken())) {

      token = tokenResponse.getMessageResponse().getBody().getAccessToken();
    }
    logger.info(Constantes.LOG_PARAM, msjTx, Constantes.FIN_ACTIVIDAD_LOG,
      nroActividad.replace(Constantes.S_UNO, Constantes.S_DOS) + " Obtener Token PLUME");

    if (StringUtils.isBlank(token)) {
      validateIDFTokenPlume(prop, numeroActividadReald, headearRequest, msjTx, request, responseAct2,
        tokenResponse.getMessageResponse().getHeader().getHeaderResponse().getStatus().getCode(),
        tokenResponse.getMessageResponse().getHeader().getHeaderResponse().getStatus().getMessage()
      );
    }

    return token;
  }

  public String generarToken(String msjTx, String idTransaccion, HeaderRequest headearRequest,
                             PropertiesExterno prop, String tipoToken, String numeroActividad, AprovisionarPlumeRequest request,
                             DatosClienteBeanRes responseAct2) throws BaseException, SQLException {
    String numeroActividadReald = numeroActividad;
    String nroActividad = Constantes.VACIO;
    numeroActividad = numeroActividad.substring(0, numeroActividad.length() - numeroActividad.length() + 1);
    nroActividad = obtenerNroActividad(numeroActividad, nroActividad);

    logger.info(Constantes.LOG_PARAM, msjTx, Constantes.INICIO_ACTIVIDAD_LOG, nroActividad + " Obtener Token APIM");

    HeaderDatapower headerGenerarToken = headerDP(idTransaccion, headearRequest, prop, tipoToken);

    BodyGeneraTokenClaroRequest bodyGenerarToken = new BodyGeneraTokenClaroRequest();
    bodyGenerarToken.setClientId(prop.wsGeneraTokenClaroClientId);
    bodyGenerarToken.setClientSecret(prop.wsGeneraTokenClaroClientSecret);

    GeneraTokenClaroMessageRequest mesgRequestGenerarToken = new GeneraTokenClaroMessageRequest();
    mesgRequestGenerarToken.setHeader(headerGenerarToken);
    mesgRequestGenerarToken.setBody(bodyGenerarToken);

    GeneraTokenClaroRequest generaTokenRequest = new GeneraTokenClaroRequest();
    generaTokenRequest.setMessageRequest(mesgRequestGenerarToken);

    String token = Constantes.STRING_VACIO;

    GeneraTokenClaroResponse tokenResponse = new GeneraTokenClaroResponse();

    tokenResponse = generaTokenClaro.generarToken(msjTx, idTransaccion, generaTokenRequest,
      prop);

    if (tokenResponse != null
      && !Constantes.STRING_VACIO.equals(tokenResponse.getMessageResponse().getBody().getAccessToken())) {

      token = tokenResponse.getMessageResponse().getBody().getAccessToken();
    }
    logger.info(Constantes.LOG_PARAM, msjTx, Constantes.FIN_ACTIVIDAD_LOG, nroActividad + " Obtener Token APIM");

    if (StringUtils.isBlank(token)) {
      validateIDFTokenClaro(prop, numeroActividadReald, headearRequest, request, responseAct2,
        tokenResponse.getMessageResponse().getBody().getError().getStatusCode(),
        tokenResponse.getMessageResponse().getBody().getError().getMessage()
      );
    }
    return token;
  }

  private void validateIDFTokenClaro(PropertiesExterno prop, String numeroActividad,
                                     HeaderRequest headearRequest, AprovisionarPlumeRequest request, DatosClienteBeanRes responseAct2,
                                     String codigo, String mensaje)
    throws BaseException, SQLException {
    logger.info("validateIDFTokenClaro: " + numeroActividad);
    switch (numeroActividad) {
      default:
        break;
      case Constantes.S_TRES:
        throw new WSException(prop.idf3CodigoLocaciones, prop.idf3MensajeLocaciones
          .replace(Constantes.PLATAFORMA, "APIM").replace(Constantes.DETALLE, Constantes.TOKEN_NO_VALIDO));

      case Constantes.S_CINCO + " " + "consultarEquipo":
        throw new WSException(prop.idf5CodigoConsultarEquipos, prop.idf5MensajeConsultarEquipos
          .replace(Constantes.PLATAFORMA, "APIM").replace(Constantes.DETALLE, Constantes.TOKEN_NO_VALIDO));

      case Constantes.S_CINCO:
        registrarTrazabilidad(headearRequest, request, responseAct2, Constantes.VACIO, codigo, mensaje, prop);
        throw new WSException(prop.idf5Codigo,
          prop.idf5Mensaje.replace(Constantes.PLATAFORMA, "APIM").replace(Constantes.DETALLE, Constantes.TOKEN_NO_VALIDO));

      case Constantes.S_SEIS:
        throw new WSException(prop.idf6CodigoObtenerEquipos, prop.idf6MensajeObtenerEquipos
          .replace(Constantes.PLATAFORMA, "APIM").replace(Constantes.DETALLE, Constantes.TOKEN_NO_VALIDO));
    }
  }

  private void validateIDFTokenPlume(PropertiesExterno prop, String numeroActividad,
                                     HeaderRequest headearRequest, String msjTx, AprovisionarPlumeRequest request, DatosClienteBeanRes responseAct2,
                                     String codigo, String mensaje)
    throws BaseException, SQLException {

    switch (numeroActividad) {
      default:
        break;
      case Constantes.S_TRES:
        throw new WSException(prop.idf3CodigoLocaciones, prop.idf3MensajeLocaciones
          .replace(Constantes.PLATAFORMA, Constantes.PLUME).replace(Constantes.DETALLE, Constantes.TOKEN_NO_VALIDO));
      case Constantes.S_CINCO + " " + "consultarEquipo":
        throw new WSException(prop.idf5CodigoConsultarEquipos, prop.idf5MensajeConsultarEquipos
          .replace(Constantes.PLATAFORMA, Constantes.PLUME).replace(Constantes.DETALLE, Constantes.TOKEN_NO_VALIDO));
      case Constantes.S_CINCO:
        logger.info(Constantes.LOG_PARAM, msjTx, Constantes.INICIO_ACTIVIDAD_LOG, "11. Registrar Trazabilidad");
        registrarTrazabilidad(headearRequest, request, responseAct2, Constantes.VACIO, codigo, mensaje, prop);
        logger.info(Constantes.LOG_PARAM, msjTx, Constantes.FIN_ACTIVIDAD_LOG, "11. Registrar Trazabilidad");
        throw new WSException(prop.idf5Codigo, prop.idf5Mensaje
          .replace(Constantes.PLATAFORMA, Constantes.PLUME).replace(Constantes.DETALLE, Constantes.TOKEN_NO_VALIDO));
      case Constantes.S_SEIS:
        throw new WSException(prop.idf6CodigoObtenerEquipos, prop.idf6MensajeObtenerEquipos
          .replace(Constantes.PLATAFORMA, Constantes.PLUME).replace(Constantes.DETALLE, Constantes.TOKEN_NO_VALIDO));
    }
  }

  private String obtenerNroActividad(String numeroActividad, String nroActividad) {
    switch (numeroActividad) {
      default:
        break;
      case Constantes.S_TRES:
        nroActividad = Constantes.S_TRES + Constantes.PUNTO + Constantes.S_UNO + Constantes.PUNTO;
        break;
      case Constantes.S_CINCO:
        nroActividad = Constantes.S_CINCO + Constantes.PUNTO + Constantes.S_UNO + Constantes.PUNTO;
        break;
      case Constantes.S_SEIS:
        nroActividad = Constantes.S_SEIS + Constantes.PUNTO + Constantes.S_UNO + Constantes.PUNTO;
        break;
    }
    return nroActividad;
  }

  public HeaderDatapower headerDP(String idTransaccion, HeaderRequest headearRequest, PropertiesExterno prop,
                                  String tipoToken) {
    HeaderDatapower headerGenerarToken = new HeaderDatapower();
    HeaderDPRequest headerRequestGenerarToken = new HeaderDPRequest();
    headerRequestGenerarToken.setConsumer(headearRequest.getCanal());
    headerRequestGenerarToken.setCountry(prop.wsGeneraTokenGenericoHeaderCountry);
    headerRequestGenerarToken.setDispositivo(prop.wsGeneraTokenGenericoHeaderDispositivo);
    headerRequestGenerarToken.setLanguage(prop.wsGeneraTokenGenericoHeaderLeanguaje);
    headerRequestGenerarToken.setModulo(prop.wsGeneraTokenGenericoHeaderModulo);
    headerRequestGenerarToken.setMsgType(prop.wsGeneraTokenGenericoHeaderMsgType);
    headerRequestGenerarToken.setPid(idTransaccion);
    headerRequestGenerarToken.setSystem(headearRequest.getCanal());
    headerRequestGenerarToken.setTimestamp(headearRequest.getTimestamp());
    headerRequestGenerarToken.setUserId(headearRequest.getUserId());

    switch (tipoToken) {
      default:
        break;
      case Constantes.NAMETOKENCLARO:
        headerRequestGenerarToken.setOperation(Constantes.NAMETOKENCLARO);
        headerRequestGenerarToken.setWsIp(prop.wsGeneraTokenClaroHeaderIp);
        break;

      case Constantes.NAMETOKENPLUME:
        headerRequestGenerarToken.setOperation(Constantes.NAMETOKENPLUME);
        headerRequestGenerarToken.setWsIp(prop.wsGeneraTokenPlumeHeaderIp);
        break;

      case Constantes.CREARUBICACION:
        headerRequestGenerarToken.setOperation(Constantes.CREARUBICACION);
        headerRequestGenerarToken.setWsIp(prop.wsCrearUbicacionHeaderIp);
        break;

      case Constantes.CREARCUENTA:
        headerRequestGenerarToken.setOperation(Constantes.CREARCUENTA);
        headerRequestGenerarToken.setWsIp(prop.wsCrearCuentaHeaderIp);
        break;

      case Constantes.CONFIGNIVEL:
        headerRequestGenerarToken.setOperation(Constantes.CONFIGNIVEL);
        headerRequestGenerarToken.setWsIp(prop.wsCrearConfigNivelHeaderIp);
        break;

      case Constantes.CREARREPETIDOR:
        headerRequestGenerarToken.setOperation(Constantes.CREARREPETIDOR);
        headerRequestGenerarToken.setWsIp(prop.wsCrearRepetidorHeaderIp);
        break;

    }

    headerGenerarToken.setHeaderRequest(headerRequestGenerarToken);
    return headerGenerarToken;

  }

  public void registrarTrazabilidad(HeaderRequest header, AprovisionarPlumeRequest request,
                                    DatosClienteBeanRes responseAct2, String proceso, String codigo, String mensaje, PropertiesExterno prop)
    throws BaseException, SQLException {
    RegistrarTrazabilidadRequest reqRegistrar = new RegistrarTrazabilidadRequest();
    Date currentDate = new Date();

    reqRegistrar.setIdTransaccion(header.getIdTransaccion());
    reqRegistrar.setEstado(codigo.equals(Constantes.S_CERO) ? Constantes.CERO : Constantes.UNO);
    reqRegistrar.setFeExec(new Timestamp(currentDate.getTime()));
    reqRegistrar.setCodigoCliente(responseAct2.getCodigoCliente());
    reqRegistrar.setCanal(header.getCanal());
    reqRegistrar.setAccountId(responseAct2.getAccountId());
    reqRegistrar.setCodigoClientePlm(Constantes.VACIO);
    reqRegistrar.setCodigoSucursalPlm(Constantes.VACIO);
    reqRegistrar.setCodigoSeriePlm(Constantes.VACIO);
    reqRegistrar.setRequest(UtilJAXRS.anyObjectToPrettyJson(request));
    reqRegistrar.setCodigoError(codigo);
    reqRegistrar.setMensajeError(mensaje);
    reqRegistrar.setTipoOperacion(proceso);
    reqRegistrar.setUserRegistro(prop.registrarTrazabilidadUserId);

    timeaiDao.registrarTrazabilidad(header.getIdTransaccion(), reqRegistrar);
  }
}
