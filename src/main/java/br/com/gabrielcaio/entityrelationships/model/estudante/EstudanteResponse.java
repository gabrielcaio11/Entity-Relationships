package br.com.gabrielcaio.entityrelationships.model.estudante;

import lombok.*;

/**
 * DTO for {@link Estudante}
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EstudanteResponse {
    private Long id;
    private String nome;
}
