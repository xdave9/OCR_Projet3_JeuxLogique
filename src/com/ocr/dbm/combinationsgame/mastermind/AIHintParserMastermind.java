package com.ocr.dbm.combinationsgame.mastermind;

import com.ocr.dbm.combinationsgame.AIHintParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AIHintParserMastermind implements AIHintParser {
    private Logger m_logger = LogManager.getLogger(AIHintParserMastermind.class.getName());

    @Override
    public String parseHint(String p_hint, String p_attribute) {
        m_logger.traceEntry("parseHint p_hint:{}    p_attribute:{}", p_hint, p_attribute);

        if (p_attribute == null) {
            return p_hint;
        }

        String attrValue = null;
        int attrIndex = p_hint.indexOf(p_attribute) - 1;

        if (attrIndex >= 0) {
            attrValue = Character.toString(p_hint.charAt(attrIndex));
        }

        return m_logger.traceExit(attrValue);
    }
}
