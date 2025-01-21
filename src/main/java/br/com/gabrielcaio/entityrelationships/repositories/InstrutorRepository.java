package br.com.gabrielcaio.entityrelationships.repositories;

import br.com.gabrielcaio.entityrelationships.model.instrutor.Instrutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InstrutorRepository extends JpaRepository<Instrutor, Long> {
    boolean existsExistsByNomeAllIgnoreCase(String nome);
}