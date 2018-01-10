package fr.afpa.balthazar.logic.dao;

import fr.afpa.balthazar.logic.model.Historique;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository("historiqueDao")
public class HistoriqueDaoImpl extends AbstractDao implements HistoriqueDao {

    public void creer(Historique historique) {
        getSession().persist(historique);
    }

    public void supprimer(Historique historique) {
        getSession().delete(historique);
    }

    public void update(Historique historique) {
        getSession().update(historique);
    }

    public Historique findById(Long id) {
        Query query = getSession().createQuery("from Historique where id_historique = :id_historique");
        query.setParameter("id_historique", id);
        return (Historique) query.list().get(0);
    }
}
