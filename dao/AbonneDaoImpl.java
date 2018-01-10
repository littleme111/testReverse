package fr.afpa.balthazar.logic.dao;

import fr.afpa.balthazar.logic.model.Abonne;
import fr.afpa.balthazar.logic.model.Livre;
import fr.afpa.balthazar.logic.model.Personne;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.*;
import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.stereotype.Repository;

import java.util.*;

import java.util.Calendar;
import java.util.List;


@Repository("AbonneDao")
public class AbonneDaoImpl extends AbstractDao implements AbonneDao{

    public void saveAbonne(Abonne abonne) {
        persist(abonne);
    }
    public void deleteAbonne(Abonne abonne){delete(abonne);}

    public Abonne findAbonneByName(String name){
        Query query = getSession().createQuery(
                "from Abonne where nom = :nom");
        query.setParameter("nom", name);
        Abonne abonne = (Abonne) query.list().get(0);
        return abonne;
    }
    public Abonne findAbonneById(Long id_personne){
        Query query = getSession().createQuery(
                "from Abonne where id = :id_personne");
        query.setParameter("id_personne", id_personne);
        Abonne abonne = (Abonne) query.list().get(0);
        return abonne;
    }
    public Abonne findAbonneByNoAbonne(String noAbonne){
        Query query = getSession().createQuery(
                "from Abonne where noAbonne = :noAbonne");
        query.setParameter("noAbonne", noAbonne);
        Abonne abonne = (Abonne) query.list().get(0);
        return abonne;
    }

    public String generateNoAbonne(){
        String prefix = "abo";
        Integer maxId;
        String noAbonne;
        Criteria criteria = null;
        try {
            criteria = getSession()
                    .createCriteria(Abonne.class)
                    .setProjection(Projections.max("id"));
            Long l = (Long) criteria.uniqueResult();
            if(l == null){
                l = 1L;
            }
            Calendar now = Calendar.getInstance();
            int year = now.get(Calendar.YEAR);
            String yearInString = String.valueOf(year);
            noAbonne = yearInString + prefix + l.toString();

            return noAbonne;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


    public ArrayList<Abonne> searchAbonnes(String search) {
        Criteria criteria = getSession().createCriteria(Abonne.class);
        Criterion fname = Restrictions.ilike("nom", "%"+search+"%");
        Criterion lname = Restrictions.ilike("prenom","%"+search+"%");
        Criterion email = Restrictions.ilike("email","%"+search+"%");
        Junction orExp2 = Restrictions.disjunction().add(fname).add(lname).add(email);
        criteria.add(orExp2);

        ArrayList<Abonne> results = (ArrayList) criteria.list();
        return results;
    }


    public void updateAbonne(Abonne abonne){
        getSession().update(abonne);
    }

}
