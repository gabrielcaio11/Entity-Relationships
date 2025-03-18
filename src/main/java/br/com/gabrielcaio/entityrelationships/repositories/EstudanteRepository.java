package br.com.gabrielcaio.entityrelationships.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.gabrielcaio.entityrelationships.model.estudante.Estudante;

public interface EstudanteRepository extends JpaRepository<Estudante, Long> {
    @Query("SELECT COUNT(e) > 0 FROM Estudante e WHERE LOWER(e.nome) = LOWER(:nomeDoEstudante)")
    boolean existsByNomeIgnoreCase(@Param("nomeDoEstudante") String nomeDoEstudante);

    @Query("""
            SELECT e FROM Estudante e JOIN FETCH e.cursos WHERE e.id = :id
            """)
    Optional<Estudante> getEstudanteByIdWithCurso(Long id);
}
