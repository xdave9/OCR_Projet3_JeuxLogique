package com.ocr.dbm.combinationsgame.mastermind;

import com.ocr.dbm.combinationsgame.AIHintParser;

public class AIHintParserMastermind implements AIHintParser {
    @Override
    public String parseHint(String p_hint, String p_attribute) {
        if (p_attribute == null) {
            return p_hint;
        }

        String attrValue = null;
        int attrIndex = p_hint.indexOf(p_attribute) - 1;

        if (attrIndex >= 0) {
            attrValue = Character.toString(p_hint.charAt(attrIndex));
        }

        return attrValue;
    }
}
