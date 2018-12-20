package com.ocr.dbm.combinationsgame;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Represent a player for a combination game
 */
public class Player {
    private Logger m_logger = LogManager.getLogger(Player.class.getName());

    private String m_name;
    private String m_combination; // Defensive combination; can be null if this is only an offensive player

    public Player(String p_name, String p_combination) {
        m_logger.traceEntry("Player p_name:{}   p_combination:{}", p_name, p_combination);
        m_name = p_name;
        m_combination = p_combination;
        m_logger.traceExit();
    }

    public String getName() {
        return m_name;
    }

    public String getCombination() {
        return m_combination;
    }
}
