package br.com.gabrielcaio.entityrelationships.controllers.mapper;

import br.com.gabrielcaio.entityrelationships.model.usuario.Usuario;
import br.com.gabrielcaio.entityrelationships.model.usuario.UsuarioDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);
    Usuario toEntity(UsuarioDTO dto);
}

