package eu.senla.eventoservice.service;

import eu.senla.eventoservice.dto.CredentialModelDto;
import eu.senla.eventoservice.dto.DeleteAccountRequestDto;
import eu.senla.eventoservice.dto.RequestStatusDto;

public interface CredentialServiceInterface {

    RequestStatusDto registeredAnAccount(CredentialModelDto registrationRequest);

    RequestStatusDto deleteAccount(DeleteAccountRequestDto deleteAccountRequest);

}
