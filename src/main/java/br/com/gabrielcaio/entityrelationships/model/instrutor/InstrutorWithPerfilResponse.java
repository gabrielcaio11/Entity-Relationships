package br.com.gabrielcaio.entityrelationships.model.instrutor;

import br.com.gabrielcaio.entityrelationships.model.perfil.PerfilResponse;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InstrutorWithPerfilResponse {
    private Long id;
    private String nome;
    private PerfilResponse perfil;
}
