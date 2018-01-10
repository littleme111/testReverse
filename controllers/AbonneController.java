package fr.afpa.balthazar.logic.controllers;

import fr.afpa.balthazar.gui.frames.Frame;
import fr.afpa.balthazar.gui.frames.nouveaux.abonneForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Controller clas for Abonnes.
@Controller("AbonneController")
public class AbonneController {



    @Autowired
    MainController mainController;

    @Autowired
    Frame frame;

    //Empty constructor for autowiring.
    public AbonneController() {
    }

    //Sets up base listeners cancel and submit.
    public void setUpListenersAbonne(abonneForm abonneForm){
        abonneForm.getSubmit().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainController.onClickSubmit(frame);
            }
        });

        abonneForm.getCancel().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainController.onClickAnnuler(frame);
            }
        });
    }

    //Returns new instance of abonneForm with listeners attached to object
    public fr.afpa.balthazar.gui.frames.nouveaux.abonneForm getAbonneForm() {
        abonneForm abonneForm = new abonneForm();
        setUpListenersAbonne(abonneForm);
        return abonneForm;
    }


}
