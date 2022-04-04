package eu.senla.eventoservice.repository;

import eu.senla.eventoservice.model.Genre;
import org.springframework.data.repository.CrudRepository;

public interface GenreRepository extends CrudRepository<Genre, Long> {
}
