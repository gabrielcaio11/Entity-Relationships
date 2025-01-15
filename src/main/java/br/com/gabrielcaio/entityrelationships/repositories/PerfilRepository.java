package br.com.gabrielcaio.entityrelationships.repositories;

import br.com.gabrielcaio.entityrelationships.model.perfil.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
}