package controller.Paziente;

import controller.LoginController;
import javafx.stage.Stage;
import model.LoginModel;
import model.Paziente.PazienteModel;
import view.LoginView;
import view.Paziente.PazienteView;
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
        pazienteStage.setMinHeight(320);
        pazienteStage.setMinWidth(240);
        loginStage.close();
        pazienteStage.show();

        pazienteView.getLogoutButton().setOnAction(e -> { pazienteStage.close();
        LoginView loginView = new LoginView();
        LoginModel loginModel = new LoginModel();
            try {
                new LoginController(loginModel, loginView, pazienteStage);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}
