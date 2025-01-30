package br.com.gabrielcaio.entityrelationships.controllers;

import br.com.gabrielcaio.entityrelationships.controllers.mapper.InstrutorMapper;
import br.com.gabrielcaio.entityrelationships.model.instrutor.CreateInstrutorDTO;
import br.com.gabrielcaio.entityrelationships.model.instrutor.InstrutorWithPerfilResponse;
import br.com.gabrielcaio.entityrelationships.model.instrutor.UpdateInstrutorDTO;
import br.com.gabrielcaio.entityrelationships.service.InstrutorService;
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
@RequestMapping("/instrutores")
@RequiredArgsConstructor
@Tag(name = "Instrutores", description = "Operações relacionadas a instrutores")
public class InstrutorController {

    private final InstrutorService instrutorService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Criar um novo instrutor", description = "Cria um novo instrutor com base nos dados fornecidos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "403", description = "Acesso negado (somente ADMIN pode criar instrutores)"),
            @ApiResponse(responseCode = "422", description = "Erro de validação"),
            @ApiResponse(responseCode = "409",description = "Email não pode ser duplicado"),
            @ApiResponse(responseCode = "200", description = "Instrutor criado com sucesso")
    })
    public ResponseEntity<InstrutorWithPerfilResponse> salvar(@Valid @RequestBody CreateInstrutorDTO dto){
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
    @Operation(summary = "Buscar detalhes de um instrutor", description = "Retorna os detalhes de um instrutor com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "403", description = "Acesso negado (somente ADMIN ou USER podem acessar)"),
            @ApiResponse(responseCode = "404", description = "Instrutor não encontrado"),
            @ApiResponse(responseCode = "200", description = "Detalhes do Instrutor encontrados"),
    })

    public ResponseEntity<InstrutorWithPerfilResponse> getById(
            @Parameter(description = "ID do instrutor a ser buscado", required = true) @PathVariable("instrutorID") Long instrutorID
    ){
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
    @Operation(summary = "Listar todos os instrutores", description = "Retorna uma lista paginada de todos os instrutores.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "403", description = "Acesso negado (somente ADMIN ou USER podem acessar)"),
            @ApiResponse(responseCode = "200", description = "Lista de instrutores retornada com sucesso")
    })
    public ResponseEntity<Page<InstrutorWithPerfilResponse>> getAll(
            @Parameter(description = "Número da página (padrão: 0)", example = "0") @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @Parameter(description = "Tamanho da página (padrão: 10)", example = "10") @RequestParam(required = false, defaultValue = "10") Integer pageSize
    ) {
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
    @Operation(summary = "Excluir um instrutor", description = "Exclui um instrutor com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "403", description = "Acesso negado (somente ADMIN pode excluir cursos)"),
            @ApiResponse(responseCode = "404", description = "Instrutor não encontrado"),
            @ApiResponse(responseCode = "400", description = "Falha de integridade referencial"),
            @ApiResponse(responseCode = "204", description = "Instrutor excluído com sucesso")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID do curso a ser excluído", required = true) @PathVariable("instrutorId") Long instrutorId
    ){
        // chama o service para deletar o instrutor
        instrutorService.deleteById(instrutorId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping(value = "/{instrutorID}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atualizar um instrutor", description = "Atualiza um instrutor com base nos dados fornecidos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "403", description = "Acesso negado (somente ADMIN pode atualizar instrutores)"),
            @ApiResponse(responseCode = "404", description = "Instrutor não encontrado"),
            @ApiResponse(responseCode = "422", description = "Erro de validação"),
            @ApiResponse(responseCode = "200", description = "Instrutor atualizado com sucesso")
    })
    public ResponseEntity<InstrutorWithPerfilResponse> atualizar(
            @Parameter(description = "ID do instrutor a ser atualizado", required = true) @PathVariable("instrutorID") Long instrutorID,
            @RequestBody UpdateInstrutorDTO dto
    ){
        // chama o service para atualizar o instrutor
        var instrutor = instrutorService.atualizar(instrutorID,dto);

        // tranforma de instrutor para instrutor response
        var response = InstrutorMapper.INSTANCE.toInstrutorWithPerfilResponseFromInstrutor(instrutor);

        // retorna o instrutor response
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
