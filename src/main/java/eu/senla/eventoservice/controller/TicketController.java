package eu.senla.eventoservice.controller;

import eu.senla.eventoservice.service.TicketServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/ticket")
@RequiredArgsConstructor
public class TicketController {

    private final TicketServiceInterface ticketService;

}
