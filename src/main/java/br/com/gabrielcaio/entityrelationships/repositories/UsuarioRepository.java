package br.com.gabrielcaio.entityrelationships.repositories;

import br.com.gabrielcaio.entityrelationships.model.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    /**
     * Retrieves a user by their login.
     *
     * @param login the login of the user
     * @return the user with the given login
     */
    Optional<Usuario> findByLogin(String login);

    /**
     * Retrieves a user by their email.
     *
     * @param email the email of the user
     * @return the user with the given email
     */
    Optional<Usuario> findByEmail(String email);
}