package controller.Paziente.AggiuntaSintomi;

import controller.NavBar;
import controller.NavBarTags;
import controller.Session;
import controller.ViewNavigator;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import model.Paziente.AggiuntaSintomi.AggiuntaSintomiModel;

public class AggiuntaSintomiController {

    AggiuntaSintomiModel model;
    String taxCode;

    @FXML private HBox navbarContainer;

    @FXML private ComboBox<String> symptoms;

    @FXML private TextArea otherSpecifications;

    @FXML private Text otherSpecificationsText;

    @FXML private DatePicker relevationDate;

    @FXML
    private void initialize() {

        model = new AggiuntaSintomiModel();
        symptoms.getItems().addAll("Spossatezza", "Nausea", "Mal di testa", "Altro");

        NavBar navbar = new NavBar(NavBarTags.PAZIENTE_toHomepage);
        navbar.prefWidthProperty().bind(navbarContainer.widthProperty());
        navbarContainer.getChildren().add(navbar);

        this.taxCode = Session.getInstance().getTaxCode();
    }

    @FXML
    private void onSendClicked(){

        int aggiuntaSintomi;

        aggiuntaSintomi = model.aggiungiSintomi(symptoms.getValue(), otherSpecifications.getText(), relevationDate.getValue(), taxCode);

        if(aggiuntaSintomi == 0) {

            ViewNavigator.navigateToPaziente();

        } else { checkOutput(aggiuntaSintomi); }

    }
    @FXML
    private void onResetClicked(){

        symptoms.setValue(null);
        otherSpecifications.setText("");
        relevationDate.setValue(null);
    }

    private void checkOutput(int aggiuntaSintomi) {

        this.symptoms.setValue("");
        this.otherSpecificationsText.setText("");
        this.relevationDate.setValue(null);

        if(aggiuntaSintomi == 1) {

            messaggioErrore("Deve essere specificato un sintomo!");

        } else if(aggiuntaSintomi == 2) {

            messaggioErrore("La data deve essere valida!");

        } else if(aggiuntaSintomi == 3) {

            messaggioErrore("e' gia' presente una aggiunta identica dello stesso utente!");

        } else if(aggiuntaSintomi == 4) {

            messaggioErrore("Errore di memorizzazione dei dati! Riprovare.");

        }

    }

    public void messaggioErrore(String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore!!!");
        alert.setHeaderText(null); // oppure "Attenzione!"
        alert.setContentText(messaggio);
        alert.showAndWait();

        onResetClicked();
    }

}
