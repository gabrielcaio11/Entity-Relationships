package br.com.gabrielcaio.entityrelationships.controllers.error;

import java.time.Instant;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {
    private Instant timestamp;
    private Integer status;
    private String error;
    private String path;
}


