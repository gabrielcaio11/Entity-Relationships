package br.com.gabrielcaio.entityrelationships.model.estudante;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO for {@link Estudante}
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateEstudanteDTO {
    private String nome;
    private List<Long> cursosIDs = new ArrayList<>();
}
