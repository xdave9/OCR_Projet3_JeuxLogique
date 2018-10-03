package com.ocr.dbm.combinationsgame.simplecombinationsgame;

import com.ocr.dbm.combinationsgame.AIHintParser;

public class AIHintParserSimple implements AIHintParser {
    @Override
    public String parseHint(String p_hint, String p_attribute) {
        if (p_attribute == null) {
            return p_hint;
        }

        return null;
    }
}
