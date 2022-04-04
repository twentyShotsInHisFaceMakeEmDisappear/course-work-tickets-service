package eu.senla.eventoservice.exception.ticket;

public class TicketNotFoundException extends RuntimeException {

    public TicketNotFoundException(String errorMessage) {
        super(errorMessage);
    }

}
