package com.ocr.dbm;

import com.ocr.dbm.combinationsgame.AICombinationsGame;
import com.ocr.dbm.combinationsgame.CombinationsGame;
import com.ocr.dbm.combinationsgame.Player;
import com.ocr.dbm.combinationsgame.mastermind.AIHintParserMastermind;
import com.ocr.dbm.combinationsgame.mastermind.AIMastermind;
import com.ocr.dbm.combinationsgame.mastermind.ConfigMastermind;
import com.ocr.dbm.combinationsgame.mastermind.Mastermind;
import com.ocr.dbm.combinationsgame.simplecombinationsgame.AIHintParserSimple;
import com.ocr.dbm.combinationsgame.simplecombinationsgame.AISimpleCombinationsGame;
import com.ocr.dbm.combinationsgame.simplecombinationsgame.ConfigSimpleCombinationsGame;
import com.ocr.dbm.combinationsgame.simplecombinationsgame.SimpleCombinationGame;
import com.ocr.dbm.utility.Global;
import com.ocr.dbm.utility.Logger;

/**
 * Singleton class for combination games handling.
 */
public class GamesHandler {
    private static GamesHandler m_instance = new GamesHandler();
    private GamesHandler() {
        Logger.info("Creating GameHandler Singleton");

        m_developerMode = false;

        for (String arg : Main.getArgs()) {
            if (arg.equals("-d")) {
                m_developerMode = true;
                break;
            }
        }

        Logger.info(String.format("m_developerMode set to :%s", m_developerMode));
    }

    private CombinationsGame m_game;
    private GameMode m_gameMode;
    private boolean m_developerMode;
    private AICombinationsGame m_ai;

    public static GamesHandler getInstance() {
        return m_instance;
    }

    /**
     * Ask to the player which game he want to play, game mode is also asked
     */
    public void askWhichGame() {
        Logger.info("Stepping into GamesHandler.askWhichGame()");

        System.out.println("What you want to play ?" + Global.NEW_LINE
                            + "1 - A Simple Combination Game" + Global.NEW_LINE
                            + "2 - Mastermind");

        int gameIndex = Global.readInt(" --> ", 1, 2);
        Logger.info("gameIndex :" + gameIndex);

        switch (gameIndex) {
            case 1:
                ConfigSimpleCombinationsGame configSimple = new ConfigSimpleCombinationsGame();
                m_game = new SimpleCombinationGame(configSimple, askGameMode(), m_developerMode);
                m_ai = new AISimpleCombinationsGame(configSimple, new AIHintParserSimple());
                break;
            case 2:
                ConfigMastermind configMasterMind = new ConfigMastermind();
                m_game = new Mastermind(configMasterMind, askGameMode(), m_developerMode);
                m_ai = new AIMastermind(configMasterMind, new AIHintParserMastermind());
                break;
        }

        Logger.info("Stepping out of GamesHandler.askWhichGame()");
    }

    /**
     * Ask to the player which game mode he want to play, for the wanted game.
     * @return Selected game mode
     */
    private GameMode askGameMode() {
        Logger.info("Stepping into GameHandler.askGameMode()");

        System.out.println("Choose a mode : ");

        for (int i = 0; i < GameMode.values().length; i++) {
            System.out.println(String.format("%d - %s", i + 1, GameMode.values()[i].toString()));
        }

        int gameModeIndex = -1 + Global.readInt(" --> ", 1, GameMode.values().length);
        Logger.info("gameModeIndex :" + gameModeIndex);

        m_gameMode = GameMode.values()[gameModeIndex];

        Logger.info(String.format("GameHandler.askGameMode() returning :%s", m_gameMode));
        return m_gameMode;
    }

    /**
     * Start a new game (game and mode should be asked first)
     * @param p_humanPlayerName Name of the human player
     * @throws IllegalStateException thrown if there's no game or mode selected. (askWhichGame() should
     * normally be called first)
     */
    public void startNewGame(String p_humanPlayerName) throws IllegalStateException {
        Logger.info("Stepping into GameHandler.startNewGame(String)");
        Logger.info("p_humanPlayerName :" + p_humanPlayerName);

        if (m_game == null || m_gameMode == null) {
            String message = "Game is not initialized properly." + Global.NEW_LINE
                    + " askWhichGame() and askGameMode() should be called first.";
            Logger.error(message);
            throw new IllegalStateException(message);
        }

        switch (m_gameMode) {
            case OFFENSIVE:
                m_game.newGame(
                        new Player(p_humanPlayerName, null),
                        new Player("AI", m_ai.generateDefensiveCombination()));
                break;
            case DEFENSIVE:
                m_game.newGame(
                        new Player(p_humanPlayerName, Global.readString("Your combination: ", m_game.getCombinationRegex())),
                        new Player("AI", null));
                break;
            case DUEL:
                m_game.newGame(
                        new Player(p_humanPlayerName, Global.readString("Your combination: ", m_game.getCombinationRegex())),
                        new Player("AI", m_ai.generateDefensiveCombination()));
                break;
        }

        if (m_developerMode) {
            StringBuilder message = new StringBuilder();
            message.append("Game mode : " + m_gameMode.toString() + Global.NEW_LINE);

            Player player1 = m_game.getPlayer(0);
            Player player2 = m_game.getPlayer(1);

            if (player1.getCombination() != null) {
                message.append(player1.getName() + " -> " + player1.getCombination() + Global.NEW_LINE);
            }

            if (player2.getCombination() != null) {
                message.append(player2.getName() + " -> " + player2.getCombination() + Global.NEW_LINE);
            }

            Logger.info("Developer mode message :" + message.toString());
            System.out.println(message.toString());
        }

        Logger.info("Stepping out of GameHandler.startNewGame(String)");
    }

    /**
     * Run the selected game.
     */
    public void runGame() {
        Logger.info("Stepping into GameHandler.runGame()");

        // Will be the previous hint given by a try, will be used by the AI :
        String previousHintForAI = null;
        String previousComb = null; // Previous combination given by AI

        // Running game here :
        while (m_game.getWinner() == null) {
            Logger.info("Starting a new iteration of the main loop.");

            String hint;
            String offensiveComb;
            String defensiveComb = m_game.getOtherPlayer().getCombination();

            Logger.info("defensiveComb :" + defensiveComb);

            if (m_game.getCurrentPlayer().getName().equals("AI")) {
                Logger.info("Current player is AI");

                offensiveComb = m_ai.generateOffensiveCombination(previousComb, previousHintForAI);
                hint = m_game.playATry(offensiveComb);
                previousHintForAI = hint;
                previousComb = offensiveComb;

                Logger.info(String.format("previousHintForAI :%s", previousHintForAI));
                Logger.info(String.format("previousComb :%s", previousComb));
            }
            else {
                Logger.info("Current player is human.");

                offensiveComb = Global.readString("Combination: ", m_game.getCombinationRegex());
                hint = m_game.playATry(offensiveComb);
            }

            Logger.info(String.format("offensiveComb :%s", offensiveComb));
            Logger.info(String.format("hint :%s", hint));

            String gameStatus;

            if (m_game.getWinner() == null) {
                gameStatus = "Proposition : " + offensiveComb + " -> Response : " + hint;
            }
            else {
                gameStatus = "Winner is " + hint + "!" + Global.NEW_LINE;
                gameStatus += "Solution : " + defensiveComb;
            }

            Logger.info(String.format("gameStatus :%s", gameStatus));
            System.out.println(gameStatus);
            Global.waitForNewLine();
        }
    }
}
