package de.ostfale.jug.beui.http;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Converter which maps Json to object and vice versa
 * Created :  31.07.2020
 *
 * @author : Uwe Sauerbrei
 */
public class JsonMapper {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private static final ObjectMapper mapper = initMapper();

    public static <T> Optional<T> jsonToObject(String json, Class<T> clazz) {
        try {
            return Optional.of(mapper.readValue(json, clazz));
        } catch (JsonProcessingException e) {
            log.error("Failed to map JSON to class: {}", clazz.getSimpleName());
            return Optional.empty();
        }
    }

    public static <T> Optional<String> objectToJson(T obj) {
        try {
            return Optional.of(mapper.writeValueAsString(obj));
        } catch (JsonProcessingException e) {
            log.error("Failed to map class to JSON: {}", obj.getClass().getSimpleName());
            return Optional.empty();
        }
    }

    public static <T> List<T> jsonToObjectList(String json, Class<T> target) {
        try {
            CollectionType javaType = mapper.getTypeFactory().constructCollectionType(List.class, target);
            return mapper.readValue(json, javaType);
        } catch (JsonProcessingException e) {
            log.error("Failed to ");
            return Collections.emptyList();
        }
    }

    private static ObjectMapper initMapper() {
        JsonFactory factory = new JsonFactory();
        factory.enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES);
        ObjectMapper objectMapper = new ObjectMapper(factory);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
    //    mapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }
}
