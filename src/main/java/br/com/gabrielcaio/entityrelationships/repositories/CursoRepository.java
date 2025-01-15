package br.com.gabrielcaio.entityrelationships.repositories;

import br.com.gabrielcaio.entityrelationships.model.curso.Curso;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    boolean existsByNome(String nome);
}