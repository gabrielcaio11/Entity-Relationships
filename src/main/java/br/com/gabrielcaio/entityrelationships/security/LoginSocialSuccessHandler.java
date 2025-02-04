package br.com.gabrielcaio.entityrelationships.security;

import br.com.gabrielcaio.entityrelationships.model.usuario.Usuario;
import br.com.gabrielcaio.entityrelationships.service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Manipulador de sucesso para autenticação social (OAuth2).
 * Esta classe é responsável por lidar com o fluxo de autenticação bem-sucedida via provedores sociais (Google, Facebook, etc.).
 * Ela busca o usuário no banco de dados com base no e-mail fornecido pelo provedor OAuth2 e, se necessário, cadastra um novo usuário.
 */
@Component // Indica que esta classe é um componente gerenciado pelo Spring.
@RequiredArgsConstructor // Gera um construtor com todos os campos obrigatórios (final).
public class LoginSocialSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final String SENHA_PADRAO = "33333333"; // Senha padrão para novos usuários cadastrados via OAuth2.

    private final UsuarioService usuarioService; // Serviço para buscar e salvar usuários no banco de dados.

    /**
     * Método chamado quando a autenticação OAuth2 é bem-sucedida.
     * Busca o usuário no banco de dados com base no e-mail fornecido pelo provedor OAuth2.
     * Se o usuário não existir, ele é cadastrado automaticamente.
     *
     * @param request Requisição HTTP.
     * @param response Resposta HTTP.
     * @param authentication Objeto de autenticação contendo os detalhes do usuário autenticado.
     * @throws ServletException Se ocorrer um erro durante o processamento da requisição.
     * @throws IOException Se ocorrer um erro de I/O.
     */
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws ServletException, IOException {

        // Converte o objeto de autenticação para OAuth2AuthenticationToken.
        OAuth2AuthenticationToken auth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;

        // Obtém o principal (usuário autenticado) do OAuth2.
        OAuth2User oAuth2User = auth2AuthenticationToken.getPrincipal();

        // Obtém o e-mail do usuário autenticado.
        String email = oAuth2User.getAttribute("email");

        // Busca o usuário no banco de dados pelo e-mail.
        Optional<Usuario> usuario = usuarioService.obterPorEmail(email);

        // Se o usuário não existir, cadastra um novo usuário.
        Usuario usuarioAuthenticacao = usuario.orElseGet(() -> cadastrarUsuarioNaBase(email));

        // Cria uma nova autenticação personalizada com o usuário encontrado ou cadastrado.
        authentication = new CustomAuthentication(usuarioAuthenticacao);

        // Define a autenticação no contexto de segurança do Spring.
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Chama o método da classe pai para continuar o fluxo de autenticação.
        super.onAuthenticationSuccess(request, response, authentication);
    }

    /**
     * Cadastra um novo usuário na base de dados com base no e-mail fornecido.
     *
     * @param email E-mail do usuário a ser cadastrado.
     * @return O usuário cadastrado.
     */
    private Usuario cadastrarUsuarioNaBase(String email) {
        Usuario usuario = new Usuario();
        usuario.setEmail(email); // Define o e-mail do usuário.
        usuario.setLogin(obterLoginApartirDoEmail(email)); // Gera um login a partir do e-mail.
        usuario.setSenha(SENHA_PADRAO); // Define uma senha padrão.
        usuario.setRoles(List.of("OPERADOR")); // Define a role padrão como "OPERADOR".

        // Salva o usuário no banco de dados.
        usuarioService.salvar(usuario);
        return usuario;
    }

    /**
     * Gera um login a partir do e-mail do usuário.
     * O login é obtido removendo a parte após o "@" no e-mail.
     *
     * @param email E-mail do usuário.
     * @return O login gerado.
     */
    private String obterLoginApartirDoEmail(String email) {
        return email.substring(0, email.indexOf("@"));
    }
}