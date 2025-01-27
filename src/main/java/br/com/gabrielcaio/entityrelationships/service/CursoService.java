package br.com.gabrielcaio.entityrelationships.service;

import br.com.gabrielcaio.entityrelationships.controllers.error.DataBaseException;
import br.com.gabrielcaio.entityrelationships.controllers.error.ResourceNotFoundException;
import br.com.gabrielcaio.entityrelationships.controllers.mapper.CursoMapper;
import br.com.gabrielcaio.entityrelationships.model.curso.CreateCursoDTO;
import br.com.gabrielcaio.entityrelationships.model.curso.Curso;
import br.com.gabrielcaio.entityrelationships.model.curso.UpdateCursoDTO;
import br.com.gabrielcaio.entityrelationships.model.instrutor.Instrutor;
import br.com.gabrielcaio.entityrelationships.model.usuario.Usuario;
import br.com.gabrielcaio.entityrelationships.repositories.CursoRepository;
import br.com.gabrielcaio.entityrelationships.repositories.InstrutorRepository;
import br.com.gabrielcaio.entityrelationships.security.SecurityService;
import br.com.gabrielcaio.entityrelationships.validator.ValidadorAtualizacaoCurso;
import br.com.gabrielcaio.entityrelationships.validator.ValidadorCriacaoCurso;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CursoService {

    private final CursoRepository cursoRepository;
    private final InstrutorRepository instrutorRepository;
    private final ValidadorCriacaoCurso validadorCriacaoCurso;
    private final ValidadorAtualizacaoCurso validadorAtualizacaoCurso;
    private final SecurityService securityService;

    @Transactional
    public Curso salvarCurso(CreateCursoDTO dto) {
        // transforma de dto para entidade
        Curso curso = CursoMapper.INSTANCE.toEntityFromCreateCursoDTO(dto);

        // validacao da criação do curso
        validadorCriacaoCurso.validar(curso, dto.getInstrutorId());

        // obter usuario autenticado para auditoria
        Usuario userAuthenticated = securityService.getUserAuthenticated();

        // consistencia na persistencia

        // Configura o usuario no objeto Curso
        curso.setUsuario(userAuthenticated);

        // busca no banco de dados o instrutor pelo id
        Instrutor instrutor = instrutorRepository.getReferenceById(dto.getInstrutorId());

        // Associa o curso ao instrutor(consistencia na persistencia)
        curso.setInstrutor(instrutor);
        instrutor.getCursos().add(curso);

        // retorna o curso salvo
        return cursoRepository.save(curso);
    }

    public Curso atualizarInstrutorCurso(Long id,UpdateCursoDTO dto) {
        // validacao atualizacao curso
        validadorAtualizacaoCurso.validar( id, dto );

        // busca no banco de dados o curso pelo id que vai ser atualizado
        Curso curso = cursoRepository.getReferenceById( id );

        // busca no banco de dados o instrutor pelo id
        Instrutor instrutor = instrutorRepository.getReferenceById( dto.getInstrutorId() );

        // Associa o curso ao novo instrutor(consistencia na persistencia)
        curso.setInstrutor( instrutor );

        // Associa o instrutor ao seu novo curso
        instrutor.getCursos().add(curso);
        
        // retorna o curso atualizado
        return cursoRepository.save(curso);
    }

    public Curso getById(Long cursoId) {
        return cursoRepository.findById(cursoId).orElseThrow(()-> new ResourceNotFoundException("Curso  com id " + cursoId + " não encontrado."));
    }

    public Page<Curso> findAll(PageRequest pageable) {
        return cursoRepository.findAll(pageable);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteById(Long cursoId) {
        // verifica se esse curso existe
        if (!cursoRepository.existsById(cursoId)) {
            throw new ResourceNotFoundException("Curso com id " + cursoId + " não foi encontrado.");
        }
        try {
            cursoRepository.deleteById(cursoId);
        }catch (DataIntegrityViolationException e){
            throw new DataBaseException("Falha de integridade referencial");
        }
    }
}
