package controller.Paziente.RilevazioneGlicemia;

import controller.Paziente.PazienteController;
import javafx.stage.Stage;
import model.Paziente.PazienteModel;
import model.Paziente.RilevazioneGlicemia.RilevazioneGlicemiaModel;
import view.Paziente.PazienteView;
import view.Paziente.RilevazioneGlicemia.RilevazioneGlicemiaView;
import java.time.LocalDate;

public class RilevazioneGlicemiaController {

    private final RilevazioneGlicemiaModel model;
    private final RilevazioneGlicemiaView view;

    public RilevazioneGlicemiaController(String taxCode, RilevazioneGlicemiaModel model, RilevazioneGlicemiaView view, Stage pazienteStage) {

        this.model = model;
        this.view = view;

        Stage rilevazioneGlicemiaStage = new Stage();
        rilevazioneGlicemiaStage.setTitle("Inserimento nuova rilevazione glicemica");
        rilevazioneGlicemiaStage.setScene(view.getScene());
        rilevazioneGlicemiaStage.setHeight(pazienteStage.getHeight());
        rilevazioneGlicemiaStage.setWidth(pazienteStage.getWidth());
        rilevazioneGlicemiaStage.setMinWidth(600);
        rilevazioneGlicemiaStage.setMinHeight(800);
        rilevazioneGlicemiaStage.setX(pazienteStage.getX());
        rilevazioneGlicemiaStage.setY(pazienteStage.getY());
        rilevazioneGlicemiaStage.alwaysOnTopProperty();
        pazienteStage.close();
        rilevazioneGlicemiaStage.show();

        view.getIndietroButton().setOnAction(e -> {

            PazienteModel pazienteModel = new PazienteModel();
            PazienteView pazienteView = new PazienteView();

            try {
                new PazienteController(taxCode, pazienteModel, pazienteView, rilevazioneGlicemiaStage);
            } catch (Exception ex) {
                System.out.println("Errore: " + ex.getMessage());
            }
        });

        view.getResetButton().setOnAction(e -> {

            view.getQuantitaRilevazioneTextField().clear();
            view.getMomentoGiornataField().cancelEdit();
            view.getPrePostField().cancelEdit();
            view.getDataField().setValue(null);

        });

        view.getConfermaButton().setOnAction(e -> {

            int quantitaInserita = Integer.parseInt(view.getQuantitaRilevazioneTextField().getText());
            String momentoGiornata = view.getMomentoGiornataField().getValue();
            String prePost = view.getPrePostField().getValue();
            LocalDate dataInserita = view.getDataField().getValue();

            if(model.inserimentoRilevazioneGlicemia(taxCode, quantitaInserita, momentoGiornata, prePost, dataInserita)){
                System.out.println("Valori inseriti correttamente");

                PazienteModel pazienteModel = new PazienteModel();
                PazienteView pazienteView = new PazienteView();

                new PazienteController(taxCode, pazienteModel, pazienteView, rilevazioneGlicemiaStage);

            } else {
                System.out.println("Valori non inseriti");
            }
        });
    }
}
