package com.ocr.dbm.combinationsgame.simplecombinationsgame;

import com.ocr.dbm.combinationsgame.AIHintParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AIHintParserSimple implements AIHintParser {
    private final Logger m_logger = LogManager.getLogger(AIHintParserSimple.class.getName());

    @Override
    public String parseHint(String p_hint, String p_attribute) {
        m_logger.traceEntry("parseHint p_hint:{}    p_attribute:{}", p_hint, p_attribute);

        if (p_attribute == null) {
            return p_hint;
        }

        return m_logger.traceExit("");
    }
}
