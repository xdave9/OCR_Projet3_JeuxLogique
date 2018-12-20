package com.ocr.dbm;

public enum GameMode {
    OFFENSIVE("Offensive"),
    DEFENSIVE("Defensive"),
    DUEL("Duel");

    private final String m_name;

    GameMode(String p_name) {
        m_name = p_name;
    }

    public String toString() {
        return m_name;
    }
}
