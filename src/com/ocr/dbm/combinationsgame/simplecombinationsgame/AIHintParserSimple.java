package com.ocr.dbm.combinationsgame.simplecombinationsgame;

import com.ocr.dbm.combinationsgame.AIHintParser;
import com.ocr.dbm.utility.Logger;

public class AIHintParserSimple implements AIHintParser {
    @Override
    public String parseHint(String p_hint, String p_attribute) {
        Logger.info("Stepping into AIHintParserSimple.parseHint(String, String)");

        if (p_attribute == null) {
            Logger.info("p_attribute is null");
            return p_hint;
        }

        Logger.info("AIHintParserSimple.parseHint(String, String) returning :null");
        return null;
    }
}
