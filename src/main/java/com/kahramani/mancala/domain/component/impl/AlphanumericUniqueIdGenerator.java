package com.kahramani.mancala.domain.component.impl;

import com.kahramani.mancala.domain.component.UniqueIdGenerator;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.stream.IntStream;

@Component
public class AlphanumericUniqueIdGenerator implements UniqueIdGenerator {

    private static final char[] ALPHANUMERIC_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

    /**
     * returns alphanumericUniqueId with given length
     */
    @Override
    public String generateId(int idLength) {
        Random random = new Random(System.nanoTime());
        return IntStream.range(0, idLength)
                .map(i -> ALPHANUMERIC_CHARS[random.nextInt(ALPHANUMERIC_CHARS.length)])
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }
}