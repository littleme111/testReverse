package fr.afpa.balthazar.logic.service;

import fr.afpa.balthazar.logic.dao.LivreDao;
import fr.afpa.balthazar.logic.exceptions.CustomConstraintViolation;
import fr.afpa.balthazar.logic.model.Livre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service("livreService")
@Transactional
public class LivreServiceImpl implements LivreService {

    @Autowired
    LivreDao livreDao;

    public void creer(Livre livre) throws CustomConstraintViolation {
        livreDao.creer(livre);
    }

    public Livre trouverParTitre(String titre) throws CustomConstraintViolation {
        return livreDao.trouverParTitre(titre);
    }

    public List<Livre> trouverTous() throws CustomConstraintViolation {
        return livreDao.trouverTous();
    }

    public ArrayList<Livre> searchLivre(String search) {
        return livreDao.searchLivre(search);
    }

    public Livre trouverParISBN(String ISBN) {
        return livreDao.trouverParISBN(ISBN);
    }

    public Livre trouverParLivre(Livre livre) {
        return livreDao.trouverParLivre(livre);
    }

    public void updateLivre(Livre livre) throws CustomConstraintViolation {
        livreDao.updateLivre(livre);
    }
}
