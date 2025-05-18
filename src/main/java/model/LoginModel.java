package model;

import controller.Amministratore.AmministratoreController;
import controller.Diabetologo.DiabetologoController;
import controller.Paziente.PazienteController;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Amministratore.AmministratoreModel;
import model.Diabetologo.DiabetologoModel;
import model.Paziente.PazienteModel;
import view.Amministratore.AmministratoreView;
import view.Diabetologo.DiabetologoView;
import view.LoginView;
import view.Paziente.PazienteView;

import java.sql.*;

public class LoginModel {
    //private final String url = "jdbc:sqlite:mydatabase.db?busy_timeout=5000";

    /*public void printAllUsers() throws SQLException {
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM loginTable");
            while (rs.next()) {
                System.out.println("username: " + rs.getString("taxCode") +
                        "\t\t\t password: " + rs.getString("password") +
                        "\t\t\t userType: " + rs.getString("userType"));
            }
            stmt.close();
        }
    }*/

    /*public ResultSet getLoginData() throws SQLException {
        conn = DriverManager.getConnection(url);
        stmt = conn.createStatement();
        return stmt.executeQuery("SELECT * FROM loginTable");
    }*/

    public void checkLogin(String taxCode, String password, Stage loginStage, LoginView loginView){

        String DB_URL = "jdbc:sqlite:mydatabase.db";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement pstmt = conn.createStatement()){

            ResultSet rs = pstmt.executeQuery("SELECT * FROM loginTable");

            while (rs.next()) {
                if (rs.getString("taxCode").equals(taxCode)
                        && rs.getString("password").equals(password)) {

                    //String taxCode = rs.getString("taxCode");
                    String userType = rs.getString("userType");

                    switch (userType) {
                        case "PAZIENTE" -> {
                            PazienteModel model = new PazienteModel();
                            PazienteView view = new PazienteView();
                            new PazienteController(taxCode, model, view, loginStage);
                        }
                        case "DIABETOLOGO" -> {
                            DiabetologoModel model = new DiabetologoModel();
                            DiabetologoView view = new DiabetologoView();
                            new DiabetologoController(model, view, loginStage);
                        }
                        case "AMMINISTRATORE" -> {
                            AmministratoreModel model = new AmministratoreModel();
                            AmministratoreView view = new AmministratoreView();
                            new AmministratoreController(model, view, loginStage);
                        }
                        default -> {
                            Text errorText = new Text("Username e/o password invalidi");
                            errorText.setFont(Font.font(14));
                            errorText.setX(50);
                            errorText.setY(40);
                            errorText.setFill(Color.RED);
                            loginView.getGroup().getChildren().add(errorText);

                            loginView.getTaxCodeField().setText("");
                            loginView.getPasswordField().setText("");
                        }
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }

    }

}
