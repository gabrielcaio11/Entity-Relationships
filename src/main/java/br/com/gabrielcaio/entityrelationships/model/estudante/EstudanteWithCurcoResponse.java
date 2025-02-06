package br.com.gabrielcaio.entityrelationships.model.estudante;

import br.com.gabrielcaio.entityrelationships.model.curso.CursoResponseDTO;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO for {@link Estudante}
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EstudanteWithCurcoResponse {
    private Long id;
    private String nome;
    private List<CursoResponseDTO> cursos = new ArrayList<>();
}
