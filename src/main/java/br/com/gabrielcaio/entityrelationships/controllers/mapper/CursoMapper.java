package br.com.gabrielcaio.entityrelationships.controllers.mapper;


import br.com.gabrielcaio.entityrelationships.model.curso.CreateCursoDTO;
import br.com.gabrielcaio.entityrelationships.model.curso.Curso;
import br.com.gabrielcaio.entityrelationships.model.curso.CursoDetails;
import br.com.gabrielcaio.entityrelationships.model.curso.CursoResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {EstudanteMapper.class, InstrutorMapper.class})
public interface CursoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "nome", source = "nome")
    Curso toEntity(CreateCursoDTO dto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    CursoResponseDTO toCursoResponseDTO(Curso curso);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    @Mapping(target = "instrutor", source = "instrutor")
    @Mapping(target = "estudantes", source = "estudantes")
    CursoDetails toCursoDetails(Curso curso);

}
