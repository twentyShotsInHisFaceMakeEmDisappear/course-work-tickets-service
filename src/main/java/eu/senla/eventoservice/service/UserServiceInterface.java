package eu.senla.eventoservice.service;

import eu.senla.eventoservice.dto.RequestStatusDto;
import eu.senla.eventoservice.dto.UpdateUserAdditionalInfoRequestDto;
import eu.senla.eventoservice.dto.UpdateUserPhoneNumberRequestDto;
import eu.senla.eventoservice.dto.UserModelDto;

public interface UserServiceInterface {

    UserModelDto getUserByPhoneNumberOrId(String getUserByPhoneNumberOrIdRequestString);

    RequestStatusDto updateUserAdditionalInfo(UpdateUserAdditionalInfoRequestDto updateUserRequest);

    RequestStatusDto updateUserPhoneNumber(UpdateUserPhoneNumberRequestDto updateUserPhoneNumberRequest);

}
