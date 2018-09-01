package com.kahramani.mancala.domain.component.impl;

import com.kahramani.mancala.domain.component.GameUrlGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

@Component
public class LocalGameUrlGenerator implements GameUrlGenerator {

    private static final Logger logger = LoggerFactory.getLogger(LocalGameUrlGenerator.class);

    private final String localhostBaseUrl;

    public LocalGameUrlGenerator(@Value("${server.port}") String serverPort) {
        this.localhostBaseUrl = String.format("http://localhost:%s", serverPort);
    }

    /**
     * return empty when given gameId or initially provided server port is not valid for URL syntax
     */
    @Override
    public Optional<URL> generateGameUrl(String gameId) {
        try {
            URL url = new URL(String.format("%s/games/%s", localhostBaseUrl, gameId));
            return Optional.of(url);
        } catch (MalformedURLException e) {
            logger.warn("Game url couldn't be generated for game: {}", gameId, e);
            return Optional.empty();
        }
    }
}