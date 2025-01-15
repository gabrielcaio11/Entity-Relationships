package br.com.gabrielcaio.entityrelationships.controllers.error;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
