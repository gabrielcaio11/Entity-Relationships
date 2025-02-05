package br.com.gabrielcaio.entityrelationships.service;

import br.com.gabrielcaio.entityrelationships.controllers.mapper.ClientMapper;
import br.com.gabrielcaio.entityrelationships.model.client.Client;
import br.com.gabrielcaio.entityrelationships.model.client.ClientResponseDto;
import br.com.gabrielcaio.entityrelationships.model.client.CreateClientDto;
import br.com.gabrielcaio.entityrelationships.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder encoder;

    public ClientResponseDto salvar(CreateClientDto dto) {
        var client = ClientMapper.INSTANCE.toEntityFromCreateClienteDto(dto);
        var senhaCriptografada = encoder.encode(client.getClientSecret());
        client.setClientSecret(senhaCriptografada);
        var clientSave = clientRepository.save(client);
        return ClientMapper.INSTANCE.toClientResponseFromEntity(clientSave);
    }

    public Optional<Client> obterPorClientID(String clientId) {
        return clientRepository.findByClientId(clientId);
    }
}
