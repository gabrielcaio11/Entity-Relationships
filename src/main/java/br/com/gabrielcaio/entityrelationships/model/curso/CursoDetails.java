package br.com.gabrielcaio.entityrelationships.model.curso;

import java.util.ArrayList;
import java.util.List;

import br.com.gabrielcaio.entityrelationships.model.estudante.EstudanteResponse;
import br.com.gabrielcaio.entityrelationships.model.instrutor.InstrutorWithPerfilResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CursoDetails {
    private Long id;
    private String nome;
    private InstrutorWithPerfilResponse instrutor;
    private List<EstudanteResponse> estudantes = new ArrayList<>();
}
