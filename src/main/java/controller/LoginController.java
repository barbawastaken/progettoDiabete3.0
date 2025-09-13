package controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.LoginModel;

public class LoginController {

    private final LoginModel loginModel = new LoginModel();
    public String taxCode;

    @FXML
    private TextField taxCodeField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Text error;

    @FXML
    private void onLoginPressed() {

        String taxCode = taxCodeField.getText();
        String password = passwordField.getText();
        System.out.println(taxCode);
        System.out.println(password);
        this.taxCode = taxCode;
        if(!LoginModel.checkLogin(taxCode, password))
            error.setVisible(true);

    }

    @FXML
    private void onResetPressed(){
        taxCodeField.clear();
        passwordField.clear();
    }

    @FXML
    private void initialize() {

        error.setVisible(false);

    }

    public void setTaxCode(String taxCode){ this.taxCode = taxCode; }
}
