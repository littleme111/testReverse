package fr.afpa.balthazar.logic.service;

import fr.afpa.balthazar.logic.model.Abonne;

import java.util.*;

public interface AbonneService {

    void saveAbonne(Abonne abonne);
    void deleteAbonne(Abonne abonne);
    Abonne findAbonneByName(String name);
    Abonne findAbonneById(Long id_psersonne);
    Abonne findAbonneByNoAbonne(String noAbonne);
    String generateNoAbonne();
    ArrayList<Abonne> searchAbonnes(String search);
    void updateAbonne(Abonne abonne);
}
