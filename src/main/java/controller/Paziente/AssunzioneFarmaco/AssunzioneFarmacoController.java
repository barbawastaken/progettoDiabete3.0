package controller.Paziente.AssunzioneFarmaco;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.converter.LocalTimeStringConverter;
import model.Paziente.AssunzioneFarmaco.AssunzioneFarmacoModel;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AssunzioneFarmacoController {

    @FXML private TextField farmacoAssunto;
    @FXML private TextField quantitaAssunta;
    @FXML private DatePicker dataAssunzione;
    @FXML private Spinner<LocalTime> orarioAssunzione;

    private String taxCode;

    public AssunzioneFarmacoController() {}

    public void setTaxCode(String taxCode) { this.taxCode = taxCode; }

    @FXML
    public void initialize() {
        // Imposta un valore iniziale e un range, ad esempio ogni 15 minuti
        SpinnerValueFactory<LocalTime> valueFactory = new SpinnerValueFactory<LocalTime>() {
            {
                setConverter(new LocalTimeStringConverter(DateTimeFormatter.ofPattern("HH:mm"), null));
                setValue(LocalTime.of(12, 0)); // valore iniziale
            }

            @Override
            public void decrement(int steps) {
                setValue(getValue().minusMinutes(steps * 30));
            }

            @Override
            public void increment(int steps) {
                setValue(getValue().plusMinutes(steps * 30));
            }
        };

        orarioAssunzione.setValueFactory(valueFactory);
        orarioAssunzione.setEditable(true); // opzionale: permette scrittura manuale

        TextField editor = orarioAssunzione.getEditor();
        editor.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                String text = editor.getText();
                try {
                    // Se il testo è valido, aggiorna il valore
                    LocalTime parsed = LocalTime.parse(text, DateTimeFormatter.ofPattern("HH:mm"));
                    orarioAssunzione.getValueFactory().setValue(parsed);
                } catch (Exception e) {
                    // Se è vuoto o invalido, ripristina default
                    LocalTime defaultTime = LocalTime.of(12,0);
                    orarioAssunzione.getValueFactory().setValue(defaultTime);
                    editor.setText(defaultTime.format(DateTimeFormatter.ofPattern("HH:mm")));
                }
            }
        });
    }

    @FXML
    public void onIndietroPressed(){

        Stage stage = (Stage) farmacoAssunto.getScene().getWindow();
        stage.close();

    }

    @FXML
    public void onResetPressed(){

        farmacoAssunto.setText("");
        quantitaAssunta.setText("");
        dataAssunzione.setValue(null);
        orarioAssunzione.cancelEdit();

    }

    @FXML
    public void onSubmitPressed(){

        AssunzioneFarmacoModel model = new AssunzioneFarmacoModel();
        int risultatoInserimento = model.inserimentoFarmacoAssunto(taxCode, farmacoAssunto.getText(), quantitaAssunta.getText(),
                                                                    dataAssunzione.getValue(), orarioAssunzione.getValue());

        if(risultatoInserimento == 0){

            System.out.println("Valori inseriti correttamente");

            Stage stage = (Stage)farmacoAssunto.getScene().getWindow();
            stage.close();

        } else { checkOutput(risultatoInserimento); }

    }

    public void checkOutput(int valoreOutput) {

        System.out.println("Valori non inseriti");

        farmacoAssunto.setText("");
        quantitaAssunta.setText("");
        dataAssunzione.setValue(null);
        orarioAssunzione.cancelEdit();

        if(valoreOutput == 1) {

            messaggioErrore("Quantita' inserita non valida!");

        } else if(valoreOutput == 2) {

            messaggioErrore("Tutti i campi devono essere compilati!");

        } else if(valoreOutput == 3) {

            messaggioErrore("Non puoi inserire rilevazioni future!");

        } else if(valoreOutput == 4) {

            messaggioErrore("Non puoi inserire la stessa assunzione due volte!");

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
