package pe.com.claro.post.activaciones.plume.common.util;

import java.io.BufferedReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import pe.com.claro.post.activaciones.plume.common.constants.Constantes;
import pe.com.claro.post.activaciones.plume.common.exceptions.BDException;
import pe.com.claro.post.activaciones.plume.common.exceptions.WSException;
import pe.com.claro.post.activaciones.plume.common.property.PropertiesExterno;

public class ClaroUtil {

  private static final Logger LOG = LoggerFactory.getLogger(ClaroUtil.class);

  public static String nuloAVacio(Object object) {

    if (object == null) {
      return Constantes.TEXTO_VACIO;
    } else {
      return object.toString();
    }
  }

  public static Object nuloAVacioObject(Object object) {
    if (object == null) {
      return Constantes.TEXTO_VACIO;
    } else {
      return object;
    }
  }

  public static String verifiyNull(Object object) {
    String a = null;
    if (object != null) {
      a = object.toString();
    }
    return a;
  }

  public static String convertProperties(Object object) {
    String a = null;
    if (object != null) {
      a = object.toString();
      try {
        a = new String(a.getBytes(Constantes.DEFAULTENCODINGPROPERTIES), Constantes.DEFAULTENCODINGAPI);
      } catch (Exception e) {
        LOG.error("Error getProperties Encoding Failed, trayendo Encoding por defecto", e);
      }
    }
    return a;
  }

  public static Integer convertirInteger(Object object) {

    Integer res = null;
    if (object != null) {
      if (object instanceof BigDecimal) {
        BigDecimal bd = (BigDecimal) object;
        res = bd.intValueExact();
      } else {
        LOG.info(object.getClass().getSimpleName());
      }
    }
    return res;
  }

  public static Float convertirFloat(Object object) {
    Float res = null;
    if (object != null) {
      if (object instanceof BigDecimal) {
        BigDecimal bd = (BigDecimal) object;
        res = bd.floatValue();
      }
    }
    return res;
  }

  public static String dateAString(Date fecha) {
    if (fecha == null) {
      return Constantes.TEXTO_VACIO;
    }
    return dateAString(fecha, Constantes.FORMATOFECHADEFAULT);
  }

  public static String dateAString(Date fecha, String formato) {
    SimpleDateFormat formatoDF = new SimpleDateFormat(formato);
    return formatoDF.format(fecha);
  }

  public static Calendar toCalendar(final String iso8601string) {
    Calendar calendar = GregorianCalendar.getInstance();
    String s = iso8601string.replace("Z", "+00:00");
    try {
      s = s.substring(0, 22) + s.substring(23);
      Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse(s);
      calendar.setTime(date);
    } catch (IndexOutOfBoundsException e) {
      LOG.error("Ocurrio un error al recorrer la cadena de Fecha", e);
      calendar = null;
    } catch (ParseException e) {
      LOG.error("Ocurrio un error al convertir a Date la cadena de la fecha", e);
      calendar = null;
    }
    return calendar;
  }

  public static boolean isValidFormat(String format, String value) {
    Date date = null;
    try {
      SimpleDateFormat sdf = new SimpleDateFormat(format);
      date = sdf.parse(value);
      if (!value.equals(sdf.format(date))) {
        date = null;
      }
    } catch (ParseException ex) {
      date = null;
    }
    return date != null;
  }

  public static Date getValidFormatDate(String format, String value) {
    Date date = null;
    if (value != null && !value.isEmpty()) {
      try {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        date = sdf.parse(value);
        if (!value.equals(sdf.format(date))) {
          date = null;
        }
      } catch (ParseException ex) {
        date = null;
      }
    }
    return date;
  }

  public static DateFormat getLocalFormat() {
    DateFormat dateFormat = new SimpleDateFormat(Constantes.FORMATOFECHACABECERA_REST);
    dateFormat.setTimeZone(TimeZone.getDefault());
    return dateFormat;
  }

  public static String printPrettyJSONString(Object o) throws JsonProcessingException {
    return new ObjectMapper().setDateFormat(ClaroUtil.getLocalFormat())
      .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false).writerWithDefaultPrettyPrinter()
      .writeValueAsString(o);
  }

  public static String printJSONString(Object o) {
    try {
      return new ObjectMapper().setDateFormat(ClaroUtil.getLocalFormat())
        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false).writeValueAsString(o);
    } catch (JsonProcessingException e) {
      LOG.error("Exception en JSON: " + e, e);
      return Constantes.VACIO;
    }
  }

  public static String getIp() {
    String ip = null;
    try {
      ip = InetAddress.getLocalHost().getHostAddress();
    } catch (UnknownHostException e) {
      LOG.error(e.getMessage(), e);
    }
    return ip;
  }

  public static XMLGregorianCalendar getXmlGregorianCalendarFromDate(Date date) {
    GregorianCalendar calendar = new GregorianCalendar();
    XMLGregorianCalendar fechaConvertida = null;
    try {
      calendar.setTime(date);
      fechaConvertida = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
    } catch (DatatypeConfigurationException e) {
      LOG.error((e + Constantes.TEXTO_VACIO), e);
    }
    return fechaConvertida;
  }

  public static String dateToString(String messageTransaction, Date fecha, String formato) {
    String fecString = null;
    try {
      fecString = (new SimpleDateFormat(formato)).format(fecha);
    } catch (Exception e) {
      LOG.error(messageTransaction + ": " + e.getMessage(), e);
    }
    return fecString;
  }

  public static Date stringToDate(String messageTransaction, String fecha, String formato) {
    Date fecDate = null;
    try {
      fecDate = (new SimpleDateFormat(formato)).parse(fecha);
    } catch (ParseException e) {
      LOG.error(messageTransaction + ": " + e.getMessage(), e);
    }
    return fecDate;
  }

  public static String clobToString(Clob data) throws SQLException, java.io.IOException {
    final StringBuilder sb = new StringBuilder();

    final Reader reader = data.getCharacterStream();
    final BufferedReader br = new BufferedReader(reader);

    int b;
    while (-1 != (b = br.read())) {
      sb.append((char) b);
    }

    br.close();
    return sb.toString();
  }

  public static Clob stringToClob(String text) {
    try {
      return new javax.sql.rowset.serial.SerialClob(text.toCharArray());
    } catch (SQLException e) {
      LOG.error(e.getMessage(), e);
    }
    return null;
  }

  public static void actividadInicial(String msgTransaction, String actividad) {
    LOG.info(Constantes.LOG2, msgTransaction, Constantes.SEPARADOR_DOBLE);
    LOG.info(Constantes.LOG4, msgTransaction, Constantes.INICIO_ACTIVIDAD, actividad, Constantes.CORCHETE_DER);
    LOG.info(Constantes.LOG2, msgTransaction, Constantes.SEPARADOR_DOBLE);
  }

  public static void actividadFinal(String msgTransaction, String actividad) {
    LOG.info(Constantes.LOG2, msgTransaction, Constantes.SEPARADOR_DOBLE);
    LOG.info(Constantes.LOG4, msgTransaction, Constantes.FIN_ACTIVIDAD, actividad, Constantes.CORCHETE_DER);
    LOG.info(Constantes.LOG2, msgTransaction, Constantes.SEPARADOR_DOBLE);
  }

  public static String getDataTableNroActividad(String json) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      JsonNode rootNode = objectMapper.readTree(json);
      return rootNode.get("nroActividad").asText();
    } catch (Exception e) {
      LOG.info(Constantes.ERROR, e);
      return null;
    }
  }

  public static String getDataTableIdTransaccion(String json) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      JsonNode idTrans = objectMapper.readTree(json);
      return idTrans.get("IdTransaccion").asText();
    } catch (Exception e) {
      LOG.info(Constantes.ERROR, e);
      return null;
    }
  }

  public static String getDataTableIdEai(String json) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      JsonNode idEai = objectMapper.readTree(json);
      return idEai.get("idEai").asText();
    } catch (Exception e) {
      LOG.info(Constantes.ERROR, e);
      return null;
    }
  }

  public static int getDataTableNroIntento(String json) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      JsonNode nroIntento = objectMapper.readTree(json);
      LOG.info("nroIntento: " + nroIntento.get(Constantes.NRO_INTENTO).asInt());
      LOG.info("nroIntento + 1: " + (nroIntento.get(Constantes.NRO_INTENTO).asInt() + Constantes.UNO));
      return (nroIntento.get(Constantes.NRO_INTENTO).asInt() + Constantes.UNO);
    } catch (Exception e) {
      LOG.info(Constantes.ERROR, e);
      return -1;
    }
  }

  public static void capturarErrorSP(Exception ex, String mensajeTransaccion, String procedure, String nombreBD,
                                     PropertiesExterno prop) throws BDException {

    String error = ex + Constantes.TEXTO_VACIO;
    String codError = Constantes.TEXTO_VACIO;
    String msjError = Constantes.TEXTO_VACIO;
    if (error.toUpperCase(Locale.getDefault()).contains(Constantes.TIMEOUT.toUpperCase(Locale.getDefault()))) {
      codError = prop.idt1Codigo;
      msjError = String.format(prop.idt1Mensaje, nombreBD, procedure);
    } else if (error.toUpperCase(Locale.getDefault())
      .contains(Constantes.PERSISTENCEEXCEPTION.toUpperCase(Locale.getDefault()))
      || error.toUpperCase(Locale.getDefault())
      .contains(Constantes.HIBERNATEJDBCEXCEPTION.toUpperCase(Locale.getDefault()))
      || error.toUpperCase(Locale.getDefault())
      .contains(Constantes.GENERICJDBCEXCEPTION.toUpperCase(Locale.getDefault()))
      || error.toUpperCase(Locale.getDefault())
      .contains(Constantes.GENERICJDBCDECLARAR.toUpperCase(Locale.getDefault()))) {
      codError = prop.idt2Codigo;
      msjError = String.format(prop.idt2Mensaje, nombreBD, procedure);
    } else {
      codError = prop.idt3Codigo;
      msjError = String.format(prop.idt3Mensaje, nombreBD, procedure, error);
    }
    LOG.error(mensajeTransaccion + Constantes.ERROR_EJECUCION_SP + codError + Constantes.ESPACIO + msjError, ex);
    throw new BDException(codError, msjError);

  }

  public static void capturarErrorWS(Exception e, String nombreComponente, String nombreMetodo,
                                     PropertiesExterno propertiesExterno) throws WSException {

    String errorMsg = e + Constantes.TEXTOVACIO;
    if (errorMsg.contains(Constantes.EXCEPTION_WS_TIMEOUT_01)
      || errorMsg.contains(Constantes.EXCEPTION_WS_TIMEOUT_02)) {

      throw new WSException(propertiesExterno.idt1Codigo,
        String.format(propertiesExterno.idt1Mensaje, nombreComponente, nombreMetodo),
        String.format(propertiesExterno.idt1Mensaje, nombreComponente, nombreMetodo) + Constantes.ESPACIO + e, e);
    } else if (errorMsg.contains(Constantes.EXCEPTION_WS_NO_DISPONIBLE_01)
      || errorMsg.contains(Constantes.EXCEPTION_WS_NO_DISPONIBLE_02)
      || errorMsg.contains(Constantes.EXCEPTION_WS_NO_DISPONIBLE_03)
      || errorMsg.contains(Constantes.EXCEPTION_WS_NO_DISPONIBLE_04)) {
      throw new WSException(propertiesExterno.idt2Codigo,
        String.format(propertiesExterno.idt2Mensaje, nombreComponente, nombreMetodo),
        String.format(propertiesExterno.idt2Mensaje, nombreComponente, nombreMetodo) + Constantes.ESPACIO + e, e);
    } else {
      throw new WSException(propertiesExterno.idt3Codigo, String.format(propertiesExterno.idt3Mensaje, e.getMessage()),
        e);
    }
  }
}
