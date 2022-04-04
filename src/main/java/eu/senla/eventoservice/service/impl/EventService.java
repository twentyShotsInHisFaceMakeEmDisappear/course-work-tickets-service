package eu.senla.eventoservice.service.impl;

import eu.senla.eventoservice.dto.ArtistModelDto;
import eu.senla.eventoservice.dto.CreateEventRequestDto;
import eu.senla.eventoservice.dto.EventModelDto;
import eu.senla.eventoservice.dto.RequestStatusDto;
import eu.senla.eventoservice.exception.artist.ArtistNotFoundException;
import eu.senla.eventoservice.exception.event.EventNotFoundException;
import eu.senla.eventoservice.exception.event.EventTitleDuplicateException;
import eu.senla.eventoservice.model.Artist;
import eu.senla.eventoservice.model.Event;
import eu.senla.eventoservice.repository.ArtistRepository;
import eu.senla.eventoservice.repository.EventRepository;
import eu.senla.eventoservice.service.EventServiceInterface;
import eu.senla.eventoservice.util.mapper.MapperInterface;
import eu.senla.eventoservice.util.parser.ParserInterface;
import eu.senla.eventoservice.util.parser.impl.Parser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService implements EventServiceInterface {

    private final ParserInterface parser;

    private final EventRepository eventRepository;

    private final ArtistRepository artistRepository;

    private final MapperInterface<EventModelDto, Event> defaultMapper;

    private final MapperInterface<CreateEventRequestDto, Event> mapper;

    @Override
    public RequestStatusDto createAnEvent(CreateEventRequestDto createEventRequest) {
        if (eventRepository.existsByTitle(createEventRequest.getTitle()))
            throw new EventTitleDuplicateException("There is already event with same title");

        Artist currentArtist = artistRepository.getArtistByNickname(createEventRequest.getEventOrganizer())
                .orElseThrow(() -> new ArtistNotFoundException("Artist not found"));

        createEventRequest.setEventOrganizer(null);

        Event currentEvent = mapper.mapToEntity(createEventRequest, Event.class)
                .setEventOrganizer(currentArtist);

        eventRepository.save(currentEvent);

        return new RequestStatusDto()
                .setStatusId(HttpStatus.OK.value())
                .setTimespan(Calendar.getInstance().getTime().toString())
                .setMessage("Event \"" + currentEvent.getTitle() + "\" successfully created");
    }

    @Override
    public EventModelDto getEventById(Long eventId) {
        Event currentEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event not found"));

        return defaultMapper.mapToDto(currentEvent, EventModelDto.class);
    }

    @Override
    public RequestStatusDto deleteEvent(Long eventId) {
        Event currentEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event not found exception"));

        eventRepository.delete(currentEvent);

        return new RequestStatusDto()
                .setStatusId(HttpStatus.OK.value())
                .setTimespan(Calendar.getInstance().getTime().toString())
                .setMessage("Event \"" + currentEvent.getTitle() + "\" successfully deleted");
    }

    @Override
    public List<EventModelDto> getEventsByArtistIdOrNickname(String idOrNickname) {
        List<Event> eventModelsList;

        if (parser.isInputStringAnId(idOrNickname)) {
            eventModelsList = eventRepository.getAllByEventOrganizer_Id(Long.parseLong(idOrNickname))
                    .orElseThrow(() -> new EventNotFoundException("Events not found"));
        } else {
            eventModelsList = eventRepository.getAllByEventOrganizer_Nickname(idOrNickname)
                    .orElseThrow(() -> new EventNotFoundException("Events not found"));
        }

        return defaultMapper.listToDto(eventModelsList, EventModelDto.class);
    }

    @Override
    public List<EventModelDto> getEventsByLocationIdOrTitle(String idOrTitle) {
        List<Event> eventModelsList;

        if (parser.isInputStringAnId(idOrTitle)) {
            eventModelsList = eventRepository.getAllByLocation_Id(Long.parseLong(idOrTitle))
                    .orElseThrow(() -> new EventNotFoundException("Events not found"));
        } else {
            eventModelsList = eventRepository.getAllByLocation_Title(idOrTitle)
                    .orElseThrow(() -> new EventNotFoundException("Events not found"));
        }

        return defaultMapper.listToDto(eventModelsList, EventModelDto.class);
    }

    @Override
    public EventModelDto getEventByTitleContains(String eventTitle) {
        Event currentEvent = eventRepository.getEventByTitleContains(eventTitle)
                .orElseThrow(() -> new EventNotFoundException("Event not found"));

        return defaultMapper.mapToDto(currentEvent, EventModelDto.class);
    }

    @Override
    public List<EventModelDto> getTopSixEvents() {
        List<Event> currentEvents = eventRepository.getTopSixEvents()
                .orElseThrow(() -> new EventNotFoundException("Something went wrong"));

        List<EventModelDto> currentDtoList = defaultMapper
                .listToDto(currentEvents, EventModelDto.class);

        for (EventModelDto eventModel : currentDtoList) {
            if (eventModel.getDescription().length() > 61)
                eventModel.setDescription(eventModel.getDescription().substring(0, 60) + "...");
            else
                eventModel.setDescription(eventModel.getDescription() + "...");
        }

        return currentDtoList;
    }

    @Override
    public List<EventModelDto> getAllEvents() {
        List<Event> currentEvents = eventRepository.getAll()
                .orElseThrow(() -> new EventNotFoundException("There is no such event"));

        List<EventModelDto> returnValue = defaultMapper
                .listToDto(currentEvents, EventModelDto.class);

        for (EventModelDto eventModel : returnValue) {
            if (eventModel.getDescription().length() > 61)
                eventModel.setDescription(eventModel.getDescription().substring(0, 60) + "...");
            else
                eventModel.setDescription(eventModel.getDescription() + "...");
        }

        return returnValue;
    }

}
