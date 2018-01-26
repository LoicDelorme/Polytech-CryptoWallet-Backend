package fr.polytech.codev.backend.requesters;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public abstract class AbstractStringRestfulRequester implements RestfulRequester {

    private final String baseUrl;

    private final RestTemplate restTemplate;

    public AbstractStringRestfulRequester(String baseUrl) {
        this.baseUrl = baseUrl;
        this.restTemplate = new RestTemplate();
    }

    public abstract HttpHeaders getHeaders();

    public String getBaseUrl() {
        return this.baseUrl;
    }

    public <T> T get(String resourcePath, Class<T> responseType) {
        return execute(resourcePath, HttpMethod.GET, responseType).getBody();
    }

    private <T> ResponseEntity<T> execute(String resourcePath, HttpMethod httpMethod, Class<T> responseType) {
        return restTemplate.exchange(getBaseUrl() + resourcePath, httpMethod, new HttpEntity(null, getHeaders()), responseType);
    }
}