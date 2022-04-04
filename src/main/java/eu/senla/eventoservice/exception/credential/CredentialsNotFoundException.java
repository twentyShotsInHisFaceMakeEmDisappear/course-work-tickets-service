package eu.senla.eventoservice.exception.credential;

public class CredentialsNotFoundException extends RuntimeException {

    public CredentialsNotFoundException(String errorMessage) {
        super(errorMessage);
    }

}
