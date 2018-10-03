package com.ocr.dbm;

/*
    TODO : Logging with log4j 2
 */

public class Main {
    private static String[] m_args;

    public static String[] getArgs() {
        return m_args;
    }

    public static void main(String[] args) {
	    m_args = args;

	    GamesHandler.getInstance().askWhichGame();
	    GamesHandler.getInstance().startNewGame();
	    GamesHandler.getInstance().runGame();
    }
}
