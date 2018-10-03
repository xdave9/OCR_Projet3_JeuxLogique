package com.ocr.dbm.combinationsgame.simplecombinationsgame;

import com.ocr.dbm.combinationsgame.AICombinationsGame;
import com.ocr.dbm.utility.Global;
import com.ocr.dbm.utility.Logger;

public class AISimpleCombinationsGame extends AICombinationsGame {
    private ConfigSimpleCombinationsGame m_config;

    /**
     * @param p_config A simple combination game configuration
     */
    public AISimpleCombinationsGame(ConfigSimpleCombinationsGame p_config) {
        Logger.info(String.format("Creating instance of AISimpleCombinationsGame, p_config :%s", p_config));

        m_config = p_config;
    }

    @Override
    public String generateDefensiveCombination() {
        Logger.info("Stepping into AISimpleCombinationsGame.generateDefensiveCombination");

        StringBuilder comb = new StringBuilder(m_config.getNumberOfSlots());

        for (int i = 0; i < m_config.getNumberOfSlots(); i++) {
            comb.append(Integer.toString(Global.generateRandom(0, 9)));
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
