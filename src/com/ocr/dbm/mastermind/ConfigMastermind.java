package com.ocr.dbm.mastermind;

import com.ocr.dbm.ConfigJeuCombinaison;

/**
 * Représente une configuration pour un jeu de combinaisons simple, (jeu du + ou -).
 */
public interface ConfigMastermind extends ConfigJeuCombinaison {

    /**
     * @return Nombre de chiffre utilisable pour une case de la combinaison
     */
    int getNbChiffresUtilisable();
}
