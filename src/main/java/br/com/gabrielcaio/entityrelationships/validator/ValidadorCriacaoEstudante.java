package br.com.gabrielcaio.entityrelationships.validator;

import br.com.gabrielcaio.entityrelationships.controllers.error.EntityExistsException;
import br.com.gabrielcaio.entityrelationships.model.estudante.Estudante;
import br.com.gabrielcaio.entityrelationships.repositories.EstudanteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidadorCriacaoEstudante {

    private final EstudanteRepository estudanteRepository;

    public void validar(Estudante estudante) {
        validarExistenciaEstudantePorNome(estudante);
    }

    private void validarExistenciaEstudantePorNome(Estudante estudante) {
        String nomeDoEstudante = estudante.getNome();
        // não pode existir um estudante com esse mesmo nome no banco de dados
        if (estudanteRepository.existsByNomeIgnoreCase(nomeDoEstudante)) {
            throw new EntityExistsException("O nome de um estudante não pode ser duplicado.");
        }
    }
}
