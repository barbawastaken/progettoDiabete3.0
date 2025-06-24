package model.Amministratore;

import controller.Amministratore.VisualizzaListaUtentiController;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import view.Amministratore.ModificaUtenteView;
import view.Amministratore.VisualizzaListaUtentiView;

import java.sql.*;
import java.util.HashMap;

public class ModificaUtenteModel {
    private static final String DB_URL = "jdbc:sqlite:mydatabase.db?busy_timeout=5000";

    public ModificaUtenteModel() {}

    public void aggiornaUtente(String vecchioTaxCode, Utente utente) {



        String updateUtenteUtenti = "UPDATE utenti " +
                "SET " +
                "taxCode=?, " +
                "password=?, " +
                "nome=?, " +
                "cognome=?, " +
                "email=?, " +
                "birthday=?, " +
                "address=?, " +
                "number=?, " +
                "city=?, " +
                "cap=?, " +
                "gender=?, " +
                "telephoneNumber=?, " +
                "userType=?, " +
                "diabetologo=?, " +
                "CountryOfResidence=?, " +
                "Altezza=?, " +
                "Peso=? " +
                "WHERE taxCode=?";
        String updateUtenteLogin = "UPDATE loginTable SET taxCode=?, password=?, userType=? WHERE taxCode=?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(updateUtenteUtenti);
             PreparedStatement pstmt2 = conn.prepareStatement(updateUtenteLogin)) {

            conn.setAutoCommit(false);

            // Query utenti

            System.out.println(utente.getTaxCode() + " " + vecchioTaxCode);
            pstmt.setString(1, utente.getTaxCode());
            pstmt.setString(2, BCrypt.hashpw(utente.getPassword(), BCrypt.gensalt()));
            pstmt.setString(3, utente.getNome());
            pstmt.setString(4, utente.getCognome());
            pstmt.setString(5, utente.getEmail());
            pstmt.setString(6, utente.getBirthday().toString());
            pstmt.setString(7, utente.getAddress());
            pstmt.setInt(8, utente.getNumber());
            pstmt.setString(9, utente.getCity());
            pstmt.setInt(10, utente.getCap());
            pstmt.setString(11, utente.getGender());
            pstmt.setString(12, utente.getTelephone());
            pstmt.setString(13, utente.getRole());
            System.out.println(utente.getRole());
            pstmt.setString(14, utente.getDiabetologo());
            pstmt.setString(15, utente.getCountryOfResidence());
            pstmt.setDouble(16,utente.getHeight());
            pstmt.setDouble(17,utente.getWeight());
            pstmt.setString(18, vecchioTaxCode); // <-- importante!

            int rows = pstmt.executeUpdate();
            if (rows == 0) {
                System.out.println("Nessun utente aggiornato.");
            } else {
                System.out.println("Utente aggiornato con successo nella tabella 'utenti'.");
            }

            // Query loginTable
            pstmt2.setString(1, utente.getTaxCode());
            pstmt2.setString(2, BCrypt.hashpw(utente.getPassword(), BCrypt.gensalt()));
            pstmt2.setString(3, utente.getRole());
            pstmt2.setString(4, vecchioTaxCode);

            rows = pstmt2.executeUpdate();

            if(rows != 0) { System.out.println("Login table aggiornata"); }


            conn.commit();

            VisualizzaListaUtentiModel visualizzaListaUtentiModel = new VisualizzaListaUtentiModel();
            VisualizzaListaUtentiView visualizzaListaUtentiView = new VisualizzaListaUtentiView();
            new VisualizzaListaUtentiController(visualizzaListaUtentiModel, visualizzaListaUtentiView, new Stage());

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private Utente utenteModificato(ModificaUtenteView modificaUtenteView) {
        return new Utente(
                modificaUtenteView.getTaxCode(),
                modificaUtenteView.getPassword(),
                modificaUtenteView.getNome(),
                modificaUtenteView.getCognome(),
                modificaUtenteView.getEmail(),
                java.sql.Date.valueOf(modificaUtenteView.getBirthday()),
                modificaUtenteView.getAddress(),
                modificaUtenteView.getNumber(),
                modificaUtenteView.getCity(),
                modificaUtenteView.getCap(),
                modificaUtenteView.getCountryOfResidence(),
                modificaUtenteView.getGender(),
                modificaUtenteView.getTelephone(),
                modificaUtenteView.getRole(),
                modificaUtenteView.getDiabetologo(),
                modificaUtenteView.getWeight(),
                modificaUtenteView.getHeight()
        );

    }
    public HashMap<String, String> getDiabetologi() throws SQLException {
        String findDiabetologi = "SELECT * FROM utenti WHERE userType='DIABETOLOGO'";
        HashMap<String, String> diabetologi = new HashMap<>();


        try (
                Connection conn = DriverManager.getConnection(DB_URL);
                Statement stmt = conn.createStatement();
        ) {
            ResultSet rs = stmt.executeQuery(findDiabetologi);


            while (rs.next()) {
                System.out.println("Zio pera " + rs.getString("taxCode"));
                diabetologi.put(((rs.getString("cognome")) + " (" + rs.getString("taxCode") + ")"),
                        rs.getString("taxCode"));
            }

            for (String key : diabetologi.keySet()) {
                System.out.println("Keysets: " + key);
                System.out.println("Diabetologi: " + diabetologi.get(key));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return diabetologi;
            /*
                Creiamo in questo modo una hashmap che ha come key la combo cognome + codice fiscale (quest'ultima sarebbe la key del database)
                su View ci sarà una comboBox che ci farà scegliere proprio la key. Una volta scelta basterà trovare con la key il codice fiscale
                corrispondente e avremo il CF del diabetologo
            */
    }

}
