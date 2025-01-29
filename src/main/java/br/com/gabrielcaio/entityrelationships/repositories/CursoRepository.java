package br.com.gabrielcaio.entityrelationships.repositories;

import br.com.gabrielcaio.entityrelationships.model.curso.Curso;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    /**
     * Checks if a course with the given name exists.
     *
     * @param nome the name of the course
     * @return true if a course with the given name exists, false otherwise
     */
    boolean existsByNome(String nome);
}