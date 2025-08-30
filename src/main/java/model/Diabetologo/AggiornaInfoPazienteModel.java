package model.Diabetologo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AggiornaInfoPazienteModel {
    private static final String DB_URL = "jdbc:sqlite:mydatabase.db?busy_timeout=5000";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public void insertData(String taxCode, String info, String taxCodeDiabetologo) {

        String sql = "INSERT INTO infoAggiuntivePaziente (taxCode, noteDaDiabetologo) VALUES (?, ?)";

        try (Connection conn = getConnection();
            var pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, taxCode);
            pstmt.setString(2, info);

            pstmt.executeUpdate();

            System.out.println("Informazioni aggiuntive salvate correttamente.");

        } catch (SQLException e) {
            System.out.println("Errore salvataggio terapia: " + e.getMessage());
        }
    }
}
