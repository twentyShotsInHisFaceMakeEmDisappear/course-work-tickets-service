package eu.senla.eventoservice.exception.phone;

public class InvalidPhoneNumberException extends RuntimeException {

    public InvalidPhoneNumberException(String message) {
        super(message);
    }

}
