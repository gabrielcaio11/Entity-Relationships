package br.com.gabrielcaio.entityrelationships.controllers.mapper;

import br.com.gabrielcaio.entityrelationships.model.curso.CreateCursoDTO;
import br.com.gabrielcaio.entityrelationships.model.curso.Curso;
import br.com.gabrielcaio.entityrelationships.model.curso.CursoResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CursoMapper {

    CursoMapper INSTANCE = Mappers.getMapper(CursoMapper.class);
    Curso toEntityFromCreateCursoDTO(CreateCursoDTO dto);
    CursoResponseDTO toCursoResponseFromEntity(Curso curso);
}
