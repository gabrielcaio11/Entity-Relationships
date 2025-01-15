package br.com.gabrielcaio.entityrelationships.service;

import br.com.gabrielcaio.entityrelationships.controllers.mapper.InstrutorMapper;
import br.com.gabrielcaio.entityrelationships.model.instrutor.CreateInstrutorDTO;
import br.com.gabrielcaio.entityrelationships.model.instrutor.Instrutor;
import br.com.gabrielcaio.entityrelationships.model.perfil.Perfil;
import br.com.gabrielcaio.entityrelationships.repositories.InstrutorRepository;
import br.com.gabrielcaio.entityrelationships.validator.ValidadorCriacaoInstrutor;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstrutorService {

    private final InstrutorRepository instrutorRepository;
    private final ValidadorCriacaoInstrutor validadorCriacaoInstrutor;

    @Transactional
    public Instrutor salvar(CreateInstrutorDTO dto) {

        // transforma os dto para  as entidades
        // TODO: refatorar essa funcionalidade desta vez usando MapStruct]
        Instrutor instrutor = InstrutorMapper.INSTANCE.toEntityFromCreateInstrutorDTO(dto);
        Perfil perfil = instrutor.getPerfil();

        // validacao da criação do instrutor
        validadorCriacaoInstrutor.validar(instrutor);

        // consistencia na persistencia

        // Configura o perfil no objeto Instrutor, estabelecendo a relação entre o Instrutor e o Perfil.
        // Isso assegura que o lado do Instrutor esteja associado corretamente ao Perfil.
        instrutor.setPerfil(perfil);

        // Configura o instrutor no objeto Perfil, completando a relação bidirecional entre os dois objetos.
        // Este passo é necessário para manter a consistência no lado do Perfil.
        perfil.setInstrutor(instrutor);

        // retorna o instrutor salvo
        return instrutorRepository.save(instrutor);
    }
}
