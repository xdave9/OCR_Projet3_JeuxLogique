package com.ocr.dbm.combinationsgame.mastermind;

import com.ocr.dbm.combinationsgame.AICombinationsGame;
import com.ocr.dbm.utility.Global;
import com.ocr.dbm.utility.Logger;

public class AIMastermind extends AICombinationsGame {
    private ConfigMastermind m_config;

    /**
     * @param p_config A Mastermind configuration
     * @param p_hintParser Hint parser to associate
     */
    public AIMastermind(ConfigMastermind p_config, AIHintParserMastermind p_hintParser) {
        super(p_hintParser);

        Logger.info(String.format("Creating instance of AIMastermind, p_config :%s  p_hintParser :%s",
                p_config, p_hintParser));

        m_config = p_config;
    }

    @Override
    public String generateDefensiveCombination() {
        Logger.info("Stepping into AIMastermind.generateDefensiveCombination");

        StringBuilder comb = new StringBuilder(m_config.getNumberOfSlots());

        for (int i = 0; i < m_config.getNumberOfSlots(); i++) {
            comb.append(Integer.toString(Global.generateRandom(0, m_config.getNumberOfAvailableNumerals() - 1)));
        }

        Logger.info("Stepping into AIMastermind.generateDefensiveCombination() returning :" + comb.toString());

        return comb.toString();
    }

    @Override
    public String generateOffensiveCombination(String p_previousCombination, String p_hint) {
        if (p_hint == null || p_previousCombination == null) {
            return generateDefensiveCombination(); // A random combination...
        }

        int existingCount = Integer.parseInt(getHintParser().parseHint(p_hint, Global.MASTERMIND_EXISTING_ATTR));
        int wellPutCount = Integer.parseInt(getHintParser().parseHint(p_hint, Global.MASTERMIND_WELL_PUT_ATTR));

        // TODO
    }
}
