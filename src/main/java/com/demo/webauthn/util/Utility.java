package com.demo.webauthn.util;

import com.yubico.webauthn.data.ByteArray;

import java.security.SecureRandom;

public final class Utility {

    private static final SecureRandom RANDOM = new SecureRandom();

    private Utility() {
    }

    public static ByteArray generateRandom(int length) {

        byte[] bytes = new byte[length];
        RANDOM.nextBytes(bytes);

        return new ByteArray(bytes);
    }
}
