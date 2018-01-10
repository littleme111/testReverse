package fr.afpa.balthazar.logic.service;

import fr.afpa.balthazar.logic.dao.HistoriqueDao;
import fr.afpa.balthazar.logic.model.Historique;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("historiqueService")
@Transactional
public class HistoriqueServiceImpl implements HistoriqueService{

    @Autowired
    HistoriqueDao historiqueDao;


    public void creer(Historique historique) {
        historiqueDao.creer(historique);
    }

    public void supprimer(Historique historique) {
        historiqueDao.supprimer(historique);
    }

    public void update(Historique historique) {
        historiqueDao.update(historique);
    }

    public Historique findById(Long id) {
        return historiqueDao.findById(id);
    }
}
