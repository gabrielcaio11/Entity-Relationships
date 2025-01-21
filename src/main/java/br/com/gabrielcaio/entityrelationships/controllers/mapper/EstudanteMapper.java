package br.com.gabrielcaio.entityrelationships.controllers.mapper;

import br.com.gabrielcaio.entityrelationships.model.estudante.CreateEstudanteDTO;
import br.com.gabrielcaio.entityrelationships.model.estudante.Estudante;
import br.com.gabrielcaio.entityrelationships.model.estudante.EstudanteWithCurcoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EstudanteMapper {
    EstudanteMapper INSTANCE = Mappers.getMapper(EstudanteMapper.class);
    Estudante toEntityFromCreateEstudanteDTO(CreateEstudanteDTO dto);
    @Mapping(source = "id", target = "id")
    @Mapping(source = "nome", target = "nome")
    @Mapping(source = "cursos", target = "cursos")
    EstudanteWithCurcoResponse toEstudanteWithCurcoResponsefromEntity(Estudante estudante);
}
