package br.com.gabrielcaio.entityrelationships.validator;

import br.com.gabrielcaio.entityrelationships.controllers.error.EntityExistsException;
import br.com.gabrielcaio.entityrelationships.controllers.error.ResourceNotFoundException;
import br.com.gabrielcaio.entityrelationships.model.curso.Curso;
import br.com.gabrielcaio.entityrelationships.repositories.CursoRepository;
import br.com.gabrielcaio.entityrelationships.repositories.InstrutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidadorCriacaoCurso {

    private final CursoRepository cursoRepository;
    private final InstrutorRepository instrutorRepository;

    public void validar(Curso curso,Long instrutorID) {
        validarExistenciaCurso(curso);
        validarExistenciaInstrutor(instrutorID);
    }

    private void validarExistenciaCurso(Curso curso) {
        // se existir um curso com esse nome não pode cadastrar um novo
        String nomeCurso = curso.getNome();
        if(cursoRepository.existsByNome(nomeCurso)) {
            throw new EntityExistsException("Curso com esse nome já existe.");
        }
    }

    private void validarExistenciaInstrutor(Long instrutorID) {
        // se não existir um instrutor com esse id não podemos criar um curso
        // o instrutor deve estar cadastrado previamente no banco de dados
        if(!instrutorRepository.existsById(instrutorID)) {
            throw new ResourceNotFoundException(
                    "Instrutor com id " + instrutorID + " não encontrado.");
        }
    }
}
