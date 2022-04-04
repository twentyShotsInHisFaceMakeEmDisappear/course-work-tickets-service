package eu.senla.eventoservice.service.impl;

import eu.senla.eventoservice.repository.ArtistRepository;
import eu.senla.eventoservice.repository.CredentialRepository;
import eu.senla.eventoservice.repository.EventRepository;
import eu.senla.eventoservice.repository.TicketRepository;
import eu.senla.eventoservice.service.ItemSecurityExpressionServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemSecurityExpressionService implements ItemSecurityExpressionServiceInterface {

    private final EventRepository eventRepository;

    private final TicketRepository ticketRepository;

    private final ArtistRepository artistRepository;

    private final CredentialRepository credentialRepository;

}
