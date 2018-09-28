package com.ocr.dbm.combinationsgame.simplecombinationsgame;

import com.ocr.dbm.combinationsgame.ConfigCombinationsGame;

import java.security.InvalidParameterException;
import java.util.Properties;

/**
 * Represent a configuration for a simple combinations game (+ or - game with numbers).
 */
public class ConfigSimpleCombinationsGame extends ConfigCombinationsGame {
    @Override
    protected void initAdditionalProperties(Properties p_configProperties) throws InvalidParameterException {
        // Nothing to do here...
    }
}
