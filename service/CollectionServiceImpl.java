package fr.afpa.balthazar.logic.service;

import fr.afpa.balthazar.logic.dao.CollectionDao;
import fr.afpa.balthazar.logic.exceptions.CustomConstraintViolation;
import fr.afpa.balthazar.logic.model.Collection;
import fr.afpa.balthazar.logic.model.Livre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("collectionService")
@Transactional
public class CollectionServiceImpl implements CollectionService{

    @Autowired
    CollectionDao collectionDao;

    public void creer(Collection collection) throws CustomConstraintViolation{
        collectionDao.creer(collection);
    }

    public Collection findByName(String name) throws CustomConstraintViolation{
        return collectionDao.findByName(name);
    }

    public List<Collection> trouverTous() throws CustomConstraintViolation {
        return collectionDao.findAll();
    }

    public ArrayList<Collection> searchCollection(String search) {
        return collectionDao.searchCollection(search);
    }

    public Collection findById(String id) {
        return collectionDao.findById(id);
    }

    public void updateCollection(Collection collection) {
        collectionDao.updateCollection(collection);
    }


}
