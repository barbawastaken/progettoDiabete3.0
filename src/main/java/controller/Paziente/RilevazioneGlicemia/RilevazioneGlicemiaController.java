package controller.Paziente.RilevazioneGlicemia;

import controller.Paziente.PazienteController;
import javafx.scene.control.Alert;
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

            int quantitaInserita = 0;
            if(!view.getQuantitaRilevazioneTextField().getText().isEmpty()) {
                quantitaInserita = Integer.parseInt(view.getQuantitaRilevazioneTextField().getText());
            }
            String momentoGiornata = view.getMomentoGiornataField().getValue();
            String prePost = view.getPrePostField().getValue();
            LocalDate dataInserita = view.getDataField().getValue();

            int risultatoInserimento = model.inserimentoRilevazioneGlicemia(taxCode, quantitaInserita, momentoGiornata, prePost, dataInserita);

            if(risultatoInserimento == 0){

                System.out.println("Valori inseriti correttamente");

                PazienteModel pazienteModel = new PazienteModel();
                PazienteView pazienteView = new PazienteView();

                new PazienteController(taxCode, pazienteModel, pazienteView, rilevazioneGlicemiaStage);

            } else { checkOutput(risultatoInserimento); }
        });
    }

    public void checkOutput(int valoreOutput) {

        System.out.println("Valori non inseriti");

        view.getQuantitaRilevazioneTextField().clear();
        view.getMomentoGiornataField().setValue("");
        view.getPrePostField().setValue("");
        view.getDataField().setValue(null);

        if(valoreOutput == 1) {

            messaggioErrore("Non puoi inserire due rilevazioni nello stesso momento!");

        } else if(valoreOutput == 2) {

            messaggioErrore("I valori di glicemia devono essere validi");

        } else if(valoreOutput == 3) {

            messaggioErrore("Non puoi inserire rilevazioni future!");

        } else if(valoreOutput == 4) {

            messaggioErrore("Tutti i campi devono essere compilati");

        } else if(valoreOutput == 5) {

            messaggioErrore("Errore inserimento database. Riprovare più tardi!");
        }

    }

    public void messaggioErrore(String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore!!!");
        alert.setHeaderText(null); // oppure "Attenzione!"
        alert.setContentText(messaggio);
        alert.showAndWait();
    }
}
