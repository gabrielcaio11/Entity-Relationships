package br.com.gabrielcaio.entityrelationships.security;

import br.com.gabrielcaio.entityrelationships.model.usuario.Usuario;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Implementação personalizada da interface Authentication do Spring Security.
 * Representa a autenticação de um usuário na aplicação, encapsulando o usuário autenticado
 * e suas autoridades (roles).
 */
@RequiredArgsConstructor // Gera um construtor com todos os campos obrigatórios (final).
@Getter // Gera automaticamente os métodos getters para os campos.
public class CustomAuthentication implements Authentication {

    private final Usuario usuario; // Usuário autenticado.

    /**
     * Retorna as autoridades (roles) do usuário autenticado.
     * As roles são convertidas em instâncias de GrantedAuthority para serem usadas pelo Spring Security.
     *
     * @return Coleção de GrantedAuthority representando as roles do usuário.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

//          A conversão das roles do usuário em instâncias de GrantedAuthority
//          é necessária para que o Spring Security possa gerenciar
//          as permissões e autorizações do usuário de forma adequada.
//          O Spring Security utiliza objetos do tipo GrantedAuthority
//          para representar as permissões concedidas a um usuário.
//          Ao converter as roles do usuário em SimpleGrantedAuthority,
//          o código garante que o Spring Security possa interpretar
//          e aplicar corretamente essas permissões
//          durante o processo de autenticação e autorização.

        return this.usuario
                .getRoles() // Obtém as roles do usuário.
                .stream() // Converte a lista de roles em um stream.
                .map(SimpleGrantedAuthority::new) // Mapeia cada role para uma instância de SimpleGrantedAuthority.
                .collect(Collectors.toList()); // Coleta as autoridades em uma lista.
    }

    /**
     * Retorna as credenciais do usuário (não utilizado nesta implementação).
     *
     * @return null, pois as credenciais não são necessárias aqui.
     */
    @Override
    public Object getCredentials() {
        return null;
    }

    /**
     * Retorna os detalhes do usuário autenticado.
     *
     * @return O objeto Usuario autenticado.
     */
    @Override
    public Object getDetails() {
        return usuario;
    }

    /**
     * Retorna o principal (identidade do usuário autenticado).
     *
     * @return O objeto Usuario autenticado.
     */
    @Override
    public Object getPrincipal() {
        return usuario;
    }

    /**
     * Indica se a autenticação foi concluída com sucesso.
     *
     * @return true, pois esta implementação assume que o usuário já está autenticado.
     */
    @Override
    public boolean isAuthenticated() {
        // se colocar false, o usuário não consegue acessar a aplicação
        return true; // Retorna true, indicando que o usuário está autenticado.
    }

    /**
     * Define o status de autenticação do usuário.
     * Este método não é utilizado nesta implementação, mas é necessário devido à interface Authentication.
     *
     * @param isAuthenticated true se o usuário estiver autenticado, false caso contrário.
     * @throws IllegalArgumentException Se o valor passado for inválido.
     */
    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        // Este método não faz nada nesta implementação, mas é obrigatório devido à interface.
    }

    /**
     * Retorna o nome do usuário autenticado (login).
     *
     * @return O login do usuário.
     */
    @Override
    public String getName() {
        return usuario.getLogin(); // Retorna o login do usuário.
    }
}