package fr.polytech.codev.backend.adapters;

import fr.polytech.codev.backend.entities.ChartPeriod;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;

public class ChartPeriodJsonbAdapter implements JsonbAdapter<ChartPeriod, JsonObject> {

    @Override
    public JsonObject adaptToJson(ChartPeriod original) throws Exception {
        return Json.createObjectBuilder().add("id", original.getId()).add("name", original.getName()).build();
    }

    @Override
    public ChartPeriod adaptFromJson(JsonObject adapted) throws Exception {
        throw new UnsupportedOperationException();
    }
}