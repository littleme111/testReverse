package fr.afpa.balthazar.logic.dao;

import fr.afpa.balthazar.logic.exceptions.CustomConstraintViolation;
import fr.afpa.balthazar.logic.model.Abonne;
import fr.afpa.balthazar.logic.model.Livre;

import java.util.ArrayList;
import java.util.List;

public interface LivreDao {

    void creer(Livre livre) throws CustomConstraintViolation;
    Livre trouverParTitre(String titre) throws CustomConstraintViolation;
    Livre trouverParLivre(Livre livre) throws CustomConstraintViolation;
    Livre trouverParISBN(String ISBN) throws CustomConstraintViolation;
    List<Livre> trouverTous() throws CustomConstraintViolation;
    ArrayList<Livre> searchLivre(String search);
    void updateLivre(Livre livre)throws CustomConstraintViolation;


}
