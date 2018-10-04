package com.ocr.dbm.combinationsgame.simplecombinationsgame;

import com.ocr.dbm.GameMode;
import com.ocr.dbm.combinationsgame.CombinationsGame;
import com.ocr.dbm.utility.Global;
import com.ocr.dbm.utility.Logger;

public class SimpleCombinationGame extends CombinationsGame {
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

        Logger.info("Creating instance of SimpleCombinationGame");
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

        Logger.info("Mastermind.getHint(String) returning :" + hintBuilder.toString());

        return hintBuilder.toString();
    }

    @Override
    public String getCombinationRegex() {
        Logger.info("Stepping into SimpleCombinationGame.getCombinationRegex()");
        String regex = "^[0-9]{" + m_config.getNumberOfSlots() + "}$";

        Logger.info("SimpleCombinationGame.getCombinationRegex() returning :" + regex);
        return regex;
    }
}
