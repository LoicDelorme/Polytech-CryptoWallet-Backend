package fr.polytech.codev.backend.responses;

public class FailureResponse extends AbstractResponse {

    public FailureResponse() {
        super(false);
    }

    public FailureResponse(String message) {
        super(false, message);
    }

    public FailureResponse(Object data) {
        super(false, null, data);
    }

    public FailureResponse(String message, Object data) {
        super(false, message, data);
    }
}