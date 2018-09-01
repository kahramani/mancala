package com.kahramani.mancala.domain.component.impl;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AlphanumericUniqueIdGeneratorTest {

    private AlphanumericUniqueIdGenerator alphanumericUniqueIdGenerator;

    @Before
    public void setUp() {
        alphanumericUniqueIdGenerator = new AlphanumericUniqueIdGenerator();
    }

    @Test
    public void should_generate_alphanumeric_id_with_length_of_15_chars() {
        int length = 15;
        String id = alphanumericUniqueIdGenerator.generateId(length);

        assertThat(id)
                .isNotNull()
                .hasSize(length);
    }

    @Test
    public void should_generate_alphanumeric_id_with_length_of_10_chars() {
        int length = 10;
        String id = alphanumericUniqueIdGenerator.generateId(length);

        assertThat(id)
                .isNotNull()
                .hasSize(length);
    }
}