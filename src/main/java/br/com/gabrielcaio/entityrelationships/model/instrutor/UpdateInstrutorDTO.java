package br.com.gabrielcaio.entityrelationships.model.instrutor;

import br.com.gabrielcaio.entityrelationships.model.perfil.UpdatePerfilDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link Instrutor}
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateInstrutorDTO {
    private String nome;
    private UpdatePerfilDTO perfil;
}
