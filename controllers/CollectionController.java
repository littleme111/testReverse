package fr.afpa.balthazar.logic.controllers;

import fr.afpa.balthazar.gui.frames.Frame;
import fr.afpa.balthazar.gui.frames.nouveaux.collectionForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//COntroller class for Collection
@Controller
public class CollectionController {

    @Autowired
    MainController mainController;

    @Autowired
    Frame frame;


    //Empty constructor for autowiring.
    public CollectionController() {}

    public void setUpListenersCollection(collectionForm collectionForm) {
        collectionForm.getCancel().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainController.onClickAnnuler(frame);
            }
        });
        collectionForm.getSubmit().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainController.onClickSubmit(frame);
            }
        });
    }


    //returns new instance of collectionForm wirth listeners attached.
    public collectionForm getCollectionForm() {
        collectionForm collectionForm = new collectionForm();
        setUpListenersCollection(collectionForm);
        return collectionForm;
    }

}
