package br.com.gabrielcaio.entityrelationships.controllers;

import br.com.gabrielcaio.entityrelationships.controllers.mapper.CursoMapper;
import br.com.gabrielcaio.entityrelationships.model.curso.CreateCursoDTO;
import br.com.gabrielcaio.entityrelationships.model.curso.CursoResponseDTO;
import br.com.gabrielcaio.entityrelationships.service.CursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cursos")
@RequiredArgsConstructor
public class CursoController {

    private final CursoService cursoService;

    @PostMapping
    public ResponseEntity<CursoResponseDTO> salvar(@RequestBody CreateCursoDTO dto){
        var curso = cursoService.salvarCurso(dto);
        var cursoResponse = CursoMapper.INSTANCE.toCursoResponseFromEntity(curso);
        return ResponseEntity.ok(cursoResponse);
    }
}
