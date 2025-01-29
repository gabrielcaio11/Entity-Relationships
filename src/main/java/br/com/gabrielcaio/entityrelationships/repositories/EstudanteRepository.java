package br.com.gabrielcaio.entityrelationships.repositories;

import br.com.gabrielcaio.entityrelationships.model.estudante.Estudante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EstudanteRepository extends JpaRepository<Estudante, Long> {

    /**
     * Checks if a student with the given name exists, ignoring case.
     *
     * @param nomeDoEstudante the name of the student
     * @return true if a student with the given name exists, false otherwise
     */
    @Query("SELECT COUNT(e) > 0 FROM Estudante e WHERE LOWER(e.nome) = LOWER(:nomeDoEstudante)")
    boolean existsByNomeIgnoreCase(@Param("nomeDoEstudante") String nomeDoEstudante);

    /**
     * Retrieves a student by ID along with their courses.
     *
     * @param id the ID of the student
     * @return an Optional containing the student with their courses if found, otherwise an empty Optional
     */
    @Query("""
            SELECT e FROM Estudante e JOIN FETCH e.cursos WHERE e.id = :id
            """)
    Optional<Estudante> getEstudanteByIdWithCurso(@Param("id") Long id);
}
