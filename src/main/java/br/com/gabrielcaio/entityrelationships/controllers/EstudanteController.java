package br.com.gabrielcaio.entityrelationships.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gabrielcaio.entityrelationships.controllers.mapper.EstudanteMapper;
import br.com.gabrielcaio.entityrelationships.model.estudante.CreateEstudanteDTO;
import br.com.gabrielcaio.entityrelationships.model.estudante.EstudanteWithCurcoResponse;
import br.com.gabrielcaio.entityrelationships.service.EstudanteService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/estudantes")
@RequiredArgsConstructor
public class EstudanteController {

    private final EstudanteService estudanteService;

    @PostMapping
    public ResponseEntity<EstudanteWithCurcoResponse> salvar(@RequestBody CreateEstudanteDTO dto) {

        // chama o service para salvar o estudante
        var estudante = estudanteService.salvar(dto);

        // tranforma de estudante para estudante response
        var response = EstudanteMapper.INSTANCE.toEstudanteWithCurcoResponsefromEntity(estudante);

        // retorna o estudante response
        return ResponseEntity.ok(response);

    }

    @GetMapping("{estudante_id}")
    public ResponseEntity<EstudanteWithCurcoResponse> getById(@PathVariable Long estudante_id) {

        // chama o service para buscar o estudante
        var estudanteWithCurso = estudanteService.getEstudanteWithCurso(estudante_id);

        // tranforma de estudante para estudante response
        var response = EstudanteMapper.INSTANCE.toEstudanteWithCurcoResponsefromEntity(estudanteWithCurso);

        // retorna o estudante response
        return ResponseEntity.ok(response);

    }

    @GetMapping
    public ResponseEntity<Page<EstudanteWithCurcoResponse>> getAll(
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        // instancia do pageable para a busca paginada
        var pageable = PageRequest.of(pageNumber, pageSize);

        // chama o service para a busca paginada
        var page = estudanteService.findAll(pageable);

        // transforma a pagina de estudante para uma pagina de estudante response
        var pageResponse = page.map(EstudanteMapper.INSTANCE::toEstudanteWithCurcoResponsefromEntity);

        // retorna a pagina de estudante response
        return ResponseEntity.ok(pageResponse);

    }

    @DeleteMapping(value = "/{estudanteId}")
    public ResponseEntity<Void> delete(@PathVariable("estudanteId") Long instrutorID) {

        // chama o service para deletar o estudante
        estudanteService.deleteById(instrutorID);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
