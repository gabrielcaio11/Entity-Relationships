package br.com.gabrielcaio.entityrelationships.service;

import br.com.gabrielcaio.entityrelationships.controllers.mapper.CursoMapper;
import br.com.gabrielcaio.entityrelationships.model.curso.CreateCursoDTO;
import br.com.gabrielcaio.entityrelationships.model.curso.Curso;
import br.com.gabrielcaio.entityrelationships.model.instrutor.Instrutor;
import br.com.gabrielcaio.entityrelationships.repositories.CursoRepository;
import br.com.gabrielcaio.entityrelationships.repositories.InstrutorRepository;
import br.com.gabrielcaio.entityrelationships.validator.ValidadorCriacaoCurso;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CursoService {

    private final CursoRepository cursoRepository;
    private final InstrutorRepository instrutorRepository;
    private final ValidadorCriacaoCurso validadorCriacaoCurso;

    @Transactional
    public Curso salvarCurso(CreateCursoDTO dto) {

        // transforma de dto para entidade
        Curso curso = CursoMapper.INSTANCE.toEntityFromCreateCursoDTO(dto);

        // validacao da criação do curso
        validadorCriacaoCurso.validar(curso, dto.getInstrutorId());

        // busca no banco de dados o instrutor pelo id
        Instrutor instrutor = instrutorRepository.getReferenceById(dto.getInstrutorId());

        // Associa o curso ao instrutor(consistencia na persistencia)
        curso.setInstrutor(instrutor);
        instrutor.getCursos().add(curso);

        // retorna o curso salvo
        return cursoRepository.save(curso);
    }
}
