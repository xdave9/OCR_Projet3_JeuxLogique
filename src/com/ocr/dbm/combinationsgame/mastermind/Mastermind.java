package com.ocr.dbm.combinationsgame.mastermind;

import com.ocr.dbm.GameMode;
import com.ocr.dbm.combinationsgame.CombinationsGame;
import com.ocr.dbm.utility.Global;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Represent a Mastermind game, with player.
 */
public class Mastermind extends CombinationsGame {
    private Logger m_logger = LogManager.getLogger(Mastermind.class.getName());
    private ConfigMastermind m_config;

    /**
     * @param p_config A configuration for the Mastermind game
     * @param p_gameMode Game mode for this game
     * @param p_developerMode true if the game should be in developer mode; false otherwise
     * @throws NullPointerException thrown when p_config is null
     */
    public Mastermind(ConfigMastermind p_config, GameMode p_gameMode, boolean p_developerMode)
            throws NullPointerException {
        super(p_config, p_gameMode, p_developerMode);

        m_logger.traceEntry("Mastermind p_config:{} p_gameMode:{}   p_developerMode:{}",
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

        int wellPutDigitCount = 0;
        int existingDigitCount = 0;

        for (int i = 0; i < p_combination.length(); i++) {
            if (defensiveCombination.charAt(i) == p_combination.charAt(i)) {
                wellPutDigitCount++;
            }

            if (defensiveCombination.contains(Character.toString(p_combination.charAt(i)))) {
                existingDigitCount++;
            }
        }

        existingDigitCount -= wellPutDigitCount;

        boolean hasExistingDigits = existingDigitCount > 0;
        boolean hasWellPutDigit = wellPutDigitCount > 0;

        /**/
        if (!hasExistingDigits && !hasWellPutDigit) {
            m_logger.info("Doesn't have any existing or well put digit");
            return "Nothing well put or existing.";
        }
        /**/

        StringBuilder hintBuilder = new StringBuilder();

        if (hasExistingDigits) {
            hintBuilder.append(existingDigitCount + Global.MASTERMIND_EXISTING_ATTR);
        }

        if (hasWellPutDigit) {
            if (hasExistingDigits) {
                hintBuilder.append(", ");
            }

            hintBuilder.append(wellPutDigitCount + Global.MASTERMIND_WELL_PUT_ATTR);
        }

        return m_logger.traceExit(hintBuilder.toString());
    }

    @Override
    public String getCombinationRegex() {
        m_logger.traceEntry("getCombinationRegex");

        String regex = "^[0-" + Integer.toString(m_config.getNumberOfAvailableNumerals() - 1) + "]"
                + "{" + m_config.getNumberOfSlots() + "}" + "$";

        return m_logger.traceExit(regex);
    }

}
