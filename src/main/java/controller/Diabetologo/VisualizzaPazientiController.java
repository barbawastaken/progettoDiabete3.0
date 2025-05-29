package controller.Diabetologo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Amministratore.Paziente;
import java.sql.DriverManager;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class VisualizzaPazientiController implements Initializable {

    private String taxCode;

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
    @FXML private TableColumn<Paziente, String> roleColumn;
    @FXML private TableColumn<Paziente, String> weightColumn;
    @FXML private TableColumn<Paziente, String> heightColumn;

    private ObservableList<Paziente> pazienti = FXCollections.observableArrayList();

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
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
        heightColumn.setCellValueFactory(new PropertyValueFactory<>("height"));

        loadFromDatabase();
        tabella.setItems(pazienti);
    }

    private void loadFromDatabase() {
        String query = "SELECT * FROM utenti WHERE diabetologo=?";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:mydatabase.db?busy_timeout=5000");
             PreparedStatement stmt = conn.prepareStatement(query);
             //stmt.setString();
             ResultSet rs = stmt.executeQuery(query))
        {

            while (rs.next()) {
                Paziente p = new Paziente(
                        rs.getString("codiceFiscale"),
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("sesso"),
                        rs.getString("dataNascita"),
                        rs.getString("password"),
                        rs.getString("via"),
                        rs.getString("numeroCivico"),
                        rs.getString("cap"),
                        rs.getString("Paese di residenza"),
                        rs.getString("citta"),
                        rs.getString("email"),
                        rs.getString("cellulare"),
                        rs.getString("ruolo"),
                        rs.getDouble("Peso"),
                        rs.getDouble("Altezza")
                );
                pazienti.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }
}
