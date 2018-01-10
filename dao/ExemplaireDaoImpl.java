package fr.afpa.balthazar.logic.dao;

import fr.afpa.balthazar.logic.model.Collection;
import fr.afpa.balthazar.logic.model.Exemplaire;
import fr.afpa.balthazar.logic.model.Livre;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Set;

@Repository("exemplaireDao")
public class ExemplaireDaoImpl extends AbstractDao implements ExemplaireDao {

    public void creer(Exemplaire exemplaire) {
        persist(exemplaire);
    }

    public Exemplaire chercherPremierDisponible(Livre livre) {
        Query query = getSession().createQuery("from Exemplaire where ISBN = :ISBN");
        query.setParameter("ISBN", livre.getISBN());
        return (Exemplaire) query.list().get(0);
    }

    public int nombreDisponibles(Livre livre) {
        Query query = getSession().createQuery("from Exemplaire where ISBN = :ISBN");
        query.setParameter("ISBN", livre.getISBN());
        return query.list().size();

    }

    public void supprimer(Exemplaire exemplaire) {
        delete(exemplaire);
    }

    public ArrayList<Exemplaire> trouverTousParLivre(Livre livre) {
        Query query = getSession().createQuery("from Exemplaire where ISBN = :ISBN");
        query.setParameter("ISBN", livre.getISBN());
        ArrayList<Exemplaire> se = (ArrayList<Exemplaire>) query.list();
        return se;
    }

    public ArrayList<Exemplaire> searchExemaplaire(String search) {
        Criteria criteria = getSession().createCriteria(Exemplaire.class);

        Criterion ISBN = Restrictions.ilike("ISBN", "%"+search+"%");
        Junction orExp2 = Restrictions.disjunction().add(ISBN);
        criteria.add(orExp2);

        ArrayList<Exemplaire> results = (ArrayList) criteria.list();
        return results;
    }

    public void update(Exemplaire exemplaire) {
        getSession().update(exemplaire);
    }

    public Exemplaire findbyId(Long id) {
        Query query = getSession().createQuery("from Exemplaire where id_exemplaire = :id_exemplaire");
        query.setParameter("id_exemplaire", id);
        return (Exemplaire) query.list().get(0);
    }
}
