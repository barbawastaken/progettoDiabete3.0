package controller.Diabetologo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Amministratore.Paziente;

import java.io.IOException;

import javafx.scene.control.Button;

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

    @FXML private Button aggiungiTerapia;
    @FXML private Button modificaTerapia;
    @FXML private Button aggiornaInfo;

    private Paziente paziente;
    private String taxCodeDiabetologo;

    public void setPaziente(Paziente p, String taxCodeDiabetologo) {
        this.paziente = p;
        this.taxCodeDiabetologo = taxCodeDiabetologo;

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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlView/aggiungi_terapia_view.fxml"));
            Parent root = loader.load();

            AggiungiTerapiaController controller = loader.getController();
            controller.setTaxCode(paziente.getTaxCode(), taxCodeDiabetologo);
            controller.setPaziente(paziente);
            Stage currentStage = (Stage) aggiungiTerapia.getScene().getWindow();

            currentStage.setTitle("Aggiungi terapia");
            currentStage.setScene(new Scene(root));
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @FXML private void handleModificaTerapia() {

        /*
        *
        * Per Riccardo: quando implementerai questo metodo arriverai ad avere un "ModificaTerapiaModel" con
        * un accesso al database per effettuare la modifica di una terapia. Una volta completato il metodo,
        * se l'operazione va a buon fine devi incollare la seguente riga
        *
        * LogOperationModel.loadLogOperation(taxCodeDiabetologo, "Terapia modificata: " + nomeTerapiaOriginale, taxCodePaziente, LocalDate.now());
        *
        * Questa riga serve per effettuare il log quindi per mantenere traccia dell'operazione di modifica
        * terapia in una tabella del database. Guarda il metodo "insertData" della classe "AggiungiTerapiaModel" che
        * trovi proprio questa riga qua che capisci un po' come ho fatto io, oppure chiedimi. Quando hai fatto tutto
        * dimmelo che faccio un po' di test.
        *
        * ocio che il log deve essere eseguito solo quando l'operazione è andata a buon fine. Ad esempio se
        * non compilo un campo e quando clicco su "Salva" questo non viene effettivamente salvato per via dei
        * controlli il log non deve essere scritto.
        *
        * */

    }

    @FXML private void handleAggiornaInfo() {

        /*
        *
        * Per Riccardo: quando implementerai questo metodo arriverai ad avere un "AggiornaInfoPazienteModel" con
         * un accesso al database per effettuare la modifica delle informazioni di un paziente. Una volta completato
         *  il metodo, se l'operazione va a buon fine devi incollare la seguente riga
         *
         * LogOperationModel.loadLogOperation(taxCodeDiabetologo, "Aggiornate informazioni paziente", taxCodePaziente, LocalDate.now());
         *
         * Questa riga serve per effettuare il log quindi per mantenere traccia dell'operazione di aggiornamento
         * informazioni di un paziente in una tabella del database. Se hai dubbi guarda il metodo "insertData"
         * della classe "AggiungiTerapiaModel" che trovi proprio questa riga qua che capisci un po' come ho fatto io,
         * oppure chiedimi. Quando hai fatto tutto dimmelo che faccio un po' di test.
         *
         * ocio che il log deve essere eseguito solo quando l'operazione è andata a buon fine. Ad esempio se
         * non compilo un campo correttaente e quando clicco su "Salva" questo non viene effettivamente salvato
         * per via dei controlli il log non deve essere scritto.
         *
        * */

    }
}

