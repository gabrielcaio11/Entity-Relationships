package br.com.gabrielcaio.entityrelationships.controllers;

import br.com.gabrielcaio.entityrelationships.controllers.error.ErrorMessage;
import br.com.gabrielcaio.entityrelationships.controllers.mapper.EstudanteMapper;
import br.com.gabrielcaio.entityrelationships.model.estudante.CreateEstudanteDTO;
import br.com.gabrielcaio.entityrelationships.model.estudante.EstudanteWithCurcoResponse;
import br.com.gabrielcaio.entityrelationships.service.EstudanteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
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
@RequestMapping("/estudantes" )
@RequiredArgsConstructor
public class EstudanteController {

    private final EstudanteService estudanteService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')" )
    @Operation(
            summary = "Registrar um estudante",
            description = "Registra um estudante com seu nome e os IDs dos cursos associados. " +
                    "A lista de `cursosIDs` deve conter pelo menos um curso válido. " +
                    "O nome do estudante deve ser único no sistema.",
            tags = {"Estudantes"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso negado (somente ADMIN pode excluir cursos)",
                    content = @Content(
                            schema = @Schema(implementation = ErrorMessage.class),
                            examples = @ExampleObject(
                                    value = "{" +
                                            "\"timestamp\": \"2025-02-12T18:40:56.153323Z\", " +
                                            "\"status\": 403, " +
                                            "\"error\": \"Acesso negado.\", " +
                                            "\"path\": \"/estudantes\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "Erro de validação",
                    content = @Content(
                            schema = @Schema(implementation = ErrorMessage.class),
                            examples = @ExampleObject(
                                    value = "{" +
                                            "\"timestamp\": \"2025-02-12T18:52:19.565590400Z\", " +
                                            "\"status\": 422, " +
                                            "\"error\": \"Dados invalidos\", " +
                                            "\"path\": \"/estudantes\", " +
                                            "\"errors\": [" +
                                                    "{" +
                                                        "\"fieldName\": \"nome\", " +
                                                        "\"message\": \"Campo obrigatório\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"fieldName\": \"cursosIDs\", " +
                                                        "\"message\": \"Campo obrigatório\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"fieldName\": \"cursosIDs\", " +
                                                        "\"message\": \"O aluno deve se matricular com pelo menos um curso\"" +
                                                    "}" +
                                                "]" +
                                            "}"
                            )

                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "O nome de um estudante não pode ser duplicado",
                    content = @Content(
                            schema = @Schema(implementation = ErrorMessage.class),
                            examples = @ExampleObject(
                                    value = "{" +
                                            "\"timestamp\": \"2025-02-12T18:40:56.153323Z\", " +
                                            "\"status\": 409, " +
                                            "\"error\": \"O nome de um estudante não pode ser duplicado.\", " +
                                            "\"path\": \"/estudantes\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Curso associado não encontrado",
                    content = @Content(
                            schema = @Schema(implementation = ErrorMessage.class),
                            examples = @ExampleObject(
                                    value = "{" +
                                            "\"timestamp\": \"2025-02-12T18:40:56.153323Z\", " +
                                            "\"status\": 404, " +
                                            "\"error\": \"Curso com id 2 não foi encontrado.\"\", " +
                                            "\"path\": \"/estudantes\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "201",
                    description = "Estudante criado com sucesso",
                    content = @Content(
                            schema = @Schema(implementation = EstudanteWithCurcoResponse.class)
                    )
            ),
    })
    public ResponseEntity<EstudanteWithCurcoResponse> salvar(
            @Valid @RequestBody CreateEstudanteDTO dto
    ) {
        // chama o service para salvar o estudante
        var estudante = estudanteService.salvar(dto);

        // tranforma de estudante para estudante response
        var response = EstudanteMapper.INSTANCE.toEstudanteWithCurcoResponsefromEntity(estudante);

        // retorna o estudante response
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("{estudante_id}/cursos" )
    @PreAuthorize("hasAnyRole('ADMIN','USER')" )
    @Operation(
            summary = "Obter os detalhes de um estudante",
            description = "Retorna os detalhes de um estudante (nome e cursos associados)  com base no ID fornecido.",
            tags = {"Estudantes"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso negado (somente ADMIN ou USER podem acessar)",
                    content = @Content(
                            schema = @Schema(implementation = ErrorMessage.class),
                            examples = @ExampleObject(
                                    value = "{" +
                                            "\"timestamp\": \"2023-10-01T12:00:00Z\", " +
                                            "\"status\": 403, " +
                                            "\"error\": \"Acesso negado\", " +
                                            "\"path\": \"/api/estudantes\"}"
                            )
                    )
            ),

            @ApiResponse(
                    responseCode = "404",
                    description = "Estudante não encontrado",
                    content = @Content(
                            schema = @Schema(implementation = ErrorMessage.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "200",
                    description = "Detalhes do Estudante encontrados",
                    content = @Content(
                            schema = @Schema(implementation = EstudanteWithCurcoResponse.class)
                    )
            ),
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
    @PreAuthorize("hasAnyRole('ADMIN','USER')" )
    @Operation(
            summary = "Listar todos os estudante",
            description = "Retorna uma lista paginada de todos os estudantes com nome e cursos associados.",
            tags = {"Estudantes"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso negado (somente ADMIN pode excluir cursos)",
                    content = @Content(
                            schema = @Schema(implementation = ErrorMessage.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de estudantes retornada com sucesso",
                    content = @Content(
                            array = @ArraySchema(
                                    schema = @Schema(
                                            implementation = EstudanteWithCurcoResponse.class
                                    )
                            )
                    )
            )

    })
    public ResponseEntity<Page<EstudanteWithCurcoResponse>> getAll(
            @Parameter(description = "Número da página (padrão: 0)", example = "0" ) @RequestParam(required = false, defaultValue = "0" ) Integer pageNumber,
            @Parameter(description = "Tamanho da página (padrão: 10)", example = "10" ) @RequestParam(required = false, defaultValue = "10" ) Integer pageSize
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

    @DeleteMapping("/{estudanteId}" )
    @PreAuthorize("hasRole('ADMIN')" )
    @Operation(
            summary = "Excluir um estudante",
            description = "Exclui um estudante com base no ID fornecido.",
            tags = {"Estudantes"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso negado (somente ADMIN pode excluir cursos)",
                    content = @Content(
                            schema = @Schema(implementation = ErrorMessage.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Estudante não encontrado",
                    content = @Content(
                            schema = @Schema(implementation = ErrorMessage.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Falha de integridade referencial",
                    content = @Content(
                            schema = @Schema(implementation = ErrorMessage.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Estudante excluído com sucesso"
            )
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID do curso a ser excluido", required = true) @PathVariable("estudanteId" ) Long instrutorID) {
        // chama o service para deletar o estudante
        estudanteService.deleteById(instrutorID);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
