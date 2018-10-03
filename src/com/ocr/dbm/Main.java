package com.ocr.dbm;

/*
    TODO : Logging with log4j 2
 */

import com.ocr.dbm.utility.Global;

public class Main {
    private static String[] m_args;

    public static String[] getArgs() {
        return m_args;
    }

    public static void main(String[] args) {
	    m_args = args;

        String playerName = Global.readString("What's your name? ");

	    GamesHandler.getInstance().askWhichGame();
	    GamesHandler.getInstance().startNewGame(playerName);
	    GamesHandler.getInstance().runGame();

	    for(;;) {
            System.out.println("What you want to do ?" + Global.NEW_LINE
                    + "1 - Replay this game" + Global.NEW_LINE
                    + "2 - Play another game" + Global.NEW_LINE
                    + "3 - Exit");

            int response = Global.readInt(" --> ", 1, 3);

            /**/
            if (response == 3) break;
            /**/

            switch (response) {
                case 1:
                    GamesHandler.getInstance().startNewGame(playerName);
                    break;
                case 2:
                    GamesHandler.getInstance().askWhichGame();
                    GamesHandler.getInstance().startNewGame(playerName);
                    break;
            }

            GamesHandler.getInstance().runGame();
        }
    }
}
