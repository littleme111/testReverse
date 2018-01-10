package fr.afpa.balthazar.logic.model;


import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;
public class Collection {


    private String id;

    private String nomCollection;

    private Set<Livre> livresCollection;

    public Collection() {}


}
