package com.demo.webauthn.controller;

import com.demo.webauthn.entity.User;
import com.demo.webauthn.service.RegistrationService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public String registerUser(@RequestParam String username, @RequestParam String display, HttpSession session) {

        return registrationService.registerUser(username, display, session);
    }

    @PostMapping("/authenticator")
    @ResponseBody
    public String registerAuthenticator(@RequestParam User user, HttpSession session) {

        return registrationService.registerAuthenticator(user, session);
    }

    @PostMapping("/finish")
    @ResponseBody
    public ModelAndView finishRegistration(@RequestParam String credential, @RequestParam String username,
                                           @RequestParam String credentialName, HttpSession session) {

        return registrationService.finishRegistration(credential, username, credentialName, session);
    }
}
