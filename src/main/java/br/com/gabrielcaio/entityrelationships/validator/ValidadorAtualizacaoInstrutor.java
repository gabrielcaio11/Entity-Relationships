package br.com.gabrielcaio.entityrelationships.validator;

import org.springframework.stereotype.Component;

import br.com.gabrielcaio.entityrelationships.controllers.error.EntityExistsException;
import br.com.gabrielcaio.entityrelationships.model.instrutor.Instrutor;
import br.com.gabrielcaio.entityrelationships.repositories.InstrutorRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ValidadorAtualizacaoInstrutor {

    private final InstrutorRepository instrutorRepository;

    public void validar(Instrutor instrutor) {
        validarExistenciaInstrutorPorNome(instrutor);
    }

    private void validarExistenciaInstrutorPorNome(Instrutor instrutor) {
        String nomeDoInstrutor = instrutor.getNome();
        // não pode existir um instrutor com esse mesmo nome no banco de dados
        if (instrutor.getNome() != null && instrutorRepository.existsByNomeIgnoreCase(nomeDoInstrutor)) {
            throw new EntityExistsException("O nome de um instrutor não pode ser duplicado");
        }
    }

}
