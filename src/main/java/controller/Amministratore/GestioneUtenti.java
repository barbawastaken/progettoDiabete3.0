package controller.Amministratore;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.regex.Pattern;

public class GestioneUtenti {


    
    protected ComboBox<String> gender;
    
    protected TextField nome;
    
    protected TextField cognome;
    
    protected TextField email;
    
    protected PasswordField password;
    
    protected TextField address;
    
    protected TextField citta;
    
    protected TextField number;
    
    protected TextField taxCode;
    
    protected RadioButton paziente;
    
    protected RadioButton diabetologo;
    
    protected RadioButton amministratore;
    
    protected ComboBox<String> medicoCurante;
    
    protected TextField cap;
    
    protected DatePicker birthday;
    
    protected TextField telephone;
    
    protected Text taxCodeError;
    
    protected Text numberError;
    
    protected Text telephoneError;
    
    protected Text emailError;
    
    protected Text capError;
    
    protected Text birthdayError;
    
    protected Text genderError;
    
    protected Text medicoCuranteText;
    
    protected Text userAddedText;
    
    protected TextField nation;
    
    protected TextField weight;
    
    protected TextField height;

    protected HashMap<String, String> diabetologi;

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
