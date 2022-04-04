package eu.senla.eventoservice.service;

import eu.senla.eventoservice.dto.OrderTicketRequestDto;
import eu.senla.eventoservice.dto.TicketModelDto;

import java.util.List;

public interface TicketServiceInterface {

    TicketModelDto getTicketById(Long id);

    List<TicketModelDto> getAllTicketsByUserId(Long userId);

    List<TicketModelDto> getAllTicketsByEventId(Long eventId);

    TicketModelDto orderAnTicket(String ownerEmail, OrderTicketRequestDto orderTicketRequest);

}
