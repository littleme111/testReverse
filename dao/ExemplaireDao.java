package fr.afpa.balthazar.logic.dao;

import fr.afpa.balthazar.logic.model.Abonne;
import fr.afpa.balthazar.logic.model.Exemplaire;
import fr.afpa.balthazar.logic.model.Livre;

import java.util.ArrayList;
import java.util.Set;

public interface ExemplaireDao {

    void creer(Exemplaire exemplaire);
    Exemplaire chercherPremierDisponible(Livre livre);
    int nombreDisponibles(Livre livre);
    void supprimer(Exemplaire exemplaire);
    ArrayList<Exemplaire>  trouverTousParLivre(Livre livre);
    ArrayList<Exemplaire> searchExemaplaire(String search);
    void update(Exemplaire exemplaire);
    Exemplaire findbyId(Long id);

}
