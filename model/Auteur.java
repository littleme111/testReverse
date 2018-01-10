package fr.afpa.balthazar.logic.model;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;


/**
 * @PrimaryKeyJoinColumn : self explanatory name : key on which to join.
 * !!!! Annoations always on getters
 *
 */



public class Auteur extends Personne{

	private String alias;

	private Set<Livre> ecrits;





}
