package eu.senla.eventoservice.exception.role;

public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException(String errorMessage) {
        super(errorMessage);
    }

}
