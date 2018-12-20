package com.ocr.dbm.combinationsgame.mastermind;

import com.ocr.dbm.GamesHandler;
import com.ocr.dbm.combinationsgame.AICombinationsGame;
import com.ocr.dbm.combinationsgame.CombinationsGame;
import com.ocr.dbm.utility.Global;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

public class AIMastermind extends AICombinationsGame {
    private final Logger m_logger = LogManager.getLogger(AIMastermind.class.getName());
    private final ConfigMastermind m_config;
    private final List<String> m_allPossiblesCombs = new LinkedList<>(); // Will be a list of all possibles combinations

    /**
     * @param p_config A Mastermind configuration
     * @param p_hintParser Hint parser to associate
     */
    public AIMastermind(ConfigMastermind p_config, AIHintParserMastermind p_hintParser) {
        super(p_hintParser);

        m_logger.traceEntry("AIMastermind p_config:{}   p_hintParse:{}", p_config, p_hintParser);

        m_config = p_config;
        populateAllPossibleCombsList();

        m_logger.traceExit();
    }

    @Override
    public String generateDefensiveCombination() {
        m_logger.traceEntry("generateDefensiveCombination");

        StringBuilder comb = new StringBuilder(m_config.getNumberOfSlots());

        for (int i = 0; i < m_config.getNumberOfSlots(); i++) {
            comb.append(Integer.toString(Global.generateRandom(0, m_config.getNumberOfAvailableNumerals() - 1)));
        }

        return m_logger.traceExit(comb.toString());
    }

    @Override
    public String generateOffensiveCombination(String p_hint) {
        m_logger.traceEntry("generateOffensiveCombination p_hint:{}", p_hint);

        /*
            This algorithm is derived from Five-guess algorithm by Donald Knuth
            https://en.wikipedia.org/wiki/Mastermind_(board_game)#Algorithms
         */

        StringBuilder comb = new StringBuilder(m_config.getNumberOfSlots());

        if (m_previousTries.isEmpty()) {
            int halfOfNumberOfSlots = m_config.getNumberOfSlots() / 2;

            for (int i = 0; i < halfOfNumberOfSlots; i++) {
                comb.append('0');
            }

            for (int i = 0; i < halfOfNumberOfSlots; i++) {
                comb.append('1');
            }

            if (halfOfNumberOfSlots * 2 != m_config.getNumberOfSlots()) {
                comb.append('1');
            }

            m_previousTries.add(comb.toString());
            return comb.toString();
        }

        String existingCount = getHintParser().parseHint(p_hint, Global.MASTERMIND_EXISTING_ATTR);
        String wellPutCount = getHintParser().parseHint(p_hint, Global.MASTERMIND_WELL_PUT_ATTR);

        eliminateBadPossibilitiesFromList(
                m_previousTries.get(m_previousTries.size() - 1),
                existingCount, wellPutCount);

        comb.append(m_allPossiblesCombs.get(0));

        m_previousTries.add(comb.toString());
        return m_logger.traceExit(comb.toString());
    }

    /**
     * Populate the list of all possible combinations
     */
    private void populateAllPossibleCombsList() {
        m_logger.traceEntry("populateAllPossibleCombsList");

        int numberOfPossibleCombs = (int)Math.pow(m_config.getNumberOfAvailableNumerals(), m_config.getNumberOfSlots());

        for (int i = 0; m_allPossiblesCombs.size() < numberOfPossibleCombs; i++) {
            StringBuilder possibleComb = new StringBuilder(m_config.getNumberOfSlots());

            String s = String.valueOf(i);

            boolean mustSkip = false;

            for (int j = 0; j < s.length(); j++) {
                if (Character.getNumericValue(s.charAt(j)) >= m_config.getNumberOfAvailableNumerals()) {
                    mustSkip = true;
                    break;
                }
            }

            /**/
            if (mustSkip) continue;
            /**/

            for (int j = 0; j < m_config.getNumberOfSlots() - s.length(); j++) {
                possibleComb.append('0');
            }

            possibleComb.append(s);
            m_allPossiblesCombs.add(possibleComb.toString());
        }

        m_logger.info("m_allPossiblesCombs.size():" + m_allPossiblesCombs.size());
        m_logger.traceExit();
    }

    /**
     * Eliminate from m_allPossiblesCombs all combinations that
     * doesn't fit the existing count and well count for a given combination.
     * @param p_combination Combination used to eliminate non-matching response.
     * @param p_existingCount Existing digit count obtained by a response
     * @param p_wellPutCount Well put digit count obtained by a response
     */
    private void eliminateBadPossibilitiesFromList(String p_combination, String p_existingCount, String p_wellPutCount) {
        m_logger.traceEntry("eliminateBadPossibilitiesFromList p_combination:{} p_existingCount:{}  p_sellPutCount:{}",
                p_combination, p_existingCount, p_wellPutCount);

        CombinationsGame game = GamesHandler.getInstance().getGame();

        m_allPossiblesCombs.removeIf(p_s -> {
            if (p_s.equals(p_combination)) {
                return true;
            }

            String hint = game.getHint(p_s, p_combination);
            String existingCount = getHintParser().parseHint(hint, Global.MASTERMIND_EXISTING_ATTR);
            String wellPutCount = getHintParser().parseHint(hint, Global.MASTERMIND_WELL_PUT_ATTR);

            return !existingCount.equals(p_existingCount) || !wellPutCount.equals(p_wellPutCount);
        });

        m_logger.info("m_allPossiblesCombs.size():" + m_allPossiblesCombs.size());
        m_logger.traceExit();
    }

    @Override
    public void clear() {
        super.clear();
        m_allPossiblesCombs.clear();
        populateAllPossibleCombsList();
    }
}
