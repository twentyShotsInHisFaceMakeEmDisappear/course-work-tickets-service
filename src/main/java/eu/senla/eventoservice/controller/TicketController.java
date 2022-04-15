package eu.senla.eventoservice.controller;

import eu.senla.eventoservice.dto.TicketModelDto;
import eu.senla.eventoservice.dto.TicketOrderDto;
import eu.senla.eventoservice.service.TicketServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketServiceInterface ticketService;

    @GetMapping("{eventId}")
    public String getOrderTicketPage(Model model,
                                     @PathVariable("eventId") Long eventId) {

        model.addAttribute("orderDto", new TicketOrderDto());
        model.addAttribute("currentEventId", eventId);

        return "tickets/ticket-order-presentation.html";
    }

    @PostMapping("")
    public String orderAnTicket(Model model,
                                 @ModelAttribute TicketOrderDto ticketOrder) {

        TicketModelDto ticketModelDto = ticketService.orderAnTicket(ticketOrder.getEmail(),
                ticketOrder.getEventId());
        model.addAttribute("ticket", ticketModelDto);

        return "tickets/ticket-order.html";
    }

}
