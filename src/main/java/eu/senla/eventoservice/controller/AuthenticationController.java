package eu.senla.eventoservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {

    @GetMapping("auth")
    private String getLoginPage() {

        return "user/login-page.html";
    }

    @GetMapping("reg")
    private String getRegistrationPage() {

        return "user/registration.html";
    }

}
