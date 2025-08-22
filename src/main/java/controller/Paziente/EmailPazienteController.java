package controller.Paziente;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Paziente.EmailPazienteModel;

public class EmailPazienteController {

    private String taxCode;

    @FXML private TextField oggetto;
    @FXML private TextArea messaggio;

    public void setTaxCode(String taxCode) { this.taxCode = taxCode;}

    @FXML
    private void onSendButtonPressed(){

        EmailPazienteModel emailPazienteModel = new EmailPazienteModel();
        emailPazienteModel.inviaEmail(taxCode, oggetto.getText(), messaggio.getText());

        Stage toClose = (Stage)oggetto.getScene().getWindow();
        toClose.close();

    }
}
