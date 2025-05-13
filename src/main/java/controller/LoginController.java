package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.DiabetologoModel;
import model.LoginModel;
import model.PazienteModel;
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
                                    PazienteModel pm = new PazienteModel();
                                    PazienteView pv = new PazienteView();
                                    new PazienteController(pm, pv, stage);
                                }
                                case "DIABETOLOGO" -> {
                                    DiabetologoModel dm = new DiabetologoModel();
                                    DiabetologoView dv = new DiabetologoView();
                                    new DiabetologoController(dm, dv, stage);
                                }
                                case "AMMINISTRATORE" -> {
                                    new HomePages.AmministratoreFrame().start(stage);
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
