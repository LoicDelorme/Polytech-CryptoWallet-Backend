package fr.polytech.codev.backend.serializers;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;

public class JsonStringSerializer extends AbstractStringSerializer {

    private final Jsonb jsonBuilder;

    public JsonStringSerializer() {
        this.jsonBuilder = JsonbBuilder.create();
    }

    public JsonStringSerializer(JsonbConfig jsonbConfig) {
        this.jsonBuilder = JsonbBuilder.create(jsonbConfig);
    }

    @Override
    public <I> String to(I in) {
        return this.jsonBuilder.toJson(in);
    }
}