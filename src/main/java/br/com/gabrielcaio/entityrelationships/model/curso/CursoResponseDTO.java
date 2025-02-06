package br.com.gabrielcaio.entityrelationships.model.curso;

import lombok.*;

/**
 * DTO for {@link Curso}
 */
@Getter
@AllArgsConstructor
@Builder
public class CursoResponseDTO {
    private Long id;
    private String nome;
}
