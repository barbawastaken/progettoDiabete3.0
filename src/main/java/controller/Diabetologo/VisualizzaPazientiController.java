package controller.Diabetologo;

import controller.NavBar;
import controller.NavBarTags;
import controller.Session;
import controller.ViewNavigator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;
import model.Amministratore.Paziente;
import model.Diabetologo.VisualizzaPazientiModel;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class VisualizzaPazientiController implements Initializable {

    private final VisualizzaPazientiModel visualizzaPazientiModel = new VisualizzaPazientiModel();
    @FXML private HBox navbarContainer;
    @FXML private TableView<Paziente> tabella;
    @FXML private TableColumn<Paziente, String> taxCodeColumn;
    @FXML private TableColumn<Paziente, String> nomeColumn;
    @FXML private TableColumn<Paziente, String> cognomeColumn;
    @FXML private TableColumn<Paziente, String> genderColumn;
    @FXML private TableColumn<Paziente, String> birthdayColumn;
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

        NavBar navbar = new NavBar(NavBarTags.DIABETOLOGO_toHomepage);
        navbar.prefWidthProperty().bind(navbarContainer.widthProperty());
        navbarContainer.getChildren().add(navbar);

        loadPazienti();

        taxCodeColumn.setCellValueFactory(new PropertyValueFactory<>("taxCode"));
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        cognomeColumn.setCellValueFactory(new PropertyValueFactory<>("cognome"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        birthdayColumn.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        capColumn.setCellValueFactory(new PropertyValueFactory<>("cap"));
        countryOfResidenceColumn.setCellValueFactory(new PropertyValueFactory<>("countryOfResidence"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        telephoneColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
        heightColumn.setCellValueFactory(new PropertyValueFactory<>("height"));
        ArrayList<String> pazientiInRitardo;
        try {
            pazientiInRitardo = new ArrayList<>(visualizzaPazientiModel.getPazientiInRitardo());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        tabella.setRowFactory(tv -> {

           TableRow<Paziente> row = new TableRow<>(){
               @Override
               protected void updateItem(Paziente item, boolean empty) {
                   super.updateItem(item, empty);

                   if(item == null || empty) {
                       setStyle("");
                   } else if(pazientiInRitardo.contains(item.getTaxCode())) {
                       setStyle("-fx-background-color: #f08080");
                   }
               }
           };

            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) { // doppio clic

                    Session.getInstance().setPazienteInEsame(row.getItem());
                    ViewNavigator.navigateToPatientDetails();

                }
            });
            return row;
        });
    }

    private void loadPazienti() {
        pazienti.setAll(visualizzaPazientiModel.getPazientiByDiabetologo(Session.getInstance().getTaxCode()));
        tabella.setItems(pazienti);
        System.out.println("da load pazienti: " + pazienti);
    }

}
