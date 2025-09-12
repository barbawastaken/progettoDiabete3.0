package controller.Paziente.AssunzioneFarmaco;

import controller.NavBar;
import controller.NavBarTags;
import controller.Session;
import controller.ViewNavigator;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.converter.LocalTimeStringConverter;
import model.Paziente.AssunzioneFarmaco.AssunzioneFarmacoModel;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AssunzioneFarmacoController {

    @FXML private HBox navbarContainer;
    @FXML private ComboBox<String> farmacoAssunto;
    @FXML private TextField quantitaAssunta;
    @FXML private DatePicker dataAssunzione;
    @FXML private Spinner<LocalTime> orarioAssunzione;

    private final AssunzioneFarmacoModel model = new AssunzioneFarmacoModel();
    private String taxCode;

    @FXML
    public void initialize() {

        // Imposta un valore iniziale e un range, ad esempio ogni 15 minuti
        SpinnerValueFactory<LocalTime> valueFactory = new SpinnerValueFactory<>() {
            {
                setConverter(new LocalTimeStringConverter(DateTimeFormatter.ofPattern("HH:mm"), null));
                setValue(LocalTime.of(12, 0)); // valore iniziale
                NavBar navBar = new NavBar(NavBarTags.PAZIENTE_toHomepage);
                navBar.prefWidthProperty().bind(navbarContainer.widthProperty());
                navbarContainer.getChildren().add(navBar);
            }

            @Override
            public void decrement(int steps) {
                setValue(getValue().minusMinutes(steps * 30L));
            }

            @Override
            public void increment(int steps) {
                setValue(getValue().plusMinutes(steps * 30L));
            }
        };

        orarioAssunzione.setValueFactory(valueFactory);
        orarioAssunzione.setEditable(false); // opzionale: non permette scrittura manuale

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

        dataAssunzione.getEditor().setDisable(true);
        dataAssunzione.getEditor().setOpacity(1);

        this.taxCode = Session.getInstance().getTaxCode();
        this.farmacoAssunto.getItems().setAll(model.getFarmaciTerapie(taxCode));
    }

    @FXML
    public void onResetPressed(){

        farmacoAssunto.cancelEdit();
        quantitaAssunta.setText("");
        dataAssunzione.setValue(null);
        orarioAssunzione.cancelEdit();

    }

    @FXML
    public void onSubmitPressed(){

        int risultatoInserimento = model.inserimentoFarmacoAssunto(taxCode, farmacoAssunto.getValue(), quantitaAssunta.getText(),
                                                                    dataAssunzione.getValue(), orarioAssunzione.getValue());

        if(risultatoInserimento == 0){

            ViewNavigator.navigateToPaziente();

        } else { checkOutput(risultatoInserimento); }

    }

    public void checkOutput(int valoreOutput) {

        System.out.println("Valori non inseriti");

        farmacoAssunto.cancelEdit();

        if(valoreOutput == 1) {

            quantitaAssunta.setText("");
            messaggioErrore("Quantita' inserita non valida!");

        } else if(valoreOutput == 2) {

            messaggioErrore("Tutti i campi devono essere compilati!");

        } else if(valoreOutput == 3) {

            dataAssunzione.setValue(null);
            orarioAssunzione.cancelEdit();
            messaggioErrore("Non puoi inserire rilevazioni future!");

        } else if(valoreOutput == 4) {

            dataAssunzione.setValue(null);
            orarioAssunzione.cancelEdit();
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
