package br.com.gabrielcaio.entityrelationships.controllers.error;

public class EntityExistsException extends RuntimeException {
    public EntityExistsException(String message) {
        super(message);
    }
}
