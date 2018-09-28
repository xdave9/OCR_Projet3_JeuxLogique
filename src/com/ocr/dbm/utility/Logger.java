package com.ocr.dbm.utility;

import com.ocr.dbm.Main;
import org.apache.logging.log4j.LogManager;

/**
 * Utility class for classic logging messages using Apache Log4j 2
 */
public final class Logger {
    private static org.apache.logging.log4j.Logger m_apacheLogger = LogManager.getLogger(Main.class);

    /**
     * Send a debug logging message
     * @param p_message Message to send
     */
    public static void debug(String p_message) {
        m_apacheLogger.debug(p_message);
    }

    /**
     * Send an error logging message
     * @param p_message Message to send
     */
    public static void error(String p_message) {
        m_apacheLogger.error(p_message);
    }

    /**
     * Send an info logging message
     * @param p_message Message to send
     */
    public static void info(String p_message) {
        m_apacheLogger.info(p_message);
    }

    /**
     * Send a warn logging message
     * @param p_message Message to send
     */
    public static void warn(String p_message) {
        m_apacheLogger.warn(p_message);
    }

    /**
     * Send a fatal logging message
     * @param p_message Message to send
     */
    public static void fatal(String p_message) {
        m_apacheLogger.fatal(p_message);
    }
}
