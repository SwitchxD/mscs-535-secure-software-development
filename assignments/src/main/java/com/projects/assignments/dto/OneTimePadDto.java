package com.projects.assignments.dto;


import lombok.Data;

@Data
public class OneTimePadDto {
    private String encryptedText;
    private String decryptedText;
    private String cipherKey;
}
