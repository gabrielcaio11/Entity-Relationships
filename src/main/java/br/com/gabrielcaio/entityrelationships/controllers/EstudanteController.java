package br.com.gabrielcaio.entityrelationships.controllers;

import br.com.gabrielcaio.entityrelationships.controllers.mapper.EstudanteMapper;
import br.com.gabrielcaio.entityrelationships.model.estudante.CreateEstudanteDTO;
import br.com.gabrielcaio.entityrelationships.model.estudante.Estudante;
import br.com.gabrielcaio.entityrelationships.model.estudante.EstudanteWithCurcoResponse;
import br.com.gabrielcaio.entityrelationships.service.EstudanteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estudantes")
@RequiredArgsConstructor
public class EstudanteController {

    private final EstudanteService estudanteService;

    @PostMapping
    public ResponseEntity<EstudanteWithCurcoResponse> salvar(@RequestBody CreateEstudanteDTO dto) {
        Estudante estudante = estudanteService.salvar(dto);
        EstudanteWithCurcoResponse response = EstudanteMapper.INSTANCE.toEstudanteWithCurcoResponsefromEntity(estudante);
        return ResponseEntity.ok(response);
    }
    @GetMapping("{estudante_id}")
    public ResponseEntity<EstudanteWithCurcoResponse> getEstudanteWithCurso(@PathVariable Long estudante_id){
        Estudante estudanteWithCurso = estudanteService.getEstudanteWithCurso(estudante_id);
        EstudanteWithCurcoResponse response = EstudanteMapper.INSTANCE.toEstudanteWithCurcoResponsefromEntity(estudanteWithCurso);
        return ResponseEntity.ok(response);
    }
}
