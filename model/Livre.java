package fr.afpa.balthazar.logic.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;


public class Livre {

    private String ISBN;
    private String title;
    private String sub_title;
    private Collection collection;
    private Set<Auteur> auteurs = new HashSet<Auteur>();
    private Set<Exemplaire> exemplaires;

    public Livre() {
    }




}
