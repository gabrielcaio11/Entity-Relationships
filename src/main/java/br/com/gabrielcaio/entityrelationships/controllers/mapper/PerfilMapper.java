package br.com.gabrielcaio.entityrelationships.controllers.mapper;

import br.com.gabrielcaio.entityrelationships.model.perfil.CreatePerfilDTO;
import br.com.gabrielcaio.entityrelationships.model.perfil.Perfil;
import br.com.gabrielcaio.entityrelationships.model.perfil.PerfilResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PerfilMapper {
    PerfilMapper INSTANCE = Mappers.getMapper(PerfilMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "instrutor", ignore = true)
    Perfil toEntityFromCreatePerfilDTO(CreatePerfilDTO dto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "biografia", target = "biografia")
    @Mapping(source = "especialidade", target = "especialidade")
    PerfilResponse toPerfilResponseFromEntity(Perfil perfil);
} 