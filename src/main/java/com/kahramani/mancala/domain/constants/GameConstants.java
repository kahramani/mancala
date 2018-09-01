package com.kahramani.mancala.domain.constants;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class GameConstants {
    // for input validations
    public static final int LENGTH_OF_GAME_ID = 16;
    public static final int LENGTH_OF_PLAYER_ID = 14;

    // predefined game rules
    public static final int PIT_INITIAL_STONE_COUNT = 6;
    public static final int PIT_COUNT_ON_BOARD = 14;
    public static final int EMPTY_PIT_STONE_COUNT = 0;
    public static final int ALLOWED_PLAYER_COUNT_IN_GAME = 2;
    public static final int FIRST_KALAH_ID = 7;
    public static final int SECOND_KALAH_ID = 14;
    public static final List<Integer> KALAH_INDEXES = Collections.unmodifiableList(Arrays.asList(FIRST_KALAH_ID, SECOND_KALAH_ID));

    // implementation needs
    public static final int LIST_INDEX_AND_ID_ADJUSTMENT = 1; // list index starts from zero but our pitIds start from 1
    public static final String EVEN_STEVEN = "even-steven";
}