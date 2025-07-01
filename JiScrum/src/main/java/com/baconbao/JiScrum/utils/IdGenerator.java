package com.baconbao.JiScrum.utils;

import java.security.SecureRandom;
import java.util.UUID;

public class IdGenerator {
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789".toUpperCase();
    private static final int ID_LENGTH = 10;
    private static final SecureRandom RANDOM = new SecureRandom();

    public static Integer getGenerationId(){
        UUID uuid = UUID.randomUUID();

        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }

    public static String generateNameFile() {
        StringBuilder id = new StringBuilder(ID_LENGTH);
        for (int i = 0; i < ID_LENGTH; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            id.append(CHARACTERS.charAt(index));
        }
        return id.toString();
    }
}
