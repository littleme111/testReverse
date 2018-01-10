package fr.afpa.balthazar.logic.controllers;

import fr.afpa.balthazar.gui.frames.Frame;
import fr.afpa.balthazar.gui.frames.nouveaux.auteurForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


//Controller class for Auteur.
@Component
public class AuteurController {



    @Autowired
    MainController mainController;

    @Autowired
    Frame frame;


    //Sets up base listeners on frame submit, cancel.
    public AuteurController() {
    }

    public void setUpListenersAuteur(auteurForm auteurForm){
        auteurForm.getSubmit().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainController.onClickSubmit(frame);
            }
        });

        auteurForm.getCancel().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainController.onClickAnnuler(frame);
            }
        });


    }

    //Returns new instance of auteurForm with listeners attached.
    public auteurForm getAuteurform() {
        auteurForm auteurForm = new auteurForm();
        setUpListenersAuteur(auteurForm);
        return auteurForm;
    }

}
