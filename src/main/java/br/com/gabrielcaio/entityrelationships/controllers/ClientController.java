package br.com.gabrielcaio.entityrelationships.controllers;

import br.com.gabrielcaio.entityrelationships.model.client.Client;
import br.com.gabrielcaio.entityrelationships.model.client.ClientResponseDto;
import br.com.gabrielcaio.entityrelationships.model.client.CreateClientDto;
import br.com.gabrielcaio.entityrelationships.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClientResponseDto> salvar(@RequestBody CreateClientDto dto){
        service.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
