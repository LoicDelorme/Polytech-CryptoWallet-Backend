package fr.polytech.codev.backend.adapters;

import fr.polytech.codev.backend.entities.AlertType;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;

public class AlertTypeJsonbAdapter implements JsonbAdapter<AlertType, JsonObject> {

    @Override
    public JsonObject adaptToJson(AlertType original) throws Exception {
        return Json.createObjectBuilder().add("id", original.getId()).add("name", original.getName()).build();
    }

    @Override
    public AlertType adaptFromJson(JsonObject adapted) throws Exception {
        throw new UnsupportedOperationException();
    }
}