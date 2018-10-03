package com.ocr.dbm.combinationsgame.mastermind;

import com.ocr.dbm.combinationsgame.AICombinationsGame;
import com.ocr.dbm.utility.Global;
import com.ocr.dbm.utility.Logger;

public class AIMastermind extends AICombinationsGame {
    private ConfigMastermind m_config;

    /**
     * @param p_config A Mastermind configuration
     */
    public AIMastermind(ConfigMastermind p_config) {
        Logger.info(String.format("Creating instance of AIMastermind, p_config :%s", p_config));

        m_config = p_config;
    }

    @Override
    public String generateDefensiveCombination() {
        Logger.info("Stepping into AIMastermind.generateDefensiveCombination");

        StringBuilder comb = new StringBuilder(m_config.getNumberOfSlots());

        for (int i = 0; i < m_config.getNumberOfSlots(); i++) {
            comb.append(Integer.toString(Global.generateRandom(0, m_config.getNumberOfAvailableNumerals() - 1)));
        }

        Logger.info("Generated combination : " + comb.toString());

        return comb.toString();
    }

    @Override
    public String generateOffensiveCombination(String p_hint) {

        // TODO
        return null;
    }
}
