package pe.com.claro.post.activaciones.plume.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class UtilJAXRS {

  private static final Logger logger = LoggerFactory.getLogger(UtilJAXRS.class);

  public static DateFormat getLocalFormat() {
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    dateFormat.setTimeZone(TimeZone.getDefault());
    return dateFormat;
  }

  public static String anyObjectToPrettyJson(Object o) {
    try {
      return (new ObjectMapper()).setDateFormat(getLocalFormat())
        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false).writerWithDefaultPrettyPrinter()
        .writeValueAsString(o);
    } catch (JsonProcessingException e) {
      logger.error("Error parseando object to json:", e);
    }
    return null;
  }

  public static String arrayToString(List<String> listaCampos) {
    String[] answer = Arrays.copyOf(listaCampos.toArray(), listaCampos.size(), String[].class);
    return Arrays.toString(answer);
  }

  public static String formatJson(String inputJson) throws JSONException {
    JSONObject jsonObject = new JSONObject(inputJson);
    return jsonObject.toString(4);
  }
}
