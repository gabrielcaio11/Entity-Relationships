package br.com.gabrielcaio.entityrelationships.controllers;


import br.com.gabrielcaio.entityrelationships.controllers.mapper.UsuarioMapper;
import br.com.gabrielcaio.entityrelationships.model.usuario.UsuarioDTO;
import br.com.gabrielcaio.entityrelationships.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid UsuarioDTO dto){
        var usuario = UsuarioMapper.INSTANCE.toEntity(dto);
        service.salvar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
