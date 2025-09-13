package model;

import controller.Session;
import controller.ViewNavigator;
import java.sql.*;

public class LoginModel {

    public static boolean checkLogin(String taxCode, String password){


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

                            return true;

                        }
                        case "PRIMARIO" -> {
                            // Carica il file FXML associato all'interfaccia del diabetologo.
                            // getClass().getResource(...) cerca il file nella cartella "resources/fxmlView".

                            Session user = Session.getInstance();
                            user.setTaxCode(taxCode);
                            user.setNome(null);
                            user.setCognome(null); //commento per non far uscire il warning di codice duplicato
                            ViewNavigator.navigateToDiabetologo();

                            return true;

                        }
                        case "AMMINISTRATORE" -> {

                           Session user = Session.getInstance();
                           user.setNome(null);
                           user.setCognome(null);
                           ViewNavigator.navigateToAmministratore();

                           return true;
                        }


                    }
                }
            }

            return false;

        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
            return false;
        }
    }

}
