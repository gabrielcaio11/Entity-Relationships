package br.com.gabrielcaio.entityrelationships.model.curso;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * DTO for {@link Curso}
 */
@Getter
@AllArgsConstructor
@Builder
@Schema(name = "CursoResponseDTO", description = "DTO de resposta para um curso")
public class CursoResponseDTO {

    @Schema(description = "Identificador do curso", example = "1")
    private Long id;

    @Schema(description = "Nome do curso", example = "Java para iniciantes")
    private String nome;
}
