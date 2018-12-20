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
    private static Logger m_logger = LogManager.getLogger(Mastermind.class.getName());
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
    public String getHint(String p_defensiveComb, String p_offensiveComb) throws IllegalArgumentException {
        m_logger.traceEntry("getHint p_defensiveComb:{} p_offensiveComb:{}", p_defensiveComb, p_offensiveComb);

        if (!isValidCombination(p_defensiveComb)) {
            String message = "p_defensiveComb is invalid combination.";
            m_logger.error(message);
            throw new IllegalArgumentException(message);
        }

        if (!isValidCombination(p_offensiveComb)) {
            String message = "p_offensiveComb is invalid combination.";
            m_logger.error(message);
            throw new IllegalArgumentException(message);
        }

        int wellPutDigitCount = 0;
        int existingDigitCount = 0;
        StringBuilder alreadyUsedDigits = new StringBuilder();

        for (int i = 0; i < p_offensiveComb.length(); i++) {
            char c = p_offensiveComb.charAt(i);

            if (!alreadyUsedDigits.toString().contains(Character.toString(c))) {
                if (p_defensiveComb.charAt(i) == c) {
                    wellPutDigitCount++;
                    alreadyUsedDigits.append(c);
                } else if (p_defensiveComb.contains(Character.toString(c))) {
                    existingDigitCount++;
                    alreadyUsedDigits.append(c);
                }
            }
        }

        boolean hasExistingDigits = existingDigitCount > 0;
        boolean hasWellPutDigit = wellPutDigitCount > 0;

        /**/
        if (!hasExistingDigits && !hasWellPutDigit) {
            return m_logger.traceExit("Nothing well put or existing.");
        }
        /**/

        StringBuilder hintBuilder = new StringBuilder();

        if (hasExistingDigits) {
            hintBuilder.append(String.format("%d%s", existingDigitCount, Global.MASTERMIND_EXISTING_ATTR));
        }

        if (hasWellPutDigit) {
            if (hasExistingDigits) {
                hintBuilder.append(", ");
            }

            hintBuilder.append(String.format("%d%s", wellPutDigitCount, Global.MASTERMIND_WELL_PUT_ATTR));
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
