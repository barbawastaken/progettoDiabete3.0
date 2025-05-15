package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import model.AggiungiUtenteModel;
import view.AggiungiUtenteView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;

import java.time.LocalDate;

public class AggiungiUtenteController {

    private AggiungiUtenteModel model;
    private AggiungiUtenteView view;

    public AggiungiUtenteController(AggiungiUtenteModel model, AggiungiUtenteView view, Stage stage) {
        this.model = model;
        this.view = view;
        view.start(stage);
        initController();
    }

    private void initController() {
        view.resetButton.setOnAction(e -> {
            view.nomeField.setText("");
            view.cognomeField.setText("");
            view.passwordField.setText("");

        });

        view.sendButton.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Toggle selectedToggle = view.toggleGroup.getSelectedToggle();
                    if (selectedToggle != null) {
                        RadioButton selected = (RadioButton) selectedToggle;
                        String nome = view.nomeField.getText();                             //qui sto aggiungendo tutti quanti i dati che dovranno
                        String cognome = view.cognomeField.getText();                       //essere inseriti all'interno del database "utenti", ora sta venendo
                        String password = view.passwordField.getText();                     //ancora creato, quindi le uniche cose che vengono caricate nel database
                        LocalDate birthday = view.datePicker.getValue();                    //sono i dati nella "loginTable", ma in futuro verrà tutto messo su utentiTable
                        String userType = selected.getText();
                        String taxCode = view.taxCodeField.getText();

                        model.inserisciUtente(taxCode, password, userType);
                        System.out.println("Dati inseriti");
                    } else {
                        System.out.println("Nessun tipo utente selezionato");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
