package controller.Diabetologo;

import controller.NavBar;
import controller.NavBarTags;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Amministratore.Paziente;
import model.Diabetologo.AggiungiTerapiaModel;

import java.io.IOException;

public class AggiungiTerapiaController {

    private String taxCode;
    private Paziente paziente;
    private String taxCodeDiabetologo;

    @FXML
    private HBox navbarContainer;
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
    private void initialize(){
        NavBar navbar = new NavBar(NavBarTags.PAZIENTE_toHomepage);
        navbar.prefWidthProperty().bind(navbarContainer.widthProperty());
        navbarContainer.getChildren().add(navbar);

        //Aggiunta di un listener che consente l'inserimento di soli valori numerici --> evita IllegalNUmberException
        frequenzaField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("\\d")) {
                event.consume(); // blocca l'inserimento
            }
        });
    }

    @FXML
    private void handleHomepage(javafx.event.ActionEvent event) {
        try {
            // Carica la nuova view da FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlView/dettaglio_paziente_view.fxml"));
            Parent root = loader.load();
            DettaglioPazienteController controller = loader.getController();
            //controller.setPaziente(paziente, taxCodeDiabetologo);
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
            System.out.println("Errore caricamento pagina HomePage: " + e.getMessage());
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
    private void handleConferma(ActionEvent event) {

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
            frequenza = 0;
        }


        AggiungiTerapiaModel model = new AggiungiTerapiaModel();
        model.insertData(taxCode, terapia, farmaco, quantita, frequenza, indicazioni, taxCodeDiabetologo);

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
                    System.out.println("Errore caricamento pagina dettaglio paziente: " + e.getMessage());
                }
            }
        });
    }

    private void mostraErrore(String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }

    public void setTaxCode(String taxCode, String taxCodeDiabetologo) {
        this.taxCode = taxCode;
        this.taxCodeDiabetologo = taxCodeDiabetologo;
    }

    public void setPaziente(Paziente paziente) {
        this.paziente = paziente;
    }
}
