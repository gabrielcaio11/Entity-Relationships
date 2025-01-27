package br.com.gabrielcaio.entityrelationships.model.perfil;

import br.com.gabrielcaio.entityrelationships.model.instrutor.Instrutor;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
