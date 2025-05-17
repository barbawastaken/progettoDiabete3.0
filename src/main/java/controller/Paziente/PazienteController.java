package controller.Paziente;

import controller.LoginController;
import controller.Paziente.RilevazioneGlicemia.RilevazioneGlicemiaController;
import javafx.stage.Stage;
import model.LoginModel;
import model.Paziente.PazienteModel;
import model.Paziente.RilevazioneGlicemia.RilevazioneGlicemiaModel;
import view.LoginView;
import view.Paziente.PazienteView;
import view.Paziente.RilevazioneGlicemia.RilevazioneGlicemiaView;

import java.sql.SQLException;

public class PazienteController {

    private final PazienteModel pazienteModel;
    private final PazienteView pazienteView;

    public PazienteController(PazienteModel pazienteModel, PazienteView pazienteView, Stage loginStage) {

        this.pazienteModel = pazienteModel;
        this.pazienteView = pazienteView;

        Stage pazienteStage = new Stage();
        pazienteStage.setTitle("Homepage paziente");
        pazienteStage.setScene(pazienteView.getScene());
        pazienteStage.setHeight(loginStage.getHeight());
        pazienteStage.setWidth(loginStage.getWidth());
        pazienteStage.setMinWidth(600);
        pazienteStage.setMinHeight(800);
        pazienteStage.setX(loginStage.getX());
        pazienteStage.setY(loginStage.getY());
        pazienteStage.alwaysOnTopProperty();
        /*pazienteStage.setMinHeight(320);
        pazienteStage.setMinWidth(240);*/
        loginStage.close();
        pazienteStage.show();

        pazienteView.getLogoutButton().setOnAction(e -> { pazienteStage.close();
        LoginView loginView = new LoginView();
        LoginModel loginModel = new LoginModel();
            try {
                new LoginController(loginModel, loginView, pazienteStage);
            } catch (SQLException ex) {
                System.out.println("Errore: " + ex.getMessage());
            }
        });

        pazienteView.getRilevazioniGlicemiaButton().setOnAction(e -> {

            RilevazioneGlicemiaModel rilevazioneGlicemiaModel = new RilevazioneGlicemiaModel();
            RilevazioneGlicemiaView rilevazioneGlicemiaView = new RilevazioneGlicemiaView();

            try {
                new RilevazioneGlicemiaController(rilevazioneGlicemiaModel, rilevazioneGlicemiaView, pazienteStage);
            } catch (Exception ex) {
                System.out.println("Errore: " + ex.getMessage());
            }
        });
    }
}
