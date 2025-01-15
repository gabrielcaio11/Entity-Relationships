package br.com.gabrielcaio.entityrelationships.model.curso;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class CursoResponseDTO {
    private Long id;
    private String nome;
}
