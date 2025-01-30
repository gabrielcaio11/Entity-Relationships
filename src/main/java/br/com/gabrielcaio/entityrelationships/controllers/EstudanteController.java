package br.com.gabrielcaio.entityrelationships.controllers;

import br.com.gabrielcaio.entityrelationships.controllers.mapper.EstudanteMapper;
import br.com.gabrielcaio.entityrelationships.model.estudante.CreateEstudanteDTO;
import br.com.gabrielcaio.entityrelationships.model.estudante.EstudanteWithCurcoResponse;
import br.com.gabrielcaio.entityrelationships.service.EstudanteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estudantes")
@RequiredArgsConstructor
public class EstudanteController {

    private final EstudanteService estudanteService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Criar um novo estudante", description = "Cria um novo estudante com base nos dados fornecidos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "403", description = "Acesso negado (somente ADMIN pode criar cursos)"),
            @ApiResponse(responseCode = "422", description = "Erro de validação"),
            @ApiResponse(responseCode = "409",description = "O nome de um estudante não pode ser duplicado"),
            @ApiResponse(responseCode = "404",description = "Curso associado não encontrado"),
            @ApiResponse(responseCode = "200", description = "Estudante criado com sucesso"),
    })
    public ResponseEntity<EstudanteWithCurcoResponse> salvar(@Valid @RequestBody CreateEstudanteDTO dto) {
        // chama o service para salvar o estudante
        var estudante = estudanteService.salvar(dto);

        // tranforma de estudante para estudante response
        var response = EstudanteMapper.INSTANCE.toEstudanteWithCurcoResponsefromEntity(estudante);

        // retorna o estudante response
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("{estudante_id}/cursos")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Operation(summary = "Buscar detalhes de um estudante", description = "Retorna os detalhes de um estudante com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "403", description = "Acesso negado (somente ADMIN ou USER podem acessar)"),
            @ApiResponse(responseCode = "404", description = "Estudante não encontrado"),
            @ApiResponse(responseCode = "200", description = "Detalhes do Estudante encontrados"),
    })
    public ResponseEntity<EstudanteWithCurcoResponse> getById(
            @Parameter(description = "ID do curso a ser buscado", required = true) @PathVariable Long estudante_id
    ) {
        // chama o service para buscar o estudante
        var estudanteWithCurso = estudanteService.getEstudanteWithCurso(estudante_id);

        // tranforma de estudante para estudante response
        var response = EstudanteMapper.INSTANCE.toEstudanteWithCurcoResponsefromEntity(estudanteWithCurso);

        // retorna o estudante response
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Operation(summary = "Listar todos os estudante", description = "Retorna uma lista paginada de todos os estudantes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "403", description = "Acesso negado (somente ADMIN ou USER podem acessar)"),
            @ApiResponse(responseCode = "200", description = "Lista de estudantes retornada com sucesso")
    })
    public ResponseEntity<Page<EstudanteWithCurcoResponse>> getAll(
            @Parameter(description = "Número da página (padrão: 0)", example = "0") @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @Parameter(description = "Tamanho da página (padrão: 10)", example = "10") @RequestParam(required = false, defaultValue = "10") Integer pageSize
    ) {
        // instancia do pageable para a busca paginada
        var pageable = PageRequest.of(pageNumber, pageSize);

        // chama o service para a busca paginada
        var page = estudanteService.findAll(pageable);

        // transforma a pagina de estudante para uma pagina de estudante response
        var pageResponse = page.map(EstudanteMapper.INSTANCE::toEstudanteWithCurcoResponsefromEntity);

        // retorna a pagina de estudante response
        return ResponseEntity.status(HttpStatus.OK).body(pageResponse);
    }

    @DeleteMapping(value = "/{estudanteId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Excluir um estudante", description = "Exclui um estudante com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "403", description = "Acesso negado (somente ADMIN pode excluir cursos)"),
            @ApiResponse(responseCode = "404", description = "Estudante não encontrado"),
            @ApiResponse(responseCode = "400", description = "Falha de integridade referencial"),
            @ApiResponse(responseCode = "204", description = "Estudante excluído com sucesso")
    })
    public ResponseEntity<Void> delete(@PathVariable("estudanteId") Long instrutorID) {
        // chama o service para deletar o estudante
        estudanteService.deleteById(instrutorID);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
