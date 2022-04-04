package eu.senla.eventoservice.exception.user;

public class UserDataMismatchException extends RuntimeException {

    public UserDataMismatchException(String errorMessage) {
        super(errorMessage);
    }

}
