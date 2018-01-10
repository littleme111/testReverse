package fr.afpa.balthazar.logic.controllers;

import fr.afpa.balthazar.gui.frames.Frame;
import fr.afpa.balthazar.gui.frames.emprunts.empruntForm;
import fr.afpa.balthazar.gui.frames.nouveaux.abonneForm;
import fr.afpa.balthazar.gui.frames.nouveaux.auteurForm;
import fr.afpa.balthazar.gui.frames.nouveaux.collectionForm;
import fr.afpa.balthazar.gui.frames.nouveaux.livreForm;
import fr.afpa.balthazar.gui.frames.recherche.Search;
import fr.afpa.balthazar.logic.model.*;
import fr.afpa.balthazar.logic.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.HashMap;

@Component
public class SearchController {

    @Autowired
    MainController mainController;

    @Autowired
    AbonneService abonneService;

    @Autowired
    LivreService livreService;

    @Autowired
    AuteurService auteurService;

    @Autowired
    ExemplaireService exemplaireService;

    @Autowired
    CollectionService collectionService;

    @Autowired
    EmpruntsController empruntsController;

    @Autowired
    AbonneController abonneController;

    @Autowired
    CollectionController collectionController;

    @Autowired
    AuteurController auteurController;

    @Autowired
    LivreController livreController;

    @Autowired
    Frame frame;

    //Empty Constructor for Autowiring.
    public SearchController() {
    }

    //set pu listeners on frame
    public  void setUpListenersSearch(Search search){
        search.getAnnulerButtonFroAbonneSearch().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainController.onClickAnnuler(frame);
            }
        });
        search.getEditButtonForAbonneSearch().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onClickEdit(frame);
            }
        });
        search.getSearchInput().addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                onKeyReleased(frame);
            }
        });
    }


    //on key released is main function for search, on each key aand depending on frame title a new criterion serach is setnt to db.
    //All search panels allow multi field search, (name, title isbn...)
    public void onKeyReleased(final Frame frame) {
        //create empty table model.
        DefaultTableModel userTableModel = null;
        //Get frame title and search String.
        String title = frame.getjDesktopPane().getSelectedFrame().getTitle();
        String search = ((Search) frame.getjDesktopPane().getSelectedFrame()).getSearchInput().getText();

        if (title.equals("Recherche Abonne")) {
            //Abonne search has extra listener for borrow/return frame.
            ((Search) frame.getjDesktopPane().getSelectedFrame()).getButtonLoanbooks().addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    onClickEmprunterLivres(frame);
                }
            });

            //get search results
            ArrayList<Abonne> la = abonneService.searchAbonnes(search);
            //Create corresponding tableModel - deactivate edition
            userTableModel = new DefaultTableModel(new Object[]{"NoAbonne", "Prenom", "Nom", "E-Mail"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            //Loop on all results and set to Array of String then add to tableModel
            for (int x = 0; x < la.size(); x++) {
                Abonne abo = la.get(x);

                String[] datas = new String[4];

                datas[0] = abo.getNoAbonne();
                datas[1] = abo.getNom();
                datas[2] = abo.getPrenom();
                datas[3] = abo.getEmail();

                userTableModel.addRow(datas);
            }


        } else if (title.equals("Recherche Livre")) {
            //Get results
            ArrayList<Livre> la = livreService.searchLivre(search);
            //Create table Model
            userTableModel = new DefaultTableModel(new Object[]{"Titre", "Sous-titre", "ISBN"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            //Loop on data and add to tablemodel
            for (int x = 0; x < la.size(); x++) {
                Livre l = la.get(x);

                String[] datas = new String[3];

                datas[0] = l.getTitle();
                datas[1] = l.getSub_title();
                datas[2] = l.getISBN();

                userTableModel.addRow(datas);
            }


        } else if (title.equals("Recherche Auteur")) {


            ArrayList<Auteur> la = auteurService.searchAuteurs(search);
            HashMap<Long, Abonne> data = new HashMap<Long, Abonne>();
            userTableModel = new DefaultTableModel(new Object[]{"Id Auteur", "Nom", "Prenom", "Alias"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            for (int x = 0; x < la.size(); x++) {
                Auteur a = la.get(x);

                String[] datas = new String[4];

                datas[0] = a.getId().toString();
                datas[1] = a.getNom();
                datas[2] = a.getPrenom();
                datas[3] = a.getAlias();

                userTableModel.addRow(datas);
            }

        } else if (title.equals("Recherche Collection")) {
            ArrayList<Collection> lc = collectionService.searchCollection(search);
            HashMap<Long, Collection> data = new HashMap<Long, Collection>();
            userTableModel = new DefaultTableModel(new Object[]{"Id Collection", "Nom"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            System.out.println(userTableModel);

            for (int x = 0; x < lc.size(); x++) {
                Collection c = lc.get(x);

                String[] datas = new String[2];

                datas[0] = c.getId();
                datas[1] = c.getNomCollection();

                userTableModel.addRow(datas);
            }

        }
        //get search results table from frame : setVisible, set single row selection, and pass tablemodel
        //Repaint table.
        ((Search) frame.getjDesktopPane().getSelectedFrame()).getSearchResultsTable().setVisible(true);
        ((Search) frame.getjDesktopPane().getSelectedFrame()).getSearchResultsTable().setRowSelectionAllowed(true);
        ((Search) frame.getjDesktopPane().getSelectedFrame()).getSearchResultsTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ((Search) frame.getjDesktopPane().getSelectedFrame()).getSearchResultsTable().setModel(userTableModel);
        ((Search) frame.getjDesktopPane().getSelectedFrame()).getSearchResultsTable().revalidate();
        ((Search) frame.getjDesktopPane().getSelectedFrame()).getSearchResultsTable().repaint();
    }


    //On click edit loads defautl creation depending on type of search and then populates fiels with data from db.
    public void onClickEdit(Frame frame) {
        //Get title and selected row index.
        String title = frame.getjDesktopPane().getSelectedFrame().getTitle();
        int i = ((Search) frame.getjDesktopPane().getSelectedFrame()).getSearchResultsTable().getSelectedRow();

        if (title.equals("Recherche Abonne")) {
            //get aboone num ber from selected row.
            String noAbonne = (String) ((Search) frame.getjDesktopPane().getSelectedFrame()).getSearchResultsTable().getValueAt(i, 0);

            //Get abone form db.
            Abonne abonne = null;
            try {
                abonne = abonneService.findAbonneByNoAbonne(noAbonne);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //get new form - populate and sety to view.
            abonneForm abonneForm = abonneController.getAbonneForm();
            abonneForm.setTitle("Modification Abonne");
            abonneForm.populate(abonne);
            abonneForm.setVisible(true);
            frame.getjDesktopPane().add(abonneForm);
            try {
                abonneForm.setSelected(true);
            } catch (PropertyVetoException e) {
                e.printStackTrace();
            }



        } else if (title.equals("Recherche Livre")) {
            //Get isbn form selected row
            String ISBN = (String) ((Search) frame.getjDesktopPane().getSelectedFrame()).getSearchResultsTable().getValueAt(i, 2);
            //get book from db.
            Livre livre = null;
            try {
                livre = livreService.trouverParISBN(ISBN);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //Get new Form - get number of authors : if == 2 then set checkbox to selected and input to enabled.
            livreForm livreForm = livreController.getLivreForm();
            livreForm.setTitle("Modification Livre");
            livreForm.setVisible(true);
            if (livre.getAuteurs().size() == 2) {
                livreForm.getAuthor2Input().setEnabled(true);
                livreForm.getAddSecondAuthorCheckbox().setSelected(true);
            }
            //populate form
            livreForm.populate(livre);

            //Get all books base on search
            ArrayList<Exemplaire> la = exemplaireService.trouverTousParLivre(livre);
            //Create Table Model
            DefaultTableModel userTableModel;
            userTableModel = new DefaultTableModel(new Object[]{"Id", "Disponible"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            //Loop on data and fill tableModel
            for (int x = 0; x < la.size(); x++) {
                Exemplaire ex = la.get(x);

                String[] datas = new String[2];

                datas[0] = ex.getId_exemplaire().toString();
                if (ex.isDisponible()) {
                    datas[1] = "Disponible";

                } else {
                    datas[1] = "Indisponible";
                }

                userTableModel.addRow(datas);
            }
            //set model
            livreForm.getTableForExemplaires().setModel(userTableModel);

            //add Form to frame and set Selected.
            frame.getjDesktopPane().add(livreForm);
            try {
                livreForm.setSelected(true);
            } catch (PropertyVetoException e) {
                e.printStackTrace();
            }
        } else if (title.equals("Recherche Auteur")) {
            //Get author id from selected index.
            String id = (String) ((Search) frame.getjDesktopPane().getSelectedFrame()).getSearchResultsTable().getValueAt(i, 0);
            //parse String and get Autho from db.
            Long idL = Long.parseLong(id);
            Auteur auteur = auteurService.findById(idL);
            //get new form from controller
            auteurForm auteurForm = auteurController.getAuteurform();
            //populate and add to view.
            auteurForm.populate(auteur);
            auteurForm.setVisible(true);
            auteurForm.setTitle("Modification Auteur");
            frame.getjDesktopPane().add(auteurForm);

            try {
                auteurForm.setSelected(true);
            } catch (PropertyVetoException e) {
                e.printStackTrace();
            }

        } else if (title.equals("Recherche Collection")) {
            //Get collection Id from seleected row.
            String id = (String) ((Search) frame.getjDesktopPane().getSelectedFrame()).getSearchResultsTable().getValueAt(i, 0);
            //get collection form db.
            Collection collection = collectionService.findById(id);
            //Get new Form
            collectionForm collectionForm = collectionController.getCollectionForm();
            //populate and add to view.
            collectionForm.populate(collection);
            collectionForm.setTitle("Modification Collection");
            frame.getjDesktopPane().add(collectionForm);
            try {
                collectionForm.setSelected(true);
            } catch (PropertyVetoException e) {
                e.printStackTrace();
            }
        }
    }


    //Loads book borrow window
    public void onClickEmprunterLivres(Frame frame){
        //Get table and selected row.
        Integer selRow = null;
        JTable jt = ((Search) frame.getjDesktopPane().getSelectedFrame()).getSearchResultsTable();
        selRow = jt.getSelectedRow();
        //If selected row isn't null
        if(selRow != null){
            //get value from table.
            Object o = jt.getValueAt(selRow, 0);
            //Get abonne from value.
            Abonne abonne = abonneService.findAbonneByNoAbonne((o.toString()));
            //get Emprint Form and add to view.
            empruntForm empruntForm = empruntsController.getEmprunts();
            empruntForm.setVisible(true);
            frame.getjDesktopPane().add(empruntForm);
            //Get all books, populate form and add to view.
            //3rd parameter in populate method corresponds to boolean used if showAllBorrows is checked .
            java.util.List<Livre> ll = livreService.trouverTous();
            empruntForm.populate(abonne, ll, true);
            try {
                empruntForm.setSelected(true);
            } catch (PropertyVetoException e) {
                e.printStackTrace();
            }

        }
    }

    //retrun a new search form.
    public Search getSearch() {
        Search search =  new Search();
        setUpListenersSearch(search);
        return search;
    }

}
