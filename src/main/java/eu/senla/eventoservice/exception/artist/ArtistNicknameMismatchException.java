package eu.senla.eventoservice.exception.artist;

public class ArtistNicknameMismatchException extends RuntimeException {

    public ArtistNicknameMismatchException(String errorMessage) {
        super(errorMessage);
    }

}
