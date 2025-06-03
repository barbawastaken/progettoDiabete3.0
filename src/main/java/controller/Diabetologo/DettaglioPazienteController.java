package controller.Diabetologo;

import javafx.fxml.FXML;
import model.Amministratore.Paziente;
import java.awt.*;
import javafx.scene.control.Label;

public class DettaglioPazienteController {

    @FXML private Label nomeLabel;
    @FXML private Label cognomeLabel;
    @FXML private Label codiceFiscaleLabel;
    @FXML private Label sessoLabel;
    @FXML private Label dataNascitaLabel;
    @FXML private Label passwordLabel;
    @FXML private Label viaLabel;
    @FXML private Label numeroCivicoLabel;
    @FXML private Label capLabel;
    @FXML private Label paeseLabel;
    @FXML private Label cittaLabel;
    @FXML private Label emailLabel;
    @FXML private Label cellulareLabel;
    @FXML private Label pesoLabel;
    @FXML private Label altezzaLabel;

    private Paziente paziente;

    public void setPaziente(Paziente p) {
        this.paziente = p;

        nomeLabel.setText("Nome: " + p.getNome());
        cognomeLabel.setText("Cognome: " + p.getCognome());
        codiceFiscaleLabel.setText("Codice Fiscale: " + p.getTaxCode());
        sessoLabel.setText("Sesso: " + p.getGender());
        dataNascitaLabel.setText("Data di Nascita: " + p.getBirthday());
        passwordLabel.setText("Password: " + p.getPassword());
        viaLabel.setText("Via: " + p.getAddress());
        numeroCivicoLabel.setText("Numero Civico: " + p.getNumber());
        capLabel.setText("CAP: " + p.getCap());
        paeseLabel.setText("Paese di Residenza: " + p.getCountryOfResidence());
        cittaLabel.setText("Città: " + p.getCity());
        emailLabel.setText("Email: " + p.getEmail());
        cellulareLabel.setText("Cellulare: " + p.getTelephone());
        pesoLabel.setText("Peso: " + p.getWeight());
        altezzaLabel.setText("Altezza: " + p.getHeight());
    }


    @FXML private void handleAggiungiTerapia() {

    }

    @FXML private void handleModificaTerapia() {

    }

    @FXML private void handleAggiornaInfo() {

    }
}

