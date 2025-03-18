package br.com.gabrielcaio.entityrelationships.controllers.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import br.com.gabrielcaio.entityrelationships.model.estudante.CreateEstudanteDTO;
import br.com.gabrielcaio.entityrelationships.model.estudante.Estudante;
import br.com.gabrielcaio.entityrelationships.model.estudante.EstudanteWithCurcoResponse;

@Mapper(componentModel = "spring")
public interface EstudanteMapper {
    EstudanteMapper INSTANCE = Mappers.getMapper(EstudanteMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    @Mapping(target = "cursos", ignore = true)
    Estudante toEntityFromCreateEstudanteDTO(CreateEstudanteDTO dto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "nome", target = "nome")
    @Mapping(source = "cursos", target = "cursos")
    EstudanteWithCurcoResponse toEstudanteWithCurcoResponsefromEntity(Estudante estudante);
}
