package com.ocr.dbm.combinationsgame.mastermind;

import com.ocr.dbm.combinationsgame.AIHintParser;
import com.ocr.dbm.utility.Logger;

public class AIHintParserMastermind implements AIHintParser {
    @Override
    public String parseHint(String p_hint, String p_attribute) {
        Logger.info("Stepping into AIHintParserMastermind.parseHint(String, String)");
        Logger.info(String.format("p_hint :%s   p_attribute :%s", p_hint, p_attribute));

        if (p_attribute == null) {
            Logger.info("p_attribute is null");
            return p_hint;
        }

        String attrValue = null;
        int attrIndex = p_hint.indexOf(p_attribute) - 1;

        if (attrIndex >= 0) {
            attrValue = Character.toString(p_hint.charAt(attrIndex));
        }

        Logger.info(String.format("AIHintParserMastermind.parseHint(String, String) returning :%s", attrValue));

        return attrValue;
    }
}
