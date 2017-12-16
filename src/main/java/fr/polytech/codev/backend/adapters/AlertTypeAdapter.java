package fr.polytech.codev.backend.adapters;

import fr.polytech.codev.backend.entities.AlertType;

import javax.json.Json;
import javax.json.JsonNumber;
import javax.json.bind.adapter.JsonbAdapter;

public class AlertTypeAdapter implements JsonbAdapter<AlertType, JsonNumber> {

    @Override
    public JsonNumber adaptToJson(AlertType original) throws Exception {
        return Json.createValue(original.getId());
    }

    @Override
    public AlertType adaptFromJson(JsonNumber adapted) throws Exception {
        throw new UnsupportedOperationException();
    }
}