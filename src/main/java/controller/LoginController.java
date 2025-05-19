package controller;

import javafx.stage.Stage;
import model.LoginModel;
import view.LoginView;
import java.sql.SQLException;

public class LoginController {
    private final LoginModel model;
    private final LoginView view;

    public LoginController(LoginModel model, LoginView view, Stage stage) throws SQLException {
        this.model = model;
        this.view = view;

        //model.printAllUsers();
        view.initialize(stage);

        view.getResetButton().setOnAction(e -> {
            view.getTaxCodeField().setText("");
            view.getPasswordField().setText("");
        });

        view.getAccessButton().setOnAction(e -> {

            String taxCode = view.getTaxCodeField().getText();
            String password = view.getPasswordField().getText();

            model.checkLogin(taxCode, password, stage, view);

        });
    }
}
