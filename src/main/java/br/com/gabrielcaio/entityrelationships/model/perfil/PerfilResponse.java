package br.com.gabrielcaio.entityrelationships.model.perfil;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
