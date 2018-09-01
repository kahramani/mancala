package com.kahramani.mancala.domain.component;

import com.kahramani.mancala.domain.entity.Pit;

import java.util.List;

/**
 * @see com.kahramani.mancala.domain.component.impl.CircularBoardPitSowingIterator
 */
public interface PitSowingIterator {

    Pit getNextPit(List<Pit> pitList, Pit previousPit, String playerId);
}