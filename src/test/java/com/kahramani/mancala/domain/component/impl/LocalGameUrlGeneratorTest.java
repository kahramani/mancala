package com.kahramani.mancala.domain.component.impl;

import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class LocalGameUrlGeneratorTest {

    private static final String SERVER_PORT = "8080";

    private LocalGameUrlGenerator localGameUrlGenerator;

    @Before
    public void setUp() {
        localGameUrlGenerator = new LocalGameUrlGenerator(SERVER_PORT);
    }

    @Test
    public void should_generate_local_game_url() {
        final String gameId = "E78WAOPKHYJXIO4C";

        Optional<URL> optionalUrl = localGameUrlGenerator.generateGameUrl(gameId);

        assertThat(optionalUrl.isPresent()).isTrue();
        assertThat(optionalUrl.get().toString()).isEqualTo("http://localhost:8080/games/E78WAOPKHYJXIO4C");
    }
}