package fr.afpa.balthazar.logic.service;

import fr.afpa.balthazar.logic.dao.ExemplaireDao;
import fr.afpa.balthazar.logic.model.Exemplaire;
import fr.afpa.balthazar.logic.model.Livre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Set;

@Service("exemplaireService")
@Transactional
public class ExemplaireServiceImpl implements ExemplaireService {

    @Autowired
    ExemplaireDao exemplaireDao;

    public void creer(Exemplaire exemplaire) {
        exemplaireDao.creer(exemplaire);
    }

    public Exemplaire chercherPremierDisponible(Livre livre) {
        return exemplaireDao.chercherPremierDisponible(livre);
    }

    public int nombreDisponibles(Livre livre) {
        return exemplaireDao.nombreDisponibles(livre);
    }

    public void supprimer(Exemplaire exemplaire) {
        exemplaireDao.supprimer(exemplaire);

    }

    public ArrayList<Exemplaire> trouverTousParLivre(Livre livre) {
        return exemplaireDao.trouverTousParLivre(livre);
    }

    public ArrayList<Exemplaire> searchExemaplaire(String search) {
        return exemplaireDao.searchExemaplaire(search);
    }

    public void update(Exemplaire exemplaire) {
        exemplaireDao.update(exemplaire);
    }

    public Exemplaire findbyId(Long id) {
        return exemplaireDao.findbyId(id);
    }
}
