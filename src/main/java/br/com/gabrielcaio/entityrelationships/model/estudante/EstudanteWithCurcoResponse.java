package br.com.gabrielcaio.entityrelationships.model.estudante;

import br.com.gabrielcaio.entityrelationships.model.curso.CursoResponseDTO;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO for {@link Estudante}
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "Estudante With Curco Response", description = "Estudante com seus respectivos cursos associados")
public class EstudanteWithCurcoResponse {

    @Schema(description = "Identificador do estudante", example = "1")
    private Long id;

    @Schema(description = "Nome do estudante", example = "Gabriel Caio")
    private String nome;

    @ArraySchema(
            schema = @Schema(implementation = CursoResponseDTO.class)
    )
    private List<CursoResponseDTO> cursos = new ArrayList<>();
}
