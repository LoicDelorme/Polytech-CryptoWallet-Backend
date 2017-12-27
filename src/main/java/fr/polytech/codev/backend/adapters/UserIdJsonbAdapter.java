package fr.polytech.codev.backend.adapters;

import fr.polytech.codev.backend.entities.User;

import javax.json.Json;
import javax.json.JsonNumber;
import javax.json.bind.adapter.JsonbAdapter;

public class UserIdJsonbAdapter implements JsonbAdapter<User, JsonNumber> {

    @Override
    public JsonNumber adaptToJson(User original) throws Exception {
        return Json.createValue(original.getId());
    }

    @Override
    public User adaptFromJson(JsonNumber adapted) throws Exception {
        throw new UnsupportedOperationException();
    }
}