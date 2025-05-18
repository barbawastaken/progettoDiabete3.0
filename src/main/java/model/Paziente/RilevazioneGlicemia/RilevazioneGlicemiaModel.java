package model.Paziente.RilevazioneGlicemia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDate;

public class RilevazioneGlicemiaModel {

    private static final String DB_URL = "jdbc:sqlite:mydatabase.db";

    public boolean inserimentoRilevazioneGlicemia(String taxCode, int quantita, String momentoGiornata, String prePost,
                                                  LocalDate data){

        String sql = "INSERT INTO rilevazioniGlicemiche (taxCode, quantita, momentoGiornata, prePost, data) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, taxCode);
            pstmt.setInt(2, quantita);
            pstmt.setString(3, momentoGiornata);
            pstmt.setString(4, prePost);
            pstmt.setString(5, data.toString());

            pstmt.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
            return false;
        }
    }
}
