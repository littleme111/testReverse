package fr.afpa.balthazar.logic.service;

import fr.afpa.balthazar.logic.dao.AbonneDao;
import fr.afpa.balthazar.logic.model.Abonne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("abonneService")
@Transactional
public class AbonneServiceImpl implements AbonneService {

    @Autowired
    AbonneDao abonneDao;

    public void saveAbonne(Abonne abonne) {
        abonneDao.saveAbonne(abonne);
    }

    public void deleteAbonne(Abonne abonne) {
        abonneDao.deleteAbonne(abonne);
    }

    public Abonne findAbonneByName(String name) {
        return abonneDao.findAbonneByName(name);
    }
    public Abonne findAbonneById(Long id_personne) {return abonneDao.findAbonneById(id_personne); }
    public Abonne findAbonneByNoAbonne(String noAbonne) {return abonneDao.findAbonneByNoAbonne(noAbonne); }

    public String generateNoAbonne(){return abonneDao.generateNoAbonne();}

    public ArrayList<Abonne> searchAbonnes(String search) {
        return abonneDao.searchAbonnes(search);
    }

    public void updateAbonne(Abonne abonne){
        abonneDao.updateAbonne(abonne);
    }
}
