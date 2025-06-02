package controller.Diabetologo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import model.Amministratore.Paziente;
import model.Diabetologo.VisualizzaPazientiModel;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class VisualizzaPazientiController implements Initializable {

    private String taxCode;
    private final VisualizzaPazientiModel visualizzaPazientiModel = new VisualizzaPazientiModel();

    @FXML private TableView<Paziente> tabella;
    @FXML private TableColumn<Paziente, String> taxCodeColumn;
    @FXML private TableColumn<Paziente, String> nomeColumn;
    @FXML private TableColumn<Paziente, String> cognomeColumn;
    @FXML private TableColumn<Paziente, String> genderColumn;
    @FXML private TableColumn<Paziente, String> birthdayColumn;
    @FXML private TableColumn<Paziente, String> passwordColumn;
    @FXML private TableColumn<Paziente, String> addressColumn;
    @FXML private TableColumn<Paziente, String> numberColumn;
    @FXML private TableColumn<Paziente, String> capColumn;
    @FXML private TableColumn<Paziente, String> countryOfResidenceColumn;
    @FXML private TableColumn<Paziente, String> cityColumn;
    @FXML private TableColumn<Paziente, String> emailColumn;
    @FXML private TableColumn<Paziente, String> telephoneColumn;
    @FXML private TableColumn<Paziente, String> weightColumn;
    @FXML private TableColumn<Paziente, String> heightColumn;

    private final ObservableList<Paziente> pazienti = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        taxCodeColumn.setCellValueFactory(new PropertyValueFactory<>("taxCode"));
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        cognomeColumn.setCellValueFactory(new PropertyValueFactory<>("cognome"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        birthdayColumn.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        capColumn.setCellValueFactory(new PropertyValueFactory<>("cap"));
        countryOfResidenceColumn.setCellValueFactory(new PropertyValueFactory<>("countryOfResidence"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        telephoneColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
        heightColumn.setCellValueFactory(new PropertyValueFactory<>("height"));
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
        loadPazienti();
    }

    private void loadPazienti() {
        pazienti.setAll(visualizzaPazientiModel.getPazientiByDiabetologo(taxCode));
        tabella.setItems(pazienti);
    }

    @FXML
    public void handleHomepageButton(javafx.event.ActionEvent event) {
        // Prendi la finestra (stage) corrente dal bottone stesso:
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();

    }
}
