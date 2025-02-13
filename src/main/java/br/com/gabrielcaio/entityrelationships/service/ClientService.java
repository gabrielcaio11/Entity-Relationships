package br.com.gabrielcaio.entityrelationships.service;

import br.com.gabrielcaio.entityrelationships.controllers.mapper.ClientMapper;
import br.com.gabrielcaio.entityrelationships.model.client.Client;
import br.com.gabrielcaio.entityrelationships.model.client.CreateClientDto;
import br.com.gabrielcaio.entityrelationships.repositories.ClientRepository;
import br.com.gabrielcaio.entityrelationships.validator.ValidadorRegistroClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder encoder;
    private final ValidadorRegistroClient validadorRegistroClient;

    public void salvar(CreateClientDto dto) {

        // transforma de dto para entidade
        var client = ClientMapper.INSTANCE.toEntity(dto);

        // validacao do registro do client
        validadorRegistroClient.validar(client);

        // criptografa a senha do client
        var senhaCriptografada = encoder.encode(client.getClientSecret());
        client.setClientSecret(senhaCriptografada);

        clientRepository.save(client);
    }

    public Optional<Client> obterPorClientID(String clientId) {
        return clientRepository.findByClientId(clientId);
    }
}
