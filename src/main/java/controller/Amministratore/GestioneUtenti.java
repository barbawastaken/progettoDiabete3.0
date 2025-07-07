package controller.Amministratore;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.regex.Pattern;

public class GestioneUtenti {
    @FXML protected ComboBox<String> gender;
    @FXML protected TextField nome;
    @FXML protected TextField cognome;
    @FXML protected TextField email;
    @FXML protected PasswordField password;
    @FXML protected TextField address;
    @FXML protected TextField citta;
    @FXML protected TextField number;
    @FXML protected TextField taxCode;
    @FXML protected ComboBox<String> medicoCurante;
    @FXML protected TextField cap;
    @FXML protected DatePicker birthday;
    @FXML protected TextField telephone;
    @FXML protected RadioButton paziente;
    @FXML protected RadioButton diabetologo;
    @FXML protected RadioButton amministratore;
    @FXML protected Text taxCodeError;
    @FXML protected Text numberError;
    @FXML protected Text telephoneError;
    @FXML protected Text emailError;
    @FXML protected Text capError;
    @FXML protected Text birthdayError;
    @FXML protected Text genderError;
    @FXML protected Text medicoCuranteText;
    @FXML protected Text userAddedText;
    @FXML protected TextField nation;
    @FXML protected TextField weight;
    @FXML protected TextField height;
    @FXML protected Text weightError;
    @FXML protected Text heightError;
    @FXML protected Text nomeError;
    @FXML protected Text cognomeError;
    @FXML protected Text passwordError;
    @FXML protected Text heightText;
    @FXML protected Text weightText;
    @FXML protected Text cityError;
    @FXML protected Text addressError;
    @FXML protected Text nationError;


    public GestioneUtenti() {}

    /*
    qui si potrebbe implementare initialize in modo da non avere il aggiungi/modifica utente due classi praticamente uguali
     */

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
    private boolean isWeightValid(){
        System.out.println("Peso: " + Double.parseDouble(weight.getText()));
        return (Double.parseDouble(weight.getText()) > 0.00 && Double.parseDouble(weight.getText()) < 999.9 && !(weight.getText() == null));
    }

    private boolean isHeightValid(){
        System.out.println("Altezza: " + Double.parseDouble(height.getText()));
        return (Double.parseDouble(height.getText()) > 0.00 && Double.parseDouble(height.getText()) < 10.0 && !(height.getText() == null));
    }

    protected boolean check(){
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

        if(nome.getText() ==null){
            nomeError.setVisible(true);
            nomeError.setManaged(true);
            flag = true;
        } else{
            nomeError.setVisible(false);
            nomeError.setManaged(false);
        }
        if(cognome.getText()== null){
            cognomeError.setVisible(true);
            cognomeError.setManaged(true);
            flag = true;
        } else{
            cognomeError.setVisible(false);
            cognomeError.setManaged(false);
        }

        if(password.getText()== null){
            passwordError.setVisible(true);
            passwordError.setManaged(true);
            flag = true;
        } else{
            passwordError.setVisible(false);
            passwordError.setManaged(false);
        }
        if(citta.getText()== null){
            cityError.setVisible(true);
            cityError.setManaged(true);
            flag = true;
        } else {
            cityError.setVisible(false);
            cityError.setManaged(false);
        }
        if(nation.getText()==null){
            nationError.setVisible(true);
            nationError.setManaged(true);
            flag = true;
        } else {
            nationError.setVisible(false);
            nationError.setManaged(false);
        }
        if(address.getText().isEmpty()){
            addressError.setVisible(true);
            addressError.setManaged(true);
            flag = true;
        } else {
            addressError.setVisible(false);
            addressError.setManaged(false);
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
        if(!isWeightValid()){
            weightError.setVisible(true);
            weightError.setManaged(true);
        } else{
            weightError.setVisible(false);
            weightError.setManaged(false);
        }

        if(!isHeightValid()){
            heightError.setVisible(true);
            heightError.setManaged(true);
        } else{
            heightError.setVisible(false);
            heightError.setManaged(false);
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
            System.out.println("Problema trovato!!");
            return flag;
        }else{
            System.out.println("Nessun problema");
            return flag;
        }

    }

}
