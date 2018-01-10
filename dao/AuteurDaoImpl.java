package fr.afpa.balthazar.logic.dao;

import fr.afpa.balthazar.logic.model.Abonne;
import fr.afpa.balthazar.logic.model.Auteur;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("auteurDao")
public class AuteurDaoImpl extends AbstractDao implements AuteurDao{


	public void saveAuteur(Auteur auteur) {
		persist(auteur);
	}
	public void deleteAuteur(Auteur auteur){delete(auteur);}

	public Auteur finAuthorByName(String name){
		Query query = getSession().createQuery(
				"from Auteur where nom = :nom");
		query.setParameter("nom", name);
		Auteur auteur = (Auteur) query.list().get(0);
		return auteur;
	}

	public Auteur finAuthorByAlias(String alias){
		Query query = getSession().createQuery(
				"from Auteur where alias = :alias");
		query.setParameter("alias", alias);
		Auteur auteur = (Auteur) query.list().get(0);
		return auteur;
	}
	public List<Auteur> findAll() {
		Query query = getSession().createQuery("FROM Auteur");
		return query.list();

	}

	public ArrayList<Auteur> searchAuteurs(String search) {

		Criteria criteria = getSession().createCriteria(Auteur.class);

		Criterion fname = Restrictions.ilike("nom", "%"+search+"%");
		Criterion lname = Restrictions.ilike("prenom","%"+search+"%");
		Criterion alias = Restrictions.ilike("alias","%"+search+"%");
		Junction orExp2 = Restrictions.disjunction().add(fname).add(lname).add(alias);
		criteria.add(orExp2);

		ArrayList<Auteur> results = (ArrayList) criteria.list();
		return results;
	}

    public Auteur findById(Long id) {
		Query query = getSession().createQuery("from Auteur where id = :id");
		query.setParameter("id", id);
		return (Auteur) query.list().get(0);

    }

    public void updateAuteur(Auteur auteur) {
        getSession().update(auteur);
    }


}
