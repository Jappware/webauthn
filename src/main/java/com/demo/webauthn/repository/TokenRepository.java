package com.demo.webauthn.repository;

import com.demo.webauthn.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Token findByToken(String token);

    @Modifying
    @Transactional
    @Query("DELETE FROM Token t WHERE t.createdAt < :threshold")
    void deleteExpired(LocalDateTime threshold);
}
