package eu.senla.eventoservice.service;

import eu.senla.eventoservice.dto.LocationModelDto;
import eu.senla.eventoservice.dto.RequestStatusDto;
import eu.senla.eventoservice.dto.UpdateLocationInformationRequestDto;

public interface LocationServiceInterface {

    RequestStatusDto deleteLocationById(Long locationId);

    LocationModelDto getLocationByIdOrTitle(String idOrTitle);

    RequestStatusDto createLocation(LocationModelDto locationModelDto);

    RequestStatusDto updateAdditionalLocationInformation(Long locationId,
                                                         UpdateLocationInformationRequestDto updateRequest);

}
