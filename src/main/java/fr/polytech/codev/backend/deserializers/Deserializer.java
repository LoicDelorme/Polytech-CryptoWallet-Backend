package fr.polytech.codev.backend.deserializers;

import java.lang.reflect.Type;

public interface Deserializer<I> {

    public <O> O from(I in, Class<O> clazz);

    public <O> O from(I in, Type type);
}