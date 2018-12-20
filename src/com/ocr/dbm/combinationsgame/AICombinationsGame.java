package com.ocr.dbm.combinationsgame;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * An AI for a combinations game, it try to find the right combination
 */
public abstract class AICombinationsGame {
    private AIHintParser m_hintParser; // A hint parser that will be associate with sub class
    protected List<SingleTry> m_previousTries = new ArrayList<>(); // Will contain all previous offensives combinations

    /**
     * @param p_hintParser Proper hint parser to associate with a sub class
     */
    public AICombinationsGame(AIHintParser p_hintParser) {
        m_hintParser = p_hintParser;
    }

    /**
     * Clear the previous tries registered. Should be called when starting a new game.
     */
    public void clearPreviousTries() {
        m_previousTries.clear();
    }

    /**
     * @return A random combination for a game
     */
    public abstract String generateDefensiveCombination();

    /**
     * Try to find the defensive combination, and return it.
     * @param p_hint Previous hint given;
     *               Combination returned will be based on this hint; null can be passed if there's no hint yet
     * @return A generated combination
     */
    public abstract String generateOffensiveCombination(String p_hint);

    /**
     * @return Associated hint parser
     */
    protected AIHintParser getHintParser() {
        return m_hintParser;
    }
}
