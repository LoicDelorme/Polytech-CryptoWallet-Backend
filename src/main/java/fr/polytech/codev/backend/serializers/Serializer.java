package fr.polytech.codev.backend.serializers;

public interface Serializer<O> {

    public <I> O to(I in);
}