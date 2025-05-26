package controller.Paziente.AggiuntaSintomi;

import controller.Paziente.PazienteController;
import javafx.stage.Stage;
import model.Paziente.AggiuntaSintomi.AggiuntaSintomiModel;
import model.Paziente.PazienteModel;
import view.Paziente.AggiuntaSintomi.AggiuntaSintomiView;
import view.Paziente.PazienteView;

public class AggiuntaSintomiController {

    AggiuntaSintomiModel model;
    AggiuntaSintomiView view;

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

            view.getSintomiPrincipaliComboBox().setValue(null);
            view.getSpecificaAltroField().setText("");
            view.getDataRilevazioneSintomoPicker().setValue(null);

        });

    }
}
