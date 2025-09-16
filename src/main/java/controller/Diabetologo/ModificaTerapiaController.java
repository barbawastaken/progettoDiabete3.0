package controller.Diabetologo;

import controller.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import model.Diabetologo.ModificaTerapiaModel;
import model.Diabetologo.Terapia;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModificaTerapiaController {

    private final String taxCode = Session.getInstance().getPazienteInEsame().getTaxCode();

    private final static String DB_URL = "jdbc:sqlite:mydatabase.db";
    @FXML private HBox navbarContainer;
    private String schermataDiArrivo;

    @FXML private TextField terapiaField;
    @FXML private TextField farmacoField;
    @FXML private TextField quantitaField;
    @FXML private TextField frequenzaField;
    @FXML private TextField indicazioniField;

    private String farmacoOriginale;

    @FXML
    private void initialize() {

        schermataDiArrivo = Session.getInstance().getSchermataDiArrivo();
        NavBar navbar;

        if(schermataDiArrivo != null && schermataDiArrivo.equals("STATISTICHE")){
            navbar = new NavBar(NavBarTags.DIABETOLOGO_ritornoVisualizzaStatistiche);
        } else if(schermataDiArrivo != null && schermataDiArrivo.equals("TABELLA_TERAPIE")) {
            navbar = new NavBar(NavBarTags.DIABETOLOGO_operazioneRitornoTabellaTerapie);
        } else {
            navbar = new NavBar(NavBarTags.DIABETOLOGO_fromNotifiche);
        }

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
            farmacoOriginale = rs.getString("farmaco_prescritto");

            this.quantitaField.setText(rs.getString("quantita"));

            this.frequenzaField.setText(rs.getString("numero_assunzioni_giornaliere"));

            this.indicazioniField.setText(rs.getString("indicazioni"));
        } catch(Exception e) {
            System.out.println("Errore caricamento dati utente: " + e);
        }

        frequenzaField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("\\d")) {
                event.consume(); // blocca l'inserimento
            }
        });
    }

   @FXML
    private void onSendButtonPressed() {

       if (terapiaField.getText().isEmpty() || farmacoField.getText().isEmpty() || quantitaField.getText().isEmpty() || frequenzaField.getText().isEmpty()) {
           mostraErrore("Tutti i campi obbligatori devono essere compilati.");
           return;
       }

       String terapia = terapiaField.getText();
       String farmaco = farmacoField.getText();
       String quantita = quantitaField.getText();
       String indicazioni = indicazioniField.getText();

       int frequenza;

       try {
           frequenza = Integer.parseInt(frequenzaField.getText());
       } catch (NumberFormatException e) {
           System.out.println("Errore nella lettura del numero di assunzioni giornaliere: " + e.getMessage());
           mostraErrore("Il numero di assunzioni giornaliere non è valido");
           frequenzaField.clear();
           return;
       }

       Pattern pattern = Pattern.compile("[-+]?\\d+");
       Matcher matcher = pattern.matcher(quantita);

       if (matcher.find()) {
           int quantitaInt = Integer.parseInt(matcher.group());

           if(quantitaInt < 0){
               mostraErrore("La quantità inserita non è valida!");
               quantitaField.clear();
               return;
           }

       }

       ModificaTerapiaModel model = new ModificaTerapiaModel();
       int result = model.updateData(taxCode, terapia, farmaco, quantita, frequenza, indicazioni, farmacoOriginale);

       if(result == 0){
           schermataDiArrivo = Session.getInstance().getSchermataDiArrivo();

           if(schermataDiArrivo != null && schermataDiArrivo.equals("STATISTICHE")) {
               ViewNavigator.navigateToVisualizzaStatistiche();
           } else { ViewNavigator.navigateToPatientDetails(); }
       }
       if(result == -1) { mostraErrore("Non puoi prescrivere due volte lo stesso farmaco!"); farmacoField.clear(); }
       if(result == -2) { mostraErrore("Errore nell'interazione col database!"); ViewNavigator.navigateToDiabetologo(); }


   }

    @FXML
    private void handleReset() {

        terapiaField.clear();
        farmacoField.clear();
        quantitaField.clear();
        frequenzaField.clear();
        indicazioniField.clear();
    }

    private void mostraErrore(String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }
}
