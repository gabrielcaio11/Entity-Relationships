package br.com.gabrielcaio.entityrelationships.model.curso;

import br.com.gabrielcaio.entityrelationships.model.estudante.EstudanteResponse;
import br.com.gabrielcaio.entityrelationships.model.instrutor.InstrutorWithPerfilResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO for {@link Curso}
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CursoDetails {
    private Long id;
    private String nome;
    private InstrutorWithPerfilResponse instrutor;
    private List<EstudanteResponse> estudantes = new ArrayList<>();
}
