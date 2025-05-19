package model.Paziente.RilevazioneGlicemia;

import java.sql.*;
import java.time.LocalDate;

public class RilevazioneGlicemiaModel {

    private static final String DB_URL = "jdbc:sqlite:mydatabase.db";

    public int inserimentoRilevazioneGlicemia(String taxCode, int quantita, String momentoGiornata, String prePost,
                                                  LocalDate data){

        String sql = "INSERT INTO rilevazioniGlicemiche (taxCode, quantita, momentoGiornata, prePost, data) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM rilevazioniGlicemiche ORDER BY taxCode")) {

            while (rs.next()) {

                if (rs.getString("taxCode").equals(taxCode) && rs.getString("momentoGiornata").equals(momentoGiornata) &&
                        rs.getString("prePost").equals(prePost) && rs.getString("data").equals(data.toString())) {

                    return 1;
                }

                if (quantita < 40 || quantita > 200) { return 2; }

                if (data.isAfter(LocalDate.now())) { return 3; }

                if(momentoGiornata.isEmpty() || prePost.isEmpty() || data.toString().isEmpty()) { return 4; }

                System.out.println(rs.getString("data") + " " + LocalDate.now().toString());

            }

            pstmt.setString(1, taxCode);
            pstmt.setInt(2, quantita);
            pstmt.setString(3, momentoGiornata);
            pstmt.setString(4, prePost);
            pstmt.setString(5, data.toString());

            pstmt.executeUpdate();
            return 0;

        } catch (Exception e) {
            System.out.println("Errore: " + e);
            return 5;
        }
    }
}
