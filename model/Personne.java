package fr.afpa.balthazar.logic.model;

import org.joda.time.DateTime;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

/**
 * Abstract class person centralizes common ionformation from authors and users.
 *
 *
 * @Entity notation : marks entity as table.
 * @Table name = alows speicification on table name, if null class name is used.
 * @Inheritance Strategy : Joined, joins sub classes - authors and abonnes
 */

public abstract class Personne {


    private Long id_personne;

    private String prenom;

    private String nom;

    private DateTime date_naissance;

    public Personne() {
    }

}
