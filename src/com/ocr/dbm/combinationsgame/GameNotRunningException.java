package com.ocr.dbm.combinationsgame;

/**
 * This exception should be thrown when we try to do an action in a game that is not running (or considered finished)
 */
class GameNotRunningException extends RuntimeException {
    public GameNotRunningException(String p_message) {
        super(p_message);
    }
}
