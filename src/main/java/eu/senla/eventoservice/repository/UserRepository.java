package eu.senla.eventoservice.repository;

import eu.senla.eventoservice.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Boolean existsByPhoneNumber(String phoneNumber);

    Optional<User> getUserByPhoneNumber(String phoneNumber);

    Optional<User> getUserByFirstNameAndSurname(String firstName, String lastName);

    Optional<User> getUserByCredential_Email(String email);

}
