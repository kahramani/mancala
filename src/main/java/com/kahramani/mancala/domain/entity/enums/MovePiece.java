package com.kahramani.mancala.domain.entity.enums;

/**
 * Sub-blocks of a move request
 */
public enum MovePiece {
    INITIAL_SWIPE, // swiping all stones from selected pit to make a move
    SOW_STONE_ORDINARY, // sowing just stone to pit with iteration
    SOW_LAST_STONE_TO_OWN_EMPTY_PIT, // sowing last stone to one of own empty pits which gives privilege to swipe symmetric pit stones
    SOW_LAST_STONE_TO_OWN_KALAH_PIT, // sowing last stone to own kalah pit which gives another turn to player
    FINAL_SWIPE // swiping all the remaining stones to kalah if the game is over
}