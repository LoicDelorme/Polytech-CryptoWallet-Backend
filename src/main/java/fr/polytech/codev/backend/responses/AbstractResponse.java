package fr.polytech.codev.backend.responses;

import lombok.Data;

@Data
public class AbstractResponse {

    private final boolean isSuccess;

    private final String message;

    private final Object data;

    public AbstractResponse(boolean isSuccess) {
        this(isSuccess, null, null);
    }

    public AbstractResponse(boolean isSuccess, String message) {
        this(isSuccess, message, null);
    }

    public AbstractResponse(boolean isSuccess, String message, Object data) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.data = data;
    }
}