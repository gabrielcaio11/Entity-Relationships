package br.com.gabrielcaio.entityrelationships.controllers;

import br.com.gabrielcaio.entityrelationships.controllers.mapper.InstrutorMapper;
import br.com.gabrielcaio.entityrelationships.model.curso.Curso;
import br.com.gabrielcaio.entityrelationships.model.curso.UpdateCursoDTO;
import br.com.gabrielcaio.entityrelationships.model.instrutor.CreateInstrutorDTO;
import br.com.gabrielcaio.entityrelationships.model.instrutor.Instrutor;
import br.com.gabrielcaio.entityrelationships.model.instrutor.InstrutorWithPerfilResponse;
import br.com.gabrielcaio.entityrelationships.model.instrutor.UpdateInstrutorDTO;
import br.com.gabrielcaio.entityrelationships.service.InstrutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/instrutores")
@RequiredArgsConstructor
public class InstrutorController {

    private final InstrutorService instrutorService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InstrutorWithPerfilResponse> salvar(@RequestBody CreateInstrutorDTO dto){

        // chama o service para salvar o instrutor
        var instrutor = instrutorService.salvar(dto);

        // tranforma de instrutor para instrutor response
        var response = InstrutorMapper.INSTANCE
                .toInstrutorWithPerfilResponseFromInstrutor(instrutor);

        // retorna o instrutor response
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @GetMapping(value = "/{instrutorID}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<InstrutorWithPerfilResponse> getById(@PathVariable("instrutorID") Long instrutorID){

        // chama o service para buscar o instrutor por um id especifico
        var instrutor = instrutorService.getById(instrutorID);

        // tranforma de instrutor para instrutor response
        var response = InstrutorMapper.INSTANCE
                .toInstrutorWithPerfilResponseFromInstrutor(instrutor);

        // retorna o instrutor response
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Page<InstrutorWithPerfilResponse>> getAll(
            @RequestParam(required = false,defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize
    ){
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable("instrutorId") Long instrutorId){

        // chama o service para deletar o instrutor
        instrutorService.deleteById(instrutorId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @PutMapping(value = "/{instrutorID}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InstrutorWithPerfilResponse> atualizar(@PathVariable("instrutorID") Long instrutorID, @RequestBody UpdateInstrutorDTO dto){

        // chama o service para atualizar o instrutor
        var instrutor = instrutorService.atualizar(instrutorID,dto);

        // tranforma de instrutor para instrutor response
        var response = InstrutorMapper.INSTANCE.toInstrutorWithPerfilResponseFromInstrutor(instrutor);

        // retorna o instrutor response
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
}
