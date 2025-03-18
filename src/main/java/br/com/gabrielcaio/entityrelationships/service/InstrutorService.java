package br.com.gabrielcaio.entityrelationships.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.gabrielcaio.entityrelationships.controllers.error.DataBaseException;
import br.com.gabrielcaio.entityrelationships.controllers.error.ResourceNotFoundException;
import br.com.gabrielcaio.entityrelationships.controllers.mapper.InstrutorMapper;
import br.com.gabrielcaio.entityrelationships.model.instrutor.CreateInstrutorDTO;
import br.com.gabrielcaio.entityrelationships.model.instrutor.Instrutor;
import br.com.gabrielcaio.entityrelationships.model.instrutor.UpdateInstrutorDTO;
import br.com.gabrielcaio.entityrelationships.model.perfil.Perfil;
import br.com.gabrielcaio.entityrelationships.model.perfil.UpdatePerfilDTO;
import br.com.gabrielcaio.entityrelationships.repositories.InstrutorRepository;
import br.com.gabrielcaio.entityrelationships.validator.ValidadorAtualizacaoInstrutor;
import br.com.gabrielcaio.entityrelationships.validator.ValidadorCriacaoInstrutor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InstrutorService {

    private final InstrutorRepository instrutorRepository;
    private final ValidadorCriacaoInstrutor validadorCriacaoInstrutor;
    private final ValidadorAtualizacaoInstrutor validadorAtualizacaoInstrutor;

    @Transactional
    public Instrutor salvar(CreateInstrutorDTO dto) {

        // transforma os dto para as entidades
        Instrutor instrutor = InstrutorMapper.INSTANCE.toEntityFromCreateInstrutorDTO(dto);
        Perfil perfil = instrutor.getPerfil();

        // validacao da criação do instrutor
        validadorCriacaoInstrutor.validar(instrutor);

        // consistencia na persistencia

        // Configura o perfil no objeto Instrutor, estabelecendo a relação entre o
        // Instrutor e o Perfil.
        // Isso assegura que o lado do Instrutor esteja associado corretamente ao
        // Perfil.
        instrutor.setPerfil(perfil);

        // Configura o instrutor no objeto Perfil, completando a relação bidirecional
        // entre os dois objetos.
        // Este passo é necessário para manter a consistência no lado do Perfil.
        perfil.setInstrutor(instrutor);

        // retorna o instrutor salvo
        return instrutorRepository.save(instrutor);
    }

    @Transactional(readOnly = true)
    public Instrutor getById(Long instrutorID) {
        // buscar no banco de dados o instrutor por id
        return instrutorRepository.findByIdWithPerfilAndCursos(instrutorID).orElseThrow(
                () -> new ResourceNotFoundException("Instrutor com id " + instrutorID + " não foi encontrado."));
        // return instrutorRepository
        // .findById(instrutorID)
        // .orElseThrow(
        // () -> new ResourceNotFoundException("Instrutor com id " + instrutorID + " não
        // foi encontrado."));
    }

    public Page<Instrutor> findAll(Pageable pageable) {
        return instrutorRepository.findAllIdWithPerfilAndCursos(pageable);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteById(Long instrutorID) {

        // verifica se esse instrutor existe
        if (!instrutorRepository.existsById(instrutorID)) {
            throw new ResourceNotFoundException("Instrutor com id " + instrutorID + " não foi encontrado.");
        }
        try {
            instrutorRepository.deleteById(instrutorID);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Falha de integridade referencial");
        }

    }

    @Transactional
    public Instrutor atualizar(Long instrutorID, UpdateInstrutorDTO dto) {

        // obter o instrutor pelo id
        Instrutor instrutor = instrutorRepository.findById(instrutorID).orElseThrow(() -> new ResourceNotFoundException(
                "Instrutor com id " + instrutorID + " não encontrado."));

        // validacao da atualizacao do instrutor atraves dos campos do
        // UpdateInstrutorDTO
        validadorAtualizacaoInstrutor.validar(InstrutorMapper.INSTANCE.toEntityFromUpdateInstrutorDTO(dto));

        // atualizacao dos campos da entidade instrutor
        updateInstrutor(instrutor, dto);
        var perfil = updatePerfil(instrutor.getPerfil(), dto.getPerfil());

        // consistencia na persistencia
        perfil.setInstrutor(instrutor);
        instrutor.setPerfil(perfil);

        // retorna o instrutor atualizado
        return instrutorRepository.save(instrutor);
    }

    private void updateInstrutor(Instrutor instrutor, UpdateInstrutorDTO dto) {
        if (!dto.getNome().isBlank()) {
            instrutor.setNome(dto.getNome());
        }
    }

    private Perfil updatePerfil(Perfil perfil, UpdatePerfilDTO dto) {
        if (!dto.getBiografia().isBlank()) {
            perfil.setBiografia(dto.getBiografia());
        }
        return perfil;
    }

}
