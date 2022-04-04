package eu.senla.eventoservice.exception.credential;

public class CredentialWithSameDataAlreadyExistsException extends RuntimeException {

    public CredentialWithSameDataAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }

}
