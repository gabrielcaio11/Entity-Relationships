package br.com.gabrielcaio.entityrelationships.controllers.mapper;

import br.com.gabrielcaio.entityrelationships.model.instrutor.CreateInstrutorDTO;
import br.com.gabrielcaio.entityrelationships.model.instrutor.Instrutor;
import br.com.gabrielcaio.entityrelationships.model.instrutor.InstrutorWithPerfilResponse;
import br.com.gabrielcaio.entityrelationships.model.instrutor.UpdateInstrutorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {PerfilMapper.class})
public interface InstrutorMapper {
    InstrutorMapper INSTANCE = Mappers.getMapper(InstrutorMapper.class);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    @Mapping(target = "cursos", ignore = true)
    @Mapping(target = "perfil", source = "perfil")
    Instrutor toEntityFromCreateInstrutorDTO(CreateInstrutorDTO dto);
    
    @Mapping(source = "id", target = "id")
    @Mapping(source = "nome", target = "nome")
    @Mapping(source = "perfil", target = "perfil")
    InstrutorWithPerfilResponse toInstrutorWithPerfilResponseFromInstrutor(Instrutor instrutor);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    @Mapping(target = "cursos", ignore = true)
    @Mapping(target = "perfil", ignore = true)
    Instrutor toEntityFromUpdateInstrutorDTO(UpdateInstrutorDTO dto);
}
