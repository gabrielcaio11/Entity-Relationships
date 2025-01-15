package br.com.gabrielcaio.entityrelationships.model.instrutor;

import br.com.gabrielcaio.entityrelationships.model.perfil.CreatePerfilDTO;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateInstrutorDTO {
    private String nome;
    private CreatePerfilDTO perfil;
}
