package controller.Paziente;

import javafx.stage.Stage;
import model.Paziente.PazienteModel;
import view.Paziente.PazienteView;

public class PazienteController {

    private PazienteModel pazienteModel;
    private PazienteView pazienteView;

    public PazienteController(PazienteModel pazienteModel, PazienteView pazienteView, Stage loginStage) {

        this.pazienteModel = pazienteModel;
        this.pazienteView = pazienteView;

        Stage pazienteStage = new Stage();
        pazienteStage.setTitle("Homepage paziente");
        pazienteStage.setScene(pazienteView.getScene());
        pazienteStage.setHeight(loginStage.getHeight());
        pazienteStage.setWidth(loginStage.getWidth());
        pazienteStage.setX(loginStage.getX());
        pazienteStage.setY(loginStage.getY());
        pazienteStage.alwaysOnTopProperty();
        pazienteStage.setMinHeight(320);
        pazienteStage.setMinWidth(240);
        loginStage.close();
        pazienteStage.show();
    }
}
