package model.Diabetologo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AggiungiTerapiaModel {
    private static final String DB_URL = "jdbc:sqlite:mydatabase.db?busy_timeout=5000";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
    public void insertData(String taxCode, String terapia, String farmacoPrescritto, int quantita, int numeroAssunzioniGiornaliere, String indicazioni) {
        String sql = "INSERT INTO terapiePrescritte (taxCode, terapia, `farmaco_prescritto`, quantita, `numero_assunzioni_giornaliere`, `indicazioni`) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             var pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, taxCode);
            pstmt.setString(2, terapia);
            pstmt.setString(3, farmacoPrescritto);
            pstmt.setInt(4, quantita);
            pstmt.setInt(5, numeroAssunzioniGiornaliere);
            pstmt.setString(6, indicazioni);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
