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

@AllArgsConstructor
@NoArgsConstructor
public class CursoDetails {
    private Long id;
    private String nome;
    private InstrutorWithPerfilResponse instrutor;
    private List<EstudanteResponse> estudantes = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public InstrutorWithPerfilResponse getInstrutor() {
        return instrutor;
    }

    public List<EstudanteResponse> getEstudantes() {
        return estudantes;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setInstrutor(InstrutorWithPerfilResponse instrutor) {
        this.instrutor = instrutor;
    }

    public void setEstudantes(List<EstudanteResponse> estudantes) {
        this.estudantes = estudantes;
    }
}
