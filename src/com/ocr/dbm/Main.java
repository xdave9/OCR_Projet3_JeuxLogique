package com.ocr.dbm;

import com.ocr.dbm.utility.Global;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class Main {
    private static final Logger m_logger = LogManager.getLogger(Main.class.getName());
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

	    m_logger.info("First game finished.");

	    for(;;) {
	        m_logger.info("Starting a new iteration in the main loop of Main.main(String)");

            System.out.println("What you want to do ?" + Global.NEW_LINE
                    + "1 - Replay this game" + Global.NEW_LINE
                    + "2 - Play another game" + Global.NEW_LINE
                    + "3 - Exit");

            int response = Global.readInt(" --> ", 1, 3);

            /**/
            if (response == 3) break;
            /**/

            if (response == 2) {
                GamesHandler.getInstance().askWhichGame();
            }

            GamesHandler.getInstance().startNewGame(playerName);
            GamesHandler.getInstance().runGame();
        }
    }
}
