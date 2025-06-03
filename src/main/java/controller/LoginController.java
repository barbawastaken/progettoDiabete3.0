package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.LoginModel;
import view.LoginView;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {

    private final LoginModel loginModel = new LoginModel();
    public String taxCode;

    @FXML
    private TextField taxCodeField;

    @FXML
    private TextField passwordField;

    @FXML
    private void onLoginPressed() throws IOException {
        String taxCode = taxCodeField.getText();
        String password = passwordField.getText();
        System.out.println(taxCode);
        System.out.println(password);
        this.taxCode = taxCode;
        loginModel.checkLogin(taxCode, password);
        Stage stage = (Stage) taxCodeField.getScene().getWindow();
        stage.close();

    }

    @FXML
    private void onResetPressed(){
        taxCodeField.clear();
        passwordField.clear();
    }

    public LoginController(LoginModel model, LoginView view, Stage stage) throws SQLException, IOException {

    }

    public LoginController(){

    }

    public void setTaxCode(String taxCode){ this.taxCode = taxCode; }
}
