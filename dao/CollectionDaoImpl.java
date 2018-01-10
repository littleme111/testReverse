package fr.afpa.balthazar.logic.dao;

import fr.afpa.balthazar.logic.model.Auteur;
import fr.afpa.balthazar.logic.model.Collection;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository("collectionDao")
public class CollectionDaoImpl extends AbstractDao implements CollectionDao {



    public void creer(Collection collection) {
        persist(collection);
    }

    public Collection findByName(String name) {

        Query query = getSession().createQuery(
                "from Collection where nomCollection = :nom"
        );
        query.setParameter("nom", name);
        Collection collection = (Collection) query.list().get(0);
        return collection;
    }

    public List<Collection> findAll() {
        Query query = getSession().createQuery("FROM Collection");
        return query.list();
    }

    public ArrayList<Collection> searchCollection(String search) {
            Criteria criteria = getSession().createCriteria(Collection.class);

            Criterion fname = Restrictions.ilike("nomCollection", "%"+search+"%");
            Junction orExp2 = Restrictions.disjunction().add(fname);
            criteria.add(orExp2);

            ArrayList<Collection> results = (ArrayList) criteria.list();
            return results;

    }

    public Collection findById(String id) {
        Query query = getSession().createQuery("from Collection where id = :id");
        query.setParameter("id", id);
        return (Collection) query.list().get(0);

    }

    public void updateCollection(Collection collection) {
        getSession().update(collection);
    }
}
