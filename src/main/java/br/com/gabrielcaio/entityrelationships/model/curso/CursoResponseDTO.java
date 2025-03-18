package br.com.gabrielcaio.entityrelationships.model.curso;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class CursoResponseDTO {
    private Long id;
    private String nome;
}
