package br.com.gabrielcaio.entityrelationships.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> {

                    authorize.requestMatchers(HttpMethod.POST,"/estudantes").hasRole("ADMIN");
                    authorize.requestMatchers(HttpMethod.DELETE,"/estudantes/**").hasRole("ADMIN");

                    authorize.requestMatchers(HttpMethod.POST,"/instrutores").hasRole("ADMIN");
                    authorize.requestMatchers(HttpMethod.DELETE,"/instrutores/**").hasRole("ADMIN");
                    authorize.requestMatchers(HttpMethod.PUT,"/instrutores/**").hasAuthority("UPDATE_INSTRUTOR");

                    authorize.requestMatchers(HttpMethod.POST,"/cursos").hasRole("ADMIN");
                    authorize.requestMatchers(HttpMethod.DELETE,"/cursos/**").hasRole("ADMIN");
                    authorize.requestMatchers(HttpMethod.PUT,"/cursos/**").hasAuthority("UPDATE_CURSO");

                    authorize.anyRequest().authenticated();
                })
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("password")) // Criptografa a senha
                .roles("USER") // Por padrão, o Spring Security espera que os roles no banco ou no sistema sejam prefixados com "ROLE_"
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .roles("ADMIN")
                .authorities("UPDATE_INSTRUTOR","UPDATE_CURSO","POST_ESTUDANTE")
                .build();

        return new InMemoryUserDetailsManager(user, admin);

    }
}
