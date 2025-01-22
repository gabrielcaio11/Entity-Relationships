package br.com.gabrielcaio.entityrelationships.repositories;

import br.com.gabrielcaio.entityrelationships.model.instrutor.Instrutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface InstrutorRepository extends JpaRepository<Instrutor, Long> {

    // Verifica se existe um instrutor com o nome ignorando maiúsculas e minúsculas
    boolean existsByNomeIgnoreCase(String nome);

    @Query("""
        SELECT i FROM Instrutor i 
        JOIN FETCH i.perfil 
        LEFT JOIN FETCH i.cursos WHERE i.id = :id
""")
    Optional<Instrutor> findByIdWithPerfilAndCursos(@Param("id") Long id);

    @Query("""
        SELECT i FROM Instrutor i
        JOIN FETCH i.perfil
        LEFT JOIN FETCH i.cursos
        
""")
    Page<Instrutor> findAllIdWithPerfilAndCursos(Pageable pageable);
}