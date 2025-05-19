package model.Amministratore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ModificaUtenteModel {
    private static final String DB_URL = "jdbc:sqlite:mydatabase.db?busy_timeout=5000";
    public void aggiornaUtente(Utente utente) {
        String sql = "UPDATE utenti SET nome=?, cognome=?, email=?, address=?, number=?, city=?, cap=?, gender=?, " +
                "telephoneNumber=?, userType=?, diabetologo=? WHERE taxCode=?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, utente.getNome());
            pstmt.setString(2, utente.getCognome());
            pstmt.setString(3, utente.getEmail());
            pstmt.setString(4, utente.getAddress());
            pstmt.setInt(5, utente.getNumber());
            pstmt.setString(6, utente.getCity());
            pstmt.setInt(7, utente.getCap());
            pstmt.setString(8, utente.getGender());
            pstmt.setString(9, utente.getTelephoneNumber());
            pstmt.setString(10, utente.getUserType());
            pstmt.setString(11, utente.getDiabetologo());
            pstmt.setString(12, utente.getTaxCode());

            pstmt.executeUpdate();
            int rows = pstmt.executeUpdate();
            if (rows == 0) {
                System.out.println("Nessun utente aggiornato.");
            } else {
                System.out.println("Utente aggiornato con successo.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
