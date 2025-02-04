package br.com.gabrielcaio.entityrelationships.service;

import br.com.gabrielcaio.entityrelationships.model.usuario.Usuario;
import br.com.gabrielcaio.entityrelationships.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public void salvar(Usuario usuario){
        var senha = usuario.getSenha();
        usuario.setSenha(passwordEncoder.encode(senha));
        usuarioRepository.save(usuario);
    }

    public Optional<Usuario> obterPorLogin(String login){
        return usuarioRepository.findByLogin(login);
    }

    public Optional<Usuario> obterPorEmail(String email){
        return usuarioRepository.findByEmail(email);
    }
}
