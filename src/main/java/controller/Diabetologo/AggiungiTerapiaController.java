package controller.Diabetologo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Diabetologo.AggiungiTerapiaModel;

import java.io.IOException;
import java.sql.SQLException;

public class AggiungiTerapiaController {

    private String taxCode;

    @FXML
    private TextField terapiaField;

    @FXML
    private TextField farmacoField;

    @FXML
    private TextField quantitaField;

    @FXML
    private TextField frequenzaField;

    @FXML
    private TextField indicazioniField;

    @FXML
    private Button resetButton;

    @FXML
    private Button confermaButton;

    @FXML
    private void handleHomepage(javafx.event.ActionEvent event) {
        try {
            // Carica la nuova view da FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlView/dettaglio_paziente_view.fxml"));
            Parent root = loader.load();

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
    private void handleReset() {
        terapiaField.clear();
        farmacoField.clear();
        quantitaField.clear();
        frequenzaField.clear();
        indicazioniField.clear();
    }

    @FXML
    private void handleConferma() {
        String terapia = terapiaField.getText();
        String farmaco = farmacoField.getText();
        int quantita = Integer.parseInt(quantitaField.getText());
        int frequenza = Integer.parseInt(frequenzaField.getText());
        String indicazioni = indicazioniField.getText();

        if (terapia.isEmpty() || farmaco.isEmpty()) {
            mostraErrore("Tutti i campi obbligatori devono essere compilati.");
            return;
        }

        AggiungiTerapiaModel model = new AggiungiTerapiaModel();
        model.insertData(taxCode, terapia, farmaco, quantita, frequenza, indicazioni);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Successo");
        alert.setHeaderText(null);
        alert.setContentText("La terapia è stata inserita correttamente nel database.");
        alert.showAndWait();

    }

    private void mostraErrore(String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }
}
