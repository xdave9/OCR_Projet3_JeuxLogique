package com.ocr.dbm.combinationsgame;

/**
 * An AI for a combinations game, it try to find the right combination
 */
public abstract class AICombinationsGame {
    /**
     * @return A random combination for a game
     */
    public abstract String generateDefensiveCombination();

    /**
     * Try to find the defensive combination, and return it.
     * @param p_hint Combination returned will be based on this hint; null can be passed if there's no hint yet
     * @return A generated combination
     */
    public abstract String generateOffensiveCombination(String p_hint);
}
