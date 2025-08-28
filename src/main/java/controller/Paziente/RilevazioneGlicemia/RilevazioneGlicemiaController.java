package controller.Paziente.RilevazioneGlicemia;

import controller.NavBar;
import controller.NavBarTags;
import controller.Paziente.PazienteController;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Paziente.PazienteModel;
import model.Paziente.RilevazioneGlicemia.RilevazioneGlicemiaModel;
import view.Paziente.PazienteView;
import view.Paziente.RilevazioneGlicemia.RilevazioneGlicemiaView;
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

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }


   @FXML
   private void initialize() {
        prepost.getItems().addAll("PRE", "POST");
        pastoGlicemia.getItems().addAll("Colazione", "Pranzo", "Cena");
        NavBar navbar = new NavBar(NavBarTags.PAZIENTE_toHomepage);
        navbar.prefWidthProperty().bind(navbarContainer.widthProperty());
        navbarContainer.getChildren().addAll(navbar);
   }

    @FXML
    private void onSubmitPressed(){
        RilevazioneGlicemiaModel model = new RilevazioneGlicemiaModel();
        int risultatoInserimento = model.inserimentoRilevazioneGlicemia(taxCode, milligrammi.getText(), pastoGlicemia.getValue(), prepost.getValue(), dataRilevamento.getValue());

        if(risultatoInserimento == 0){

            System.out.println("Valori inseriti correttamente");

            Stage stage = (Stage) milligrammi.getScene().getWindow();
            stage.close();

        } else { checkOutput(risultatoInserimento); }
    }

    @FXML
    private void onResetPressed(){

        milligrammi.setText("");
        pastoGlicemia.setValue(null);
        prepost.setValue(null);
        dataRilevamento.setValue(null);

    }

    public RilevazioneGlicemiaController() {
    }

    public void checkOutput(int valoreOutput) {

        System.out.println("Valori non inseriti");

        milligrammi.setText("");
        pastoGlicemia.setValue(null);
        prepost.setValue(null);
        dataRilevamento.setValue(null);

        if(valoreOutput == 1) {

            messaggioErrore("Non puoi inserire due rilevazioni nello stesso momento!");

        } else if(valoreOutput == 2) {

            messaggioErrore("I valori di glicemia devono essere validi");

        } else if(valoreOutput == 3) {

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
