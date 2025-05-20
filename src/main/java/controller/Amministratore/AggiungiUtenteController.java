package controller.Amministratore;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import model.Amministratore.AggiungiUtenteModel;
import view.Amministratore.AggiungiUtenteView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import java.util.regex.Pattern;

import java.time.LocalDate;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AggiungiUtenteController {

    private AggiungiUtenteModel model;
    private AggiungiUtenteView view;




    public AggiungiUtenteController(){

    }
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
            view.datePicker.setValue(null);
            view.emailField.setText("");
            view.addressField.setText("");
            view.cityField.setText("");
            view.capField.setText("");
            view.genderField.setValue(null);
            view.taxCodeField.setText("");
            view.numberField.setText("");
            view.telephoneField.setText("");



        });

        view.sendButton.setOnAction(new EventHandler<>() {          //con questo metodo raccogliamo tutti i valori inseriti in "aggiungi utente",
            @Override                                               //dopodiché chiamiamo model.inserisciUtente() che inserisce tutti i dati nel database
            public void handle(ActionEvent event) {
                try {
                    Toggle selectedToggle = view.toggleGroup.getSelectedToggle();
                    if (selectedToggle != null) {
                        RadioButton selected = (RadioButton) selectedToggle;
                        String nome = view.nomeField.getText();

                        String cognome = view.cognomeField.getText();
                        String password = view.passwordField.getText();
                        LocalDate birthday = view.datePicker.getValue();
                        java.sql.Date birthdayForSQL = java.sql.Date.valueOf(birthday);
                        System.out.println(birthday);
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

        view.emailField.textProperty().addListener((observable, oldValue, newValue) -> {
            Pattern validEmail = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$");      //[\\w.-] -> campo "libero", con lettere, punti e trattini
                                                                                            //@ -> simbolo obbligatorio
                                                                                            //\\.\\w{2,}$ ->almeno due caratteri preceduti da un punto
            int i = 0;
            if(!validEmail.matcher(newValue).matches()) {

                i++;
                System.out.println(i + " Email invalido");
            } else{
                System.out.println("VALIDOOO");
            }

        });

        view.taxCodeField.textProperty().addListener((observable, oldValue, newValue) -> {
           if(newValue.length() != 3){
               System.out.println("A che ghe son solo 3 caratteri");
           }
        });

        view.numberField.textProperty().addListener((observable, oldValue, newValue) -> {
            Pattern soloNumeri = Pattern.compile("^[0-9]*");
            int i=0;
            if(!soloNumeri.matcher(newValue).matches()){
                i++;
                System.out.println(i + " Il numero di telefono può contenere solo numeri, non lettere!");

            }

            if(newValue.length()!= 10){
                System.out.println("Il numero di telefono deve avere 10 numeri");
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
