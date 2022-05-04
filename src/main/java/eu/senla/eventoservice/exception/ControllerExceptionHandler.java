package eu.senla.eventoservice.exception;

import eu.senla.eventoservice.dto.ExceptionCaughtDto;
import eu.senla.eventoservice.exception.artist.ArtistNicknameMismatchException;
import eu.senla.eventoservice.exception.artist.ArtistNotFoundException;
import eu.senla.eventoservice.exception.credential.CredentialWithSameDataAlreadyExistsException;
import eu.senla.eventoservice.exception.credential.CredentialsNotFoundException;
import eu.senla.eventoservice.exception.event.EventNotFoundException;
import eu.senla.eventoservice.exception.event.EventTitleDuplicateException;
import eu.senla.eventoservice.exception.location.LocationTitleDuplicateException;
import eu.senla.eventoservice.exception.location.OutOfLocationSpaceException;
import eu.senla.eventoservice.exception.other.CameNullEntityException;
import eu.senla.eventoservice.exception.role.RoleNotFoundException;
import eu.senla.eventoservice.exception.ticket.TicketNotFoundException;
import eu.senla.eventoservice.exception.user.UserDataMismatchException;
import eu.senla.eventoservice.exception.user.UserNotFoundException;
import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Calendar;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {
            RoleNotFoundException.class,
            UserNotFoundException.class,
            EventNotFoundException.class,
            ArtistNotFoundException.class,
            TicketNotFoundException.class,
            CredentialsNotFoundException.class
    })
    public String resourceNotFoundException(Model model,
                                            RuntimeException exception) {
        ExceptionCaughtDto exceptionCaughtDto = new ExceptionCaughtDto()
                .setStatusId(HttpStatus.NOT_FOUND.value())
                .setTimespan(Calendar.getInstance().getTime().toString())
                .setMessage(exception.getMessage());

        model.addAttribute("error", exceptionCaughtDto);

        return "error.html";
    }

    @ExceptionHandler(value = {
            UserDataMismatchException.class,
            OutOfLocationSpaceException.class,
            EventTitleDuplicateException.class,
            LocationTitleDuplicateException.class,
            ArtistNicknameMismatchException.class,
            CredentialWithSameDataAlreadyExistsException.class,
            InvalidFileNameException.class
    })
    public String dataDuplicationException(Model model,
                                           RuntimeException exception) {
        ExceptionCaughtDto exceptionCaughtDto = new ExceptionCaughtDto()
                .setStatusId(HttpStatus.BAD_REQUEST.value())
                .setTimespan(Calendar.getInstance().getTime().toString())
                .setMessage(exception.getMessage());

        model.addAttribute("error", exceptionCaughtDto);

        return "error.html";
    }

    @ExceptionHandler(value = CameNullEntityException.class)
    public String internalServerErrorException(Model model,
                                               RuntimeException exception) {
        ExceptionCaughtDto exceptionCaughtDto = new ExceptionCaughtDto()
                .setStatusId(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .setTimespan(Calendar.getInstance().getTime().toString())
                .setMessage(exception.getMessage());

        model.addAttribute("error", exceptionCaughtDto);

        return "error.html";
    }

}
