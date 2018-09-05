package com.ocr.dbm.mastermind;

import com.ocr.dbm.ConfigCombinationsGame;

/**
 * Represent a configuration for a Mastermind game.
 */
public interface ConfigMastermind extends ConfigCombinationsGame {

    /**
     * @return Number of numerals available for one slot in a combination
     */
    int getNumberOfAvailableNumerals();
}
