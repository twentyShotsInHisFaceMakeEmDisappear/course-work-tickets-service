package eu.senla.eventoservice.controller;

import eu.senla.eventoservice.dto.CredentialModelDto;
import eu.senla.eventoservice.dto.RequestStatusDto;
import eu.senla.eventoservice.service.CredentialServiceInterface;
import eu.senla.eventoservice.service.UserServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {

    private final CredentialServiceInterface credentialService;

    @GetMapping("auth")
    public String getLoginPage() {

        return "user/login-page.html";
    }

    @GetMapping("reg")
    public String getRegistrationPage() {

        return "user/registration.html";
    }

    @PostMapping("account")
    public String createAnNewAccount(Model model,
                                     @RequestBody CredentialModelDto credentialModel) {

        RequestStatusDto statusDto = credentialService.registeredAnAccount(credentialModel);
        model.addAttribute("status", statusDto);

        return "account";
    }

}
