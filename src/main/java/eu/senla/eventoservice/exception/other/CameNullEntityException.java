package eu.senla.eventoservice.exception.other;

public class CameNullEntityException extends RuntimeException {

    public CameNullEntityException(String errorMessage) {
        super(errorMessage);
    }

}
