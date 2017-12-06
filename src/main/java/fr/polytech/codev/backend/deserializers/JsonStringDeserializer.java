package fr.polytech.codev.backend.deserializers;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import java.lang.reflect.Type;

public class JsonStringDeserializer extends AbstractStringDeserializer {

    private final Jsonb jsonBuilder;

    public JsonStringDeserializer() {
        this.jsonBuilder = JsonbBuilder.create();
    }

    public JsonStringDeserializer(JsonbConfig jsonbConfig) {
        this.jsonBuilder = JsonbBuilder.create(jsonbConfig);
    }

    @Override
    public <O> O from(String in, Class<O> clazz) {
        return this.jsonBuilder.fromJson(in, clazz);
    }

    @Override
    public <O> O from(String in, Type type) {
        return this.jsonBuilder.fromJson(in, type);
    }
}