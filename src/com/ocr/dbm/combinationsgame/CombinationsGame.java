package com.ocr.dbm.combinationsgame;

import com.ocr.dbm.GameMode;

/**
 * Abstract class representing a combination game
 */
public abstract class CombinationsGame {
    private boolean m_isFinished; // true if the game is finished
    private Player m_winner = null;
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
            throw new NullPointerException("p_config can't be null.");
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
     * @throws IllegalArgumentException thrown when a combination associated with a plyer is invalid for this game
     * @throws NullPointerException thrown when p_player1 or p_player2 is null
     */
    public void newGame(Player p_player1, Player p_player2)
            throws IllegalArgumentException {
        if (p_player1 == null || p_player2 == null) {
            throw new NullPointerException("players can't be null");
        }

        if (!isValidCombination(p_player1.getCombination())) {
            throw new IllegalArgumentException(("Player one combination is invalid for this game"));
        }

        if (!isValidCombination(p_player2.getCombination())) {
            throw new IllegalArgumentException(("Player two combination is invalid for this game"));
        }

        m_player1 = p_player1;
        m_player2 = p_player2;
        m_isFinished = false;
        m_playedTries = 0;

        m_currentPlayer = (m_gameMode == GameMode.DUEL || m_gameMode == GameMode.OFFENSIVE) ? m_player1 : m_player2;
    }

    /**
     * Play a try
     * @param p_combination Combination to try
     * @throws IllegalArgumentException thrown when p_combination is not a valid combination for this game
     * @throws GameNotRunningException thrown when game is finished or not started yet
     * @return A sentence representing the game status after this try (A hint for the offensive player, or a winner)
     */
    public String playATry(String p_combination) throws IllegalArgumentException {
        if (p_combination.isEmpty()) {
            throw new IllegalArgumentException("p_combination can't be empty");
        }

        if (m_isFinished) {
            throw new GameNotRunningException("Can't play a try when the game is finished or not started");
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

        if (m_isFinished) {
            return "Winner is " + m_winner.getName() + "!";
        }
        else {
            return "Proposition : " + p_combination + " -> Response : " + getHint(p_combination);
        }
    }

    /**
     * Update current player, game mode is considered (it will switch the current player
     * to the other player, or not, depending of game mode).
     */
    private void updateCurrentPlayer() {
        if (m_gameMode == GameMode.DUEL) {
            m_currentPlayer = getOtherPlayer();
        }
    }

    /**
     * @return The player that is not the current player
     * @throws IllegalStateException thrown if current player is null
     */
    protected Player getOtherPlayer() throws IllegalStateException {
        if (m_currentPlayer == null) {
            throw new IllegalStateException("m_currentPlayer can't be null, maybe game as not been initialized yet ?");
        }

        return (m_currentPlayer == m_player1) ? m_player2 : m_player1;
    }

    /**
     * Check if a combination is valid this game
     * @param p_combination Combination to test
     * @return true if p_combination is valid; false otherwise
     */
    public final boolean isValidCombination(String p_combination) {
        return p_combination.matches(getCombinationRegex());
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
    public abstract String getHint(String p_combination) throws IllegalArgumentException;

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
            throw new IndexOutOfBoundsException("Currently, there's only two players... So must be between 0 and 1");
        }

        return (p_index == 0) ? m_player1 : m_player2;
    }
}
