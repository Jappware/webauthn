package com.demo.webauthn.jpa.repository;

import com.demo.webauthn.jpa.entity.User;
import com.yubico.webauthn.data.ByteArray;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findByHandle(ByteArray handle);
}
