package eu.senla.eventoservice.service.impl;

import eu.senla.eventoservice.dto.RequestStatusDto;
import eu.senla.eventoservice.dto.UpdateUserAdditionalInfoRequestDto;
import eu.senla.eventoservice.dto.UpdateUserPhoneNumberRequestDto;
import eu.senla.eventoservice.dto.UserModelDto;
import eu.senla.eventoservice.exception.user.UserDataMismatchException;
import eu.senla.eventoservice.exception.user.UserNotFoundException;
import eu.senla.eventoservice.model.User;
import eu.senla.eventoservice.repository.UserRepository;
import eu.senla.eventoservice.service.UserServiceInterface;
import eu.senla.eventoservice.util.mapper.MapperInterface;
import eu.senla.eventoservice.util.parser.ParserInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface {

    private final ParserInterface parser;

    private final UserRepository userRepository;

    private final MapperInterface<UserModelDto, User> mapper;

    @Override
    public UserModelDto getUserByPhoneNumberOrId(String getUserByPhoneNumberOrIdRequestString) {
        User currentUser;

        if (parser.isInputStringAnId(getUserByPhoneNumberOrIdRequestString)) {
            currentUser = userRepository
                    .findById(Long.parseLong(getUserByPhoneNumberOrIdRequestString))
                    .orElseThrow(() -> new UserNotFoundException("User not found"));
        } else {
            currentUser = userRepository.getUserByPhoneNumber(getUserByPhoneNumberOrIdRequestString)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));
        }

        return mapper.mapToDto(currentUser, UserModelDto.class);
    }

    @Override
    public RequestStatusDto updateUserPhoneNumber(UpdateUserPhoneNumberRequestDto updateUserPhoneNumberRequest) {
        if (!updateUserPhoneNumberRequest.getPhoneNumber()
                .equals(updateUserPhoneNumberRequest.getPhoneNumberConfirmation())
                || userRepository.existsByPhoneNumber(updateUserPhoneNumberRequest.getPhoneNumberConfirmation()))
            throw new UserDataMismatchException("There is phone number mismatch");

        User currentUser = userRepository.getUserByPhoneNumber(updateUserPhoneNumberRequest.getPhoneNumber())
                .orElseThrow(() -> new UserNotFoundException("User with such phone number not found"));

        currentUser.setPhoneNumber(updateUserPhoneNumberRequest.getPhoneNumberConfirmation());

        userRepository.save(currentUser);

        return new RequestStatusDto()
                .setStatusId(HttpStatus.OK.value())
                .setTimespan(Calendar.getInstance().getTime().toString())
                .setMessage("Phone number successfully change");
    }

    @Override
    public RequestStatusDto updateUserAdditionalInfo(UpdateUserAdditionalInfoRequestDto updateUserRequest) {
        User currentUser = userRepository.getUserByPhoneNumber(updateUserRequest.getPhoneNumber())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        currentUser.setFirstName(updateUserRequest.getFirstName());
        currentUser.setSurname(updateUserRequest.getSurname());

        userRepository.save(currentUser);

        return new RequestStatusDto()
                .setStatusId(HttpStatus.OK.value())
                .setTimespan(Calendar.getInstance().getTime().toString())
                .setMessage("Additional user info successfully updated");
    }
}
