package br.com.gabrielcaio.entityrelationships.repositories;

import br.com.gabrielcaio.entityrelationships.model.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
  Usuario findByLogin(String login);
  Usuario findByEmail(String email);
}