package com.ocr.dbm.combinationsgame;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidParameterException;
import java.util.Properties;

/**
 * Represent a configuration for a combinations game.
 */
public abstract class ConfigCombinationsGame {
    private int m_maxPossibleTries; // Maximum of possible tries before the game end
    private int m_numberOfSlots; // Number of slots for the combination

    public ConfigCombinationsGame() {
        readConfigFile();
    }

    /**
     * @return Number of slots for the combination
     */
    public int getNumberOfSlots() {
        return m_numberOfSlots;
    }

    /**
     * @return Maximum of possible tries before the game end
     */
    int getMaxPossibleTries() {
        return m_maxPossibleTries;
    }

    /**
     * Read configuration file (config.properties) to init configurations
     */
    private void readConfigFile() {
        Properties config = new Properties();
        InputStream inputFile = null;

        try {

            inputFile = new FileInputStream("config.properties");
            config.load(inputFile);

            m_numberOfSlots = Integer.parseInt(config.getProperty("number_of_slots"));
            m_maxPossibleTries = Integer.parseInt(config.getProperty("max_possible_tries"));
            initAdditionalProperties(config);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (NumberFormatException e) {
            // TODO : un log avec log4j
        }
        finally {
            if (inputFile != null) {
                try {
                    inputFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Initialize additional configuration for a combination game
     * @param p_configProperties Properties loaded from config.properties
     * @throws InvalidParameterException Thrown if a property is not valid in config.properties
     */
    protected abstract void initAdditionalProperties(Properties p_configProperties) throws InvalidParameterException;
}