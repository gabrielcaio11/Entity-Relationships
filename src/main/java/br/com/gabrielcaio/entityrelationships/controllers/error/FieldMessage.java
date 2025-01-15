package br.com.gabrielcaio.entityrelationships.controllers.error;

import lombok.*;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FieldMessage {
    private String fieldName;
    private String message;
}
