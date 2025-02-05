package br.com.gabrielcaio.entityrelationships.controllers.mapper;

import br.com.gabrielcaio.entityrelationships.model.client.Client;
import br.com.gabrielcaio.entityrelationships.model.client.ClientResponseDto;
import br.com.gabrielcaio.entityrelationships.model.client.CreateClientDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);
    Client toEntityFromCreateClienteDto(CreateClientDto dto);
    ClientResponseDto toClientResponseFromEntity(Client client);
}
