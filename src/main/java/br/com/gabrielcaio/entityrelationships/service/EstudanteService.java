package br.com.gabrielcaio.entityrelationships.service;

import br.com.gabrielcaio.entityrelationships.controllers.error.DataBaseException;
import br.com.gabrielcaio.entityrelationships.controllers.error.ResourceNotFoundException;
import br.com.gabrielcaio.entityrelationships.controllers.mapper.EstudanteMapper;
import br.com.gabrielcaio.entityrelationships.model.curso.Curso;
import br.com.gabrielcaio.entityrelationships.model.estudante.CreateEstudanteDTO;
import br.com.gabrielcaio.entityrelationships.model.estudante.Estudante;
import br.com.gabrielcaio.entityrelationships.model.usuario.Usuario;
import br.com.gabrielcaio.entityrelationships.repositories.CursoRepository;
import br.com.gabrielcaio.entityrelationships.repositories.EstudanteRepository;
import br.com.gabrielcaio.entityrelationships.security.SecurityService;
import br.com.gabrielcaio.entityrelationships.validator.ValidadorCriacaoEstudante;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EstudanteService {

    private final EstudanteRepository estudanteRepository;
    private final CursoRepository cursoRepository;
    private final ValidadorCriacaoEstudante validadorCriacaoEstudante;
    private final SecurityService securityService;

    @Transactional
    public Estudante salvar(CreateEstudanteDTO dto) {
        // Transforma de dto para entidade
        Estudante estudante = EstudanteMapper.INSTANCE.toEntityFromCreateEstudanteDTO(dto);
        // validacao da criação do estudante
        validadorCriacaoEstudante.validar(estudante);

        // Busca os cursos validos
        List<Curso> cursos = buscarCursos(dto.getCursosIDs());

        // obter usuario autenticado para auditoria
        Usuario userAuthenticated = securityService.getUserAuthenticated();

        // consistencia na persistencia

        // Configura o usuario no objeto Estudante
        estudante.setUsuario(userAuthenticated);

        // Associa os cursos ao estudante(consistencia na persistencia)
        estudante.setCursos(cursos);

        // Associa o estudante ao curso(consistencia na persistencia)
        cursos.forEach(curso -> curso.getEstudantes().add(estudante));

        // retorna o estudante salvo
        return estudanteRepository.save(estudante);
    }

    private List<Curso> buscarCursos(List<Long> cursosIDs) {
        return cursosIDs
                .stream()
                .map(cursoID -> cursoRepository.findById(cursoID)
                        .orElseThrow(() -> new ResourceNotFoundException("Curso com id " + cursoID + " não foi encontrado.")))
                .collect(Collectors.toList());
    }

    public Estudante getEstudanteWithCurso(Long id) {
        return estudanteRepository
                .getEstudanteByIdWithCurso(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Estudante com id " + id + " não foi encontrado."));
    }

    public Page<Estudante> findAll(Pageable pageable) {
        return estudanteRepository.findAll(pageable);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteById(Long estudanteId) {
        // verifica se esse estudante existe
        if (!estudanteRepository.existsById(estudanteId)) {
            throw new ResourceNotFoundException("Estudante com id " + estudanteId + " não foi encontrado.");
        }
        try {
            estudanteRepository.deleteById(estudanteId);
        }catch (DataIntegrityViolationException e){
            throw new DataBaseException("Falha de integridade referencial");
        }
    }
}
