package br.com.gabrielcaio.entityrelationships.model.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record CreateUsuarioDTO(
        @NotBlank(message = "campo obrigatorio")
        String login,
        @Email (message = "inválido")
        @NotBlank(message = "campo obrigatorio")
        String email,
        @NotBlank(message = "campo obrigatorio")
        String senha,
        List<String> roles) {
}
