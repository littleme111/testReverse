package fr.afpa.balthazar.logic.dao;

import fr.afpa.balthazar.logic.model.Abonne;

import java.util.*;

public interface AbonneDao {


    void saveAbonne(Abonne abonne);
    void deleteAbonne(Abonne abonne);
    Abonne findAbonneByName(String name);
    Abonne findAbonneById(Long id_personne);
    Abonne findAbonneByNoAbonne(String noAbonne);
    String generateNoAbonne();
    ArrayList<Abonne> searchAbonnes(String search);
    void updateAbonne(Abonne abonne);
}
