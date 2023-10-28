package com.demo.webauthn.repository;

import com.demo.webauthn.entity.Authenticator;
import com.demo.webauthn.entity.User;
import com.yubico.webauthn.CredentialRepository;
import com.yubico.webauthn.RegisteredCredential;
import com.yubico.webauthn.data.ByteArray;
import com.yubico.webauthn.data.PublicKeyCredentialDescriptor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Getter
@Slf4j
public class CredentialRepositoryImpl implements CredentialRepository {

    private final UserRepository userRepository;

    private final AuthenticatorRepository authenticatorRepository;

    @Override
    public Set<PublicKeyCredentialDescriptor> getCredentialIdsForUsername(String username) {

        var user = userRepository.findByUsername(username);
        checkUserExistence(user, username);

        var authenticators = authenticatorRepository.findAllByUser(user);

        return authenticators.stream()
                             .map(authenticator -> PublicKeyCredentialDescriptor.builder()
                                                                                .id(authenticator.getCredentialId())
                                                                                .build())
                             .collect(Collectors.toSet());
    }

    @Override
    public Optional<ByteArray> getUserHandleForUsername(String username) {

        var user = userRepository.findByUsername(username);
        checkUserExistence(user, username);

        return Optional.of(user.getHandle());
    }

    @Override
    public Optional<String> getUsernameForUserHandle(ByteArray userHandle) {

        var user = userRepository.findByHandle(userHandle);

        return Optional.of(user.getUsername());
    }

    @Override
    public Optional<RegisteredCredential> lookup(ByteArray credentialId, ByteArray userHandle) {

        var authenticator = authenticatorRepository.findByCredentialId(credentialId);

        return authenticator.map(Authenticator::toRegisteredCredential);
    }

    @Override
    public Set<RegisteredCredential> lookupAll(ByteArray credentialId) {

        var authenticators = authenticatorRepository.findAllByCredentialId(credentialId);

        return authenticators.stream()
                             .map(Authenticator::toRegisteredCredential)
                             .collect(Collectors.toSet());
    }

    private void checkUserExistence(User user, String username) {

        if (user == null) {
            log.error("User " + username + " not found.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User " + username + " not found.");
        }
    }
}
