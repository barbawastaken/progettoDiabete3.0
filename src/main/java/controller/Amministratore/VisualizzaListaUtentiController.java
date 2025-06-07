package controller.Amministratore;

import controller.Diabetologo.DiabetologoController;
import controller.Paziente.PazienteController;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.Amministratore.AmministratoreModel;
import model.Amministratore.ModificaUtenteModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Amministratore.Utente;
import model.Amministratore.VisualizzaListaUtentiModel;
import view.Amministratore.AmministratoreView;
import view.Amministratore.ModificaUtenteView;
import view.Amministratore.VisualizzaListaUtentiView;

import java.io.IOException;
import java.util.List;

public class VisualizzaListaUtentiController {

    private Utente selectedUtente;

    @FXML
    private TableView<Utente> tabella;

    @FXML
    private TableColumn<Utente, String> nomeColumn;

    @FXML
    private TableColumn<Utente, String> cognomeColumn;

    @FXML
    private TableColumn<Utente, String> emailColumn;

    @FXML
    private TableColumn<Utente, String> telephoneColumn;

    @FXML
    private TableColumn<Utente, String> taxCodeColumn;
    @FXML
    private TableColumn<Utente, String> addressColumn;
    @FXML
    private TableColumn<Utente, String> cityColumn;

    @FXML
    private TableColumn<Utente, String> capColumn;

    @FXML
    private TableColumn<Utente, String> roleColumn;

    @FXML
    private TableColumn<Utente, String> numberColumn;

    private  VisualizzaListaUtentiModel model;
    private  VisualizzaListaUtentiView view;
    private Stage listaUtentiStage;

    @FXML
    private TableColumn<Utente, String> diabetologoColumn;

    @FXML
    private TableColumn<Utente, String> genderColumn;

    @FXML
    private TableColumn<Utente, String> passwordColumn;

    @FXML
    private TableColumn<Utente, String> birthdayColumn;

    @FXML
    private void initialize() {
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        cognomeColumn.setCellValueFactory(new PropertyValueFactory<>("cognome"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        telephoneColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        taxCodeColumn.setCellValueFactory(new PropertyValueFactory<>("taxCode"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        capColumn.setCellValueFactory(new PropertyValueFactory<>("cap"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        diabetologoColumn.setCellValueFactory(new PropertyValueFactory<>("diabetologo"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        birthdayColumn.setCellValueFactory(new PropertyValueFactory<>("birthday"));


        VisualizzaListaUtentiModel model = new VisualizzaListaUtentiModel();
        List<Utente> utenti = model.getTuttiGliUtenti();
        //this.caricaUtenti(utenti);
        tabella.setItems(FXCollections.observableList(utenti));

        tabella.setRowFactory(utenteTableView -> {
            TableRow<Utente> row = new TableRow<>();
            ContextMenu contextMenu = new ContextMenu();

            //impostiamo un modifica utente

            MenuItem modificaItem = new MenuItem("Modifica utente");
            modificaItem.setOnAction(event -> {
                Utente selected = row.getItem();
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlView/modifica_utente_view.fxml"));
                    Parent root = loader.load();

                    ModificaUtenteController controller = loader.getController();
                    controller.initializeData(VisualizzaListaUtentiController.this, selected, model, new ModificaUtenteModel(), (Stage)tabella.getScene().getWindow());


                    Stage modificaStage = new Stage();
                    modificaStage.setTitle("Modifica Utente");
                    modificaStage.setScene(new Scene(root, 650, 500));
                    modificaStage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, "Errore nell'apertura della finestra di modifica.").showAndWait();
                }
        });



            // impostiamo il delete :)
            MenuItem eliminaItem = new MenuItem("Elimina utente");
            eliminaItem.setOnAction(event -> {
                Utente selected = row.getItem();
                if (selected != null) {
                    model.rimuoviUtente(selected); // Aggiorna anche il DB
                    tabella.getItems().remove(selected); // Rimuovi dalla tabella
                }
            });
            contextMenu.getItems().addAll(modificaItem, eliminaItem);

            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(contextMenu)
            );


            return row;
        });


    }

        @FXML
        private void onEliminaClicked() {
        model.rimuoviUtente(selectedUtente);
        }

        @FXML
        private void onModificaClicked() {


        }

    public VisualizzaListaUtentiController(VisualizzaListaUtentiModel model, VisualizzaListaUtentiView view, Stage stage)
        {
            this.model = model;
            this.view = view;
            this.listaUtentiStage = stage;
            view.start(stage, this); // chiama view.start e caricaUtent
    }

    public VisualizzaListaUtentiController() {}

/*
    public void caricaUtenti(List<Utente> utenti) {
        VisualizzaListaUtentiModel model = new VisualizzaListaUtentiModel();
        ObservableList<Utente> listaOsservabile = FXCollections.observableArrayList(model.getTuttiGliUtenti());
        tabella.setItems(listaOsservabile);

        //Aggiungo un Context Menu per ogni riga presente nella tabella
        //COMMENTATO: non serve più dopo inserimento fxml
*/
        /*
        tabella.setRowFactory(utenteTableView -> {
            TableRow<Utente> row = new TableRow<>();
            ContextMenu contextMenu = new ContextMenu();

            //impostiamo un modifica utente
            MenuItem modificaItem = new MenuItem("Modifica utente");
            modificaItem.setOnAction(event -> {
                Utente selected = row.getItem();
                //apriFinestraModifica(selected);

                ModificaUtenteModel modificaUtenteModel = new ModificaUtenteModel();
                ModificaUtenteView modificaUtenteView = new ModificaUtenteView(selected);
                new ModificaUtenteController(modificaUtenteModel, modificaUtenteView, selected, listaUtentiStage);
            });



            // impostiamo il delete :)
            MenuItem eliminaItem = new MenuItem("Elimina utente");
            eliminaItem.setOnAction(event -> {
                Utente selected = row.getItem();
                if (selected != null) {
                    model.rimuoviUtente(selected); // Aggiorna anche il DB
                    tabella.getItems().remove(selected); // Rimuovi dalla tabella
                }
            });
            contextMenu.getItems().addAll(modificaItem, eliminaItem);

            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(contextMenu)
            );


            return row;
        });

         */

        //imposto indietro button
        /*
        view.getIndietroButton().setOnAction(e -> {
            listaUtentiStage.close();

            AmministratoreModel amministratoreModel = new AmministratoreModel();
            AmministratoreView amministratoreView = new AmministratoreView();


            try {
                new AmministratoreController(amministratoreModel, amministratoreView, view.getStage());
                // Nessun .start() qui, lo fa già il controller

            } catch (Exception ex) {
                System.out.println("Errore: " + ex.getMessage());
                ex.printStackTrace(); // Aggiungo stack trace per debug
            }




        });



    }


    private void apriFinestraModifica(Utente utente) {
        ModificaUtenteView modificaView = new ModificaUtenteView(utente);
        ModificaUtenteController modificaController = new ModificaUtenteController(modificaView, utente, model, listaUtentiStage);
        Stage modificaStage = new Stage();
        modificaView.start(modificaStage, utente, modificaController);
        // Quando la finestra viene chiusa, ricarica la tabella
        //modificaStage.setOnHiding(e -> caricaUtenti(view.getTabellaUtenti())); //ricarica la tabella

        modificaView.getSalvaButton().setOnAction(e -> modificaController.salvaModifiche(listaUtentiStage));

    }

    public void start(Stage stage) {
        view.start(stage, this); // chiama la view, passandole anche il controller
    }
*/
}