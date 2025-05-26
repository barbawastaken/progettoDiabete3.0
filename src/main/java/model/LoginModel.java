package model;

import controller.Amministratore.AmministratoreController;
import controller.Diabetologo.DiabetologoController;
import controller.Paziente.PazienteController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

import java.io.IOException;
import java.sql.*;

public class LoginModel {



    public void checkLogin(String taxCode, String password){

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

                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlView/paziente_view.fxml"));

                            Parent root = loader.load();
                            PazienteController pazienteController = loader.getController();
                            pazienteController.setTaxCode(taxCode);
                            Stage stage = new Stage();
                            stage.setTitle("Paziente");
                            stage.setScene(new Scene(root, 650, 500));
                            stage.show();


                        }
                        case "DIABETOLOGO" -> {
                            // Carica il file FXML associato all'interfaccia del diabetologo.
                            // getClass().getResource(...) cerca il file nella cartella "resources/fxmlView".
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlView/diabetologo_view.fxml"));

                            Parent root = loader.load(); // Carica il contenuto del file FXML e crea la struttura grafica
                            // Recupera l'istanza del controller associato al file diabetologo_view.fxml.
                            // Questo è il controller specificato con fx:controller nel file FXML.
                            DiabetologoController diabetologoController = loader.getController();
                            diabetologoController.setTaxCode(taxCode);
                            Stage stage = new Stage();
                            stage.setTitle("Diabetologo");
                            stage.setScene(new Scene(root, 650, 500));
                            stage.show();
                        }
                        case "AMMINISTRATORE" -> {

                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlView/amministratore_view.fxml"));
                            Parent root = loader.load();
                            AmministratoreController amministratoreController = loader.getController();
                            Stage stage = new Stage();
                            stage.setTitle("Amministratore");
                            stage.setScene(new Scene(root, 650, 500));
                            stage.show();
                            AmministratoreModel model = new AmministratoreModel();
                            AmministratoreView view = new AmministratoreView();
                            //new AmministratoreController(model, view, loginStage);
                        }

                    }
                }
            }

            Text errorText = new Text("Username e/o password invalidi");
            errorText.setFont(Font.font(14));
            errorText.setX(50);
            errorText.setY(40);
            errorText.setFill(Color.RED);
            //loginView.getGroup().getChildren().add(errorText);

            //loginView.getTaxCodeField().setText("");
            //loginView.getPasswordField().setText("");

        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
