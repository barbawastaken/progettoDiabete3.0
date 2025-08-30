package controller.Diabetologo;

import controller.Session;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;
import model.Diabetologo.AggiornaInfoPazienteModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class AggiornaInfoPazienteController {

    private String taxCode = Session.getInstance().getTaxCode();
    private String taxCodeDiabetologo;

    private final static String DB_URL = "jdbc:sqlite:mydatabase.db";

    @FXML private TextArea infoTextArea;

    public void setTaxCode(String taxCode, String taxCodeDiabetologo) {
        this.taxCode = taxCode;
        this.taxCodeDiabetologo = taxCodeDiabetologo;
    }

    @FXML
    private void initialize() {
        //parte navBar??
    }

    @FXML
    private void onResetClicked() {
        infoTextArea.setText("");
    }

    @FXML
    private void onSaveClicked(ActionEvent event) {
        if(infoTextArea.getText().isEmpty())
            return;

        String info = infoTextArea.getText();

        AggiornaInfoPazienteModel model = new AggiornaInfoPazienteModel();
        model.insertData(taxCode, info, taxCodeDiabetologo);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.close();
    }
}
