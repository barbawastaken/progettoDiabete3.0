package controller.Amministratore;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import model.Amministratore.AggiungiUtenteModel;
import view.Amministratore.AggiungiUtenteView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;

import java.time.LocalDate;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
                        LocalDate birthday = view.datePicker.getValue();
                        java.sql.Date birthdayForSQL = java.sql.Date.valueOf(birthday);
                        System.out.println(birthday.toString());                            //sono i dati nella "loginTable", ma in futuro verrà tutto messo su utentiTable
                        String userType = selected.getText();
                        System.out.println(userType);
                        String taxCode = view.taxCodeField.getText();
                        String address = view.addressField.getText();
                        String cap = view.capField.getText();
                        String email = view.emailField.getText();
                        String city = view.cityField.getText();
                        String number = view.numberField.getText();
                        String gender = view.genderField.getValue();
                        String telephone = view.telephoneField.getText();
                        String diabetologoSelezionato = view.diabetologoSelection.getValue();

                        model.inserisciUtente(taxCode, password, nome, cognome, address, cap, city, email, gender, birthdayForSQL,
                                number, telephone, userType, diabetologoSelezionato);

                        System.out.println("Dati inseriti");

                    } else {
                        System.out.println("Nessun tipo utente selezionato");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        view.toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {

            if(newValue != null) {
                RadioButton radioButton = (RadioButton) newValue;
                String val = radioButton.getText();
                System.out.println(val);
                if ("PAZIENTE".equals(val)) {

                    try {
                        HashMap<String, String> map = model.getDiabetologi();
                       for(String s : map.keySet()){
                           view.diabetologoSelection.getItems().add(s);
                       }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }


                    view.diabetologoSelection.setVisible(true);
                    view.diabetologoSelection.setManaged(true);
                    view.diabetologoSelectionText.setVisible(true);
                    view.diabetologoSelectionText.setManaged(true);
                } else {
                    view.diabetologoSelection.setVisible(false);
                    view.diabetologoSelection.setManaged(false);
                    view.diabetologoSelectionText.setVisible(false);
                    view.diabetologoSelectionText.setManaged(false);
                }
            }
        });


    }
}
