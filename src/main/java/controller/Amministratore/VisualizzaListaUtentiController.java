package controller.Amministratore;

import controller.NavBar;
import controller.NavBarTags;
import controller.Session;
import controller.ViewNavigator;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Amministratore.Utente;
import model.Amministratore.VisualizzaListaUtentiModel;
import java.util.List;

public class VisualizzaListaUtentiController {
    @FXML private TableView<Utente> tabella;
    @FXML private TableColumn<Utente, String> nomeColumn;
    @FXML private TableColumn<Utente, String> cognomeColumn;
    @FXML private TableColumn<Utente, String> emailColumn;
    @FXML private TableColumn<Utente, String> telephoneColumn;
    @FXML private TableColumn<Utente, String> taxCodeColumn;
    @FXML private TableColumn<Utente, String> addressColumn;
    @FXML private TableColumn<Utente, String> cityColumn;
    @FXML private TableColumn<Utente, String> capColumn;
    @FXML private TableColumn<Utente, String> roleColumn;
    @FXML private TableColumn<Utente, String> numberColumn;
    @FXML private TableColumn<Utente, String> diabetologoColumn;
    @FXML private TableColumn<Utente, String> genderColumn;
    @FXML private TableColumn<Utente, String> passwordColumn;
    @FXML private TableColumn<Utente, String> birthdayColumn;
    @FXML private TableColumn<Utente, String> countryOfResidenceColumn;
    @FXML private TableColumn<Utente, String> altezzaColumn;
    @FXML private TableColumn<Utente, String> pesoColumn;
    @FXML private HBox navbarContainer;

    private  VisualizzaListaUtentiModel model = new VisualizzaListaUtentiModel();

    public VisualizzaListaUtentiController(){}

    @FXML
    private void initialize() {

        NavBar navbar = new NavBar(NavBarTags.AMMINISTRATORE_toHomepage);
        navbar.prefWidthProperty().bind(navbarContainer.widthProperty());
        navbarContainer.getChildren().add(navbar);

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
        countryOfResidenceColumn.setCellValueFactory(new PropertyValueFactory<>("countryOfResidence"));
        altezzaColumn.setCellValueFactory(new PropertyValueFactory<>("height"));
        pesoColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));

        nomeColumn.prefWidthProperty().bind(tabella.widthProperty().multiply(0.05));
        cognomeColumn.prefWidthProperty().bind(tabella.widthProperty().multiply(0.05));
        emailColumn.prefWidthProperty().bind(tabella.widthProperty().multiply(0.1));
        telephoneColumn.prefWidthProperty().bind(tabella.widthProperty().multiply(0.09));
        taxCodeColumn.prefWidthProperty().bind(tabella.widthProperty().multiply(0.05));
        addressColumn.prefWidthProperty().bind(tabella.widthProperty().multiply(0.1));
        cityColumn.prefWidthProperty().bind(tabella.widthProperty().multiply(0.05));
        capColumn.prefWidthProperty().bind(tabella.widthProperty().multiply(0.05));
        roleColumn.prefWidthProperty().bind(tabella.widthProperty().multiply(0.05));
        numberColumn.prefWidthProperty().bind(tabella.widthProperty().multiply(0.05));
        diabetologoColumn.prefWidthProperty().bind(tabella.widthProperty().multiply(0.05));
        genderColumn.prefWidthProperty().bind(tabella.widthProperty().multiply(0.05));
        passwordColumn.prefWidthProperty().bind(tabella.widthProperty().multiply(0.05));
        birthdayColumn.prefWidthProperty().bind(tabella.widthProperty().multiply(0.05));
        countryOfResidenceColumn.prefWidthProperty().bind(tabella.widthProperty().multiply(0.05));
        altezzaColumn.prefWidthProperty().bind(tabella.widthProperty().multiply(0.05));
        pesoColumn.prefWidthProperty().bind(tabella.widthProperty().multiply(0.05));

        if(this.model == null) { this.model = new VisualizzaListaUtentiModel(); }

        List<Utente> utenti = this.model.getTuttiGliUtenti();
        tabella.setItems(FXCollections.observableList(utenti));

        tabella.setRowFactory(utenteTableView -> {
            TableRow<Utente> row = new TableRow<>();
            ContextMenu contextMenu = new ContextMenu();

            MenuItem modificaItem = new MenuItem("Modifica utente");
            modificaItem.setOnAction(event -> {
                Utente selected = row.getItem();
                if (selected != null) {

                    Session.getInstance().setUtenteInEsame(selected);
                    ViewNavigator.navigateToModificaUtente();

                }
            });

            MenuItem eliminaItem = new MenuItem("Elimina utente");
            eliminaItem.setOnAction(event -> {
                Utente selected = row.getItem();
                if (selected != null) {
                    this.model.rimuoviUtente(selected); // Aggiorna DB
                    tabella.getItems().remove(selected); // Aggiorna vista
                }
            });

            contextMenu.getItems().addAll(modificaItem, eliminaItem);
            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty()).then((ContextMenu) null).otherwise(contextMenu)
            );
            return row;
        });
    }
    public void aggiornaTabellaUtenti() {
        List<Utente> utentiAggiornati = model.getTuttiGliUtenti();
        tabella.setItems(FXCollections.observableList(utentiAggiornati));
    }

}