package br.com.gabrielcaio.entityrelationships.validator;

import br.com.gabrielcaio.entityrelationships.model.client.Client;
import br.com.gabrielcaio.entityrelationships.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidadorRegistroClient {

    private final ClientRepository clientRepository;

    public void validar(Client client) {
        validarExistenciaClientId(client.getClientId());
    }

    private void validarExistenciaClientId(String clientId) {
        if(clientRepository.existsByClientId(clientId)) {
            throw new RuntimeException("Client com esse clientId já existe.");
        }
    }
}
