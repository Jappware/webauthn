package com.demo.webauthn.service;

import com.demo.webauthn.jpa.entity.Authenticator;
import com.demo.webauthn.jpa.entity.User;
import com.demo.webauthn.jpa.repository.CredentialRepositoryImpl;
import com.demo.webauthn.util.Utility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.yubico.webauthn.FinishRegistrationOptions;
import com.yubico.webauthn.RelyingParty;
import com.yubico.webauthn.StartRegistrationOptions;
import com.yubico.webauthn.data.PublicKeyCredential;
import com.yubico.webauthn.data.PublicKeyCredentialCreationOptions;
import com.yubico.webauthn.data.UserIdentity;
import com.yubico.webauthn.exception.RegistrationFailedException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationService {

    private final RelyingParty relyingParty;

    private final CredentialRepositoryImpl credentialRepository;

    public String registerUser(String username, String display, HttpSession session) {

        var userRepository = credentialRepository.getUserRepository();
        var user = userRepository.findByUsername(username);

        if (user == null) {
            var userIdentity = UserIdentity.builder()
                                           .name(username)
                                           .displayName(display)
                                           .id(Utility.generateRandom(32))
                                           .build();

            var userWithIdentity = new User(userIdentity);
            var savedUser = userRepository.save(userWithIdentity);

            return registerAuthenticator(savedUser, session);
        } else {
            log.error("User " + username + " already exists. Choose a new identifier.");
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                                              "User " + username + " already exists. Choose a new identifier.");
        }
    }

    public String registerAuthenticator(User user, HttpSession session) {

        var userRepository = credentialRepository.getUserRepository();
        var userWithHandle = userRepository.findByHandle(user.getHandle());

        if (userWithHandle != null) {
            var userIdentity = user.toUserIdentity();

            var registrationOptions = StartRegistrationOptions.builder()
                                                              .user(userIdentity)
                                                              .build();

            var registration = relyingParty.startRegistration(registrationOptions);
            session.setAttribute(userIdentity.getName(), registration);

            try {
                return registration.toCredentialsCreateJson();
            } catch (JsonProcessingException e) {
                log.error("Error processing JSON.", e);
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error processing JSON.", e);
            }
        } else {
            log.error("User " + user.getUsername() + " does not exist. Please register.");
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                                              "User " + user.getUsername() + " does not exist. Please register.");
        }
    }

    public ModelAndView finishRegistration(String credential, String username, String credentialName,
                                           HttpSession session) {

        try {
            var userRepository = credentialRepository.getUserRepository();
            var authenticatorRepository = credentialRepository.getAuthenticatorRepository();

            var user = userRepository.findByUsername(username);
            var requestOptions = (PublicKeyCredentialCreationOptions) session.getAttribute(user.getUsername());

            if (requestOptions != null) {
                var publicKeyCredential = PublicKeyCredential.parseRegistrationResponseJson(credential);

                var options = FinishRegistrationOptions.builder()
                                                       .request(requestOptions)
                                                       .response(publicKeyCredential)
                                                       .build();

                var result = relyingParty.finishRegistration(options);

                var authenticator = new Authenticator(result, publicKeyCredential.getResponse(),
                                                      user, credentialName);

                authenticatorRepository.save(authenticator);

                return new ModelAndView("redirect:/login", HttpStatus.SEE_OTHER);
            } else {
                log.error("Cached request expired. Try to register again!");
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                                                  "Cached request expired. Try to register again!");
            }
        } catch (RegistrationFailedException e) {
            log.error("Registration failed.", e);
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Registration failed.", e);
        } catch (IOException e) {
            log.error("Failed to save credential, please try again.", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                              "Failed to save credential, please try again!",
                                              e);
        }
    }
}
