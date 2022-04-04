package eu.senla.eventoservice.exception.location;

public class LocationTitleDuplicateException extends RuntimeException {

    public LocationTitleDuplicateException(String errorMessage) {
        super(errorMessage);
    }

}
