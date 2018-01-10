package fr.afpa.balthazar.logic.service;

import fr.afpa.balthazar.logic.dao.AuteurDao;
import fr.afpa.balthazar.logic.model.Auteur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("auteurService")
@Transactional
public class AuteurServiceImpl implements AuteurService{

	@Autowired
	private AuteurDao auteurDao;
	
	public void saveAuteur(Auteur auteur) {
		auteurDao.saveAuteur(auteur);
	}

	public void deleteAuteur(Auteur auteur) {
		auteurDao.deleteAuteur(auteur);
	}

	public Auteur finAuthorByName(String name) {
		return auteurDao.finAuthorByName(name);
	}
	public Auteur finAuthorByAlias(String alias) {
		return auteurDao.finAuthorByAlias(alias);
	}

	public List<Auteur> findAll() {
		return auteurDao.findAll();
	}

	public ArrayList<Auteur> searchAuteurs(String search) {
		return auteurDao.searchAuteurs(search);
	}

    public Auteur findById(Long id) {
        return auteurDao.findById(id);
    }

    public void updateAuteur(Auteur auteur) {
        auteurDao.updateAuteur(auteur);
    }


}
