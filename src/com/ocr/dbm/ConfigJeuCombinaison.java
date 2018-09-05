package com.ocr.dbm;

/**
 * Représente une configuration pour un jeu de combinaison.
 */
public interface ConfigJeuCombinaison {

    /**
     * @return Nombre de cases que devrait avoir une combinaison
     */
    int getNbCasesCombinaison();

    /**
     * @return Nombre maximum d'essais possibles avant que ce soit la fin d'une partie
     */
    int getNbEssaisPossible();
}
