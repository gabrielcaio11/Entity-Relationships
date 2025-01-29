package br.com.gabrielcaio.entityrelationships.security;

import br.com.gabrielcaio.entityrelationships.model.usuario.Usuario;
import br.com.gabrielcaio.entityrelationships.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Implementação personalizada de AuthenticationProvider para autenticação de usuários.
 * Esta classe é responsável por validar as credenciais do usuário (login e senha)
 * e retornar um objeto de autenticação personalizado (CustomAuthentication) em caso de sucesso.
 */
@Component // Indica que esta classe é um componente gerenciado pelo Spring.
@RequiredArgsConstructor // Gera um construtor com todos os campos obrigatórios (final).
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UsuarioService usuarioService; // Serviço para buscar usuários.
    private final PasswordEncoder encoder; // Codificador de senhas para verificar a senha.

    /**
     * Método principal para autenticar um usuário.
     * Verifica se o login e a senha fornecidos correspondem a um usuário válido.
     *
     * @param authentication Objeto de autenticação contendo o login e a senha.
     * @return Um objeto Authentication personalizado (CustomAuthentication) em caso de sucesso.
     * @throws AuthenticationException Se a autenticação falhar (usuário não encontrado ou senha incorreta).
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // Obtém o login do objeto de autenticação.
        String login = authentication.getName();

        // Obtém a senha digitada do objeto de autenticação.
        String senhaDigitada = authentication.getCredentials().toString();

        // Busca o usuário pelo login fornecido.
        Usuario usuarioEncontrado = usuarioService.obterPorLogin(login);

        // Verifica se o usuário foi encontrado.
        if (usuarioEncontrado == null) {
            // Lança uma exceção se o usuário não for encontrado.
            throw getErroUsuarioNaoEncontrado();
        }

        // Obtém a senha criptografada do usuário encontrado.
        String senhaCriptografada = usuarioEncontrado.getSenha();

        // Verifica se a senha digitada corresponde à senha criptografada.
        boolean senhasBatem = isMatches(senhaDigitada, senhaCriptografada);

        // Se as senhas corresponderem, retorna um objeto de autenticação personalizado.
        if (senhasBatem) {
            return new CustomAuthentication(usuarioEncontrado);
        }

        // Lança uma exceção se as senhas não corresponderem.
        throw getErroUsuarioNaoEncontrado();
    }

    /**
     * Verifica se a senha digitada corresponde à senha criptografada.
     *
     * @param senhaDigitada Senha digitada pelo usuário (não criptografada).
     * @param senhaCriptografada Senha criptografada armazenada no banco de dados.
     * @return true se as senhas corresponderem, false caso contrário.
     */
    private boolean isMatches(String senhaDigitada, String senhaCriptografada) {
        return encoder.matches(senhaDigitada, senhaCriptografada);
    }

    /**
     * Retorna uma exceção personalizada para usuário não encontrado ou senha incorreta.
     *
     * @return UsernameNotFoundException com uma mensagem de erro.
     */
    private UsernameNotFoundException getErroUsuarioNaoEncontrado() {
        return new UsernameNotFoundException("Usuário e/ou senha incorretos!");
    }

    /**
     * Verifica se este AuthenticationProvider suporta o tipo de autenticação fornecido.
     *
     * @param authentication Classe de autenticação a ser verificada.
     * @return true se a classe for compatível com UsernamePasswordAuthenticationToken, false caso contrário.
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}