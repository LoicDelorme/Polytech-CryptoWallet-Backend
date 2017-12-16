package fr.polytech.codev.backend.adapters;

import fr.polytech.codev.backend.entities.Wallet;

import javax.json.Json;
import javax.json.JsonNumber;
import javax.json.bind.adapter.JsonbAdapter;

public class WalletAdapter implements JsonbAdapter<Wallet, JsonNumber> {

    @Override
    public JsonNumber adaptToJson(Wallet original) throws Exception {
        return Json.createValue(original.getId());
    }

    @Override
    public Wallet adaptFromJson(JsonNumber adapted) throws Exception {
        throw new UnsupportedOperationException();
    }
}