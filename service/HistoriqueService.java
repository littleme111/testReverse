package fr.afpa.balthazar.logic.service;

import fr.afpa.balthazar.logic.model.Historique;

public interface HistoriqueService {

    void creer(Historique historique);
    void supprimer(Historique historique);
    void update(Historique historique);
    Historique findById(Long id);
}
