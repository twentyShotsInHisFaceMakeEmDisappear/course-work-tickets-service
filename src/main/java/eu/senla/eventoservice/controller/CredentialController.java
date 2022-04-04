package eu.senla.eventoservice.controller;

import eu.senla.eventoservice.service.CredentialServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class CredentialController {

    private final CredentialServiceInterface credentialService;

}
