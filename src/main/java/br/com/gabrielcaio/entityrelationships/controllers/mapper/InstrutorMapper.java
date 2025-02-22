package br.com.gabrielcaio.entityrelationships.controllers.mapper;

import br.com.gabrielcaio.entityrelationships.model.instrutor.CreateInstrutorDTO;
import br.com.gabrielcaio.entityrelationships.model.instrutor.Instrutor;
import br.com.gabrielcaio.entityrelationships.model.instrutor.InstrutorWithPerfilResponse;
import br.com.gabrielcaio.entityrelationships.model.instrutor.UpdateInstrutorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {PerfilMapper.class})
public interface InstrutorMapper {

    InstrutorMapper INSTANCE = Mappers.getMapper(InstrutorMapper.class);

    InstrutorWithPerfilResponse toInstrutorWithPerfilResponseFromInstrutor(Instrutor instrutor);

    Instrutor toEntityFromCreateInstrutorDTO(CreateInstrutorDTO dto);

    Instrutor toEntityFromUpdateInstrutorDTO(UpdateInstrutorDTO dto);
}
