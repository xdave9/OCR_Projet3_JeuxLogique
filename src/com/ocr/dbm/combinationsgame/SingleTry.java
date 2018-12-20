package com.ocr.dbm.combinationsgame;

/**
 * Class representing a single try. Hold a combination with a given hint for this combination.
 */
public class SingleTry {
    private String m_combination;
    private String m_givenHint; // Given hint for the combination

    /**
     * @param p_combination A combination that will be associate with a hint
     * @param p_givenHint Given hint for the associate combination
     */
    public SingleTry(String p_combination, String p_givenHint) {
        m_combination = p_combination;
        m_givenHint = p_givenHint;
    }

    /**
     * The given hint will be set to null.
     * @param p_combination A combination that will be associate with a hint
     */
    public SingleTry(String p_combination) {
        m_combination = p_combination;
        m_givenHint = null;
    }

    /**
     * The combination and given hint will be set to null.
     */
    public SingleTry() {
        m_givenHint = null;
        m_combination = null;
    }

    public String getCombination() {
        return m_combination;
    }

    public void setCombination(String p_combination) {
        m_combination = p_combination;
    }

    public String getGivenHint() {
        return m_givenHint;
    }

    public void setGivenHint(String p_givenHint) {
        m_givenHint = p_givenHint;
    }
}
