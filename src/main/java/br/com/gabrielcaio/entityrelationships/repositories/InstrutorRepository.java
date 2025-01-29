package br.com.gabrielcaio.entityrelationships.repositories;

import br.com.gabrielcaio.entityrelationships.model.instrutor.Instrutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface InstrutorRepository extends JpaRepository<Instrutor, Long> {


    /**
     * Checks if an instructor with the given name exists, ignoring case.
     *
     * @param nome the name of the instructor
     * @return true if an instructor with the given name exists, false otherwise
     */
    boolean existsByNomeIgnoreCase(String nome);

    /**
     * Retrieves an instructor by ID along with their profile and courses.
     *
     * @param id the ID of the instructor
     * @return an Optional containing the instructor with their profile and courses if found, otherwise an empty Optional
     */
    @Query("""
                    SELECT i FROM Instrutor i 
                    JOIN FETCH i.perfil 
                    LEFT JOIN FETCH i.cursos WHERE i.id = :id
            """)
    Optional<Instrutor> findByIdWithPerfilAndCursos(@Param("id") Long id);

    /**
     * Retrieves all instructors along with their profiles and courses, with pagination support.
     *
     * @param pageable the pagination information
     * @return a page of instructors with their profiles and courses
     */
    @Query("""
                    SELECT i FROM Instrutor i
                    JOIN FETCH i.perfil
                    LEFT JOIN FETCH i.cursos
            """)
    Page<Instrutor> findAllIdWithPerfilAndCursos(Pageable pageable);
}