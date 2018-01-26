package fr.polytech.codev.backend.requesters;

import org.springframework.http.HttpHeaders;

public class JsonStringRestfulRequester extends AbstractStringRestfulRequester {

    public JsonStringRestfulRequester(String baseUrl) {
        super(baseUrl);
    }

    @Override
    public HttpHeaders getHeaders() {
        return new HttpHeaders();
    }
}