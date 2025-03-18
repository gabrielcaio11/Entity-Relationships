package br.com.gabrielcaio.entityrelationships.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gabrielcaio.entityrelationships.model.perfil.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
}