package br.com.gabrielcaio.entityrelationships.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gabrielcaio.entityrelationships.model.curso.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    boolean existsByNome(String nome);
}