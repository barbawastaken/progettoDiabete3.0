package controller.Diabetologo;

import controller.LoginController;
import controller.Paziente.PazienteController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Amministratore.Paziente;
import model.Diabetologo.ModificaTerapiaModel;
import model.Diabetologo.Terapia;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ModificaTerapiaController {

    private String taxCode;
    private Paziente paziente;
    private Terapia terapia;
    private String taxCodeDiabetologo;

    private final static String DB_URL = "jdbc:sqlite:mydatabase.db";
    @FXML private HBox topBar;

    @FXML private TextField terapiaField;
    @FXML private TextField farmacoField;
    @FXML private TextField quantitaField;
    @FXML private TextField frequenzaField;
    @FXML private TextField indicazioniField;

    public void setTaxCode(String taxCode) { this.taxCode = taxCode; inizialize();}

    public void inizialize() {

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
   private void onLogoutPressed() {

        try {
            Stage loginStage = new Stage();
            loginStage.setTitle("Login");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlView/login_view.fxml"));
            Parent root = loader.load();
            LoginController loginController = loader.getController();
            loginController.setTaxCode(taxCode);
            loginStage.setScene(new Scene(root));
            loginStage.show();

        } catch (IOException e) {
            System.out.println("Errore caricamento pagina di login!" + e.getMessage());
        }

        Stage diabetologoStage = (Stage) topBar.getScene().getWindow();
        diabetologoStage.close();
   }

   @FXML
    private void onHomePagePressed(javafx.event.ActionEvent event) {
       try {
           // Carica la nuova view da FXML
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlView/dettaglio_paziente_view.fxml"));
           Parent root = loader.load();
           DettaglioPazienteController controller = loader.getController();
           controller.setPaziente(paziente, taxCodeDiabetologo);
           // Prendi lo stage corrente dal bottone cliccato
           Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

           // Crea la nuova scena con il root appena caricato
           Scene scene = new Scene(root);

           // Imposta la nuova scena sullo stage corrente
           stage.setScene(scene);

           //Imposta titolo finestra
           stage.setTitle("Dettaglio paziente");

           // Mostra la finestra (non chiude, cambia scena)
           stage.show();

       } catch (IOException e) {
           e.printStackTrace();
       }
   }

   @FXML
    private void onSendButtonPressed(ActionEvent event) {

       String terapia = terapiaField.getText();
       String farmaco = farmacoField.getText();
       String quantita = quantitaField.getText();
       int frequenza = Integer.parseInt(frequenzaField.getText());
       String indicazioni = indicazioniField.getText();

        if(terapia.isEmpty() || farmaco.isEmpty()) return;

       ModificaTerapiaModel model = new ModificaTerapiaModel();
       model.updateData(taxCode, terapia, farmaco, quantita, frequenza, indicazioni, taxCodeDiabetologo, this.terapia.getTerapia());

       Alert alert = new Alert(Alert.AlertType.INFORMATION);
       alert.setTitle("Successo");
       alert.setHeaderText(null);
       alert.setContentText("La terapia è stata inserita correttamente nel database.");
       alert.showAndWait().ifPresent(response -> {
           if (response == ButtonType.OK) {
               // Torno indietro alla scena precedente
               Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

               try {
                   Parent root = FXMLLoader.load(getClass().getResource("/fxmlView/dettaglio_paziente_view.fxml"));
                   stage.setScene(new Scene(root));
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       });
   }

    @FXML
    private void handleReset() {

        terapiaField.clear();
        farmacoField.clear();
        quantitaField.clear();
        frequenzaField.clear();
        indicazioniField.clear();
    }

    public void setTaxCode(String taxCode, String taxCodeDiabetologo, Terapia terapia) {
        this.taxCode = taxCode;
        this.taxCodeDiabetologo = taxCodeDiabetologo;
        this.terapia = terapia;
    }

    public void setPaziente(Paziente paziente) {
        this.paziente = paziente;
    }
}
