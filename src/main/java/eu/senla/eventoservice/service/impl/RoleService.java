package eu.senla.eventoservice.service.impl;

import eu.senla.eventoservice.dto.RequestStatusDto;
import eu.senla.eventoservice.dto.UserRoleManipulationRequestDto;
import eu.senla.eventoservice.exception.credential.CredentialsNotFoundException;
import eu.senla.eventoservice.exception.role.RoleNotFoundException;
import eu.senla.eventoservice.model.Credential;
import eu.senla.eventoservice.model.Role;
import eu.senla.eventoservice.repository.CredentialRepository;
import eu.senla.eventoservice.repository.RoleRepository;
import eu.senla.eventoservice.service.RoleServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
@RequiredArgsConstructor
public class RoleService implements RoleServiceInterface {

    private final RoleRepository roleRepository;

    private final CredentialRepository credentialRepository;

    @Override
    public RequestStatusDto assigningRoleToUser(UserRoleManipulationRequestDto grantRoleToUserRequest) {

        Role currentRoleToGrant = roleRepository.findByName(grantRoleToUserRequest.getRole())
                .orElseThrow(() -> new RoleNotFoundException("Role not found"));

        Credential currentUser = credentialRepository.findByEmail(grantRoleToUserRequest.getUserEmail())
                .orElseThrow(() -> new CredentialsNotFoundException("User not found"));

        if (currentUser.getRoles().stream()
                .anyMatch(role -> role.getName().equals(currentRoleToGrant.getName())))
            throw new RoleNotFoundException("This user already has such role");

        currentUser.getRoles().add(currentRoleToGrant);

        credentialRepository.save(currentUser);

        return new RequestStatusDto()
                .setStatusId(HttpStatus.OK.value())
                .setTimespan(Calendar.getInstance().getTime().toString())
                .setMessage("Selected role successfully granted");
    }

    @Override
    public RequestStatusDto demoteRoleFromUser(UserRoleManipulationRequestDto demoteRoleFromUserRequest) {

        Role currentRoleToGrant = roleRepository.findByName(demoteRoleFromUserRequest.getRole())
                .orElseThrow(() -> new RoleNotFoundException("Role not found"));

        Credential currentUser = credentialRepository.findByEmail(demoteRoleFromUserRequest.getUserEmail())
                .orElseThrow(() -> new CredentialsNotFoundException("User not found"));

        if (currentUser.getRoles().stream()
                .anyMatch(role -> role.getName().equals(currentRoleToGrant.getName())))
            throw new RoleNotFoundException("This user did not own current role");

        currentUser.getRoles().remove(currentRoleToGrant);

        return new RequestStatusDto()
                .setStatusId(HttpStatus.OK.value())
                .setTimespan(Calendar.getInstance().getTime().toString())
                .setMessage("Selected role successfully demote");
    }

}
