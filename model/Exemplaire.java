package fr.afpa.balthazar.logic.model;

import javax.persistence.*;
import java.util.Set;


public class Exemplaire {


    private Long id_exemplaire;
    private boolean disponible;
    private Livre livre;
    private Set<Historique> historique;

    public Exemplaire() {
    }


}
