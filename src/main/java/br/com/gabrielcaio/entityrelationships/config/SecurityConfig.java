package br.com.gabrielcaio.entityrelationships.config;

import br.com.gabrielcaio.entityrelationships.security.LoginSocialSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Classe de configuração do Spring Security.
 * Define as regras de segurança, autenticação e autorização para a aplicação.
 */
@Configuration // Indica que esta classe é uma classe de configuração do Spring.
@EnableWebSecurity // Habilita a configuração de segurança para a aplicação.
@EnableMethodSecurity( // Habilita a segurança em nível de método.
        securedEnabled = true, // Permite o uso da anotação @Secured.
        jsr250Enabled = true // Permite o uso de anotações JSR-250, como @RolesAllowed.
)
public class SecurityConfig {

    /**
     * Configura a cadeia de filtros de segurança (SecurityFilterChain).
     * Define como as requisições HTTP serão tratadas em termos de autenticação e autorização.
     *
     * @param http Objeto HttpSecurity usado para configurar as regras de segurança.
     * @param successHandler Manipulador de sucesso personalizado para autenticação OAuth2.
     * @return SecurityFilterChain configurado.
     * @throws Exception Caso ocorra algum erro durante a configuração.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            LoginSocialSuccessHandler successHandler
    ) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // Desabilita a proteção CSRF (comum em APIs RESTful).
                .formLogin(Customizer.withDefaults()) // Habilita o login baseado em formulário com configurações padrão.
                .httpBasic(Customizer.withDefaults()) // Habilita a autenticação básica HTTP (usando username e password).
                .authorizeHttpRequests(authorize -> {
                    // Permite requisições POST para /usuarios/** sem autenticação.
                    authorize.requestMatchers(HttpMethod.POST, "/usuarios/**").permitAll();

                    // Exige autenticação para todas as outras requisições.
                    authorize.anyRequest().authenticated();
                })
                .oauth2Login(oauth2 -> {
                    // Configura o login OAuth2 (autenticação social, como Google, Facebook, etc.).
                    oauth2
                            .successHandler(successHandler); // Define o manipulador de sucesso para autenticação OAuth2.
                })
                .build(); // Constrói e retorna a cadeia de filtros de segurança.
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> web.ignoring().requestMatchers(
                "/v2/api-docs/**",
                "/v3/api-docs/**",
                "/swagger-resources/**",
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/webjars/**",
                "/actuator/**"
        );
    }

    /**
     * Define um bean para codificar senhas.
     * O BCryptPasswordEncoder é um codificador seguro e amplamente utilizado.
     *
     * @return Instância de PasswordEncoder usando BCrypt.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Cria uma instância do BCryptPasswordEncoder com força de hashing 10.
        return new BCryptPasswordEncoder(10);
    }

    /**
     * Personaliza o prefixo padrão para autoridades (roles) no Spring Security.
     * Por padrão, o Spring Security adiciona o prefixo "ROLE_" às roles.
     * Este método remove esse prefixo, permitindo que as roles sejam usadas sem ele.
     *
     * @return Instância de GrantedAuthorityDefaults com prefixo vazio.
     */
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        // Retorna uma instância de GrantedAuthorityDefaults com prefixo vazio.
        return new GrantedAuthorityDefaults("");
    }
}