package controller.Diabetologo;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AggiungiTerapiaController {

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
        String quantita = quantitaField.getText();
        String frequenza = frequenzaField.getText();
        String indicazioni = indicazioniField.getText();

        if (terapia.isEmpty() || farmaco.isEmpty() || quantita.isEmpty() || frequenza.isEmpty()) {
            mostraErrore("Tutti i campi obbligatori devono essere compilati.");
            return;
        }
    }

    private void mostraErrore(String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }
}
