package com.projects.assignments.service;


import com.projects.assignments.dto.OneTimePadDto;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Arrays;

@Service
public class ServiceOneTimePad {

    private static final String PLAINTEXT = "MY NAME IS UNKNOWN";
    private static final SecureRandom random = new SecureRandom();
    private static final int LETTERCOUNT = PLAINTEXT.length();

    OneTimePadDto oneTimePadDto = new OneTimePadDto();

    private int[] generatedKey;

    // Encryption method returning cipher text
    public OneTimePadDto encrypt() {
        // Count letters only (spaces are skipped)
        int letterCount = (int) PLAINTEXT.chars().filter(c -> c != ' ').count();

        // Generate a random key (one value per letter)
        getCipherKey();

        // Encrypt: C = (P + K) mod 26
        StringBuilder ciphertext = new StringBuilder();
        int ki = 0;
        for (char c : PLAINTEXT.toCharArray()) {
            if (c == ' ') {
                ciphertext.append(' ');
            } else {
                int p = c - 'A';
                int cipher = (p + generatedKey[ki++]) % 26;
                ciphertext.append((char) ('A' + cipher));
            }
        }

        oneTimePadDto.setEncryptedText(PLAINTEXT);
        oneTimePadDto.setCipherKey(Arrays.toString(generatedKey));
        oneTimePadDto.setDecryptedText(String.valueOf(ciphertext));


        return oneTimePadDto;
    }

    // Get cipher key
    public void getCipherKey() {
        generatedKey = new int[LETTERCOUNT];
        for (int i = 0; i < LETTERCOUNT; i++) {
            generatedKey[i] = random.nextInt(26);
        }
    }


    // decryption method returning plain text
    // parameter ciphertext
    public String decrypt(String ciphertext) {
        // Decrypt: P = (C - K + 26) mod 26
        StringBuilder plaintext = new StringBuilder();
        int ki = 0;
        for (char c : ciphertext.toCharArray()) {
            if (c == ' ') {
                plaintext.append(' ');
            } else {
                int cipher = c - 'A';
                int p = ((cipher - generatedKey[ki++]) + 26) % 26;
                plaintext.append((char) ('A' + p));
            }
        }

        System.out.println("Decrypted  : " + plaintext);
        return plaintext.toString();
    }
}
