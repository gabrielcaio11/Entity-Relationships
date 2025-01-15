package br.com.gabrielcaio.entityrelationships.controllers;

import br.com.gabrielcaio.entityrelationships.controllers.mapper.InstrutorMapper;
import br.com.gabrielcaio.entityrelationships.model.instrutor.CreateInstrutorDTO;
import br.com.gabrielcaio.entityrelationships.model.instrutor.InstrutorWithPerfilResponse;
import br.com.gabrielcaio.entityrelationships.service.InstrutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/instrutores")
@RequiredArgsConstructor
public class InstrutorController {

    private final InstrutorService instrutorService;

    @PostMapping
    public ResponseEntity<InstrutorWithPerfilResponse> salvar(@RequestBody CreateInstrutorDTO dto){
        var instrutor = instrutorService.salvar(dto);
        var response = InstrutorMapper.INSTANCE
                .toInstrutorWithPerfilResponseFromInstrutor(instrutor);
        return ResponseEntity.ok(response);
    }
}
