package br.com.gabrielcaio.entityrelationships.model.estudante;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateEstudanteDTO {
    private String nome;
    private List<Long> cursosIDs = new ArrayList<>();
}
