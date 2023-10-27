package com.demo.webauthn.entity;

import com.yubico.webauthn.data.ByteArray;
import com.yubico.webauthn.data.UserIdentity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String displayName;

    @Lob
    @Column(nullable = false, length = 64)
    @JdbcTypeCode(SqlTypes.JSON)
    private ByteArray handle;

    public User(UserIdentity userIdentity) {
        this.handle = userIdentity.getId();
        this.username = userIdentity.getName();
        this.displayName = userIdentity.getDisplayName();
    }

    public UserIdentity toUserIdentity() {

        return UserIdentity.builder()
                           .name(username)
                           .displayName(displayName)
                           .id(handle)
                           .build();
    }
}
