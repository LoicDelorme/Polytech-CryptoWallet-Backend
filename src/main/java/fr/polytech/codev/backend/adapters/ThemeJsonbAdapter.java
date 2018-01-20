package fr.polytech.codev.backend.adapters;

import fr.polytech.codev.backend.entities.Theme;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;

public class ThemeJsonbAdapter implements JsonbAdapter<Theme, JsonObject> {

    @Override
    public JsonObject adaptToJson(Theme original) throws Exception {
        return Json.createObjectBuilder().add("id", original.getId()).add("name", original.getName()).build();
    }

    @Override
    public Theme adaptFromJson(JsonObject adapted) throws Exception {
        throw new UnsupportedOperationException();
    }
}