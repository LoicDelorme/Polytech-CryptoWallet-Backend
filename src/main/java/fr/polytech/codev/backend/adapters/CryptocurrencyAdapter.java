package fr.polytech.codev.backend.adapters;

import fr.polytech.codev.backend.entities.Cryptocurrency;

import javax.json.Json;
import javax.json.JsonNumber;
import javax.json.bind.adapter.JsonbAdapter;

public class CryptocurrencyAdapter implements JsonbAdapter<Cryptocurrency, JsonNumber> {

    @Override
    public JsonNumber adaptToJson(Cryptocurrency original) throws Exception {
        return Json.createValue(original.getId());
    }

    @Override
    public Cryptocurrency adaptFromJson(JsonNumber adapted) throws Exception {
        throw new UnsupportedOperationException();
    }
}