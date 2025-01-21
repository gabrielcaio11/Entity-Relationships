package br.com.gabrielcaio.entityrelationships.model.estudante;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EstudanteResponse {
    private Long id;
    private String nome;
}
