package br.com.gabrielcaio.entityrelationships.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.web.SecurityFilterChain;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.UUID;

@Configuration
@EnableWebSecurity
public class AuthorizationServerConfiguration {

    /**
     * Configures the security filter chain for the authorization server.
     *
     * This method applies the default security configuration of the OAuth2 Authorization Server,
     * enables support for OpenID Connect (OIDC), configures the OAuth2 resource server to use JWT,
     * and enables form login.
     *
     * @param http Instance of HttpSecurity to configure HTTP security.
     * @return Configured instance of SecurityFilterChain.
     * @throws Exception If an error occurs during security configuration.
     */
    @Bean
    @Order(1)
    public SecurityFilterChain authServerSecurityFilterChain(HttpSecurity http) throws Exception {

        // Apply the default security configuration of the OAuth2 Authorization Server
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

        // Enable support for OpenID Connect (OIDC)
        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(Customizer.withDefaults());

        // Configure the OAuth2 resource server to use JWT
        http.oauth2ResourceServer(oauth2Rs -> oauth2Rs.jwt(Customizer.withDefaults()));

        // Enable form login
        http.formLogin(Customizer.withDefaults());

        // Build and return the configured security filter chain
        return http.build();
    }

    /**
    * Defines a bean for password encoding.
    * The BCryptPasswordEncoder is a secure and widely used encoder.
    *
    * @return Instance of PasswordEncoder using BCrypt.
    */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Cria uma instância do BCryptPasswordEncoder com força de hashing 10.
        return new BCryptPasswordEncoder(10);
    }


    /**
     * Defines a bean for token settings.
     *
     * This bean configures the format and lifetime of the access token.
     * The SELF_CONTAINED format indicates that the token is self-contained,
     * meaning all necessary information is within the token itself.
     * The access token lifetime is set to 10 minutes.
     *
     * @return Instance of TokenSettings with the defined configurations.
     */
    @Bean
    public TokenSettings tokenSettings() {
        return TokenSettings.builder()
                .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
                .accessTokenTimeToLive(Duration.ofMinutes(10))
                .build();
    }

    /**
     * Defines a bean for client settings.
     *
     * This bean configures the client settings for the authorization server.
     * The requireAuthorizationConsent is set to false, meaning that the client
     * does not require user consent for authorization.
     *
     * @return Instance of ClientSettings with the defined configurations.
     */
    @Bean
    public ClientSettings clientSettings() {
        return ClientSettings.builder()
                .requireAuthorizationConsent(false)
                .build();
    }


    /**
     * Defines a bean for the JSON Web Key (JWK) source.
     *
     * This bean generates an RSA key pair, creates a JWK set with the generated key,
     * and returns an immutable JWK source.
     *
     * @return Instance of JWKSource containing the JWK set.
     * @throws Exception If an error occurs during key generation.
     */
    @Bean
    public JWKSource<SecurityContext> jwkSource() throws Exception {
        RSAKey rsaKey = gerarChaveRSA();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

   /**
    * Generates an RSA key pair and returns it as an RSAKey.
    *
    * This method uses the RSA algorithm to generate a key pair with a key size of 2048 bits.
    * The generated key pair includes a public key and a private key.
    * The public key and private key are then used to create an RSAKey instance.
    * A unique key ID is also generated and associated with the RSAKey.
    *
    * @return An instance of RSAKey containing the generated RSA key pair.
    * @throws Exception If an error occurs during key generation.
    */
   private RSAKey gerarChaveRSA() throws Exception {
       // Create a KeyPairGenerator instance for the RSA algorithm
       KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");

       // Initialize the KeyPairGenerator with a key size of 2048 bits
       keyPairGenerator.initialize(2048);

       // Generate the RSA key pair
       KeyPair keyPair = keyPairGenerator.generateKeyPair();

       // Extract the public key from the generated key pair
       RSAPublicKey chavePublica = (RSAPublicKey) keyPair.getPublic();

       // Extract the private key from the generated key pair
       RSAPrivateKey chavePrivada = (RSAPrivateKey) keyPair.getPrivate();

       // Create and return an RSAKey instance with the generated keys and a unique key ID
       return new RSAKey
               .Builder(chavePublica)
               .privateKey(chavePrivada)
               .keyID(UUID.randomUUID().toString())
               .build();
   }
    /**
     * Defines a bean for JWT decoder.
     *
     * This bean configures the JWT decoder using the provided JWK source.
     *
     * @param jwkSource The source of JSON Web Keys (JWK).
     * @return Instance of JwtDecoder configured with the provided JWK source.
     */
    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }
}
