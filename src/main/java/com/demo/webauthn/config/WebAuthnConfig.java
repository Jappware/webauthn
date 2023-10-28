package com.demo.webauthn.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "webauthn")
public class WebAuthnConfig {

    private String hostname;

    private String display;

    private Set<String> origin;
}
