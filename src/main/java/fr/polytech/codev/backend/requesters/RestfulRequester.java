package fr.polytech.codev.backend.requesters;

public interface RestfulRequester {

    public String getBaseUrl();

    public <T> T get(String resourcePath, Class<T> responseType);
}