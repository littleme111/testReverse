package fr.afpa.balthazar.logic.controllers;

import fr.afpa.balthazar.gui.frames.Frame;
import fr.afpa.balthazar.gui.frames.nouveaux.exemplaireForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Xontroller Class for Exemplaire - Copy.
@Component
public class ExemplaireController {


    @Autowired
    MainController mainController;

    @Autowired
    Frame frame;

    //Empty constructor for autowiring.
    public ExemplaireController() {

    }

    //Set up base listeners submit, cancel
    public void setUpListenersExemplaire(exemplaireForm exemplaireForm) {
        exemplaireForm.getCancel().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainController.onClickAnnuler(frame);
            }
        });
        exemplaireForm.getSubmit().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainController.onClickSubmit(frame);
            }
        });

    }

    //Returns ne instance of form with listeners attached.
    public exemplaireForm getExemplaireForm() {
        exemplaireForm exemplaireForm = new exemplaireForm();
        setUpListenersExemplaire(exemplaireForm);
        return exemplaireForm;
    }


}

