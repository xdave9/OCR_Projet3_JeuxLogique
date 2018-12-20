package com.ocr.dbm.combinationsgame.simplecombinationsgame;

import com.ocr.dbm.GameMode;
import com.ocr.dbm.combinationsgame.CombinationsGame;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleCombinationGame extends CombinationsGame {
    private Logger m_logger = LogManager.getLogger(SimpleCombinationGame.class.getName());
    private ConfigSimpleCombinationsGame m_config;

    /**
     * @param p_config A configuration for the game
     * @param p_gameMode Game mode for this game
     * @param p_developerMode true if the game should be in developer mode; false otherwise
     * @throws NullPointerException thrown when p_config is null
     */
    public SimpleCombinationGame(ConfigSimpleCombinationsGame p_config, GameMode p_gameMode, boolean p_developerMode)
            throws NullPointerException {
        super(p_config, p_gameMode, p_developerMode);

        m_logger.traceEntry("SimpleCombinationGame p_config:{}  p_gameMode:{}   p_developerMode:{}",
                p_config, p_gameMode, p_developerMode);

        m_config = p_config;
        m_logger.traceExit();
    }

    @Override
    public String getHint(String p_combination) throws IllegalArgumentException {
        m_logger.traceEntry("getHint p_combination:{}", p_combination);

        if (!isValidCombination(p_combination)) {
            String message = "p_combination is invalid combination.";
            m_logger.error(message);
            throw new IllegalArgumentException(message);
        }

        String defensiveCombination = getOtherPlayer().getCombination();

        m_logger.info("defensiveCombination:" + defensiveCombination);

        StringBuilder hintBuilder = new StringBuilder(p_combination.length());

        for (int i = 0; i < p_combination.length(); i++) {
            // One digit in the combination of the defensive player, and the offensive player :
            int digitDefensiveComb = Character.getNumericValue(defensiveCombination.charAt(i));
            int digitOffensiveComb = Character.getNumericValue(p_combination.charAt(i));

            hintBuilder.append(
                    digitDefensiveComb > digitOffensiveComb ? "+"
                            : digitDefensiveComb < digitOffensiveComb ? "-" : "="
            );
        }

        return m_logger.traceExit(hintBuilder.toString());
    }

    @Override
    public String getCombinationRegex() {
        m_logger.traceEntry("getCombinationRegex");
        String regex = "^[0-9]{" + m_config.getNumberOfSlots() + "}$";
        return m_logger.traceExit(regex);
    }
}
