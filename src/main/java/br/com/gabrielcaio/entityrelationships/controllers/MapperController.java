package br.com.gabrielcaio.entityrelationships.controllers;

import br.com.gabrielcaio.entityrelationships.controllers.mapper.CursoMapper;
import br.com.gabrielcaio.entityrelationships.model.curso.CreateCursoDTO;
import br.com.gabrielcaio.entityrelationships.model.curso.Curso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mappers")
public class MapperController {

    private final CursoMapper cursoMapper;

    @Autowired
    public MapperController(CursoMapper cursoMapper) {
        this.cursoMapper = cursoMapper;
    }

    @GetMapping
    public ResponseEntity<Curso> test(@RequestBody CreateCursoDTO dto){
        System.out.println(dto);
        var cursoMapper = CursoMapper.INSTANCE.toEntityFromCreateCursoDTO(dto);
        System.out.println("-----------");
        System.out.println(cursoMapper);
        return ResponseEntity.ok(cursoMapper);
    }
}
