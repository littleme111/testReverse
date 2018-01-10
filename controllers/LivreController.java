package fr.afpa.balthazar.logic.controllers;

import fr.afpa.balthazar.gui.frames.Frame;
import fr.afpa.balthazar.gui.frames.nouveaux.livreForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

//Controller Class for Livre
@Component
public class LivreController {


    @Autowired
    MainController mainController;

    @Autowired
    Frame frame;

    //Empty Contstructor for autowiring
    public LivreController() {
        System.out.println(frame);
    }


    //Set up listeners for form. cancel, submit and add author1, author2 (if selected), collection
    public void setUpListenersLivre(livreForm livreForm){
        livreForm.getAddSecondAuthorCheckbox().addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                mainController.onClickAddAuthorCheckBox(frame, e);
            }
        });


        livreForm.getCreateCollection().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainController.onClickNewCollection(frame);
            }
        });
        livreForm.getCreateAuthor1().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainController.onClickNewAuteur(frame);
            }
        });
        livreForm.getCreateAuthor2().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainController.onClickNewAuteur(frame);
            }
        });

        livreForm.getAnnulerButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainController.onClickAnnuler(frame);
            }
        });

        livreForm.getValiderButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainController.onClickSubmit(frame);
            }
        });

    }

    //returns form instance with listeners attached.
    public livreForm getLivreForm() {
        livreForm livreForm = new livreForm();
        setUpListenersLivre(livreForm);
        return livreForm;
    }

}
