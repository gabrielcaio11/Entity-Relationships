package br.com.gabrielcaio.entityrelationships.model.estudante;

import java.util.ArrayList;
import java.util.List;

import br.com.gabrielcaio.entityrelationships.model.curso.CursoResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
