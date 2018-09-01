package com.kahramani.mancala.domain.component;

import java.net.URL;
import java.util.Optional;

/**
 * @see com.kahramani.mancala.domain.component.impl.LocalGameUrlGenerator
 */
public interface GameUrlGenerator {

    Optional<URL> generateGameUrl(String gameId);
}