package br.com.gabrielcaio.entityrelationships.controllers;

import br.com.gabrielcaio.entityrelationships.controllers.mapper.CursoMapper;
import br.com.gabrielcaio.entityrelationships.model.curso.CreateCursoDTO;
import br.com.gabrielcaio.entityrelationships.model.curso.CursoDetails;
import br.com.gabrielcaio.entityrelationships.model.curso.CursoResponseDTO;
import br.com.gabrielcaio.entityrelationships.model.curso.UpdateCursoDTO;
import br.com.gabrielcaio.entityrelationships.service.CursoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cursos")
@RequiredArgsConstructor
@Tag(name = "Cursos", description = "Operações relacionadas a cursos")
public class CursoController {

    private final CursoService cursoService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Criar um novo curso", description = "Cria um novo curso com base nos dados fornecidos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "403", description = "Acesso negado (somente ADMIN pode criar cursos)"),
            @ApiResponse(responseCode = "422", description = "Erro de validação"),
            @ApiResponse(responseCode = "409",description = "Curso com esse nome já existe"),
            @ApiResponse(responseCode = "400",description = "Instrutor associado não encontrado"),
            @ApiResponse(responseCode = "200", description = "Curso criado com sucesso"),
            @ApiResponse(responseCode = "409",description = "Curso com esse nome já existe")
    })
    public ResponseEntity<CursoResponseDTO> salvar(@Valid @RequestBody CreateCursoDTO dto) {
        // chama o service para salvar o curso
        var curso = cursoService.salvarCurso(dto);

        // tranforma de curso para curso response
        var cursoResponse = CursoMapper.INSTANCE.toCursoResponseFromEntity(curso);

        // retorna o curso response
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoResponse);
    }

    @PutMapping(value = "/{cursoID}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atualizar instrutor de um curso", description = "Atualiza o instrutor de um curso existente com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "403", description = "Acesso negado (somente ADMIN pode atualizar cursos)"),
            @ApiResponse(responseCode = "422", description = "Erro de validação"),
            @ApiResponse(responseCode = "404", description = "Curso não encontrado"),
            @ApiResponse(responseCode = "404",description = "Instrutor associado não encontrado"),
            @ApiResponse(responseCode = "200", description = "Instrutor do curso atualizado com sucesso")
    })
    public ResponseEntity<CursoResponseDTO> atualizarInstrutorCurso(
            @Parameter(description = "ID do curso a ser atualizado", required = true) @PathVariable Long cursoID,
            @Valid @RequestBody UpdateCursoDTO dto
    ) {
        // chama o service para salvar o curso
        var curso = cursoService.atualizarInstrutorCurso(cursoID, dto);

        // tranforma de curso para curso response
        var cursoAtualizado = CursoMapper.INSTANCE.toCursoResponseFromEntity(curso);

        // retorna o curso response
        return ResponseEntity.status(HttpStatus.OK).body(cursoAtualizado);
    }

    @GetMapping(value = "/{cursoId}/details")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Operation(summary = "Buscar detalhes de um curso", description = "Retorna os detalhes de um curso com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "403", description = "Acesso negado (somente ADMIN ou USER podem acessar)"),
            @ApiResponse(responseCode = "404", description = "Curso não encontrado"),
            @ApiResponse(responseCode = "200", description = "Detalhes do curso encontrados"),
    })
    public ResponseEntity<CursoDetails> getById(
            @Parameter(description = "ID do curso a ser buscado", required = true) @PathVariable("cursoId") Long cursoId
    ) {
        // chama o service para buscar o curso por um id especifico
        var curso = cursoService.getById(cursoId);

        // tranforma de curso para curso response
        var cursoDetails = CursoMapper.INSTANCE.toCursoDetailsFromEntity(curso);

        // retorna o curso response
        return ResponseEntity.status(HttpStatus.OK).body(cursoDetails);
    }


    @GetMapping(value = "/details")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Operation(summary = "Listar todos os cursos", description = "Retorna uma lista paginada de todos os cursos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de cursos retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado (somente ADMIN ou USER podem acessar)")
    })
    public ResponseEntity<Page<CursoDetails>> getAll(
            @Parameter(description = "Número da página (padrão: 0)", example = "0") @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @Parameter(description = "Tamanho da página (padrão: 10)", example = "10") @RequestParam(required = false, defaultValue = "10") Integer pageSize
    ) {
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
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Excluir um curso", description = "Exclui um curso com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "403", description = "Acesso negado (somente ADMIN pode excluir cursos)"),
            @ApiResponse(responseCode = "404", description = "Curso não encontrado"),
            @ApiResponse(responseCode = "400", description = "Falha de integridade referencial"),
            @ApiResponse(responseCode = "204", description = "Curso excluído com sucesso")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID do curso a ser excluído", required = true) @PathVariable("CursoId") Long cursoId
    ) {
        // chama o service para deletar o curso
        cursoService.deleteById(cursoId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
