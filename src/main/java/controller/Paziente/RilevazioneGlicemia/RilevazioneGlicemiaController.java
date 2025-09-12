package controller.Paziente.RilevazioneGlicemia;

import controller.NavBar;
import controller.NavBarTags;
import controller.Session;
import controller.ViewNavigator;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import model.Paziente.RilevazioneGlicemia.RilevazioneGlicemiaModel;
import javafx.fxml.FXML;

public class RilevazioneGlicemiaController {

    @FXML
    private HBox navbarContainer;

    @FXML
    private TextField milligrammi;

    @FXML
    private DatePicker dataRilevamento;

    @FXML
    private ComboBox<String> prepost;

    @FXML
    private ComboBox<String> pastoGlicemia;

    private String taxCode;

   @FXML
   private void initialize() {

        prepost.getItems().addAll("PRE", "POST");
        pastoGlicemia.getItems().addAll("Colazione", "Pranzo", "Cena");

       dataRilevamento.getEditor().setDisable(true);
       dataRilevamento.getEditor().setOpacity(1);

        NavBar navbar = new NavBar(NavBarTags.PAZIENTE_toHomepage);
        navbar.prefWidthProperty().bind(navbarContainer.widthProperty());
        navbarContainer.getChildren().addAll(navbar);

       this.taxCode = Session.getInstance().getTaxCode();
   }

    @FXML
    private void onSubmitPressed(){

        RilevazioneGlicemiaModel model = new RilevazioneGlicemiaModel();
        int risultatoInserimento = model.inserimentoRilevazioneGlicemia(taxCode, milligrammi.getText(), pastoGlicemia.getValue(), prepost.getValue(), dataRilevamento.getValue());

        if(risultatoInserimento == 0){

            ViewNavigator.navigateToPaziente();

        } else { checkOutput(risultatoInserimento); }
    }

    @FXML
    private void onResetPressed(){

        milligrammi.setText("");
        pastoGlicemia.setValue(null);
        prepost.setValue(null);
        dataRilevamento.setValue(null);

    }

    public void checkOutput(int valoreOutput) {

        System.out.println("Valori non inseriti");

        if(valoreOutput == 1) {

            pastoGlicemia.setValue(null);
            prepost.setValue(null);
            dataRilevamento.setValue(null);
            messaggioErrore("Non puoi inserire due rilevazioni nello stesso momento!");

        } else if(valoreOutput == 2) {

            milligrammi.setText("");
            messaggioErrore("I valori di glicemia devono essere validi");

        } else if(valoreOutput == 3) {

            dataRilevamento.setValue(null);
            messaggioErrore("Non puoi inserire rilevazioni future!");

        } else if(valoreOutput == 4) {

            messaggioErrore("Tutti i campi devono essere compilati");

        } else if(valoreOutput == 5) {

            messaggioErrore("Errore inserimento database. Riprovare più tardi!");
        }

    }

    public void messaggioErrore(String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore!!!");
        alert.setHeaderText(null); // oppure "Attenzione!"
        alert.setContentText(messaggio);
        alert.showAndWait();
    }
}
