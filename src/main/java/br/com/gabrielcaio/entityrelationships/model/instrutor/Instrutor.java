package br.com.gabrielcaio.entityrelationships.model.instrutor;

import br.com.gabrielcaio.entityrelationships.model.curso.Curso;
import br.com.gabrielcaio.entityrelationships.model.perfil.Perfil;
import br.com.gabrielcaio.entityrelationships.model.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_instrutor")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Instrutor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @OneToMany(mappedBy = "instrutor", fetch = FetchType.LAZY)
    private List<Curso> cursos = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "perfil_id", referencedColumnName = "perfil_id", nullable = false)
    private Perfil perfil;

    @Column(updatable = false, name = "data_criacao")
    @CreatedDate
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    @LastModifiedDate
    private LocalDateTime dataAtualizacao;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
}

