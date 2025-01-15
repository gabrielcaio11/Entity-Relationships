package br.com.gabrielcaio.entityrelationships.model.perfil;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PerfilResponse {
    private Long id;
    private String biografia;
    private String especialidade;
}
