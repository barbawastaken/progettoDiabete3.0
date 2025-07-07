package controller.Amministratore;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Amministratore.AggiungiUtenteModel;
import view.Amministratore.AggiungiUtenteView;

import java.util.regex.Pattern;

import java.time.LocalDate;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AggiungiUtenteController extends GestioneUtenti {


    private ToggleGroup ruolo;
    private HashMap<String, String> diabetologi = new HashMap<>();

    @FXML
    private void initialize() throws SQLException {
        AggiungiUtenteModel model = new AggiungiUtenteModel();

        gender.getItems().addAll("Maschio", "Femmina");
        medicoCurante.getItems().addAll();
        ruolo = new ToggleGroup();
        paziente.setToggleGroup(ruolo);
        diabetologo.setToggleGroup(ruolo);
        amministratore.setToggleGroup(ruolo);

        taxCodeError.setVisible(false);
        taxCodeError.setManaged(false);
        numberError.setVisible(false);
        numberError.setManaged(false);
        telephoneError.setVisible(false);
        telephoneError.setManaged(false);
        emailError.setVisible(false);
        emailError.setManaged(false);
        capError.setVisible(false);
        capError.setManaged(false);
        birthdayError.setVisible(false);
        birthdayError.setManaged(false);
        genderError.setVisible(false);
        genderError.setManaged(false);
        medicoCurante.setVisible(false);
        medicoCurante.setManaged(false);
        medicoCuranteText.setVisible(false);
        medicoCuranteText.setManaged(false);
        userAddedText.setVisible(false);
        userAddedText.setManaged(false);
        weight.setVisible(false);
        weight.setManaged(false);
        height.setVisible(false);
        height.setManaged(false);
        heightText.setVisible(false);
        heightText.setManaged(false);
        weightText.setVisible(false);
        weightText.setManaged(false);
        heightError.setVisible(false);
        heightError.setManaged(false);
        weightError.setVisible(false);
        weightError.setManaged(false);
        nomeError.setVisible(false);
        nomeError.setManaged(false);
        cognomeError.setVisible(false);
        cognomeError.setManaged(false);
        passwordError.setVisible(false);
        passwordError.setManaged(false);
        nationError.setVisible(false);
        nationError.setManaged(false);
        cityError.setVisible(false);
        cityError.setManaged(false);
        addressError.setVisible(false);
        addressError.setManaged(false);


        ruolo.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {

            if(newValue != null) {
                RadioButton radioButton = (RadioButton) newValue;
                String val = radioButton.getText();
                if ("PAZIENTE".equals(val)) {

                    try {
                        HashMap<String, String> map = model.getDiabetologi();
                        for(String s : map.keySet()){
                            medicoCurante.getItems().add(s);
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    medicoCuranteText.setVisible(true);
                    medicoCuranteText.setManaged(true);
                    medicoCurante.setVisible(true);
                    medicoCurante.setManaged(true);
                    weight.setVisible(true);
                    weight.setManaged(true);
                    height.setVisible(true);
                    height.setManaged(true);
                    weightText.setVisible(true);
                    weightText.setManaged(true);
                    heightText.setVisible(true);
                    heightText.setManaged(true);


                } else {
                    medicoCuranteText.setVisible(false);
                    medicoCuranteText.setManaged(false);
                    medicoCurante.setVisible(false);
                    medicoCurante.setManaged(false);
                    medicoCurante.getItems().clear();
                    weight.setVisible(false);
                    weight.setManaged(false);
                    height.setVisible(false);
                    height.setManaged(false);
                    weightText.setVisible(false);
                    weightText.setManaged(false);
                    heightText.setVisible(false);
                    heightText.setManaged(false);
                    //medicoCurante.setValue(null);
                    //height.setText(null);
                    //weight.setText(null);
                }
            }
        });

        diabetologi = model.getDiabetologi();

    }



    @FXML
    private void resetButtonPressed(){
        nome.setText("");
        cognome.setText("");
        email.setText("");
        password.setText("");
        address.setText("");
        citta.setText("");
        taxCode.setText("");
        address.setText("");
        cap.setText("");

    }

    @FXML
    private void sendButtonPressed() throws SQLException {


        AggiungiUtenteModel model = new AggiungiUtenteModel();
        RadioButton selected = (RadioButton) ruolo.getSelectedToggle();
        boolean isPaziente;
        if(!(selected == null) && selected.getText().equals("PAZIENTE")){
            isPaziente = true;
            super.checkForPazienti();
        } else{
            isPaziente = false;
            super.check();
        }



            if(isPaziente && !checkForPazienti()) {
                try {
                    model.inserisciUtente(taxCode.getText(), password.getText(), nome.getText(), cognome.getText(), address.getText(),
                            cap.getText(), citta.getText(), email.getText(), gender.getValue(), java.sql.Date.valueOf(birthday.getValue()),
                            number.getText(), telephone.getText(), selected.getText(), medicoCurante.getValue(), height.getText(), weight.getText());

                    userAddedText.setVisible(true);
                    userAddedText.setManaged(true);

                } catch (SQLException e) {
                    throw new RuntimeException(e);

                }
            } else if(!isPaziente && !check()) {
                try {
                    model.inserisciUtente(taxCode.getText(), password.getText(), nome.getText(), cognome.getText(), address.getText(),
                            cap.getText(), citta.getText(), email.getText(), gender.getValue(), java.sql.Date.valueOf(birthday.getValue()),
                            number.getText(), telephone.getText(), selected.getText(), "", "", "");

                    userAddedText.setVisible(true);
                    userAddedText.setManaged(true);

                } catch (SQLException e) {
                    throw new RuntimeException(e);

                }
            }



    }





    public AggiungiUtenteController(){

    }
}
