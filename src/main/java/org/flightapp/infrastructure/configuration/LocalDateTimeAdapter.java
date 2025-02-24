package org.flightapp.infrastructure.configuration;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeAdapter implements JsonSerializer<java.time.LocalDateTime>, JsonDeserializer<java.time.LocalDateTime> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public JsonElement serialize(java.time.LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.format(formatter));
    }

    @Override
    public java.time.LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return java.time.LocalDateTime.parse(json.getAsString(), formatter);
    }
}
