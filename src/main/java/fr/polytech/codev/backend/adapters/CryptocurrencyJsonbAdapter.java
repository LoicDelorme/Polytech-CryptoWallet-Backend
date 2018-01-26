package fr.polytech.codev.backend.adapters;

import fr.polytech.codev.backend.entities.Cryptocurrency;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;

public class CryptocurrencyJsonbAdapter implements JsonbAdapter<Cryptocurrency, JsonObject> {

    @Override
    public JsonObject adaptToJson(Cryptocurrency original) throws Exception {
        return Json.createObjectBuilder().add("id", original.getId()).add("name", original.getName()).add("symbol", original.getSymbol()).add("imageUrl", original.getImageUrl()).add("resourceUrl", original.getResourceUrl()).build();
    }

    @Override
    public Cryptocurrency adaptFromJson(JsonObject adapted) throws Exception {
        throw new UnsupportedOperationException();
    }
}