package eu.senla.eventoservice.service;

import eu.senla.eventoservice.dto.RequestStatusDto;
import eu.senla.eventoservice.dto.UserRoleManipulationRequestDto;

public interface RoleServiceInterface {

    RequestStatusDto assigningRoleToUser(UserRoleManipulationRequestDto grantRoleToUserRequest);

    RequestStatusDto demoteRoleFromUser(UserRoleManipulationRequestDto demoteRoleFromUserRequest);

}
