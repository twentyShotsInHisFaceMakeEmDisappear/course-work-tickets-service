package eu.senla.eventoservice.repository;

import eu.senla.eventoservice.dto.RequestStatusDto;
import eu.senla.eventoservice.dto.UpdateLocationInformationRequestDto;
import eu.senla.eventoservice.model.Location;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LocationRepository extends CrudRepository<Location, Long> {

    Boolean existsByTitle(String title);

    Optional<Location> getLocationById(Long id);

    Optional<Location> getLocationByTitle(String title);

}
