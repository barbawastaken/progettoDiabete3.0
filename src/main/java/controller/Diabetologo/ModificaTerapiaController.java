package controller.Diabetologo;

import controller.*;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import model.Diabetologo.ModificaTerapiaModel;
import model.Diabetologo.Terapia;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ModificaTerapiaController {

    private final String taxCode = Session.getInstance().getPazienteInEsame().getTaxCode();

    private final static String DB_URL = "jdbc:sqlite:mydatabase.db";
    @FXML private HBox navbarContainer;

    @FXML private TextField terapiaField;
    @FXML private TextField farmacoField;
    @FXML private TextField quantitaField;
    @FXML private TextField frequenzaField;
    @FXML private TextField indicazioniField;

    @FXML
    private void initialize() {

        NavBar navbar = new NavBar(NavBarTags.DIABETOLOGO_operazioneRitornoTabellaTerapie);
        navbar.prefWidthProperty().bind(navbarContainer.widthProperty());
        navbarContainer.getChildren().add(navbar);

        Terapia terapia = Session.getInstance().getTerapiaInEsame();

        String query = "SELECT taxCode, terapia, farmaco_prescritto, quantita, numero_assunzioni_giornaliere, indicazioni FROM terapiePrescritte WHERE taxCode = ? AND terapia = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, taxCode);
            stmt.setString(2, terapia.getTerapia());

            ResultSet rs = stmt.executeQuery();

            this.terapiaField.setText(rs.getString("terapia"));

            this.farmacoField.setText(rs.getString("farmaco_prescritto"));

            this.quantitaField.setText(rs.getString("quantita"));

            this.frequenzaField.setText(rs.getString("numero_assunzioni_giornaliere"));

            this.indicazioniField.setText(rs.getString("indicazioni"));
        } catch(Exception e) {
            System.out.println("Errore caricamento dati utente: " + e);
        }
    }

   @FXML
    private void onSendButtonPressed() {

       String terapia = terapiaField.getText();
       String farmaco = farmacoField.getText();
       String quantita = quantitaField.getText();
       int frequenza = Integer.parseInt(frequenzaField.getText());
       String indicazioni = indicazioniField.getText();

        if(terapia.isEmpty() || farmaco.isEmpty()) return;

       ModificaTerapiaModel model = new ModificaTerapiaModel();
       model.updateData(taxCode, terapia, farmaco, quantita, frequenza, indicazioni);

       ViewNavigator.navigateToDiabetologo();
   }

    @FXML
    private void handleReset() {

        terapiaField.clear();
        farmacoField.clear();
        quantitaField.clear();
        frequenzaField.clear();
        indicazioniField.clear();
    }
}
