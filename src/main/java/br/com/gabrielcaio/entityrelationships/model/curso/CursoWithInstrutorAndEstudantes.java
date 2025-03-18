package br.com.gabrielcaio.entityrelationships.model.curso;

import br.com.gabrielcaio.entityrelationships.model.estudante.Estudante;
import br.com.gabrielcaio.entityrelationships.model.instrutor.Instrutor;
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
public class CursoWithInstrutorAndEstudantes {
    private Long id;
    private String nome;
    private Instrutor instrutor;
    private Estudante estudante;
}
