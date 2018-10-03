package com.ocr.dbm.combinationsgame.simplecombinationsgame;

import com.ocr.dbm.GameMode;
import com.ocr.dbm.combinationsgame.CombinationsGame;

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
        m_config = p_config;
    }

    @Override
    public String getHint(String p_combination) throws IllegalArgumentException {
        if (!isValidCombination(p_combination)) {
            throw new IllegalArgumentException("p_combination is invalid combination.");
        }

        String defensiveCombination = getOtherPlayer().getCombination();

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

        return hintBuilder.toString();
    }

    @Override
    public String getCombinationRegex() {
        return "^[0-9]{" + m_config.getNumberOfSlots() + "}$";
    }
}
