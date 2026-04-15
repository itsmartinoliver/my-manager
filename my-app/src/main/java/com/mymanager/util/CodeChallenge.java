package com.mymanager.util;

import java.nio.charset.StandardCharsets;

import com.google.common.hash.Hashing;
import com.google.common.io.BaseEncoding;

public class CodeChallenge {
    public String hash(String codeVerifier) {
        String challenge = BaseEncoding.base64().encode(
            Hashing.sha256()
                .hashString(codeVerifier, StandardCharsets.UTF_8)
                .asBytes()
            ).replace('+','-').replace('/','_');
        challenge = challenge.substring(0, challenge.length() - 1); // Removes trailing '=' from challenge
        return challenge;
    }
}
