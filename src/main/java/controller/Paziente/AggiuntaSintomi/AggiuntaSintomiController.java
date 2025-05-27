package controller.Paziente.AggiuntaSintomi;

import controller.Paziente.PazienteController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Amministratore.Utente;
import model.Paziente.AggiuntaSintomi.AggiuntaSintomiModel;
import model.Paziente.PazienteModel;
import view.Paziente.AggiuntaSintomi.AggiuntaSintomiView;
import view.Paziente.PazienteView;

public class AggiuntaSintomiController {

    AggiuntaSintomiModel model;
    AggiuntaSintomiView view;
    String taxCode;

    @FXML
    private TextField other;
    @FXML
    private Text otherText;
    @FXML
    private ComboBox<String> symptoms;
    @FXML
    private Text symptomsText;
    @FXML
    private TextArea otherSpecifications;
    @FXML
    private Text otherSpecificationsText;

    @FXML
    private void initialize() {
        model = new AggiuntaSintomiModel();
        symptoms.getItems().addAll("Spossatezza", "Nausea", "Mal di testa", "Altro");
        other.setVisible(false);
        otherText.setVisible(false);
        other.setManaged(false);
        otherText.setManaged(false);


        //model.aggiungiSintomi()

    }

    @FXML
    private DatePicker relevationDate;

    @FXML
    private void onSendClicked(){
        int aggiuntaSintomi;
        if(symptoms.getValue().isEmpty()){
            aggiuntaSintomi = model.aggiungiSintomi(other.getText(), otherSpecifications.getText(), relevationDate.getValue(), taxCode);
        }else{
            aggiuntaSintomi = model.aggiungiSintomi(symptoms.getValue(), otherSpecifications.getText(), relevationDate.getValue(), taxCode);
        }


        if(aggiuntaSintomi == 0) {

            System.out.println("Valori inseriti correttamente");

            //PazienteModel pazienteModel = new PazienteModel();
            //PazienteView pazienteView = new PazienteView();
            //new PazienteController(taxCode, pazienteModel, pazienteView);

        } else { checkOutput(aggiuntaSintomi); }

    }
    @FXML
    private void onResetClicked(){

        symptoms.setValue(null);
        other.setText("");
        otherSpecifications.setText("");
        relevationDate.setValue(null);
    }

    public AggiuntaSintomiController() {}

    public AggiuntaSintomiController(String taxCode, AggiuntaSintomiModel model, AggiuntaSintomiView view, Stage pazienteStage) {

        this.model = model;
        this.view = view;

        Stage aggiuntaSintomistage = new Stage();
        aggiuntaSintomistage.setTitle("Aggiunta Sintomi");
        aggiuntaSintomistage.setScene(view.getScene());
        aggiuntaSintomistage.setHeight(pazienteStage.getHeight());
        aggiuntaSintomistage.setWidth(pazienteStage.getWidth());
        aggiuntaSintomistage.setMinWidth(600);
        aggiuntaSintomistage.setMinHeight(800);
        aggiuntaSintomistage.setX(pazienteStage.getX());
        aggiuntaSintomistage.setY(pazienteStage.getY());
        aggiuntaSintomistage.alwaysOnTopProperty();
        pazienteStage.close();
        aggiuntaSintomistage.show();

        view.getIndietroButton().setOnAction(e -> {

            PazienteModel pazienteModel = new PazienteModel();
            PazienteView pazienteView = new PazienteView();

            try {
                new PazienteController(taxCode, pazienteModel, pazienteView);
            } catch (Exception ex) {
                System.out.println("Errore: " + ex.getMessage());
            }
        });

        view.getResetButton().setOnAction(e -> {


        });

        view.getConfermaButton().setOnAction(e -> {


        });

    }

    private void checkOutput(int aggiuntaSintomi) {

        view.getSintomiPrincipaliComboBox().setValue("");
        view.getSpecificaAltroField().setText("");
        view.getDataRilevazioneSintomoPicker().setValue(null);

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
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }
}
