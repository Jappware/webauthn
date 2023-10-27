package com.demo.webauthn.entity;

import com.yubico.webauthn.RegisteredCredential;
import com.yubico.webauthn.RegistrationResult;
import com.yubico.webauthn.data.AuthenticatorAttestationResponse;
import com.yubico.webauthn.data.ByteArray;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Getter
@NoArgsConstructor
public class Authenticator {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Lob
    @Column(nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private ByteArray credentialId;

    @Lob
    @Column(nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private ByteArray publicKey;

    @ManyToOne
    private User user;

    /* The authenticator potentially provides a range of additional information. This
     * application stores some of it to enable functionality that could be useful for
     * a production-quality web authentication project.
     */

    @Column(nullable = false)
    private Long count;

    @Lob
    @Column
    @JdbcTypeCode(SqlTypes.JSON)
    private ByteArray aaguid;

    public Authenticator(RegistrationResult result,
                         AuthenticatorAttestationResponse response,
                         User user,
                         String name) {

        var attestationData = response.getAttestation()
                                      .getAuthenticatorData()
                                      .getAttestedCredentialData();

        this.credentialId = result.getKeyId().getId();
        this.publicKey = result.getPublicKeyCose();

        attestationData.ifPresent(data -> this.aaguid = data.getAaguid());

        this.count = result.getSignatureCount();
        this.name = name;
        this.user = user;
    }

    public RegisteredCredential toRegisteredCredential() {

        return RegisteredCredential.builder()
                                   .credentialId(credentialId)
                                   .userHandle(user.getHandle())
                                   .publicKeyCose(publicKey)
                                   .signatureCount(count)
                                   .build();
    }
}
