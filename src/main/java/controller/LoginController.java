package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.AmministratoreModel;
import model.DiabetologoModel;
import model.LoginModel;
import model.PazienteModel;
import view.AmministratoreView;
import view.DiabetologoView;
import view.LoginView;
import view.PazienteView;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
    private final LoginModel model;
    private final LoginView view;

    public LoginController(LoginModel model, LoginView view, Stage stage) throws SQLException {
        this.model = model;
        this.view = view;

        model.printAllUsers();
        view.initialize(stage);

        view.getResetButton().setOnAction(e -> {
            view.getUsernameField().setText("");
            view.getPasswordField().setText("");
        });

        view.getAccessButton().setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent buttonPressed) {
                try {
                    ResultSet rs = model.getLoginData();
                    boolean found = false;

                    while (rs.next()) {
                        if (rs.getString("username").equals(view.getUsernameField().getText())
                                && rs.getString("password").equals(view.getPasswordField().getText())) {

                            found = true;
                            String userType = rs.getString("userType");

                            switch (userType) {
                                case "PAZIENTE" -> {
                                    PazienteModel model = new PazienteModel();
                                    PazienteView view = new PazienteView();
                                    new PazienteController(model, view, stage);
                                }
                                case "DIABETOLOGO" -> {
                                    DiabetologoModel model = new DiabetologoModel();
                                    DiabetologoView view = new DiabetologoView();
                                    new DiabetologoController(model, view, stage);
                                }
                                case "AMMINISTRATORE" -> {
                                    AmministratoreModel model = new AmministratoreModel();
                                    AmministratoreView view = new AmministratoreView();
                                    new AmministratoreController(model, view, stage);
                                }
                            }
                            return;
                        }
                    }

                    if (!found) {
                        Text errorText = new Text("Username e/o password invalidi");
                        errorText.setFont(Font.font(14));
                        errorText.setX(50);
                        errorText.setY(40);
                        errorText.setFill(Color.RED);
                        view.getGroup().getChildren().add(errorText);

                        view.getUsernameField().setText("");
                        view.getPasswordField().setText("");
                    }

                } catch (SQLException e) {
                    stage.close();
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
