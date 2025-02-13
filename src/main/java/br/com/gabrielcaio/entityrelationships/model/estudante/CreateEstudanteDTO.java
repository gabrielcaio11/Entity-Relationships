package br.com.gabrielcaio.entityrelationships.model.estudante;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
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
@Schema(name = "CreateEstudanteDTO", description = "DTO para criação de um estudante")
public class CreateEstudanteDTO {

    @Schema(description = "Nome do estudante", example = "Gabriel Caio", required = true)
    @NotBlank(message = "Campo obrigatório")
    private String nome;

    @ArraySchema(
            schema = @Schema(description = "IDs dos cursos associados", example = "[1]", type = "integer")
    )
    @NotNull(message = "Campo obrigatório")
    @Size(min = 1, message = "O aluno deve se matricular com pelo menos um curso")
    private List<Long> cursosIDs = new ArrayList<>();
}
