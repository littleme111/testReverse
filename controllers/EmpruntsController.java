package fr.afpa.balthazar.logic.controllers;

import fr.afpa.balthazar.gui.frames.Frame;
import fr.afpa.balthazar.gui.frames.emprunts.empruntForm;
import fr.afpa.balthazar.logic.model.Abonne;
import fr.afpa.balthazar.logic.model.Exemplaire;
import fr.afpa.balthazar.logic.model.Historique;
import fr.afpa.balthazar.logic.model.Livre;
import fr.afpa.balthazar.logic.service.AbonneService;
import fr.afpa.balthazar.logic.service.ExemplaireService;
import fr.afpa.balthazar.logic.service.HistoriqueService;
import fr.afpa.balthazar.logic.service.LivreService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyVetoException;
import java.util.List;


//Controller vclass for Emprunts.
@Controller
public class EmpruntsController {

    @Autowired
    Frame frame;

    @Autowired
    MainController mainController;


    //Most setvices are needed as a new borrow will need to update  or consult abonne livre historique and exemplaire.
    @Autowired
    AbonneService abonneService;

    @Autowired
    ExemplaireService exemplaireService;

    @Autowired
    HistoriqueService historiqueService;

    @Autowired
    LivreService livreService;

    //All books boolean reflects properties of boolean found in emprunts form.
    //allBooks will change value depending if user has selected checkbox : show only active borrows.
    private boolean allBooks;





    //empty constructor for autowiring.
    public EmpruntsController() {
    }

    //sets up listeners for cancel, submit, borrow, give back and checkbox for active borrows.
    public void setUpListenersEmprunts(empruntForm empruntForm) {
        empruntForm.getCancel().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainController.onClickAnnuler(frame);
            }
        });
        empruntForm.getSubmit().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainController.onClickSubmit(frame);
            }
        });
        empruntForm.getEmprunterButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onClickEmprunterUnLivre(frame);
            }
        });
        empruntForm.getRendreButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onClickRendreUnLivre(frame);
            }
        });
        empruntForm.getCheckBoxForOnlyActiveBorrows().addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                onClickShowOnlyActualBorrows(frame, e);
            }
        });
    }


    //When user borrows one book.
    public void onClickEmprunterUnLivre(Frame frame){
        //get form as well as both tables.
        empruntForm empruntForm = ((empruntForm) frame.getjDesktopPane().getSelectedFrame());
        JTable tableLivres = ((empruntForm) frame.getjDesktopPane().getSelectedFrame()).getTableForSearchbooks();
        JTable emprunts = ((empruntForm) frame.getjDesktopPane().getSelectedFrame()).getTableForActualBorrows();
        //get table model.
        DefaultTableModel dfm = (DefaultTableModel) emprunts.getModel();

        //Get abonne form id on jframe.
        Abonne abo = abonneService.findAbonneByNoAbonne(((empruntForm) frame.getjDesktopPane().getSelectedFrame()).getLabelForAboId().getText());

        //I is set to null to be abler to test validity of the selected row.
        Integer i = null;
        //Get index of selected row.
        i = tableLivres.getSelectedRow();

        if(i != null){
            //get isbn of selected book.
            String isbn = (String) tableLivres.getValueAt(i, 0);
            //Only proceed if the book is available.
            if((String) tableLivres.getValueAt(i, 2) == "Disponible"){
                //get the first copy of said book that is available.
                Exemplaire ex =exemplaireService.chercherPremierDisponible(livreService.trouverParISBN(isbn));


                //Set available to falsE.
                ex.setDisponible(false);
                //update object.
                exemplaireService.update(ex);

                //Create new Historique.
                Historique historique = new Historique();
                //Set actual user to historique.
                historique.setAbonne(abo);
                //Set borrow date to now.
                historique.setDate_emp(DateTime.now());
                //Set the copy
                historique.setExemplaire(ex);
                //Persist historique to db.
                historiqueService.creer(historique);

                //reload all books from database. case : book is not available anymore.
                List<Livre> ll = livreService.trouverTous();
                //Get aboone from database with update valueS.
                Abonne aboAfter = abonneService.findAbonneByNoAbonne(abo.getNoAbonne());
                //repopulate new form.
                empruntForm.populate(aboAfter, ll, allBooks);
                //Repaint tables.
                tableLivres.repaint();
                emprunts.repaint();
            }
        }


    }

    //When user returns book.
    public void onClickRendreUnLivre(Frame frame){

        //Get table, abonne and form.
        JTable emprunts = ((empruntForm) frame.getjDesktopPane().getSelectedFrame()).getTableForActualBorrows();
        Abonne abo = abonneService.findAbonneByNoAbonne(((empruntForm) frame.getjDesktopPane().getSelectedFrame()).getLabelForAboId().getText());
        empruntForm empruntForm = ((empruntForm) frame.getjDesktopPane().getSelectedFrame());


        //Get selectefd row and check if value is valid.
        Integer i = null;
        i = emprunts.getSelectedRow();
        if(i != null){

            //Get the ids of the copy and the historique.
            String id_exemplaire = (String) emprunts.getValueAt(i, 4);
            String id_historique = (String) emprunts.getModel().getValueAt(i, 5);
            //get the objects
            Exemplaire ex = exemplaireService.findbyId(Long.parseLong(id_exemplaire));
            Historique historique = historiqueService.findById(Long.parseLong(id_historique));

            //set copy to available.
            ex.setDisponible(true);
            //update copy.
            exemplaireService.update(ex);
            //set return date to now.
            historique.setDate_ret(DateTime.now());
            //Update historique.
            historiqueService.update(historique);
            //Load all books
            List<Livre> ll = livreService.trouverTous();
            //Load abonne
            Abonne abonneAfter = abonneService.findAbonneById(abo.getId());
            //Repopulate form
            empruntForm.populate(abonneAfter, ll, allBooks);
            emprunts.repaint();


        }
    }

    //Return instance of empruntForm with listeners attached.
    public empruntForm getEmprunts(){
        empruntForm empruntForm = new empruntForm();
        setUpListenersEmprunts(empruntForm);
        return empruntForm;
    }

    //If user selects checkbox to show only ongoing borrows.

    public void onClickShowOnlyActualBorrows(Frame frame, ItemEvent e){

        //Change boolean value accordingly.
        if (e.getStateChange() == ItemEvent.SELECTED) {
            allBooks = false;
        } else {
            allBooks = true;
        }
        //repopulate form with update boolean value.
        empruntForm empruntForm = ((empruntForm) frame.getjDesktopPane().getSelectedFrame());
        Abonne abo = abonneService.findAbonneByNoAbonne(((empruntForm) frame.getjDesktopPane().getSelectedFrame()).getLabelForAboId().getText());
        List<Livre> ll = livreService.trouverTous();
        empruntForm.populate(abo, ll, allBooks);
    }


}
