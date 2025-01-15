package br.com.gabrielcaio.entityrelationships.model.estudante;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateEstudanteDTO {
    private String nome;
    private List<Long> cursosIDs = new ArrayList<>();
}
