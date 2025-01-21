package br.com.gabrielcaio.entityrelationships.model.perfil;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePerfilDTO {
    private String biografia;
    private String especialadade;
}
