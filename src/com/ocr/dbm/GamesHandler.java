package com.ocr.dbm;

import com.ocr.dbm.combinationsgame.AICombinationsGame;
import com.ocr.dbm.combinationsgame.CombinationsGame;
import com.ocr.dbm.combinationsgame.Player;
import com.ocr.dbm.combinationsgame.mastermind.ConfigMastermind;
import com.ocr.dbm.combinationsgame.mastermind.Mastermind;
import com.ocr.dbm.combinationsgame.simplecombinationsgame.ConfigSimpleCombinationsGame;
import com.ocr.dbm.combinationsgame.simplecombinationsgame.SimpleCombinationGame;
import com.ocr.dbm.utility.Global;

/**
 * Singleton class for combination games handling.
 */
public class GamesHandler {
    // TODO : Finalize this class

    private static GamesHandler m_instance = new GamesHandler();
    private GamesHandler() {
        m_developerMode = Main.getArgs()[0] == "-d";
    }

    private CombinationsGame m_game;
    private GameMode m_gameMode;
    private boolean m_developerMode;
    private AICombinationsGame m_ai;

    public static GamesHandler getInstance() {
        return m_instance;
    }

    /**
     * Ask to the player which game he want to play
     */
    public void askWhichGame() {
        System.out.println("What you want to play ?" + Global.NEW_LINE
                            + "1 - A Simple Combination Game" + Global.NEW_LINE
                            + "2 - Mastermind");

        int gameIndex = Global.readInt(" --> ", 1, 2);

        switch (gameIndex) {
            case 1: m_game = new SimpleCombinationGame(new ConfigSimpleCombinationsGame(), askGameMode(), m_developerMode);
                break;
            case 2: m_game = new Mastermind(new ConfigMastermind(), askGameMode(), m_developerMode);
                break;
        }
    }

    /**
     * Ask to the player which game mode he want to play, for the wanted game.
     * @return Selected game mode
     */
    public GameMode askGameMode() {
        System.out.println("Choose a mode : ");

        for (int i = 0; i < GameMode.values().length; i++) {
            System.out.println(String.format("%d - %s", i + 1, GameMode.values()[i].toString()));
        }

        int gameModeIndex = -1 + Global.readInt(" --> ", 1, GameMode.values().length);

        m_gameMode = GameMode.values()[gameModeIndex];

        return m_gameMode;
    }

    /**
     * Run the selected game.
     * @throws IllegalStateException thrown if there's no game or mode selected. (askWhichGame() and askGameMode() should
     *                               normally be called first)
     */
    public void runGame() {
        if (m_game == null || m_gameMode == null) {
            throw new IllegalStateException("Game is not initialized properly."
                    + Global.NEW_LINE + " askWhichGame() and askGameMode() should be called first.");
        }

        String playerName = Global.readString("What's your name? ");

        switch (m_gameMode) {
            case OFFENSIVE:
                m_game.newGame(
                        new Player(playerName, null),
                        new Player("AI", m_ai.generateDefensiveCombination()));
            break;
            case DEFENSIVE:
                m_game.newGame(
                        new Player(playerName, Global.readString("Your combination: ", m_game.getCombinationRegex())),
                        new Player("AI", null));
                break;
            case DUEL:
                m_game.newGame(
                        new Player(playerName, Global.readString("Your combination: ", m_game.getCombinationRegex())),
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

            System.out.println(message.toString());
        }

        // TODO : Start game
    }
}
