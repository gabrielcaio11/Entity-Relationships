package br.com.gabrielcaio.entityrelationships.security;

import br.com.gabrielcaio.entityrelationships.model.usuario.Usuario;
import br.com.gabrielcaio.entityrelationships.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityService {
    private final UsuarioService usuarioService;

    public Usuario getUserAuthenticated(){
        // Recupera o objeto de autenticação do contexto de segurança
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Verifica se o objeto de autenticação é uma instância de CustomAuthentication
        if(authentication instanceof CustomAuthentication customAuth){
            // Se for, retorna o objeto Usuario contido no CustomAuthentication
            return customAuth.getUsuario();
        }
        // Caso contrário, retorna null
        return null;
    }
}
