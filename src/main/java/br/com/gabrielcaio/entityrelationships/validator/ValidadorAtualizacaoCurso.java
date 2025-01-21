package br.com.gabrielcaio.entityrelationships.validator;

import br.com.gabrielcaio.entityrelationships.controllers.error.EntityExistsException;
import br.com.gabrielcaio.entityrelationships.controllers.error.ResourceNotFoundException;
import br.com.gabrielcaio.entityrelationships.model.curso.UpdateCursoDTO;
import br.com.gabrielcaio.entityrelationships.repositories.CursoRepository;
import br.com.gabrielcaio.entityrelationships.repositories.InstrutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidadorAtualizacaoCurso {
    private final CursoRepository cursoRepository;
    private final InstrutorRepository instrutorRepository;

    public void validar(Long idCurso, UpdateCursoDTO dto) {
        validarExistenciaCurso(idCurso);
        validarExistenciaInstrutor(dto.getInstrutorId());
    }
    private void validarExistenciaCurso(Long idCurso) {
        // se não existir um curso com esse id não pode atualizar o curso
        if(!cursoRepository.existsById(idCurso)) {
            throw new ResourceNotFoundException("Curso  com id " + idCurso + " não encontrado.");
        }
    }

    private void validarExistenciaInstrutor(Long instrutorID) {
        // se não existir um instrutor com esse id não podemos atualizar um curso
        // o instrutor deve estar cadastrado previamente no banco de dados
        if(!instrutorRepository.existsById(instrutorID)) {
            throw new ResourceNotFoundException(
                    "Instrutor com id " + instrutorID + " não encontrado.");
        }
    }
}
