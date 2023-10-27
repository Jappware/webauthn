package com.demo.webauthn.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yubico.webauthn.AssertionRequest;
import com.yubico.webauthn.FinishAssertionOptions;
import com.yubico.webauthn.RelyingParty;
import com.yubico.webauthn.StartAssertionOptions;
import com.yubico.webauthn.data.PublicKeyCredential;
import com.yubico.webauthn.exception.AssertionFailedException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {

    private final RelyingParty relyingParty;

    public String startLogin(String username, HttpSession session) {

        var startAssertionOptions = StartAssertionOptions.builder()
                                                         .username(username)
                                                         .build();

        var request = relyingParty.startAssertion(startAssertionOptions);

        try {
            session.setAttribute(username, request);

            return request.toCredentialsGetJson();
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public String finishLogin(String credential, String username, Model model, HttpSession session) {

        try {
            var publicKeyCredential = PublicKeyCredential.parseAssertionResponseJson(credential);
            var request = (AssertionRequest) session.getAttribute(username);

            var finishAssertionOptions = FinishAssertionOptions.builder()
                                                               .request(request)
                                                               .response(publicKeyCredential)
                                                               .build();

            var result = relyingParty.finishAssertion(finishAssertionOptions);

            if (result.isSuccess()) {
                model.addAttribute("username", username);
                return "welcome";
            } else {
                return "index";
            }
        } catch (IOException | AssertionFailedException e) {
            log.error("Authentication failed", e);
            throw new RuntimeException("Authentication failed", e);
        }
    }
}
