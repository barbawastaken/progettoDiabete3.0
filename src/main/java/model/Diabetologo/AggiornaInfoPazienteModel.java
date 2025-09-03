package model.Diabetologo;

import java.sql.*;
import java.time.LocalDate;

public class AggiornaInfoPazienteModel {
    private static final String DB_URL = "jdbc:sqlite:mydatabase.db?busy_timeout=5000";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public String getInfoPaziente(String taxCode){

        String sql = "SELECT noteDaDiabetologo FROM infoAggiuntivePaziente WHERE taxCode = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, taxCode);
            ResultSet rs = pstmt.executeQuery();

            return rs.getString("noteDaDiabetologo");

        } catch (SQLException e) {
            System.out.println("Errore caricamento info paziente: " + e.getMessage());
            return null;
        }

    }

    public void insertData(String taxCode, String info, String taxCodeDiabetologo) {

        String sql = "INSERT INTO infoAggiuntivePaziente (taxCode, noteDaDiabetologo) VALUES (?, ?) ON CONFLICT(taxCode) DO UPDATE SET noteDaDiabetologo = excluded.noteDaDiabetologo";

        try (Connection conn = getConnection();
            var pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, taxCode);
            pstmt.setString(2, info);

            pstmt.executeUpdate();

            LogOperationModel.loadLogOperation(taxCodeDiabetologo, "Aggiornate informazioni paziente", taxCode, LocalDate.now());

        } catch (SQLException e) {
            System.out.println("Errore salvataggio info paziente: " + e.getMessage());
        }
    }
}
