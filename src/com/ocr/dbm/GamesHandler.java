package com.ocr.dbm;

import com.ocr.dbm.combinationsgame.AICombinationsGame;
import com.ocr.dbm.combinationsgame.CombinationsGame;
import com.ocr.dbm.combinationsgame.mastermind.ConfigMastermind;
import com.ocr.dbm.combinationsgame.mastermind.Mastermind;
import com.ocr.dbm.combinationsgame.simplecombinationsgame.ConfigSimpleCombinationsGame;
import com.ocr.dbm.combinationsgame.simplecombinationsgame.SimpleCombinationGame;
import com.ocr.dbm.utility.Global;

/**
 * Singleton class for combination games handling.
 */
public class GamesHandler {
    // FIXME : Fix errors here! (Because players notion has been added)
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
            case 1: m_game = new SimpleCombinationGame(new ConfigSimpleCombinationsGame(), m_developerMode);
                break;
            case 2: m_game = new Mastermind(new ConfigMastermind(), m_developerMode);
                break;
        }
    }

    /**
     * Ask to the player which game mode he want to play, for the wanted game.
     */
    public void askGameMode() {
        System.out.println("Choose a mode : ");

        for (int i = 0; i < GameMode.values().length; i++) {
            System.out.println(String.format("%d - %s", i + 1, GameMode.values()[i].toString()));
        }

        int gameModeIndex = -1 + Global.readInt(" --> ", 1, GameMode.values().length);

        m_gameMode = GameMode.values()[gameModeIndex];
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
                        m_ai.generateDefensiveCombination(),
                        "AI",
                        playerName);
            break;
            case DEFENSIVE:
                m_game.newGame(
                        Global.readString("Your combination: ", m_game.getCombinationRegex()),
                        playerName,
                        "AI");
                break;
            case DUEL:

        }

        if (m_developerMode) {
            System.out.println(String.format("(Secret combination : %s)", m_game.getDefensiveCombination()));
        }

        // TODO : Start game
    }
}
