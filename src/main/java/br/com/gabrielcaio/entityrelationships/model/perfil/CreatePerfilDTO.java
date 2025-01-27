package br.com.gabrielcaio.entityrelationships.model.perfil;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePerfilDTO {
    @NotBlank(message = "A Biografia é um campo obrigatório.")
    @Size(max = 1234, min = 10, message = "a biografia do perfil do instrutor precisa estar no no intervalo de 10 a 1234 caracteres.")
    private String biografia;

    @NotBlank(message = "A Especilidade é um campo obrigatório.")
    @Size(max = 50, min = 5, message = "a especialidade do perfil do instrutor precisa estar no no intervalo de 5 a 50 caracteres.")
    private String especialidade;
}
