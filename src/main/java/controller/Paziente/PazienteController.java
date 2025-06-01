package controller.Paziente;

import controller.LoginController;
import controller.Paziente.AggiuntaSintomi.AggiuntaSintomiController;
import controller.Paziente.RilevazioneGlicemia.RilevazioneGlicemiaController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.LoginModel;
import model.Paziente.AggiuntaSintomi.AggiuntaSintomiModel;
import model.Paziente.PazienteModel;
import model.Paziente.RilevazioneGlicemia.RilevazioneGlicemiaModel;
import view.LoginView;
import view.Paziente.AggiuntaSintomi.AggiuntaSintomiView;
import view.Paziente.PazienteView;
import view.Paziente.RilevazioneGlicemia.RilevazioneGlicemiaView;

import java.io.IOException;
import java.sql.SQLException;

public class PazienteController {

    private String taxCode;

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    @FXML
    private void onRilevazioneGlicemiaClicked() throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Rilevazione Glicemia");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlView/rilevazione_glicemia_view.fxml"));
        Parent root = loader.load();
        RilevazioneGlicemiaController controller = loader.getController();
        controller.setTaxCode(taxCode);
        stage.setScene(new Scene(root));
        stage.show();


    }

    @FXML
    private void onAddSymptomsClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlView/aggiunta_sintomi_view.fxml"));
        Parent root = loader.load();
        AggiuntaSintomiController controller = loader.getController();
        controller.setTaxCode(taxCode);
        Stage stage = new Stage();
        stage.setTitle("Aggiunta sintomi");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public PazienteController(){

    }

    public PazienteController(String taxCode, PazienteModel pazienteModel, PazienteView pazienteView) {



        Stage pazienteStage = new Stage();
        pazienteStage.setTitle("Homepage paziente");
        pazienteStage.setScene(pazienteView.getScene());

        pazienteStage.setMinWidth(600);
        pazienteStage.setMinHeight(800);
        pazienteStage.alwaysOnTopProperty();

        pazienteStage.show();



        /*pazienteView.getRilevazioniGlicemiaButton().setOnAction(e -> {

            RilevazioneGlicemiaModel rilevazioneGlicemiaModel = new RilevazioneGlicemiaModel();
            RilevazioneGlicemiaView rilevazioneGlicemiaView = new RilevazioneGlicemiaView();

            try {
                new RilevazioneGlicemiaController(taxCode, rilevazioneGlicemiaModel, rilevazioneGlicemiaView, pazienteStage);
            } catch (Exception ex) {
                System.out.println("Errore: " + ex.getMessage());
            }
        });*/

        pazienteView.getInserimentoSintomiButton().setOnAction(e -> {

            AggiuntaSintomiModel aggiuntaSintomiModel = new AggiuntaSintomiModel();
            AggiuntaSintomiView aggiuntaSintomiView = new AggiuntaSintomiView();

            try {
                new AggiuntaSintomiController(taxCode, aggiuntaSintomiModel, aggiuntaSintomiView, pazienteStage);
            } catch (Exception ex) {
                System.out.println("Errore: " + ex.getMessage());
            }

        });
    }
}
