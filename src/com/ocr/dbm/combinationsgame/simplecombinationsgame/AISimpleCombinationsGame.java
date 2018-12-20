package com.ocr.dbm.combinationsgame.simplecombinationsgame;

import com.ocr.dbm.combinationsgame.AICombinationsGame;
import com.ocr.dbm.combinationsgame.SingleTry;
import com.ocr.dbm.utility.Global;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AISimpleCombinationsGame extends AICombinationsGame {
    private Logger m_logger = LogManager.getLogger(AISimpleCombinationsGame.class.getName());
    private ConfigSimpleCombinationsGame m_config;

    /**
     * @param p_config A simple combination game configuration
     * @param p_hintParser Hint parser to associate
     */
    public AISimpleCombinationsGame(ConfigSimpleCombinationsGame p_config, AIHintParserSimple p_hintParser) {
        super(p_hintParser);
        m_logger.traceEntry("AISimpleCombinationsGame p_config:{}   p_hintParser:{}", p_config, p_hintParser);
        m_config = p_config;
        m_logger.traceExit();
    }

    @Override
    public String generateDefensiveCombination() {
        m_logger.traceEntry("generateDefensiveCombination");

        StringBuilder comb = new StringBuilder(m_config.getNumberOfSlots());

        for (int i = 0; i < m_config.getNumberOfSlots(); i++) {
            comb.append(Integer.toString(Global.generateRandom(0, 9)));
        }

        return m_logger.traceExit(comb.toString());
    }

    @Override
    public String generateOffensiveCombination(String p_hint) {
        m_logger.traceEntry("generateOffensiveCombination p_hint:{}", p_hint);

        /*
            This algorithm will looks like:
                1234567890 <-- Defensive combination

            Tries:
                5555555555
                3333577773
                1134567991
                1234567890
         */

        StringBuilder comb = new StringBuilder(m_config.getNumberOfSlots());

        if (p_hint == null || m_previousTries.isEmpty()) {

            for (int i = 0; i < m_config.getNumberOfSlots(); i++) {
                comb.append("5");
            }

            m_previousTries.add(new SingleTry(comb.toString()));
            return comb.toString();
        }

        String hint = getHintParser().parseHint(p_hint, null);
        int previousTryIndex = m_previousTries.size() - 1;

        m_previousTries.get(previousTryIndex).setGivenHint(hint);
        String previousComb = m_previousTries.get(previousTryIndex).getCombination();

        switch (m_previousTries.size()) {
            case 1:
                for (int i = 0; i < hint.length(); i++) {
                    char hintChar = hint.charAt(i);
                    char newDigit = '5';

                    if (hintChar == '-') {
                        newDigit = '3';
                    }
                    else if (hintChar == '+') {
                        newDigit = '7';
                    }

                    comb.append(newDigit);
                }
                break;
            case 2:
                for (int i = 0; i < hint.length(); i++) {
                    char hintChar = hint.charAt(i);
                    char previousCombChar = previousComb.charAt(i);
                    char newDigit = previousCombChar;

                    if (hintChar == '-') {
                        if (previousCombChar == '7') {
                            newDigit = '6';
                        }
                        else { // Equals 3... :
                            newDigit = '1';
                        }
                    }
                    else if (hintChar == '+') {
                        if (previousCombChar == '7') {
                            newDigit = '9';
                        }
                        else { // Equals 3... :
                            newDigit = '4';
                        }
                    }

                    comb.append(newDigit);
                }
                break;
            case 3:
                for (int i = 0; i < hint.length(); i++) {
                    char hintChar = hint.charAt(i);
                    char newDigit = previousComb.charAt(i);

                    if (hintChar == '-') {
                        newDigit--;
                    }
                    else if (hintChar == '+') {
                        newDigit++;
                    }

                    comb.append(newDigit);
                }
                break;
        }

        m_previousTries.add(new SingleTry(comb.toString()));

        return m_logger.traceExit(comb.toString());
    }
}
