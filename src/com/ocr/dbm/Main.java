package com.ocr.dbm;

import com.ocr.dbm.utility.Global;
import com.ocr.dbm.utility.Logger;

public class Main {
    private static String[] m_args;

    public static String[] getArgs() {
        return m_args;
    }

    public static void main(String[] args) {
        Logger.info("Stepping into Main.main(String)");

	    m_args = args;

        String playerName = Global.readString("What's your name? ");

	    GamesHandler.getInstance().askWhichGame();
	    GamesHandler.getInstance().startNewGame(playerName);
	    GamesHandler.getInstance().runGame();

	    Logger.info("First game finished.");

	    for(;;) {
	        Logger.info("Starting a new iteration in the main loop of Main.main(String)");

            System.out.println("What you want to do ?" + Global.NEW_LINE
                    + "1 - Replay this game" + Global.NEW_LINE
                    + "2 - Play another game" + Global.NEW_LINE
                    + "3 - Exit");

            int response = Global.readInt(" --> ", 1, 3);

            Logger.info(String.format("response :%d", response));

            /**/
            if (response == 3) break;
            /**/

            if (response == 2) {
                GamesHandler.getInstance().askWhichGame();
            }

            GamesHandler.getInstance().startNewGame(playerName);
            GamesHandler.getInstance().runGame();
        }

        Logger.info("Stepping out of Main.main(String), program closing.");
    }
}
