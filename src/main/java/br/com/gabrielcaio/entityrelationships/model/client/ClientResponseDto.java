package br.com.gabrielcaio.entityrelationships.model.client;

import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link Client}
 */
@Value
public class ClientResponseDto implements Serializable {
    UUID id;
    String clientId;
    String clientSecret;
    String redirectURI;
    String scope;
}