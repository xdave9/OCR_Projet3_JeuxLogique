package com.ocr.dbm.combinationsgame;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidParameterException;
import java.util.Properties;

/**
 * Represent a configuration for a combinations game.
 */
public abstract class ConfigCombinationsGame {
    private Logger m_logger = LogManager.getLogger(ConfigCombinationsGame.class.getName());

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
        m_logger.traceEntry("readConfigFile");

        Properties config = new Properties();
        InputStream inputFile = null;

        try {

            inputFile = new FileInputStream("./resources/config.properties");
            config.load(inputFile);

            m_numberOfSlots = Integer.parseInt(config.getProperty("number_of_slots"));
            m_maxPossibleTries = Integer.parseInt(config.getProperty("max_possible_tries"));
            initAdditionalProperties(config);
        }
        catch (IOException e) {
            m_logger.error(e.getMessage());
            e.printStackTrace();
        }
        catch (NumberFormatException e) {
            m_logger.error(e.getMessage());
        }
        finally {
            if (inputFile != null) {
                try {
                    inputFile.close();
                } catch (IOException e) {
                    m_logger.error(e.getMessage());
                    e.printStackTrace();
                }
            }
        }

        m_logger.traceExit();
    }

    /**
     * Initialize additional configuration for a combination game
     * @param p_configProperties Properties loaded from config.properties
     * @throws InvalidParameterException Thrown if a property is not valid in config.properties
     */
    protected abstract void initAdditionalProperties(Properties p_configProperties) throws InvalidParameterException;
}