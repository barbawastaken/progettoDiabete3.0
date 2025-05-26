package controller.Diabetologo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import model.Diabetologo.DiabetologoModel;
import view.Diabetologo.DiabetologoView;
import javafx.stage.Stage;

import java.io.IOException;

public class DiabetologoController {

    private DiabetologoModel diabetologoModel;
    private DiabetologoView diabetologoView;

    private String taxCode;

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public DiabetologoController() {

    }

    @FXML
    public void handleLogout(javafx.event.ActionEvent event) {
        try {
            // Carica il file FXML del login
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlView/login_view.fxml"));
            Parent root = loader.load();

            // Mi prendo la finestra corrente dal pulsante;
            // event praticamente viene passato automaticamente da FXML come parametro quindi
            // lo puoi usare per ottenere lo Stage e cambiare scena
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Imposta la nuova scena con quella del login
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.show();

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void isVisualizzaPazientiClicked() {

    }

    @FXML
    public void isVisualizzaNotificheClicked() {

    }

    public DiabetologoController(DiabetologoModel diabetologoModel, DiabetologoView diabetologoView, Stage loginStage){

        this.diabetologoModel = diabetologoModel;
        this.diabetologoView = diabetologoView;

        Stage diabetologoStage = new Stage();
        diabetologoStage.setScene(diabetologoView.getScene());
        diabetologoStage.setHeight(loginStage.getHeight());
        diabetologoStage.setWidth(loginStage.getWidth());
        diabetologoStage.setX(loginStage.getX());
        diabetologoStage.setY(loginStage.getY());
        diabetologoStage.alwaysOnTopProperty();
        diabetologoStage.setMinHeight(320);
        diabetologoStage.setMinWidth(240);
        diabetologoStage.setTitle("Homepage diabetologo");
        loginStage.close();
        diabetologoStage.show();
    }

}
