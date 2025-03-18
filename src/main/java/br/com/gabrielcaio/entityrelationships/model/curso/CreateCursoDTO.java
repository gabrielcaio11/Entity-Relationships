package br.com.gabrielcaio.entityrelationships.model.curso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCursoDTO {
    @NotBlank
    @Size(max = 100, min = 5, message = "o nome do curso precisa estar no no intervalo de 5 a 100 caracteres.")
    private String nome;

    @NotNull(message = "o numero do identificador do instrutor não pode ser null.")
    private Long instrutorId;
}
