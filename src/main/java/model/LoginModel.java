package model;

import controller.Amministratore.AmministratoreController;
import controller.Session;
import controller.ViewNavigator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Amministratore.AmministratoreModel;
import view.Amministratore.AmministratoreView;

import java.io.IOException;
import java.sql.*;

public class LoginModel {

    public boolean checkLogin(String taxCode, String password){


        String DB_URL = "jdbc:sqlite:mydatabase.db";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement pstmt = conn.createStatement()){

            ResultSet rs = pstmt.executeQuery("SELECT * FROM loginTable");

            while (rs.next()) {

                if (rs.getString("taxCode").equals(taxCode) && rs.getString("password").equals(password)) {

                    String userType = rs.getString("userType");

                    /*
                        Qui dopo aver trovato una corrispondenza con i campi username e password, si trova il tipo di
                        utente che ha eseguito l'accesso, e viene anche creata la Sessione dell'utente stesso.
                     */

                    switch (userType) {
                        case "PAZIENTE" -> {
                            /*
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlView/paziente_view.fxml"));

                            Parent root = loader.load();
                            PazienteController pazienteController = loader.getController();
                            pazienteController.setTaxCode(taxCode);
                            Stage stage = new Stage();
                            stage.setTitle("Paziente");
                            stage.setScene(new Scene(root, 650, 500));
                            stage.show();
                            */
                            Session user = Session.getInstance();

                            user.setTaxCode(taxCode);
                            user.setNome(null);
                            user.setCognome(null);



                            /*
                            * appena possibile bisogna creare una nuova query che legga le info dalla tabella utenti anziché la tabella
                            logintable !!!!

                            *Se no un macello: Session può essere riempito solo con il codice fiscale, che potrebbe anche andare bene
                            se l'idea è quella di fare una query che ti cerca solo quello in mezzo a utenti
                             */
                             ViewNavigator.navigateToPaziente();

                            return true;


                        }
                        case "DIABETOLOGO" -> {
                            // Carica il file FXML associato all'interfaccia del diabetologo.
                            // getClass().getResource(...) cerca il file nella cartella "resources/fxmlView".

                            Session user = Session.getInstance();
                            user.setTaxCode(taxCode);
                            user.setNome(null);
                            user.setCognome(null);
                            ViewNavigator.navigateToDiabetologo();

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
                            return true;
                            //new AmministratoreController(model, view, loginStage);
                        }


                    }
                }
            }

            return false;

        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
