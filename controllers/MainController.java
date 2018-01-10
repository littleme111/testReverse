package fr.afpa.balthazar.logic.controllers;

import fr.afpa.balthazar.gui.frames.Frame;
import fr.afpa.balthazar.gui.frames.nouveaux.*;
import fr.afpa.balthazar.gui.frames.recherche.Search;
import fr.afpa.balthazar.gui.menus.MenuFile;
import fr.afpa.balthazar.logic.exceptions.CustomConstraintViolation;
import fr.afpa.balthazar.logic.model.*;
import fr.afpa.balthazar.logic.service.*;
import fr.afpa.balthazar.logic.validators.*;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import javax.swing.*;
import javax.validation.ConstraintViolation;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.beans.PropertyVetoException;
import java.util.List;
import java.util.Set;


/**
 * Maincontroller Class, sets up listeneers for menu.
 * Defines all methods for actions on menu and instantiates appropriate controllers for frame Actions.
 * As well as some utilitarian methods used across the board.
 *
 */

@Controller("MainController")
public class MainController {

    @Autowired
    protected Frame frame;

    @Autowired
    private CollectionService collectionService;
    @Autowired
    private AuteurService auteurService;
    @Autowired
    private LivreService livreService;
    @Autowired
    private ExemplaireService exemplaireService;
    @Autowired
    private AbonneService abonneService;
    @Autowired
    private HistoriqueService historiqueService;

    @Autowired
    private  ExemplaireController exemplaireController;
    @Autowired
    private  LivreController livreController;
    @Autowired
    private  AbonneController abonneController;
    @Autowired
    private CollectionController CollectionController;
    @Autowired
    private  AuteurController auteurController;
    @Autowired
    private SearchController searchController;

    @Autowired
    private livreValidator livreValidator;
    @Autowired
    private abonneValidator abonneValidator;
    @Autowired
    private auteurValidator auteurValidator;
    @Autowired
    private collectionValidator collectionValidator;
    @Autowired
    private exemplaireValidator exemplaireValidator;


    //empty constructor for autowiring.
    public MainController() {

    }


    //Set up listeners for all menu items.
    public void setUpListeners(){
        //File -> New -> Book
        ((MenuFile) frame.getJMenuBar().getMenu(0)).getNouveau().getLivre().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                onClickNewBook(frame);
            }
        });

        //File -> New -> Exemplaire
        ((MenuFile) frame.getJMenuBar().getMenu(0)).getNouveau().getExemplaire().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                onClickNewExemplaire(frame);
            }
        });

        //File -> New -> Auteur
        ((MenuFile) frame.getJMenuBar().getMenu(0)).getNouveau().getAuteur().addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent actionEvent) {
                    onClickNewAuteur(frame);
                }
        });

        //File -> New -> Collection
        ((MenuFile) frame.getJMenuBar().getMenu(0)).getNouveau().getCollection().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                onClickNewCollection(frame);
            }
        });

        //File -> New -> Abonne
        ((MenuFile) frame.getJMenuBar().getMenu(0)).getNouveau().getAbonne().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                onClickNewAbonne(frame);
            }
        });

        //File -> Search -> Book
        ((MenuFile) frame.getJMenuBar().getMenu(0)).getChercher().getLivre().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                onClickSearchBook(frame);
            }
        });
        //File -> Search -> Exemplaire
        ((MenuFile) frame.getJMenuBar().getMenu(0)).getChercher().getExemplaire().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                onClickSearchExemplaire(frame);
            }
        });
        //File -> Search -> Auteur
        ((MenuFile) frame.getJMenuBar().getMenu(0)).getChercher().getAuteur().addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent actionEvent) {
                    onClickSearchAuteur(frame);
                }
            });
        //File -> Search -> Collection
        ((MenuFile) frame.getJMenuBar().getMenu(0)).getChercher().getCollection().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                onClickSearchCollection(frame);
            }
        });

        //File -> Search -> Abonne
        ((MenuFile) frame.getJMenuBar().getMenu(0)).getChercher().getAbonne().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                onClickSearchAbonne(frame);
            }

        });

        ((MenuFile) frame.getJMenuBar().getMenu(0)).getQuitter().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                Container container = ((MenuFile) frame.getJMenuBar().getMenu(0)).getQuitter().getParent();
                do
                    container = container.getParent();
                while (!(frame instanceof JFrame));
                ((JFrame) frame).dispose();
            }
        });
    }


    //all methods walled in listeners instantion aboved defined Here.


    //New Exemplaire Form : get form instance from controller
    public void onClickNewExemplaire(Frame frame) {

            exemplaireForm exemplaireForm = exemplaireController.getExemplaireForm();

            //Fetch all books and set title to jcombobox
            List<Livre> ll = livreService.trouverTous();

            for (Livre L : ll) {
                exemplaireForm.getBookSelect().addItem(L.getTitle());
            }

            //add frame to desktop
            frame.getjDesktopPane().add(exemplaireForm);
            //Set selected.
            try {
                exemplaireForm.setSelected(true);
            } catch (PropertyVetoException e) {
                e.printStackTrace();
            }
        }


        //new Book Form : get from instance from controller
    public void onClickNewBook(Frame frame) {


            //Create new Book Form
            livreForm livreForm = livreController.getLivreForm();

//            livreForm.getRootPanelForExemplaireTable().setVisible(false);

            //Get all collections.
            List<Collection> lc = null;
            try {
                lc = collectionService.trouverTous();
            } catch (CustomConstraintViolation customConstraintViolation) {
                customConstraintViolation.printStackTrace();
            }
            //Add Collections to combobox.
            for (Collection a : lc) {
                livreForm.getCollectionInput().addItem(a.getNomCollection());
            }

            //Get all authors
            addAuthorsToComboBox(livreForm.getAuthor1Input());
            addAuthorsToComboBox(livreForm.getAuthor2Input());

            //add to desjtop pane
            frame.getjDesktopPane().add(livreForm);
            //Set selected.
            try {
                livreForm.setSelected(true);
            } catch (PropertyVetoException e) {
                e.printStackTrace();
            }
        }

        //new abonne Form : get instance from controller
    public void onClickNewAbonne(Frame frame) {

            abonneForm abonneForm = abonneController.getAbonneForm();
            //ad to desktop
            frame.getjDesktopPane().add(abonneForm);
            //set Selected
            try {
                abonneForm.setSelected(true);
            } catch (PropertyVetoException e) {
                e.printStackTrace();
            }
        }

        //new collection form : get instance from controller
    public void onClickNewCollection(Frame frame) {
        //Tries to get Selected Frame title, checking if the new window is called from book panel or from menu.
        //if called from book form walues must be update on submit.
        String i = null;
        try {
            i = frame.getjDesktopPane().getSelectedFrame().getTitle();
        } catch (NullPointerException e) {

        }

        //If title equals new book then set boolean to true.
        if (i != null && i.equals("Nouveau Livre")) {
            ((livreForm) frame.getjDesktopPane().getSelectedFrame()).setAdingCollection(true);
        }

        //Get new collection form from controller
        collectionForm collectionForm = CollectionController.getCollectionForm();
        //Set boolean fromBook to true.
        collectionForm.setFromBook(true);
        //Add to desktop
        frame.getjDesktopPane().add(collectionForm);

        //set selected
        try {
            collectionForm.setSelected(true);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }

    }
    //new author form : get instance from controller
    public void onClickNewAuteur(Frame frame) {
        ///get title from frame.
        String i = null;
        try {
            i = frame.getjDesktopPane().getSelectedFrame().getTitle();
        } catch (NullPointerException e) {

        }
        //If actual frame is new book or modification book then set adding author to true.
        if ((i != null && i.equals("Nouveau Livre")) || ((i != null && i.equals("Modification Livre")))) {
            ((livreForm) frame.getjDesktopPane().getSelectedFrame()).setAddingAuthor(true);
        }

        //Ger new author from from controller
        auteurForm auteurForm  = auteurController.getAuteurform();
        //Set from book to true
        auteurForm.setFromBook(true);
        //add to desktop
        frame.getjDesktopPane().add(auteurForm);
        //set selected
        try {
            auteurForm.setSelected(true);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    //search book : get search panel from controller and add to desktop.
    public void onClickSearchBook(Frame frame) {
        Search bookSearch = searchController.getSearch();
        bookSearch.setTitle("Recherche Livre");
        frame.getjDesktopPane().add(bookSearch);
        try {
            bookSearch.setSelected(true);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }
    //search Copy : get search panel from controller and add to desktop.
    public void onClickSearchExemplaire(Frame frame) {

        Search examplaireSearch = searchController.getSearch();

        examplaireSearch.setTitle("Recherche Exemplaire");
        frame.getjDesktopPane().add(examplaireSearch);
        try {
            examplaireSearch.setSelected(true);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }
    //search Author : get search panel from controller and add to desktop.
    public void onClickSearchAuteur(Frame frame) {
        Search auteurSearch = searchController.getSearch();

        auteurSearch.setTitle("Recherche Auteur");
        frame.getjDesktopPane().add(auteurSearch);

        try {
            auteurSearch.setSelected(true);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }


    //search Collection : get search panel from controller and add to desktop.
    public void onClickSearchCollection(Frame frame) {

        Search collectionSearch = searchController.getSearch();

        collectionSearch.setTitle("Recherche Collection");
        frame.getjDesktopPane().add(collectionSearch);
        try {
            collectionSearch.setSelected(true);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    //search Abonne : get search panel from controller and add to desktop.
    public void onClickSearchAbonne(Frame frame) {
        Search abonneSearch = searchController.getSearch();

        abonneSearch.setTitle("Recherche Abonne");
        frame.getjDesktopPane().add(abonneSearch);

        abonneSearch.getButtonLoanbooks().setVisible(true);
        try {
            abonneSearch.setSelected(true);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }



    //utilitarian method : gets all authors from db and adds them to comboBox

    public void addAuthorsToComboBox(JComboBox jcb) {
        List<Auteur> la = null;
        try {
            la = auteurService.findAll();
        } catch (CustomConstraintViolation customConstraintViolation) {
            customConstraintViolation.printStackTrace();
        }
        jcb.removeAllItems();
        //Add authors to combobox, alias and if not set name.
        for (Auteur a : la) {
            System.out.println(a.getAlias());
            if (StringUtils.isEmpty(a.getAlias())) {
                jcb.addItem(a.getNom());

            } else {
                jcb.addItem(a.getAlias());
            }
        }

    }
    //utilitarian method : gets all Collection from db and adds them to comboBox
    public void addCollectionToComboBox(JComboBox jcb) {
        List<Collection> lc = null;
        try {
            lc = collectionService.trouverTous();
        } catch (CustomConstraintViolation customConstraintViolation) {
            customConstraintViolation.printStackTrace();
        }
        jcb.removeAllItems();

        for (Collection coll : lc) {
            jcb.addItem(coll.getNomCollection());
        }


    }


    //Format DataTime to string for user visualization
    public DateTime stringToDate(String date) {
        DateTime dt = null;

        try {
            DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
            dt = formatter.parseDateTime(date + " 00:00:00");
            return dt;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    //On Click cancel : get selected frame and close.
    public void onClickAnnuler(Frame frame) {
        JInternalFrame jif = frame.getjDesktopPane().getSelectedFrame();
        jif.doDefaultCloseAction();
    }

    //on click submit : action based on frame title.
    public void onClickSubmit(Frame frame) {
        //get title.
        String title = frame.getjDesktopPane().getSelectedFrame().getTitle();

        //Get Jinternal Frame that was selected.
        JInternalFrame jif = frame.getjDesktopPane().getSelectedFrame();

        ////////NEW BOOK ///////////////////////////////////////////////
        if (title.equals("Nouveau Livre")) {

            //Cast internalFrame to corresponding sub class to allow use of specific methods
            livreForm lf = ((livreForm) jif);
            //Create new book model
            Livre livre = new Livre();

            //Get authors based on name.
            Auteur auteur = null;
            Auteur auteur2 = null;
            //Try to find authors by alias but if none found retry with name.
            try {
                auteur = auteurService.finAuthorByAlias((String) lf.getAuthor1Input().getSelectedItem());
                if (lf.getAuthor2Input().isEnabled()) {
                    auteur2 = auteurService.finAuthorByAlias((String) lf.getAuthor2Input().getSelectedItem());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (auteur == null) {
                try {
                    auteur = auteurService.finAuthorByName((String) lf.getAuthor1Input().getSelectedItem());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (auteur2 == null && lf.getAuthor2Input().isEnabled()) {
                try {
                    auteur2 = auteurService.finAuthorByName((String) lf.getAuthor2Input().getSelectedItem());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //get empty set from book.
            Set<Auteur> sa = livre.getAuteurs();
            //add authors to set.
            sa.add(auteur);
            if (auteur2 != null) {
                sa.add(auteur2);
            }
            //set the Set to Books
            livre.setAuteurs(sa);
            //Set title and subtitle
            livre.setTitle(lf.getTitleInput().getText());
            livre.setSub_title(lf.getSubtitleInput().getText());
            //Get Collection
            Collection collection = collectionService.findByName((String) lf.getCollectionInput().getSelectedItem());
            //Set collection
            livre.setCollection(collection);
            //Get ISBN
            livre.setISBN(lf.getISBNInput().getText());
            //Validate data
            Set<ConstraintViolation<Livre>> livreViolations = null;
            try {
                livreViolations = livreValidator.validateLivre(livre);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //If no vbiolations found then create book.
            if (livreViolations.isEmpty()) {
                livreService.creer(livre);
            } else {
                showErrorsForLivre(livreViolations, lf);
            }

            ////////NEW Author///////////////////////////////////////////////
        } else if (title.equals("Nouveau Auteur")) {
            //get new Authors Model
            Auteur auteur = new Auteur();//Cast jinternafframe to auteurForm.
            auteurForm auteurForm = (auteurForm) jif;

            //Set data to model.
            auteur.setAlias(auteurForm.getAliasInput().getText());
            auteur.setNom(auteurForm.getNameInput().getText());
            auteur.setPrenom(auteurForm.getfNameInput().getText());

            //Format date.
            DateTime dt = null;
            try {
                DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
                dt = formatter.parseDateTime(auteurForm.getDateInput().getText() + " 00:00:00");
                auteur.setDate_naissance(dt);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //Validate data.
            Set<ConstraintViolation<Auteur>> auteurViolations = auteurValidator.validateAuteur(auteur);
            //if no violatiosn found persist author to db.
            if (auteurViolations.isEmpty()) {
                auteurService.saveAuteur(auteur);
                auteurForm.getLabelErrorsNom().setText("Félicitations, l'Auteur a bien été enregistré");
            } else {
                //else print violations to view
                showErrorsForAuteur(auteurViolations, auteurForm);


            }
            //if the form is ferom the book form : once submitted the value must be added to the book form
            if (auteurForm.isFromBook()) {
                //Get all jinternalframes.
                JInternalFrame[] jifs = frame.getjDesktopPane().getAllFrames();
                //Loop on all frames to gety get the book form whith boolean property isAddingAuthor.
                for (JInternalFrame j : jifs) {
                    try {
                        livreForm livreForm = (livreForm) j;
                        //if property is found add authors to combo box.
                        if (livreForm.isAddingAuthor()) {
                            addAuthorsToComboBox(livreForm.getAuthor1Input());
                            addAuthorsToComboBox(livreForm.getAuthor2Input());
                            //Loop on combo box to find new author and set that value as selected.
                            for (int x = 0; x < livreForm.getAuthor1Input().getItemCount(); x++) {
                                if (livreForm.getAuthor1Input().getItemAt(x) == auteur.getAlias() || livreForm.getAuthor1Input().getItemAt(x) == auteur.getNom()) {
                                    livreForm.getAuthor1Input().setSelectedIndex(x);
                                }
                            }

                            //Same for second author.
                            for (int x = 0; x < livreForm.getAuthor2Input().getItemCount(); x++) {
                                if (livreForm.getAuthor2Input().getItemAt(x) == auteur.getAlias() || livreForm.getAuthor2Input().getItemAt(x) == auteur.getNom()) {
                                    livreForm.getAuthor2Input().setSelectedIndex(x);
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        } else if (title.equals("Nouvelle Collection")) {
            //get collection Model
            Collection collection = new Collection();
            //Get collectionform.
            collectionForm collectionForm = (collectionForm) jif;
            //populate model with date from form.
            collection.setNomCollection(collectionForm.getCollectionName().getText());
            //Validate data
            collectionValidator.validateCollection(collection);
            Set<ConstraintViolation<Collection>> collectionViolations = collectionValidator.validateCollection(collection);
            //If no violatiosn persist collection
            if(collectionViolations.isEmpty()){
                collectionService.creer(collection);
            } else {
                //else show violations to user.
                showErrorsForCollection(collectionViolations, collectionForm);
            }
            //If the collection form was called from the book form.
            if (collectionForm.isFromBook()) {

                //Loop on all jinternalframes.
                JInternalFrame[] jifs = frame.getjDesktopPane().getAllFrames();
                for (JInternalFrame j : jifs) {
                    try {
                        //Try to cast jinternalframe to check for boolean.
                        livreForm livreForm = (livreForm) j;
                        //if right jinteralframe found then add collection to combobox.
                        if (livreForm.isAdingCollection()) {
                            addCollectionToComboBox(livreForm.getCollectionInput());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }


        } else if (title.equals("Nouveau Abonne")) {
            //new aboone model and get abonneForm
            Abonne abonne = new Abonne();
            abonneForm abonneForm = (abonneForm) jif;
            //populate model
            abonne.setEmail(abonneForm.getEmailInput().getText());
            abonne.setPrenom(abonneForm.getfNameInput().getText());
            abonne.setNom(abonneForm.getNameInput().getText());
            try {
                abonne.setDate_naissance(stringToDate(abonneForm.getDateInput().getText()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            //generate AbonneNumber
            String noAbonne = abonneService.generateNoAbonne();
            //set value
            abonne.setNoAbonne(noAbonne);
            //Validate data
            Set<ConstraintViolation<Abonne>> abonneViolations = abonneValidator.validateAbonne(abonne);
            //If no violations persist Abonne.
            if (abonneViolations.isEmpty()) {
                abonneService.saveAbonne(abonne);
            } else {
                //show errors
                showErrorsForAbonne(abonneViolations, abonneForm);
            }


        } else if (title.equals("Nouveau Exemplaire")) {
            //get model and form
            Exemplaire exemplaire = new Exemplaire();
            exemplaireForm exemplaireForm = (exemplaireForm) jif;
            //Get title and book from title
            String livreTitle = exemplaireForm.getBookSelect().getSelectedItem().toString();
            Livre livre = livreService.trouverParTitre(livreTitle);
            //set book to copy model ans set available to true
            exemplaire.setLivre(livre);
            exemplaire.setDisponible(true);
            //No need to validate data as copy must have a book selected in the form
            exemplaireService.creer(exemplaire);

        } else if (title.equals("Modification Abonne")) {
            //Get form and get abonne id from jlabel.
            abonneForm abonneForm = (abonneForm) jif;
            String noAbonne = abonneForm.getLabelForNoAbonne().getText().substring(16);
            //Get abonne from service
            Abonne abonne = abonneService.findAbonneByNoAbonne(noAbonne);
            //populate model
            abonne.setEmail(abonneForm.getEmailInput().getText());
            abonne.setPrenom(abonneForm.getfNameInput().getText());
            abonne.setNom(abonneForm.getNameInput().getText());
            abonne.setNoAbonne(noAbonne);


            try {
                abonne.setDate_naissance(stringToDate(abonneForm.getDateInput().getText()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            //Validate Data
            abonneValidator abonneValidator = new abonneValidator();
            Set<ConstraintViolation<Abonne>> abonneViolations = abonneValidator.validateAbonne(abonne);
            //If no errors update
            if (abonneViolations.isEmpty()) {
                abonneService.updateAbonne(abonne);
            } else {
                showErrorsForAbonne(abonneViolations, abonneForm);
            }

        } else if (title.equals("Modification Livre")) {
            //count for authors
            int nbAuthors = 0;
            //Get from
            livreForm livreForm = (livreForm) jif;
            //Get author and find by alias, if not set then by name
            String s = livreForm.getAuthor1Input().getSelectedItem().toString();
            Auteur auteur1 = auteurService.finAuthorByAlias(s);
            if (auteur1 == null) {
                auteur1 = auteurService.finAuthorByAlias(livreForm.getAuthor1Input().getSelectedItem().toString());
            }
            //Same form author 2 if he is set.
            Auteur auteur2 = null;
            if (livreForm.getAuthor2Input().isEnabled()) {
                auteur2 = auteurService.finAuthorByAlias(livreForm.getAuthor2Input().getSelectedItem().toString());
            }
            if (auteur2 == null) {
                auteur2 = auteurService.finAuthorByAlias(livreForm.getAuthor2Input().getSelectedItem().toString());
            }

            //Get Model
            Livre livre = new Livre();
            //Populate Model
            livre.setISBN(livreForm.getISBNInput().getText());
            //get Set from model
            Set<Auteur> sa = livre.getAuteurs();
            sa.removeAll(sa);
            sa.add(auteur1);
            sa.add(auteur2);
            livre.setAuteurs(sa);
            livre.setTitle(livreForm.getTitleInput().getText());
            livre.setSub_title(livreForm.getSubtitleInput().getText());
            Collection coll = collectionService.findByName(livreForm.getCollectionInput().getSelectedItem().toString());
            livre.setCollection(coll);

            Set<ConstraintViolation<Livre>> livreViolations = livreValidator.validateLivre(livre);
            if(livreViolations.isEmpty()){
                livreService.updateLivre(livre);
            }else{
                showErrorsForLivre(livreViolations, livreForm);
            }
        } else if (title.equals("Modification Auteur")) {
            //Cast form.
            auteurForm auteurForm = (auteurForm) jif;
            //Get id and parse to long
            Long idL = Long.parseLong(auteurForm.getLabelForAuteurId()
                    .getText().substring(12));
            //Get author
            Auteur auteur = auteurService.findById(idL);
            //Populate model
            auteur.setAlias(auteurForm.getAliasInput().getText());
            auteur.setPrenom(auteurForm.getfNameInput().getText());
            auteur.setNom(auteurForm.getNameInput().getText());
            auteur.setDate_naissance(stringToDate(auteurForm.getDateInput().getText()));
            Set<ConstraintViolation<Auteur>> auteurViolations = auteurValidator.validateAuteur(auteur);
            if(auteurViolations.isEmpty()){
                auteurService.updateAuteur(auteur);
            }else{
                showErrorsForAuteur(auteurViolations, auteurForm);
            }

        } else if (title.equals("Modification Collection")) {
            //Get collection form
            collectionForm collectionForm = (collectionForm) jif;
            //get collection id.
            String id = (collectionForm.getLabelForIdCollection().getText().substring(13));
            //Get collection by id.
            Collection collection = collectionService.findById(id);
            //set collection name to model.
            collection.setNomCollection(collectionForm.getCollectionName().getText());
            Set<ConstraintViolation<Collection>> collectionViolations = collectionValidator.validateCollection(collection);
            if(collectionViolations.isEmpty()){
                collectionService.updateCollection(collection);
            }else{
                showErrorsForCollection(collectionViolations, collectionForm);
            }


        }
    }

    public void onClickAddAuthorCheckBox(Frame frame, ItemEvent e) {
        if (frame.getjDesktopPane().getSelectedFrame().getTitle().equals("Nouveau Livre") || (frame.getjDesktopPane().getSelectedFrame().getTitle().equals("Modification Livre"))) {

            if (e.getStateChange() == ItemEvent.SELECTED) {
                ((livreForm) frame.getjDesktopPane().getSelectedFrame()).getAuthor2Input().setEnabled(true);
                ((livreForm) frame.getjDesktopPane().getSelectedFrame()).getCreateAuthor2().setEnabled(true);
            } else {
                ((livreForm) frame.getjDesktopPane().getSelectedFrame()).getAuthor2Input().setEnabled(false);
                ((livreForm) frame.getjDesktopPane().getSelectedFrame()).getCreateAuthor2().setEnabled(false);
            }
        }
    }


    //all show errors method work the same.
    //Loop on violation Set if property path corresponds to a property, set message to corresponding label in view.

    public void showErrorsForAbonne(Set<ConstraintViolation<Abonne>> abonneViolations, abonneForm abonneForm){
        for (ConstraintViolation cv : abonneViolations) {
            System.out.println(cv.getPropertyPath().toString());
            if (cv.getPropertyPath().toString().equals("date_naissance")) {
                abonneForm.getLabelForErrorsDate().setText(cv.getMessage());
            }
            if (cv.getPropertyPath().toString().equals("nom")) {
                abonneForm.getLabelForErrorsNom().setText(cv.getMessage());
            }
            if (cv.getPropertyPath().toString().equals("prenom")) {
                abonneForm.getLabelForErrosPrenom().setText(cv.getMessage());
            }
            if (cv.getPropertyPath().toString().equals("email")) {
                abonneForm.getLabelForErrorsEmail().setText(cv.getMessage());
            }
        }
    }

    public void showErrorsForLivre(Set<ConstraintViolation<Livre>> livreViolations, livreForm livreForm){

        for (ConstraintViolation<Livre> la : livreViolations) {
            if (la.getPropertyPath().toString().equals("title")) {
                livreForm.getLabeForErrorsTitle().setText(la.getMessage());
            }
            if (la.getPropertyPath().toString().equals("ISBN")) {
                livreForm.getLabelForErrorsISBN().setText(la.getMessage());
            }
        }
    }
    public void showErrorsForAuteur( Set<ConstraintViolation<Auteur>> auteurViolations, auteurForm auteurForm){
        for (ConstraintViolation<Auteur> ca : auteurViolations) {
            System.out.println(ca.getPropertyPath());
            if (ca.getPropertyPath().toString().equals("nom")) {
                auteurForm.getLabelErrorsNom().setText(ca.getMessage());
            }
            if (ca.getPropertyPath().toString().equals("prenom")) {
                auteurForm.getLabelErrorsPrenom().setText(ca.getMessage());
            }
            if (ca.getPropertyPath().toString().equals("date_naissance")) {
                auteurForm.getLabelErrorsDate().setText(ca.getMessage());
            }
            if (ca.getPropertyPath().toString().equals("alias")) {
                auteurForm.getLabelErrorsAlias().setText(ca.getMessage());
            }
        }
    }

    public void showErrorsForCollection(Set<ConstraintViolation<Collection>> collectionViolations, collectionForm collectionForm){
        for (ConstraintViolation cv : collectionViolations) {
            System.out.println(cv.getPropertyPath().toString());
            if (cv.getPropertyPath().toString().equals("nomCollection")) {
                collectionForm.getLabelForErrosNom().setText(cv.getMessage());
            }
        }
    }

}
