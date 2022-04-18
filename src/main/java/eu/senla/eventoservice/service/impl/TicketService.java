package eu.senla.eventoservice.service.impl;

import eu.senla.eventoservice.dto.OrderTicketRequestDto;
import eu.senla.eventoservice.dto.TicketModelDto;
import eu.senla.eventoservice.exception.event.EventNotFoundException;
import eu.senla.eventoservice.exception.location.OutOfLocationSpaceException;
import eu.senla.eventoservice.exception.ticket.TicketNotFoundException;
import eu.senla.eventoservice.exception.user.UserNotFoundException;
import eu.senla.eventoservice.model.Event;
import eu.senla.eventoservice.model.Ticket;
import eu.senla.eventoservice.model.User;
import eu.senla.eventoservice.repository.EventRepository;
import eu.senla.eventoservice.repository.TicketRepository;
import eu.senla.eventoservice.repository.UserRepository;
import eu.senla.eventoservice.service.TicketServiceInterface;
import eu.senla.eventoservice.util.mapper.MapperInterface;
import liquibase.pro.packaged.T;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService implements TicketServiceInterface {

    @Value("${com.company.qrCode-link}")
    private String qrCodeGeneratorLink;

    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;

    private final EventRepository eventRepository;

    private final TicketRepository ticketRepository;

    private final MapperInterface<TicketModelDto, Ticket> mapper;

    @Override
    @Transactional
    public TicketModelDto orderAnTicket(String ownerEmail, Long eventId) {
        User currentUser = userRepository.getUserByCredential_Email(ownerEmail)
                .orElseThrow(() -> new UserNotFoundException("Something went wrong"));

        Event currentEvent = eventRepository.findById(eventId)
                        .orElseThrow(() -> new EventNotFoundException("Event not found"));

        if (currentEvent.getOccupiedPlace() + 1 > currentEvent.getLocation().getCapacity())
            throw new OutOfLocationSpaceException(currentEvent.getTitle() + " is sold out!");

        currentEvent.setOccupiedPlace((short)(currentEvent.getOccupiedPlace() + 1));

        Ticket currentTicket = new Ticket()
                .setOwner(currentUser)
                .setEventHolding(currentEvent)
                .setOrderDate(Calendar.getInstance().getTime())
                .setQrCode(qrCodeGeneratorLink);

        eventRepository.save(currentEvent);
        ticketRepository.save(currentTicket);

        ticketRepository.save(currentTicket.setQrCode(currentTicket.getQrCode() + currentTicket.getId()));

        sendMessageToCustomer(ownerEmail, currentTicket);

        return mapper.mapToDto(currentTicket, TicketModelDto.class);
    }

    @Override
    public TicketModelDto getTicketById(Long id) {
        Ticket currentTicket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found"));

        return mapper.mapToDto(currentTicket, TicketModelDto.class);
    }

    @Override
    public List<TicketModelDto> getAllTicketsByEventId(Long eventId) {
        List<Ticket> ticketList = ticketRepository.getAllByEventHolding_Id(eventId)
                .orElseThrow(() -> new TicketNotFoundException("Tickets not found"));

        return mapper.listToDto(ticketList, TicketModelDto.class);
    }

    @Override
    public List<TicketModelDto> getAllTicketsByUserId(Long userId) {
        List<Ticket> ticketList = ticketRepository.getAllByOwner_Id(userId)
                .orElseThrow(() -> new TicketNotFoundException("Tickets not found"));

        return mapper.listToDto(ticketList, TicketModelDto.class);
    }

    private void sendMessageToCustomer(String customerEmail, Ticket currentTicket) {

        Thread parallelEmailSendingThread = new Thread(() -> {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

            simpleMailMessage.setFrom("noreply@company.com");
            simpleMailMessage.setTo(customerEmail);

            simpleMailMessage.setSubject("COM.COMPANY: " + currentTicket.getEventHolding().getTitle() + " Ticket Order");
            simpleMailMessage.setText("You are successfully ordered ticket!\n\nYour personal ticket code: "
                    + currentTicket.getId() + "\n\nAnd QR-Code: " + currentTicket.getQrCode());

            javaMailSender.send(simpleMailMessage);
        });

        parallelEmailSendingThread.start();
    }

}
