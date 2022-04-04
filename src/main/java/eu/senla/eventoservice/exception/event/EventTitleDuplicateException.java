package eu.senla.eventoservice.exception.event;

public class EventTitleDuplicateException extends RuntimeException {

    public EventTitleDuplicateException(String errorMessage) {
        super(errorMessage);
    }

}
