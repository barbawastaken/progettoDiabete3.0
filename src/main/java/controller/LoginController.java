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

        /*view.getAccessButton().setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent buttonPressed) {
                try {
                    ResultSet rs = model.getLoginData();
                    boolean found = false;

                    while (rs.next()) {
                        if (rs.getString("taxCode").equals(view.getTaxCodeField().getText())
                                && rs.getString("password").equals(view.getPasswordField().getText())) {

                            String taxCode = rs.getString("taxCode");
                            found = true;
                            String userType = rs.getString("userType");

                            switch (userType) {
                                case "PAZIENTE" -> {
                                    PazienteModel model = new PazienteModel();
                                    PazienteView view = new PazienteView();
                                    new PazienteController(taxCode, model, view, stage);
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

                        view.getTaxCodeField().setText("");
                        view.getPasswordField().setText("");
                    }

                } catch (SQLException e) {
                    stage.close();
                    throw new RuntimeException(e);
                }
            }
        });*/

        view.getAccessButton().setOnAction(e -> {

            String taxCode = view.getTaxCodeField().getText();
            String password = view.getPasswordField().getText();

            model.checkLogin(taxCode, password, stage, view);

        });
    }
}
