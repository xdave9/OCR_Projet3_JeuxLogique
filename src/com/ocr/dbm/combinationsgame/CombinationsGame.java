package com.ocr.dbm.combinationsgame;

import com.ocr.dbm.GameMode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Abstract class representing a combination game
 */
public abstract class CombinationsGame {
    private Logger m_logger = LogManager.getLogger(CombinationsGame.class.getName());

    private boolean m_isFinished; // true if the game is finished
    private Player m_winner;
    private int m_playedTries; // Number of played tries in the actual game
    private boolean m_developerMode; // Should this game be in developer mode ?
    private ConfigCombinationsGame m_config;
    private GameMode m_gameMode;

    protected Player m_player1;
    protected Player m_player2;
    protected Player m_currentPlayer;

    /**
     * @param p_config A configuration for the game
     * @param p_gameMode Game mode for this game
     * @param p_developerMode true if the game should be in developer mode; false otherwise
     * @throws NullPointerException thrown when p_config is null
     */
    public CombinationsGame(ConfigCombinationsGame p_config, GameMode p_gameMode, boolean p_developerMode)
            throws NullPointerException {
        if (p_config == null) {
            String message = "p_config can't be null.";
            m_logger.error(message);
            throw new NullPointerException(message);
        }

        m_config = p_config;
        m_developerMode = p_developerMode;
        m_gameMode = p_gameMode;

        m_isFinished = true; // Set it to true because newGame(String) should be called to start a game
    }

    /**
     * Start a new game
     * @param p_player1 Player one (will be the player who's starting, in duel mode)
     * @param p_player2 Player two
     * @throws IllegalArgumentException thrown when a combination associated with a player is invalid for this game
     * @throws NullPointerException thrown when p_player1 or p_player2 is null
     */
    public void newGame(Player p_player1, Player p_player2)
            throws IllegalArgumentException {
        m_logger.traceEntry("newGame p_player1:{}   p_player2:{}", p_player1, p_player2);

        if (p_player1 == null || p_player2 == null) {
            String message = "players can't be null";
            m_logger.error(message);
            throw new NullPointerException(message);
        }

        if (!isValidCombination(p_player1.getCombination())) {
            String message = "Player one combination is invalid for this game";
            m_logger.error(message);
            throw new IllegalArgumentException(message);
        }

        if (!isValidCombination(p_player2.getCombination())) {
            String message = "Player two combination is invalid for this game";
            m_logger.error(message);
            throw new IllegalArgumentException(message);
        }

        m_player1 = p_player1;
        m_player2 = p_player2;
        m_isFinished = false;
        m_winner = null;
        m_playedTries = 0;

        m_currentPlayer = (m_gameMode == GameMode.DUEL || m_gameMode == GameMode.OFFENSIVE) ? m_player1 : m_player2;
        m_logger.info(String.format("m_currentPlayer :%s", m_currentPlayer));
    }

    /**
     * Play a try
     * @param p_combination Combination to try
     * @throws IllegalArgumentException thrown when p_combination is not a valid combination for this game
     * @throws GameNotRunningException thrown when game is finished or not started yet
     * @return A hint for the offensive player, or a winner
     */
    public String playATry(String p_combination) throws IllegalArgumentException {
        m_logger.traceEntry("playATry p_combination:{}", p_combination);

        if (p_combination.isEmpty()) {
            String message = "p_combination can't be empty";
            m_logger.error(message);
            throw new IllegalArgumentException(message);
        }

        if (m_isFinished) {
            String message = "Can't play a try when the game is finished or not started";
            m_logger.error(message);
            throw new GameNotRunningException(message);
        }

        if (isRightCombination(p_combination)) {
            m_winner = m_currentPlayer;
            m_isFinished = true;
        }
        else {
            m_playedTries++;

            if (m_playedTries >= m_config.getMaxPossibleTries()) {
                m_winner = getOtherPlayer();
                m_isFinished = true;
            }
        }

        String hintOrWinner;

        if (m_isFinished) {
            hintOrWinner = m_winner.getName();
        }
        else {
            hintOrWinner = getHint(p_combination);
            updateCurrentPlayer();
        }

        return m_logger.traceExit(hintOrWinner);
    }

    /**
     * Update current player, game mode is considered (it will switch the current player
     * to the other player, or not, depending of game mode).
     */
    private void updateCurrentPlayer() {
        m_logger.traceEntry("updateCurrentPlayer");

        if (m_gameMode == GameMode.DUEL) {
            m_currentPlayer = getOtherPlayer();
        }

        m_logger.info(String.format("m_currentPlayer updated:%s", m_currentPlayer));
        m_logger.traceExit();
    }

    /**
     * @return Current player
     */
    public Player getCurrentPlayer() {
        return m_currentPlayer;
    }

    /**
     * @return The player that is not the current player
     * @throws IllegalStateException thrown if current player is null
     */
    public Player getOtherPlayer() throws IllegalStateException {
        if (m_currentPlayer == null) {
            String message = "m_currentPlayer can't be null, maybe game as not been initialized yet ?";
            m_logger.error(message);
            throw new IllegalStateException(message);
        }

        return (m_currentPlayer == m_player1) ? m_player2 : m_player1;
    }

    /**
     * Check if a combination is valid this game
     * @param p_combination Combination to test
     * @return true if p_combination is valid; false otherwise
     */
    public final boolean isValidCombination(String p_combination) {
        return p_combination == null || p_combination.matches(getCombinationRegex());
    }

    /**
     * @return Regex for a valid combination
     */
    public abstract String getCombinationRegex();

    /**
     * Return a hint, based on a given combination, to help finding the secret combination of the defensive player
     * @param p_combination Hint will be based on that combination
     * @return a hint to help finding the secret combination
     * @throws IllegalArgumentException thrown when p_combination is not a valid combination for this game
     */
    public String getHint(String p_combination) throws IllegalArgumentException
    {
        m_logger.traceEntry("getHint p_combination:{}", p_combination);
        String defensiveCombination = getOtherPlayer().getCombination();
        return m_logger.traceExit(getHint(defensiveCombination, p_combination));
    }

    /**
     * Return a hint, based on two given combinations, to help finding the secret combination of the defensive player
     * @param p_defensiveComb The defensive combination
     * @param p_offensiveComb The offensive combination
     * @return a hint to help finding the secret combination
     * @throws IllegalArgumentException thrown when a combination is not a valid combination for this game
     */
    public abstract String getHint(String p_defensiveComb, String p_offensiveComb) throws IllegalArgumentException;

    /**
     * @param p_combination Combination to test
     * @return true if p_combination is equals to the defensive player combination
     */
    private boolean isRightCombination(String p_combination) {
        String defensiveCombination = getOtherPlayer().getCombination();
        return defensiveCombination.equals(p_combination);
    }

    /**
     * @return true if this game is in developer mode; false otherwise
     */
    public boolean isInDeveloperMode() {
        return m_developerMode;
    }

    /**
     * @param p_index Index of the player to return (index starting at 0)
     * @return Player at given index
     */
    public Player getPlayer(int p_index) throws IndexOutOfBoundsException {
        if (p_index != 0 && p_index != 1) {
            String message = "There's only two players... So index must be between 0 and 1. p_index :" + p_index;
            m_logger.error(message);
            throw new IndexOutOfBoundsException(message);
        }

        return (p_index == 0) ? m_player1 : m_player2;
    }

    /**
     * @return The winner; or null if there's no one yet
     */
    public Player getWinner() {
        return m_winner;
    }
}
