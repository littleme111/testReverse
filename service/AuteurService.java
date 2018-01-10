package fr.afpa.balthazar.logic.service;

import fr.afpa.balthazar.logic.model.Auteur;

import java.util.ArrayList;
import java.util.List;

public interface AuteurService {

	void saveAuteur(Auteur auteur);
	void deleteAuteur(Auteur auteur);
	Auteur finAuthorByName(String name);
	Auteur finAuthorByAlias(String alias);
	List<Auteur> findAll();
	ArrayList<Auteur> searchAuteurs(String search);
	Auteur findById(Long id);
	void updateAuteur(Auteur auteur);
}
