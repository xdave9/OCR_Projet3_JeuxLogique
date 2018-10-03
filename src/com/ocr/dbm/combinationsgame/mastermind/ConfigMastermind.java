package com.ocr.dbm.combinationsgame.mastermind;

import com.ocr.dbm.combinationsgame.ConfigCombinationsGame;
import com.ocr.dbm.utility.Global;

import java.security.InvalidParameterException;
import java.util.Properties;

/**
 * Represent a configuration for a Mastermind game.
 */
public class ConfigMastermind extends ConfigCombinationsGame {
    private int m_numberOfAvailableNumerals;

    /**
     * @return Number of numerals available for one slot in a combination
     */
    public int getNumberOfAvailableNumerals() {
        return m_numberOfAvailableNumerals;
    }


    @Override
    protected void initAdditionalProperties(Properties p_configProperties) throws InvalidParameterException {
        m_numberOfAvailableNumerals = Integer.parseInt(p_configProperties.getProperty("number_of_available_numerals"));

        if (!(4 <= m_numberOfAvailableNumerals && m_numberOfAvailableNumerals <= 10)) {
            throw new InvalidParameterException(
                    String.format("number_of_available_numerals should be between %d and %d",
                            Global.MASTERMIND_MIN_AVAILABLE_NUMERALS, Global.MASTERMIND_MAX_AVAILABLE_NUMERALS));
        }
    }
}