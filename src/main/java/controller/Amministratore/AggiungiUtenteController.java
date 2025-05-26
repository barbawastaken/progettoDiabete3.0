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

public class AggiungiUtenteController {

    private AggiungiUtenteModel model;
    private AggiungiUtenteView view;

    @FXML
    private ComboBox<String> gender;

    @FXML
    private TextField nome;

    @FXML
    private TextField cognome;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private TextField address;

    @FXML
    private TextField citta;
    @FXML
    private TextField number;

    @FXML
    private TextField taxCode;

    @FXML
    private RadioButton paziente;

    @FXML
    private RadioButton diabetologo;

    @FXML
    private RadioButton amministratore;

    @FXML
    private ComboBox<String> medicoCurante;

    @FXML
    private TextField cap;

    @FXML
    private DatePicker birthday;

    @FXML
    private TextField telephone;

    @FXML
    private TextField phone;

    @FXML
    private Text taxCodeError;

    @FXML
    private Text numberError;

    @FXML
    private Text telephoneError;

    @FXML
    private Text emailError;

    @FXML
    private Text capError;

    @FXML
    private Text birthdayError;

    @FXML
    private Text genderError;

    @FXML
    private Text medicoCuranteText;

    @FXML
    private Text userAddedText;

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

                } else {
                    medicoCuranteText.setVisible(false);
                    medicoCuranteText.setManaged(false);
                    medicoCurante.setVisible(false);
                    medicoCurante.setManaged(false);
                    medicoCurante.getItems().clear();
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
        boolean flag = false;
        if(taxCode.getText().length() != 3){
            taxCodeError.setVisible(true);
            taxCodeError.setManaged(true);
            flag = true;
        } else{
            taxCodeError.setVisible(false);
            taxCodeError.setManaged(false);

        }

        if(cap.getText().length() != 5){
            capError.setVisible(true);
            capError.setManaged(true);
            flag = true;
        } else {
            capError.setVisible(false);
            capError.setManaged(false);

        }

        if(!isEmailValid()){
            emailError.setVisible(true);
            emailError.setManaged(true);
            flag = true;
        }  else{
            emailError.setVisible(false);
            emailError.setManaged(false);

        }

        if(!isTelephoneValid()){
            telephoneError.setVisible(true);
            telephoneError.setManaged(true);
            flag = true;
        } else{
            telephoneError.setVisible(false);
            telephoneError.setManaged(false);

        }

        if(!isCapValid()){
            capError.setVisible(true);
            capError.setManaged(true);
            flag = true;
        } else{
            capError.setVisible(false);
            capError.setManaged(false);
        }

        if(!isNumberValid()){
            numberError.setVisible(true);
            numberError.setManaged(true);
            flag = true;
        } else{
            numberError.setVisible(false);
            numberError.setManaged(false);
        }

        if(gender.getValue() == null){
            genderError.setVisible(true);
            genderError.setManaged(true);
            flag = true;
        } else{
            genderError.setVisible(false);
            genderError.setManaged(false);
        }

        if(birthday.getValue() == null){
            birthdayError.setVisible(true);
            birthdayError.setManaged(true);
            flag = true;
        } else{
            birthdayError.setVisible(false);
            birthdayError.setManaged(false);

        }
        if(flag){
            return;
        }

        AggiungiUtenteModel model = new AggiungiUtenteModel();
        RadioButton selected = (RadioButton) ruolo.getSelectedToggle();

        try {
            model.inserisciUtente(taxCode.getText(), password.getText(), nome.getText(), cognome.getText(), address.getText(),
                    cap.getText(), citta.getText(), email.getText(), gender.getValue(), java.sql.Date.valueOf(birthday.getValue()),
                    number.getText(), telephone.getText(), selected.getText(), diabetologi.get(medicoCurante.getValue()));

            userAddedText.setVisible(true);
            userAddedText.setManaged(true);

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }

    }



    private boolean isEmailValid(){
        Pattern validEmail = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$");
        //[\\w.-] -> campo "libero", con lettere, punti e trattini
        //@ -> simbolo obbligatorio
        //\\.\\w{2,}$ ->almeno due caratteri preceduti da un punto

        return validEmail.matcher(email.getText()).matches();
    }

    private boolean isTelephoneValid(){
        Pattern validTelephone = Pattern.compile("^\\d{10}$");

        return validTelephone.matcher(telephone.getText()).matches();
    }

    private boolean isCapValid(){
        Pattern validCap = Pattern.compile("^\\d{5}$");

        return validCap.matcher(cap.getText()).matches();
    }

    private boolean isNumberValid(){
        Pattern validNumber = Pattern.compile("^\\d{1,3}$");
        return validNumber.matcher(number.getText()).matches();
    }

    public AggiungiUtenteController(){

    }
}
