package eu.senla.eventoservice.service;

import eu.senla.eventoservice.dto.CreateEventRequestDto;
import eu.senla.eventoservice.dto.EventModelDto;
import eu.senla.eventoservice.dto.RequestStatusDto;

import java.util.List;

public interface EventServiceInterface {

    List<EventModelDto> getAllEvents();

    List<EventModelDto> getTopSixEvents();

    EventModelDto getEventById(Long eventId);

    RequestStatusDto deleteEvent(Long eventId);

    EventModelDto getEventByTitleContains(String eventTitle);

    List<EventModelDto> getEventsByLocationIdOrTitle(String idOrTitle);

    List<EventModelDto> getEventsByArtistIdOrNickname(String idOrNickname);

    RequestStatusDto createAnEvent(CreateEventRequestDto createEventRequest);

}
