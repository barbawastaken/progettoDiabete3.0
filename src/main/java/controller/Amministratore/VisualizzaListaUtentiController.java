package controller.Amministratore;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Amministratore.Utente;
import model.Amministratore.VisualizzaListaUtentiModel;

import java.util.List;

public class VisualizzaListaUtentiController {


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

    @FXML
    private TableColumn<Utente, String> diabetologoColumn;

    @FXML
    private TableColumn<Utente, String> genderColumn;

    @FXML
    private TableColumn<Utente, String> passwordColumn;

    @FXML
    private TableColumn<Utente, String> birthdayColumn;

    @FXML
    private void initialize(){
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
        this.caricaUtenti(utenti);
        tabella.setItems(FXCollections.observableList(utenti));

    }

    public VisualizzaListaUtentiController() {}


    private void caricaUtenti(List<Utente> utenti) {
        VisualizzaListaUtentiModel model = new VisualizzaListaUtentiModel();
        ObservableList<Utente> listaOsservabile = FXCollections.observableArrayList(model.getTuttiGliUtenti());
        tabella.setItems(listaOsservabile);

        //Aggiungo un Context Menu per ogni riga presente nella tabella
        tabella.setRowFactory(utenteTableView -> {
            TableRow<Utente> row = new TableRow<>();
            ContextMenu contextMenu = new ContextMenu();
            //impostiamo un modifica utente
            MenuItem modificaItem = new MenuItem("Modifica utente");
            modificaItem.setOnAction(event -> {
                Utente selected = row.getItem();
                apriFinestraModifica(selected);
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
    private void apriFinestraModifica(Utente utente) {
        System.out.println("Modifica utente: " + utente.getTaxCode());
        // TODO: Qui puoi aprire una nuova finestra o dialog con i dati dell'utente
    }
}