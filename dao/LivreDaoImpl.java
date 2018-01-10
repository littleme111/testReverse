package fr.afpa.balthazar.logic.dao;

import fr.afpa.balthazar.logic.exceptions.CustomConstraintViolation;
import fr.afpa.balthazar.logic.model.Collection;
import fr.afpa.balthazar.logic.model.Livre;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository("livreDao")
public class LivreDaoImpl extends AbstractDao implements LivreDao {


    public void creer(Livre livre) throws CustomConstraintViolation {
        try {
            persist(livre);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public Livre trouverParTitre(String titre) throws CustomConstraintViolation {
        Query query = getSession().createQuery("from Livre where titre = :titre");
        query.setParameter("titre", titre);
        return (Livre) query.list().get(0);
    }

    public Livre trouverParISBN(String ISBN) throws CustomConstraintViolation {
        Query query = getSession().createQuery("from Livre where ISBN = :ISBN");
        query.setParameter("ISBN", ISBN);
        return (Livre) query.list().get(0);
    }

    public Livre trouverParLivre(Livre livre) throws CustomConstraintViolation {
        Query query = getSession().createQuery("from Livre where livre = :livre");
        query.setParameter("livre", livre);
        return (Livre) query.list().get(0);
    }


    public List<Livre> trouverTous() throws CustomConstraintViolation {
        Query query = getSession().createQuery("from Livre");
        return query.list();
    }

    public ArrayList<Livre> searchLivre(String search) {
        Criteria criteria = getSession().createCriteria(Livre.class);

        Criterion ISBN = Restrictions.ilike("ISBN", "%"+search+"%");
        Criterion titre = Restrictions.ilike("title", "%"+search+"%");
        Criterion soustitre = Restrictions.ilike("sub_title", "%"+search+"%");
        Junction orExp = Restrictions.disjunction().add(ISBN).add(titre).add(soustitre);
        criteria.add(orExp);

        ArrayList<Livre> results = (ArrayList) criteria.list();
        System.out.println(results);
        return results;
    }

    public void updateLivre(Livre livre) throws CustomConstraintViolation {
        getSession().update(livre);
    }
}
