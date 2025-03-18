package br.com.gabrielcaio.entityrelationships.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.gabrielcaio.entityrelationships.model.curso.Curso;
import br.com.gabrielcaio.entityrelationships.model.estudante.Estudante;
import jakarta.transaction.Transactional;

/** testes feitos por ia */
@SpringBootTest
public class EstudanteRepositoryTest {

    @Autowired
    private EstudanteRepository estudanteRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Test
    public void existsByNomeIgnoreCase_ReturnsTrue_WhenEstudanteWithNameExists() {
        // Arrange
        Estudante estudante = new Estudante();
        estudante.setNome("Gabriel.Caio");
        estudanteRepository.save(estudante);

        // Act
        boolean exists = estudanteRepository.existsByNomeIgnoreCase("gabriel.caio");

        // Assert
        assertThat(exists).isTrue();
    }

    @Test
    public void existsByNomeIgnoreCase_ReturnsFalse_WhenEstudanteWithNameDoesNotExist() {
        // Act
        boolean exists = estudanteRepository.existsByNomeIgnoreCase("NonExistent Name");

        // Assert
        assertThat(exists).isFalse();
    }

    @Test
    public void existsByNomeIgnoreCase_IsCaseInsensitive() {
        // Arrange
        Estudante estudante = new Estudante();
        estudante.setNome("Gabriel Caio");
        estudanteRepository.save(estudante);

        // Act
        boolean exists = estudanteRepository.existsByNomeIgnoreCase("GABRIEL CAIO");

        // Assert
        assertThat(exists).isTrue();
    }

    @Test
    public void existsByNomeIgnoreCase_ReturnsFalse_ForEmptyDatabase() {
        // Act
        boolean exists = estudanteRepository.existsByNomeIgnoreCase("Any Name");

        // Assert
        assertThat(exists).isFalse();
    }

    @Test
    @Transactional
    public void shouldRetrieveEstudanteByIdWithAssociatedCursos() {
        // Arrange
        Estudante estudante = createEstudanteWithNome("Gabriel Caio");
        estudante = associateAndSaveEstudanteWithCursos(estudante, 1L, 2L);

        // Act
        Optional<Estudante> estudanteOptional = estudanteRepository.getEstudanteByIdWithCurso(estudante.getId());

        // Assert
        assertThat(estudanteOptional)
                .isPresent()
                .get()
                .extracting("cursos")
                .asInstanceOf(LIST)
                .hasSize(2)
                .extracting("id")
                .containsExactlyInAnyOrder(1L, 2L);
    }

    private Estudante createEstudanteWithNome(String nome) {
        Estudante estudante = new Estudante();
        estudante.setNome(nome);
        return estudante;
    }

    private Estudante associateAndSaveEstudanteWithCursos(Estudante estudante, Long... cursoIds) {
        List<Curso> cursos = Arrays.stream(cursoIds)
                .map(cursoRepository::getReferenceById)
                .peek(curso -> curso.getEstudantes().add(estudante))
                .toList();

        estudante.setCursos(cursos);
        return estudanteRepository.save(estudante);
    }
}