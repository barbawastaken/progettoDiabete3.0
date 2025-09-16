package controller.Diabetologo;

import controller.NavBar;
import controller.NavBarTags;
import controller.Session;
import controller.ViewNavigator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.input.KeyEvent;
import model.Diabetologo.AggiungiTerapiaModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AggiungiTerapiaController {

    private String taxCode;

    @FXML private HBox navbarContainer;

    @FXML private TextField terapiaField;
    @FXML private TextField farmacoField;
    @FXML private TextField quantitaField;
    @FXML private TextField frequenzaField;
    @FXML private TextField indicazioniField;


    @FXML
    private void initialize(){

        NavBar navbar = new NavBar(NavBarTags.DIABETOLOGO_operazioneRitornoTabellaTerapie);
        navbar.prefWidthProperty().bind(navbarContainer.widthProperty());
        navbarContainer.getChildren().add(navbar);

        //Aggiunta di un listener che consente l'inserimento di soli valori numerici --> evita IllegalNUmberException
        frequenzaField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("\\d")) {
                event.consume(); // blocca l'inserimento
            }
        });

        this.taxCode = Session.getInstance().getPazienteInEsame().getTaxCode();

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

        AggiungiTerapiaModel model = new AggiungiTerapiaModel();
        int result = model.insertData(taxCode, terapia, farmaco, quantita, frequenza, indicazioni);

        if(result == 0) { ViewNavigator.navigateToPatientDetails(); }
        if(result == -1) { mostraErrore("Non puoi prescrivere due volte lo stesso farmaco!"); farmacoField.clear(); }
        if(result == -2) { mostraErrore("Errore nell'interazione col database!"); ViewNavigator.navigateToDiabetologo(); }

    }

    private void mostraErrore(String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }

}
