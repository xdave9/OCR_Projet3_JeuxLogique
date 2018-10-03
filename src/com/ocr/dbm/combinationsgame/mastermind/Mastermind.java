package com.ocr.dbm.combinationsgame.mastermind;

import com.ocr.dbm.GameMode;
import com.ocr.dbm.combinationsgame.CombinationsGame;

/**
 * Represent a Mastermind game, with player.
 */
public class Mastermind extends CombinationsGame {
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
        m_config = p_config;
    }

    @Override
    public String getHint(String p_combination) throws IllegalArgumentException {
        if (!isValidCombination(p_combination)) {
            throw new IllegalArgumentException("p_combination is invalid combination.");
        }

        String defensiveCombination = getOtherPlayer().getCombination();

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
            return "Nothing well put or existing.";
        }
        /**/

        StringBuilder hintBuilder = new StringBuilder();

        if (hasExistingDigits) {
            hintBuilder.append(existingDigitCount + " existing");
        }

        if (hasWellPutDigit) {
            if (hasExistingDigits) {
                hintBuilder.append(", ");
            }

            hintBuilder.append(wellPutDigitCount + " well put");
        }

        return hintBuilder.toString();
    }

    @Override
    public String getCombinationRegex() {
        return "^[0-" + Integer.toString(m_config.getNumberOfAvailableNumerals() - 1) + "]"
                + "{" + m_config.getNumberOfSlots() + "}" + "$";
    }

}
