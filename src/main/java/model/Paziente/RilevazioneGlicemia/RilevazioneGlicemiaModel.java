package model.Paziente.RilevazioneGlicemia;

import java.sql.*;
import java.time.LocalDate;

public class RilevazioneGlicemiaModel {

    private static final String DB_URL = "jdbc:sqlite:mydatabase.db";

    public int inserimentoRilevazioneGlicemia(String taxCode, String quantita, String momentoGiornata, String prePost,
                                                  LocalDate data){

        String sql = "INSERT INTO rilevazioniGlicemiche (taxCode, quantita, momentoGiornata, prePost, data) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM rilevazioniGlicemiche ORDER BY taxCode")) {

            if(quantita.isEmpty()) { return 4;}

            int quantitaInt;

            try {

                quantitaInt = Integer.parseInt(quantita);

                if (quantitaInt < 40 || quantitaInt > 200) { return 2; }
            } catch (NumberFormatException e) {
                return 2;
            }

            if(momentoGiornata == null || prePost == null || data == null) { return 4; }


            if (data.isAfter(LocalDate.now())) { return 3; }

            while (rs.next()) {

                if (rs.getString("taxCode").equals(taxCode) && rs.getString("momentoGiornata").equals(momentoGiornata) &&
                        rs.getString("prePost").equals(prePost) && rs.getString("data").equals(data.toString())) {

                    return 1;
                }

            }

            pstmt.setString(1, taxCode);
            pstmt.setInt(2, quantitaInt);
            pstmt.setString(3, momentoGiornata);
            pstmt.setString(4, prePost);
            pstmt.setString(5, data.toString());

            pstmt.executeUpdate();
            return 0;

        } catch (Exception e) {
            System.out.println("Errore caricamento dati rilevazione: " + e);
            return 5;
        }
    }
}
