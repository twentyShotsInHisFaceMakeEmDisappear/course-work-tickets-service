package eu.senla.eventoservice.repository;

import eu.senla.eventoservice.model.Credential;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CredentialRepository extends CrudRepository<Credential, Long> {

    Boolean existsByEmail(String email);

    Optional<Credential> findByEmail(String email);

}
