package model.Amministratore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ModificaUtenteModel {
    private static final String DB_URL = "jdbc:sqlite:mydatabase.db?busy_timeout=5000";

    public void aggiornaUtente(Utente utente, String vecchioTaxCode) {
        String updateUtenteUtenti = "UPDATE utenti SET taxCode=?, password=?, nome=?, cognome=?, email=?, birthday=?, address=?, number=?, city=?, cap=?, gender=?, " +
                "telephoneNumber=?, userType=?, diabetologo=? WHERE taxCode=?";
        String updateUtenteLogin = "UPDATE loginTable SET taxCode=?, password=?, userType=? WHERE taxCode=?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(updateUtenteUtenti);
             PreparedStatement pstmt2 = conn.prepareStatement(updateUtenteLogin)) {

            // Query utenti
            pstmt.setString(1, utente.getTaxCode());
            pstmt.setString(2, utente.getPassword());
            pstmt.setString(3, utente.getNome());
            pstmt.setString(4, utente.getCognome());
            pstmt.setString(5, utente.getEmail());
            pstmt.setString(6, utente.getBirthDate().toString());
            pstmt.setString(7, utente.getAddress());
            pstmt.setInt(8, utente.getNumber());
            pstmt.setString(9, utente.getCity());
            pstmt.setInt(10, utente.getCap());
            pstmt.setString(11, utente.getGender());
            pstmt.setString(12, utente.getTelephoneNumber());
            pstmt.setString(13, utente.getUserType());
            pstmt.setString(14, utente.getDiabetologo());
            pstmt.setString(15, vecchioTaxCode); // <-- importante!

            int rows = pstmt.executeUpdate();
            if (rows == 0) {
                System.out.println("Nessun utente aggiornato.");
            } else {
                System.out.println("Utente aggiornato con successo nella tabella 'utenti'.");
            }

            // Query loginTable
            pstmt2.setString(1, utente.getTaxCode());
            pstmt2.setString(2, utente.getPassword());
            pstmt2.setString(3, utente.getUserType());
            pstmt2.setString(4, vecchioTaxCode);

            pstmt2.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
