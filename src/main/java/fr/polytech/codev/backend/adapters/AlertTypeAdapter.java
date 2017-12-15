package fr.polytech.codev.backend.adapters;

import fr.polytech.codev.backend.entities.AlertType;

import javax.json.Json;
import javax.json.JsonString;
import javax.json.bind.adapter.JsonbAdapter;

public class AlertTypeAdapter implements JsonbAdapter<AlertType, JsonString> {

    @Override
    public JsonString adaptToJson(AlertType original) throws Exception {
        return Json.createValue(original.getName());
    }

    @Override
    public AlertType adaptFromJson(JsonString adapted) throws Exception {
        throw new UnsupportedOperationException();
    }
}