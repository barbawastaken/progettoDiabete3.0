package controller.Paziente.PatologieConcomitanti;

import controller.NavBar;
import controller.NavBarTags;
import controller.Session;
import controller.ViewNavigator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import model.Paziente.PatologieConcomitanti.PatologieConcomitantiModel;

public class PatologieConcomitantiController {

    @FXML private HBox navbarContainer;

    @FXML private TextArea patologiaSpecificata;
    @FXML private DatePicker dataInizio;
    @FXML private DatePicker dataFine;

    private String taxCode;
    private final PatologieConcomitantiModel model = new PatologieConcomitantiModel();

    @FXML private void initialize() {

        NavBar navbar = new NavBar(NavBarTags.PAZIENTE_toHomepage);
        navbar.prefWidthProperty().bind(navbarContainer.widthProperty());
        navbarContainer.getChildren().addAll(navbar);

        dataInizio.getEditor().setDisable(true);
        dataInizio.getEditor().setOpacity(1);

        dataFine.getEditor().setDisable(true);
        dataFine.getEditor().setOpacity(1);

        this.taxCode = Session.getInstance().getTaxCode();

    }

    @FXML
    public void onResetPressed(){

        patologiaSpecificata.setText("");
        dataInizio.setValue(null);
        dataFine.setValue(null);

    }

    @FXML
    public void onSubmitPressed(){

        int risultatoInserimento = model.inserimentoPatologiaConcomitante(taxCode, patologiaSpecificata.getText(), dataInizio.getValue(), dataFine.getValue());

        if(risultatoInserimento == 0){

            ViewNavigator.navigateToPaziente();

        } else { checkOutput(risultatoInserimento); }
    }

    public void checkOutput(int valoreOutput) {

        System.out.println("Valori non inseriti");

        if(valoreOutput == 1) {

            messaggioErrore("Tutti i campi obbligatori devono essere compilati!");

        } else if(valoreOutput == 2) {

            dataFine.setValue(null);
            messaggioErrore("La data di fine non deve essere precedente a quella di inizio!");

         } else if(valoreOutput == 3) {

            dataFine.setValue(null);
            messaggioErrore("Non si possono inserire specifiche future!");

        } else if(valoreOutput == 4) {

            patologiaSpecificata.setText("");
            dataInizio.setValue(null);
            dataFine.setValue(null);
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
