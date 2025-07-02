package controller.Amministratore;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.regex.Pattern;

public class GestioneUtenti {
    @FXML  protected ComboBox<String> gender;
    @FXML protected TextField nome;
    @FXML protected TextField cognome;
    @FXML protected TextField email;
    @FXML protected PasswordField password;
    @FXML protected TextField address;
    @FXML protected TextField citta;
    @FXML protected TextField number;
    @FXML protected TextField taxCode;
    @FXML protected RadioButton paziente;
    @FXML protected RadioButton diabetologo;
    @FXML protected RadioButton amministratore;
    @FXML protected ComboBox<String> medicoCurante;
    @FXML protected TextField cap;
    @FXML protected DatePicker birthday;
    @FXML protected TextField telephone;
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
    @FXML protected HashMap<String, String> diabetologi;

    public GestioneUtenti() {}

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

        if(flag==true){
            System.out.println("Problema trovato!!");
            return flag;
        }else{
            System.out.println("Nessun problema");
            return flag;
        }

    }

}
