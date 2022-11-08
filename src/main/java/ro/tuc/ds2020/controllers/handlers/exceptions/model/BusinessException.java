package ro.tuc.ds2020.controllers.handlers.exceptions.model;

public class BusinessException extends RuntimeException {
    private String message;
    public BusinessException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
