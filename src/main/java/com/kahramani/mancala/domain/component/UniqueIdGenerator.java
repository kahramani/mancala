package com.kahramani.mancala.domain.component;

/**
 * @see com.kahramani.mancala.domain.component.impl.AlphanumericUniqueIdGenerator
 */
public interface UniqueIdGenerator {

    String generateId(int idLength);
}
