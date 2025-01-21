package br.com.gabrielcaio.entityrelationships.controllers.error;

public class DataBaseException extends RuntimeException {
    public DataBaseException(String message) {
        super(message);
    }
}
