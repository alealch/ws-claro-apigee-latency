package pe.com.claro.post.activaciones.plume.integration.ws.bean.type;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import pe.com.claro.post.activaciones.plume.integration.ws.bean.response.EliminarRepetidorBodyResponse;

public class EliminarRepetidorBodyResponseDeserializer implements JsonDeserializer<EliminarRepetidorBodyResponse> {

  @Override
  public EliminarRepetidorBodyResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
    throws JsonParseException {
    EliminarRepetidorBodyResponse bodyResponse = new EliminarRepetidorBodyResponse();
    if (json.isJsonObject()) {
      bodyResponse = context.deserialize(json, EliminarRepetidorBodyResponse.class);
    } else if (json.isJsonPrimitive()) {
      bodyResponse.setError(null);
    }
    return bodyResponse;
  }

}
