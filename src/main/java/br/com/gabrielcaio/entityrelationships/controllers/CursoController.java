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

import br.com.gabrielcaio.entityrelationships.controllers.mapper.CursoMapper;
import br.com.gabrielcaio.entityrelationships.model.curso.CreateCursoDTO;
import br.com.gabrielcaio.entityrelationships.model.curso.CursoDetails;
import br.com.gabrielcaio.entityrelationships.model.curso.CursoResponseDTO;
import br.com.gabrielcaio.entityrelationships.model.curso.UpdateCursoDTO;
import br.com.gabrielcaio.entityrelationships.service.CursoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cursos")
@RequiredArgsConstructor
public class CursoController {

    private final CursoService cursoService;

    @PostMapping
    public ResponseEntity<CursoResponseDTO> salvar(@RequestBody CreateCursoDTO dto) {

        // chama o service para salvar o curso
        var curso = cursoService.salvarCurso(dto);

        // tranforma de curso para curso response
        var cursoResponse = CursoMapper.INSTANCE.toCursoResponseFromEntity(curso);

        // retorna o curso response
        return ResponseEntity.ok(cursoResponse);

    }

    @PutMapping("/{cursoID}")
    public ResponseEntity<CursoResponseDTO> atualizarInstrutorCurso(@PathVariable Long cursoID,
            @RequestBody UpdateCursoDTO dto) {

        // chama o service para salvar o curso
        var curso = cursoService.atualizarInstrutorCurso(cursoID, dto);

        // tranforma de curso para curso response
        var cursoAtualizado = CursoMapper.INSTANCE.toCursoResponseFromEntity(curso);

        // retorna o curso response
        return ResponseEntity.ok(cursoAtualizado);

    }

    @GetMapping(value = "/{cursoId}/details")
    public ResponseEntity<CursoDetails> getById(@PathVariable("cursoId") Long cursoId) {

        // chama o service para buscar o curso por um id especifico
        var curso = cursoService.getById(cursoId);

        // tranforma de curso para curso response
        var cursoDetails = CursoMapper.INSTANCE.toCursoDetailsFromEntity(curso);

        // retorna o curso response
        return ResponseEntity.ok(cursoDetails);

    }

    @GetMapping(value = "/details")
    public ResponseEntity<Page<CursoDetails>> getAll(
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize) {

        // instancia do pageable para a busca paginada
        var pageable = PageRequest.of(pageNumber, pageSize);

        // chama o service para a busca paginada
        var page = cursoService.findAll(pageable);

        // transforma a pagina de curso para uma pagina de curso response
        var pageResponse = page.map(CursoMapper.INSTANCE::toCursoDetailsFromEntity);

        // retorna a pagina de curso response
        return ResponseEntity.status(HttpStatus.OK).body(pageResponse);

    }

    @DeleteMapping(value = "/{CursoId}")
    public ResponseEntity<Void> delete(@PathVariable("instrutorID") Long instrutorID) {

        // chama o service para deletar o curso
        cursoService.deleteById(instrutorID);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

}
