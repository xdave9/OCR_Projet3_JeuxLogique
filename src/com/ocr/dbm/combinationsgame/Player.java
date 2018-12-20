package com.ocr.dbm.combinationsgame;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Represent a player for a combination game
 */
public class Player {
    private final String m_name;
    private final String m_combination; // Defensive combination; can be null if this is only an offensive player

    public Player(String p_name, String p_combination) {
        Logger logger = LogManager.getLogger(Player.class.getName());

        logger.traceEntry("Player p_name:{}   p_combination:{}", p_name, p_combination);
        m_name = p_name;
        m_combination = p_combination;
        logger.traceExit();
    }

    public String getName() {
        return m_name;
    }

    public String getCombination() {
        return m_combination;
    }
}
