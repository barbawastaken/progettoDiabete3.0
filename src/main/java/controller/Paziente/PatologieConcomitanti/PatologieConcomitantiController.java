package controller.Paziente.PatologieConcomitanti;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.Paziente.PatologieConcomitanti.PatologieConcomitantiModel;

public class PatologieConcomitantiController {

    @FXML private TextArea patologiaSpecificata;
    @FXML private DatePicker dataInizio;
    @FXML private DatePicker dataFine;

    private String taxCode;

    public PatologieConcomitantiController(){}

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    @FXML
    public void onIndietroPressed(){

        Stage stage = (Stage) dataInizio.getScene().getWindow();
        stage.close();

    }

    @FXML
    public void onResetPressed(){

        patologiaSpecificata.setText("");
        dataInizio.setValue(null);
        dataFine.setValue(null);

    }

    @FXML
    public void onSubmitPressed(){

        PatologieConcomitantiModel model = new PatologieConcomitantiModel();
        int risultatoInserimento = model.inserimentoPatologiaConcomitante(taxCode, patologiaSpecificata.getText(), dataInizio.getValue(), dataFine.getValue());

        if(risultatoInserimento == 0){

            System.out.println("Valori inseriti correttamente");

            Stage stage = (Stage)dataInizio.getScene().getWindow();
            stage.close();

        } else { checkOutput(risultatoInserimento); }
    }

    public void checkOutput(int valoreOutput) {

        System.out.println("Valori non inseriti");

        patologiaSpecificata.setText("");
        dataInizio.setValue(null);
        dataFine.setValue(null);

        if(valoreOutput == 1) {

            messaggioErrore("Tutti i campi obbligatori devono essere compilati!");

        } else if(valoreOutput == 2) {

            messaggioErrore("La data di fine non deve essere precedente a quella di inizio!");

         } else if(valoreOutput == 3) {

            messaggioErrore("Non si possono inserire specifiche future!");

        } else if(valoreOutput == 4) {

            messaggioErrore("Esiste gia' la stessa specifica in un periodo simile!");

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
