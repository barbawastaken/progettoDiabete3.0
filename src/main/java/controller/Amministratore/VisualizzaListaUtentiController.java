package controller.Amministratore;

import controller.NavBar;
import controller.NavBarTags;
import controller.Session;
import controller.ViewNavigator;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Amministratore.ModificaUtenteModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Amministratore.Utente;
import model.Amministratore.VisualizzaListaUtentiModel;
import view.Amministratore.VisualizzaListaUtentiView;
import java.io.IOException;
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
    @FXML private TableView<VisualizzaListaUtentiController> tableViewUtenti;
    @FXML private HBox navbarContainer;

    private  VisualizzaListaUtentiModel model;
    private  VisualizzaListaUtentiView view;
    private Stage listaUtentiStage;

    public VisualizzaListaUtentiController(VisualizzaListaUtentiModel model, VisualizzaListaUtentiView view, Stage stage){


        this.model = model;
        this.view = view;
        this.listaUtentiStage = stage;
        view.start(stage, this);


    }
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

                    /*
                    try {





                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlView/modifica_utente_view.fxml"));
                        Parent root = loader.load();

                        ModificaUtenteController controller = loader.getController();
                        controller.initializeData();

                        Stage modificaStage = new Stage();
                        modificaStage.setTitle("Modifica Utente");
                        modificaStage.setScene(new Scene(root, 650, 500));
                        modificaStage.show();



                    } catch (IOException e) {
                        e.printStackTrace();
                        new Alert(Alert.AlertType.ERROR, "Errore nell'apertura della finestra di modifica.").showAndWait();
                    }

                     */
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

    //Nome evidenziato
    public void evidenziaUtente(String taxCode) {
        for (Utente u : tabella.getItems()) {
            if (u.getTaxCode().equalsIgnoreCase(taxCode)) {
                tabella.getSelectionModel().select(u);
                tabella.scrollTo(u);
                break;
            }
        }
    }

}