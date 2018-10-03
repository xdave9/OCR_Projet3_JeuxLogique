package com.ocr.dbm.combinationsgame.mastermind;

import com.ocr.dbm.GameMode;
import com.ocr.dbm.combinationsgame.CombinationsGame;
import com.ocr.dbm.utility.Global;
import com.ocr.dbm.utility.Logger;

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

        Logger.info("Creating instance of Mastermind");
        m_config = p_config;

        Logger.info(
                String.format(
                        "p_config : %s %s p_gameMode : %s %s p_developerMode : %s",
                        p_config, Global.NEW_LINE,
                        p_gameMode, Global.NEW_LINE,
                        p_developerMode));
    }

    @Override
    public String getHint(String p_combination) throws IllegalArgumentException {
        Logger.info(String.format("Stepping into Mastermind.getHint(String), p_combination : %s", p_combination));


        if (!isValidCombination(p_combination)) {
            String message = "p_combination is invalid combination.";
            Logger.error(message);
            throw new IllegalArgumentException(message);
        }

        String defensiveCombination = getOtherPlayer().getCombination();

        Logger.info("Defensive combination :" + defensiveCombination);

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
            Logger.info("Doesn't have any existing or well put digit");
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

        Logger.info(String.format("hasExistingDigits :%s", hasExistingDigits));
        Logger.info(String.format("hasWellPutDigit :%s", hasWellPutDigit));
        Logger.info(String.format("existingDigitCount :%d", existingDigitCount));
        Logger.info(String.format("wellPutDigitCount :%d", wellPutDigitCount));

        Logger.info("Returning :" + hintBuilder.toString());

        return hintBuilder.toString();
    }

    @Override
    public String getCombinationRegex() {
        Logger.info("Stepping into Mastermind.getCombinationRegex");

        String regex = "^[0-" + Integer.toString(m_config.getNumberOfAvailableNumerals() - 1) + "]"
                + "{" + m_config.getNumberOfSlots() + "}" + "$";

        Logger.info("Returning :" + regex);

        return regex;
    }

}
