package br.com.gabrielcaio.entityrelationships.security;

import br.com.gabrielcaio.entityrelationships.model.usuario.Usuario;
import br.com.gabrielcaio.entityrelationships.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Implementação personalizada de UserDetailsService.
 * Esta classe é responsável por carregar os detalhes do usuário (UserDetails) com base no login fornecido.
 * É usada pelo Spring Security para autenticação e autorização.
 */
@RequiredArgsConstructor // Gera um construtor com todos os campos obrigatórios (final).
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioService service; // Serviço para buscar usuários no banco de dados.

    /**
     * Carrega os detalhes do usuário com base no login fornecido.
     *
     * @param login O login do usuário a ser carregado.
     * @return UserDetails contendo as informações do usuário (login, senha e roles).
     * @throws UsernameNotFoundException Se o usuário não for encontrado.
     */
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        // Busca o usuário pelo login no banco de dados.
        Usuario usuario = service.obterPorLogin(login);

        // Verifica se o usuário foi encontrado.
        if (usuario == null) {
            // Lança uma exceção se o usuário não for encontrado.
            throw new UsernameNotFoundException("Usuário não encontrado!");
        }

        // Constrói e retorna um objeto UserDetails com as informações do usuário.
        return User.builder()
                .username(usuario.getLogin()) // Define o login do usuário.
                .password(usuario.getSenha()) // Define a senha do usuário.
                .roles(usuario.getRoles().toArray(new String[usuario.getRoles().size()])) // Define as roles do usuário.
                .build();
    }
}