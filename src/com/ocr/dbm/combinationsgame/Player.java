package com.ocr.dbm.combinationsgame;

import com.ocr.dbm.utility.Logger;

/**
 * Represent a player for a combination game
 */
public class Player {
    private String m_name;
    private String m_combination; // Defensive combination; can be null if this is only an offensive player

    public Player(String p_name, String p_combination) {
        Logger.info("Creating instance of Player");
        Logger.info(String.format("p_name :%s   p_combination :%s", p_name, p_combination));

        m_name = p_name;
        m_combination = p_combination;
    }

    public String getName() {
        return m_name;
    }

    public String getCombination() {
        return m_combination;
    }
}
