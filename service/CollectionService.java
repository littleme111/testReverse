package fr.afpa.balthazar.logic.service;

import fr.afpa.balthazar.logic.exceptions.CustomConstraintViolation;
import fr.afpa.balthazar.logic.model.Collection;
import fr.afpa.balthazar.logic.model.Livre;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

public interface CollectionService {

    void creer(Collection collection);
    Collection findByName(String name);
    List<Collection> trouverTous() throws CustomConstraintViolation;
    ArrayList<Collection> searchCollection(String search);
    Collection findById(String id);
    void updateCollection(Collection collection);
}
