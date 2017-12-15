package fr.polytech.codev.backend.adapters;

import fr.polytech.codev.backend.entities.Cryptocurrency;

import javax.json.Json;
import javax.json.JsonString;
import javax.json.bind.adapter.JsonbAdapter;

public class CryptocurrencyAdapter implements JsonbAdapter<Cryptocurrency, JsonString> {

    @Override
    public JsonString adaptToJson(Cryptocurrency original) throws Exception {
        return Json.createValue(original.getName());
    }

    @Override
    public Cryptocurrency adaptFromJson(JsonString adapted) throws Exception {
        throw new UnsupportedOperationException();
    }
}