package br.com.gabrielcaio.entityrelationships.model.curso;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCursoDTO {
    @NotNull
    Long instrutorId;
}
