package br.com.gabrielcaio.entityrelationships.controllers.mapper;

import br.com.gabrielcaio.entityrelationships.model.client.Client;
import br.com.gabrielcaio.entityrelationships.model.client.ClientResponseDto;
import br.com.gabrielcaio.entityrelationships.model.client.CreateClientDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "clientId", source = "clientId")
    @Mapping(target = "clientSecret", source = "clientSecret")
    @Mapping(target = "redirectURI", source = "redirectURI")
    @Mapping(target = "scope", source = "scope")
    Client toEntity(CreateClientDto dto);


    @Mapping(target = "id", source = "id")
    @Mapping(target = "clientId", source = "clientId")
    @Mapping(target = "clientSecret", source = "clientSecret")
    @Mapping(target = "redirectURI", source = "redirectURI")
    @Mapping(target = "scope", source = "scope")
    ClientResponseDto toDTO(Client client);
}
