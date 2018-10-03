package com.ocr.dbm.combinationsgame;

public interface AIHintParser {
    /**
     * Parse a hint and return selected attribute.
     * @param p_hint Hint to be parsed
     * @param p_attribute An attribute to find in the given hint;
     *                    if null, return the given hint without parsing (maybe useless!)
     * @return The found attribute in the hint; or null if nothing was found
     */
    public String parseHint(String p_hint, String p_attribute);
}
