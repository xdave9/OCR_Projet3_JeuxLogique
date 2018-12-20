package com.ocr.dbm.combinationsgame.mastermind;

import com.ocr.dbm.combinationsgame.AICombinationsGame;
import com.ocr.dbm.combinationsgame.SingleTry;
import com.ocr.dbm.utility.Global;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AIMastermind extends AICombinationsGame {
    private Logger m_logger = LogManager.getLogger(AIMastermind.class.getName());
    private ConfigMastermind m_config;

    /**
     * @param p_config A Mastermind configuration
     * @param p_hintParser Hint parser to associate
     */
    public AIMastermind(ConfigMastermind p_config, AIHintParserMastermind p_hintParser) {
        super(p_hintParser);

        m_logger.traceEntry("AIMastermind p_config:{}   p_hintParse:{}", p_config, p_hintParser);
        m_config = p_config;
        m_logger.traceExit();
    }

    @Override
    public String generateDefensiveCombination() {
        m_logger.traceEntry("generateDefensiveCombination");

        StringBuilder comb = new StringBuilder(m_config.getNumberOfSlots());

        for (int i = 0; i < m_config.getNumberOfSlots(); i++) {
            comb.append(Integer.toString(Global.generateRandom(0, m_config.getNumberOfAvailableNumerals() - 1)));
        }

        return m_logger.traceExit(comb.toString());
    }

    @Override
    public String generateOffensiveCombination(String p_hint) {
        m_logger.traceEntry("generateOffensiveCombination p_hint:{}", p_hint);

        if (m_previousTries.isEmpty()) {
            String comb = generateDefensiveCombination(); // A random combination...
            m_previousTries.add(new SingleTry(comb));
            return comb;
        }

        m_previousTries.get(m_previousTries.size() - 1).setGivenHint(p_hint);

        int existingCount = Integer.parseInt(getHintParser().parseHint(p_hint, Global.MASTERMIND_EXISTING_ATTR));
        int wellPutCount = Integer.parseInt(getHintParser().parseHint(p_hint, Global.MASTERMIND_WELL_PUT_ATTR));

        StringBuilder comb = new StringBuilder(m_config.getNumberOfSlots());

        // TODO

        m_previousTries.add(new SingleTry(comb.toString()));


        // TODO ************** FOR TESTING ONLY :
        return m_logger.traceExit(generateDefensiveCombination());
    }
}
