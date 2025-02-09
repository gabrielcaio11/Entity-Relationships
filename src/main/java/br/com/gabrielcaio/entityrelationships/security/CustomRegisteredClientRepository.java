package br.com.gabrielcaio.entityrelationships.security;

import br.com.gabrielcaio.entityrelationships.service.ClientService;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;

/**
 * Custom implementation of RegisteredClientRepository to manage registered clients.
 * This bean is implemented to provide custom client retrieval and storage logic
 * using the ClientService, TokenSettings, and ClientSettings.
 */
@Component
public class CustomRegisteredClientRepository implements RegisteredClientRepository {

    private final ClientService clientService;
    private final TokenSettings tokenSettings;
    private final ClientSettings clientSettings;

    /**
     * Constructs a new CustomRegisteredClientRepository.
     *
     * @param clientService the client service
     * @param tokenSettings the token settings
     * @param clientSettings the client settings
     */
    public CustomRegisteredClientRepository(ClientService clientService, TokenSettings tokenSettings, ClientSettings clientSettings) {
        this.clientService = clientService;
        this.tokenSettings = tokenSettings;
        this.clientSettings = clientSettings;
    }

    /**
     * Saves the registered client.
     *
     * @param registeredClient the registered client
     */
    @Override
    public void save(RegisteredClient registeredClient) {}

    /**
     * Finds a registered client by its ID.
     *
     * @param id the ID of the registered client
     * @return the registered client, or null if not found
     */
    @Override
    public RegisteredClient findById(String id) {
        return null;
    }

    /**
     * Finds a registered client by its client ID.
     *
     * @param clientId the client ID of the registered client
     * @return the registered client, or null if not found
     */
    @Override
    public RegisteredClient findByClientId(String clientId) {
        var clientOptional = clientService.obterPorClientID(clientId);

        if (  clientOptional.isEmpty()  ) {
            return null;
        }
        var client = clientOptional.get();

        return RegisteredClient
                .withId(client.getId().toString())
                .clientId(client.getClientId())
                .clientSecret(client.getClientSecret())
                .redirectUri(client.getRedirectURI())
                .scope(client.getScope())
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .tokenSettings(tokenSettings)
                .clientSettings(clientSettings)
                .build();
    }
}