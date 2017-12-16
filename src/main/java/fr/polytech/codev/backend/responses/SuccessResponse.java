package fr.polytech.codev.backend.responses;

public class SuccessResponse extends AbstractResponse {

    public SuccessResponse() {
        super(true);
    }

    public SuccessResponse(String message) {
        super(true, message);
    }

    public SuccessResponse(Object data) {
        super(true, null, data);
    }

    public SuccessResponse(String message, Object data) {
        super(true, message, data);
    }
}