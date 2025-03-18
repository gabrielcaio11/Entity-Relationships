package br.com.gabrielcaio.entityrelationships.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gabrielcaio.entityrelationships.controllers.mapper.InstrutorMapper;
import br.com.gabrielcaio.entityrelationships.model.instrutor.CreateInstrutorDTO;
import br.com.gabrielcaio.entityrelationships.model.instrutor.InstrutorWithPerfilResponse;
import br.com.gabrielcaio.entityrelationships.model.instrutor.UpdateInstrutorDTO;
import br.com.gabrielcaio.entityrelationships.service.InstrutorService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/instrutores")
@RequiredArgsConstructor
public class InstrutorController {

    private final InstrutorService instrutorService;

    @PostMapping
    public ResponseEntity<InstrutorWithPerfilResponse> salvar(@RequestBody CreateInstrutorDTO dto) {

        // chama o service para salvar o instrutor
        var instrutor = instrutorService.salvar(dto);

        // tranforma de instrutor para instrutor response
        var response = InstrutorMapper.INSTANCE
                .toInstrutorWithPerfilResponseFromInstrutor(instrutor);

        // retorna o instrutor response
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @GetMapping(value = "/{instrutorID}")
    public ResponseEntity<InstrutorWithPerfilResponse> getById(@PathVariable("instrutorID") Long instrutorID) {

        // chama o service para buscar o instrutor por um id especifico
        var instrutor = instrutorService.getById(instrutorID);

        // tranforma de instrutor para instrutor response
        var response = InstrutorMapper.INSTANCE
                .toInstrutorWithPerfilResponseFromInstrutor(instrutor);

        // retorna o instrutor response
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @GetMapping
    public ResponseEntity<Page<InstrutorWithPerfilResponse>> getAll(
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        // instancia do pageable para a busca paginada
        var pageable = PageRequest.of(pageNumber, pageSize);

        // chama o service para a busca paginada
        var page = instrutorService.findAll(pageable);

        // transforma a pagina de instrutor para uma pagina de instrutor response
        var pageResponse = page.map(InstrutorMapper.INSTANCE::toInstrutorWithPerfilResponseFromInstrutor);

        // retorna a pagina de instrutores response
        return ResponseEntity.status(HttpStatus.OK).body(pageResponse);
    }

    @DeleteMapping(value = "/{instrutorId}")
    public ResponseEntity<Void> delete(@PathVariable("instrutorId") Long instrutorId) {

        // chama o service para deletar o instrutor
        instrutorService.deleteById(instrutorId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @PutMapping(value = "/{instrutorID}")
    public ResponseEntity<InstrutorWithPerfilResponse> atualizar(@PathVariable("instrutorID") Long instrutorID,
            @RequestBody UpdateInstrutorDTO dto) {

        // chama o service para atualizar o instrutor
        var instrutor = instrutorService.atualizar(instrutorID, dto);

        // tranforma de instrutor para instrutor response
        var response = InstrutorMapper.INSTANCE.toInstrutorWithPerfilResponseFromInstrutor(instrutor);

        // retorna o instrutor response
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
}
