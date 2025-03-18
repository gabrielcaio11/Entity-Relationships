package br.com.gabrielcaio.entityrelationships.model.estudante;

import br.com.gabrielcaio.entityrelationships.model.curso.CursoResponseDTO;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EstudanteWithCurcoResponse {
    private Long id;
    private String nome;
    @Builder.Default
    private List<CursoResponseDTO> cursos = new ArrayList<>();
}
