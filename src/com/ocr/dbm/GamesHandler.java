package com.ocr.dbm;

import com.ocr.dbm.combinationsgame.CombinationsGame;
import com.ocr.dbm.combinationsgame.mastermind.ConfigMastermind;
import com.ocr.dbm.combinationsgame.mastermind.Mastermind;
import com.ocr.dbm.combinationsgame.simplecombinationsgame.ConfigSimpleCombinationsGame;
import com.ocr.dbm.combinationsgame.simplecombinationsgame.SimpleCombinationGame;
import com.ocr.dbm.utility.Global;

import java.io.Console;

/**
 * Singleton class for combination games handling.
 */
public class GamesHandler {

    private static GamesHandler m_instance = new GamesHandler();
    private GamesHandler() {
        // TODO : TESTER SI ON EST EN MODE DÉVELOPPEUR (VIA LES PARAMÈTRES DONNÉS AU PROGRAMME)
    }

    private CombinationsGame m_game;
    private GameMode m_gameMode;
    private boolean m_developerMode;

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
            case 1: m_game = new SimpleCombinationGame(readSimpleCombinationGameConfig(), m_developerMode);
                break;
            case 2: m_game = new Mastermind(readMasterMindConfig(), m_developerMode);
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
     * @throws IllegalStateException thrown if there's no game or mode selected. (askWhichGame and askGameMode should
     *                               normally be called first)
     */
    public void runGame() {
        // TODO
    }

    /**
     * Read, in configuration file, configuration for a simple combination game
     * @return Configuration for this game
     */
    private ConfigSimpleCombinationsGame readSimpleCombinationGameConfig() {
        // TODO
    }

    /**
     * Read, in configuration file, configuration for a mastermind game
     * @return Configuration for this game
     */
    private ConfigMastermind readMasterMindConfig() {
        // TODO
    }
}
