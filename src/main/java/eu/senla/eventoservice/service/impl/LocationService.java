package eu.senla.eventoservice.service.impl;

import eu.senla.eventoservice.dto.LocationModelDto;
import eu.senla.eventoservice.dto.RequestStatusDto;
import eu.senla.eventoservice.dto.UpdateLocationInformationRequestDto;
import eu.senla.eventoservice.exception.location.LocationNotFoundException;
import eu.senla.eventoservice.exception.location.LocationTitleDuplicateException;
import eu.senla.eventoservice.model.Location;
import eu.senla.eventoservice.repository.LocationRepository;
import eu.senla.eventoservice.service.LocationServiceInterface;
import eu.senla.eventoservice.util.mapper.MapperInterface;
import eu.senla.eventoservice.util.parser.ParserInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
@RequiredArgsConstructor
public class LocationService implements LocationServiceInterface {

    private final ParserInterface parser;

    private final LocationRepository locationRepository;

    private final MapperInterface<LocationModelDto, Location> mapper;

    @Override
    public RequestStatusDto createLocation(LocationModelDto locationModelDto) {
        if (locationRepository.existsByTitle(locationModelDto.getTitle()))
            throw new LocationTitleDuplicateException("There is already location with same title");

        Location currentLocation = mapper.mapToEntity(locationModelDto, Location.class);

        locationRepository.save(currentLocation);

        return new RequestStatusDto()
                .setStatusId(HttpStatus.OK.value())
                .setTimespan(Calendar.getInstance().getTime().toString())
                .setMessage("Location successfully created");
    }

    @Override
    public LocationModelDto getLocationByIdOrTitle(String idOrTitle) {
        Location currentLocation;

        if (parser.isInputStringAnId(idOrTitle)) {
            currentLocation = locationRepository.getLocationById(Long.parseLong(idOrTitle))
                    .orElseThrow(() -> new LocationNotFoundException("Location not found"));
        } else {
            currentLocation = locationRepository.getLocationByTitle(idOrTitle)
                    .orElseThrow(() -> new LocationNotFoundException("Location not found"));
        }

        return mapper.mapToDto(currentLocation, LocationModelDto.class);
    }

    @Override
    public RequestStatusDto deleteLocationById(Long locationId) {
        Location currentLocation = locationRepository.getLocationById(locationId)
                .orElseThrow(() -> new LocationNotFoundException("Location not found"));

        locationRepository.delete(currentLocation);

        return new RequestStatusDto()
                .setStatusId(HttpStatus.OK.value())
                .setTimespan(Calendar.getInstance().getTime().toString())
                .setMessage("Location successfully deleted");
    }

    @Override
    public RequestStatusDto updateAdditionalLocationInformation(Long locationId,
                                                                UpdateLocationInformationRequestDto updateRequest) {
        Location currentLocation = locationRepository.getLocationById(locationId)
                .orElseThrow(() -> new LocationNotFoundException("Location not found"));

        currentLocation.setTitle(updateRequest.getTitle());
        currentLocation.setAddress(updateRequest.getAddress());
        currentLocation.setCapacity(updateRequest.getCapacity());

        locationRepository.save(currentLocation);

        return new RequestStatusDto()
                .setStatusId(HttpStatus.OK.value())
                .setTimespan(Calendar.getInstance().getTime().toString())
                .setMessage("Location successfully update");
    }
}
