package com.demo.webauthn.config;

import com.demo.webauthn.repository.CredentialRepositoryImpl;
import com.yubico.webauthn.RelyingParty;
import com.yubico.webauthn.data.RelyingPartyIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RelyingPartyConfig {

    @Bean
    @Autowired
    public RelyingParty relyingParty(CredentialRepositoryImpl credentialRepository,
                                     WebauthnConfig webAuthnConfig) {

        var relyingPartyIdentity = RelyingPartyIdentity.builder()
                                                       .id(webAuthnConfig.getHostname())
                                                       .name(webAuthnConfig.getDisplay())
                                                       .build();

        return RelyingParty.builder()
                           .identity(relyingPartyIdentity)
                           .credentialRepository(credentialRepository)
                           .origins(webAuthnConfig.getOrigin())
                           .build();
    }
}
