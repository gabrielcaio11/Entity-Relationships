package br.com.gabrielcaio.entityrelationships.controllers.error;

import java.time.Instant;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "ErrorMessage", description = "Mensagem de erro retornada pela API")
public class ErrorMessage {

    @Schema(description = "Timestamp", example = "2021-09-01T00:00:00Z")
    private Instant timestamp;

    @Schema(description = "Status HTTP do erro", example = "404")
    private Integer status;

    @Schema(description = "Mensagem de erro detalhada", example = "Entidade não encontrada")
    private String error;

    @Schema(description = "Caminho da requisição que causou o erro", example = "api/estudantes")
    private String path;
}


