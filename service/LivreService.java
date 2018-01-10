package fr.afpa.balthazar.logic.service;

import fr.afpa.balthazar.logic.exceptions.CustomConstraintViolation;
import fr.afpa.balthazar.logic.model.Livre;

import java.util.ArrayList;
import java.util.List;

public interface LivreService {

    void creer(Livre livre) throws CustomConstraintViolation;
    Livre trouverParTitre(String titre) throws CustomConstraintViolation;
    List<Livre> trouverTous() throws CustomConstraintViolation;
    Livre trouverParLivre(Livre livre) throws CustomConstraintViolation;
    ArrayList<Livre> searchLivre(String search);
    Livre trouverParISBN(String ISBN);
    void updateLivre(Livre livre)throws CustomConstraintViolation;

}
