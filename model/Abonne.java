package fr.afpa.balthazar.logic.model;


import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.joda.time.DateTime;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;
import java.util.Collection;

/**
 * @PrimaryKeyJoinColumn : self explanatory name : kay on which to join.
 * !!!! Annoations always on getters
 */

public class Abonne extends Personne {



    private String email;


    private String noAbonne;

    private Set<Historique> historique;
    private int nbEmprunts;

    public Abonne() {

    }


}
