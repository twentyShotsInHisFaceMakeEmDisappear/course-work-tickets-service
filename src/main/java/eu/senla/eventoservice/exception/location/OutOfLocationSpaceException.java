package eu.senla.eventoservice.exception.location;

public class OutOfLocationSpaceException extends RuntimeException {

    public OutOfLocationSpaceException(String errorMessage) {
        super(errorMessage);
    }

}
