package br.com.gabrielcaio.entityrelationships.controllers.mapper;

import br.com.gabrielcaio.entityrelationships.model.perfil.Perfil;
import br.com.gabrielcaio.entityrelationships.model.perfil.PerfilResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PerfilMapper {
    PerfilMapper INSTANCE = Mappers.getMapper(PerfilMapper.class);

    PerfilResponse toPerfilResponseFromPerfil(Perfil perfil);
}
