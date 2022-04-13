package eu.senla.eventoservice.controller;

import eu.senla.eventoservice.dto.TicketModelDto;
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

    @PostMapping("orders/{userEmail}")
    private String orderAnTicket(Model model,
                                 @PathVariable("userEmail") String userEmail,
                                 @RequestParam("eventId") Long eventId) {

        TicketModelDto ticketModelDto = ticketService.orderAnTicket(userEmail, eventId);
        model.addAttribute("ticket", ticketModelDto);

        return "ticket-order.html";
    }

}
