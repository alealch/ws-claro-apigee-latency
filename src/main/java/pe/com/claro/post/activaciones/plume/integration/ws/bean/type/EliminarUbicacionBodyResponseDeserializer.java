package pe.com.claro.post.activaciones.plume.integration.ws.bean.type;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.EliminarUbicacionBodyResponse;

public class EliminarUbicacionBodyResponseDeserializer implements JsonDeserializer<EliminarUbicacionBodyResponse> {

  @Override
  public EliminarUbicacionBodyResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
    throws JsonParseException {
    EliminarUbicacionBodyResponse bodyResponse = new EliminarUbicacionBodyResponse();
    if (json.isJsonObject()) {
      bodyResponse = context.deserialize(json, EliminarUbicacionBodyResponse.class);
    } else if (json.isJsonPrimitive()) {
      bodyResponse.setError(null);
    }
    return bodyResponse;
  }

}
