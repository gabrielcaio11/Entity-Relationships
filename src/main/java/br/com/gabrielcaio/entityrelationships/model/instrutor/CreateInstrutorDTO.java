package br.com.gabrielcaio.entityrelationships.model.instrutor;

import br.com.gabrielcaio.entityrelationships.model.perfil.CreatePerfilDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link Instrutor}
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateInstrutorDTO {
    private String nome;
    private CreatePerfilDTO perfil;
}
