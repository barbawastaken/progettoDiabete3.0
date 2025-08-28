package controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
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
    private PasswordField passwordField;

    @FXML
    private Text error;

    @FXML
    private void onLoginPressed() throws IOException {

        String taxCode = taxCodeField.getText();
        String password = passwordField.getText();
        System.out.println(taxCode);
        System.out.println(password);
        this.taxCode = taxCode;
        if(loginModel.checkLogin(taxCode, password)){


            /*
            qua ci dovrebbe essere una istanza di viewnavigator che chiamerebbe la funzione
            to
             */

        } else{error.setVisible(true);}




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

    public LoginController(LoginModel model, LoginView view, Stage stage) throws SQLException, IOException {

    }

    public LoginController(){

    }

    public void setTaxCode(String taxCode){ this.taxCode = taxCode; }
}
