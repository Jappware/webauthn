package com.demo.webauthn.controller;

import com.demo.webauthn.jpa.entity.User;
import com.demo.webauthn.service.RegistrationService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/api/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    @ResponseBody
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
