package fr.afpa.balthazar.logic.dao;

import fr.afpa.balthazar.logic.model.Abonne;
import fr.afpa.balthazar.logic.model.Collection;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface CollectionDao {
    void creer(Collection collection);
    Collection findByName(String name);
    List<Collection> findAll();
    ArrayList<Collection> searchCollection(String search);
    Collection findById(String id);
    void updateCollection(Collection collection);
}
