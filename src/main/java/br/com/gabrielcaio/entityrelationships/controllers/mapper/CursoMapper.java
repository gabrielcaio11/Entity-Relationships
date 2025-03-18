package br.com.gabrielcaio.entityrelationships.controllers.mapper;

import br.com.gabrielcaio.entityrelationships.model.curso.CreateCursoDTO;
import br.com.gabrielcaio.entityrelationships.model.curso.Curso;
import br.com.gabrielcaio.entityrelationships.model.curso.CursoDetails;
import br.com.gabrielcaio.entityrelationships.model.curso.CursoResponseDTO;
import br.com.gabrielcaio.entityrelationships.model.instrutor.UpdateInstrutorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CursoMapper {

    CursoMapper INSTANCE = Mappers.getMapper(CursoMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    @Mapping(target = "estudantes", ignore = true)
    @Mapping(target = "instrutor", ignore = true)
    Curso toEntityFromCreateCursoDTO(CreateCursoDTO dto);

    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)ø
    @Mapping(target = "dataAtualizacao", ignore = true)
    @Mapping(target = "estudantes", ignore = true)
    @Mapping(target = "instrutor", ignore = true)
    Curso toEntityFromUpdateInstrutorDTO(UpdateInstrutorDTO dto);
    
    CursoResponseDTO toCursoResponseFromEntity(Curso curso);
    CursoDetails toCursoDetailsFromEntity(Curso curso);
    
}
