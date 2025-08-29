package controller.Diabetologo;

import controller.NavBar;
import controller.NavBarTags;
import controller.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Amministratore.Paziente;

import java.io.IOException;

import javafx.scene.control.Button;

import javafx.scene.control.Label;
import model.Diabetologo.ModificaTerapiaModel;

public class DettaglioPazienteController {
    
    @FXML private HBox navbarContainer;
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

    @FXML
    private void initialize() {

        NavBar navbar = new NavBar(NavBarTags.toModificaUtenti);
        navbar.prefWidthProperty().bind(navbarContainer.widthProperty());
        
        navbarContainer.getChildren().add(navbar);
        this.paziente = Session.getInstance().getPazienteInEsame();
        this.taxCodeDiabetologo = Session.getInstance().getTaxCode();

        System.out.println("VALORE PAZIENTE: " + paziente.toString());
        System.out.println("TAXCODE DIABETOLOGO: " + taxCodeDiabetologo);

        nomeLabel.setText("Nome: " + paziente.getNome());
        cognomeLabel.setText("Cognome: " + paziente.getCognome());
        codiceFiscaleLabel.setText("Codice Fiscale: " + paziente.getTaxCode());
        sessoLabel.setText("Sesso: " + paziente.getGender());
        dataNascitaLabel.setText("Data di Nascita: " + paziente.getBirthday());
        passwordLabel.setText("Password: " + paziente.getPassword());
        viaLabel.setText("Via: " + paziente.getAddress());
        numeroCivicoLabel.setText("Numero Civico: " + paziente.getNumber());
        capLabel.setText("CAP: " + paziente.getCap());
        paeseLabel.setText("Paese di Residenza: " + paziente.getCountryOfResidence());
        cittaLabel.setText("Città: " + paziente.getCity());
        emailLabel.setText("Email: " + paziente.getEmail());
        cellulareLabel.setText("Cellulare: " + paziente.getTelephone());
        pesoLabel.setText("Peso: " + paziente.getWeight());
        altezzaLabel.setText("Altezza: " + paziente.getHeight());
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


        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlView/modifica_terapia_view.fxml"));
            Parent root = loader.load();

            ModificaTerapiaController controller = loader.getController();
            controller.setTaxCode(paziente.getTaxCode(), taxCodeDiabetologo);
            controller.setPaziente(paziente);
            Stage currentStage = (Stage) modificaTerapia.getScene().getWindow();

            currentStage.setTitle("Modifica terapia");
            currentStage.setScene(new Scene(root));
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

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

