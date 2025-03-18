package br.com.gabrielcaio.entityrelationships.model.perfil;

import br.com.gabrielcaio.entityrelationships.model.instrutor.Instrutor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_perfil")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Perfil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "perfil_id")
    private Long id;

    @Column(name = "perfil_biografia")
    private String biografia;

    @Column(name = "perfil_especialidade")
    private String especialidade;

    @OneToOne(mappedBy = "perfil")
    private Instrutor instrutor;

}
