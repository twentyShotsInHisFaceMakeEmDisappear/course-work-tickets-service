package eu.senla.eventoservice.service.impl;

import eu.senla.eventoservice.dto.CredentialModelDto;
import eu.senla.eventoservice.dto.DeleteAccountRequestDto;
import eu.senla.eventoservice.dto.RequestStatusDto;
import eu.senla.eventoservice.exception.credential.CredentialWithSameDataAlreadyExistsException;
import eu.senla.eventoservice.exception.credential.CredentialsNotFoundException;
import eu.senla.eventoservice.exception.role.RoleNotFoundException;
import eu.senla.eventoservice.exception.user.UserDataMismatchException;
import eu.senla.eventoservice.model.Credential;
import eu.senla.eventoservice.model.Role;
import eu.senla.eventoservice.model.User;
import eu.senla.eventoservice.repository.CredentialRepository;
import eu.senla.eventoservice.repository.RoleRepository;
import eu.senla.eventoservice.service.CredentialServiceInterface;
import eu.senla.eventoservice.util.mapper.MapperInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class CredentialService implements CredentialServiceInterface {

    private final RoleRepository roleRepository;

    private final CredentialRepository credentialRepository;

    private final MapperInterface<CredentialModelDto, Credential> mapper;

    @Override
    public RequestStatusDto registeredAnAccount(CredentialModelDto registrationRequest) {
        if (credentialRepository.existsByEmail(registrationRequest.getEmail()))
            throw new CredentialWithSameDataAlreadyExistsException("Account with same email already exists");

        Role roleToGrant = roleRepository.findByName("USER")
                .orElseThrow(() -> new RoleNotFoundException("Internal server error"));

        Credential currentCredential = mapper.mapToEntity(registrationRequest, Credential.class);

        currentCredential.setRoles(new HashSet<>(){{ add(roleToGrant); }})
                .setUser(new User().setCredential(currentCredential));

        return new RequestStatusDto()
                .setStatusId(HttpStatus.OK.value())
                .setTimespan(Calendar.getInstance().getTime().toString())
                .setMessage("Account successfully created. " +
                        "You can login by your credentials.");
    }

    @Override
    public RequestStatusDto deleteAccount(DeleteAccountRequestDto deleteAccountRequest) {
        if (!deleteAccountRequest.getEmail().equals(deleteAccountRequest.getEmailConfirmation()))
            throw new UserDataMismatchException("Email mismatch");

        Credential currentCredential = credentialRepository.findByEmail(deleteAccountRequest.getEmail())
                .orElseThrow(() -> new CredentialsNotFoundException("User not found"));

        credentialRepository.delete(currentCredential);

        return new RequestStatusDto()
                .setStatusId(HttpStatus.OK.value())
                .setTimespan(Calendar.getInstance().getTime().toString())
                .setMessage("Account successfully deleted");
    }
}
