package fr.polytech.codev.backend.adapters;

import fr.polytech.codev.backend.entities.Currency;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;

public class CurrencyJsonbAdapter implements JsonbAdapter<Currency, JsonObject> {

    @Override
    public JsonObject adaptToJson(Currency original) throws Exception {
        return Json.createObjectBuilder().add("id", original.getId()).add("name", original.getName()).add("symbol", original.getSymbol()).build();
    }

    @Override
    public Currency adaptFromJson(JsonObject adapted) throws Exception {
        throw new UnsupportedOperationException();
    }
}