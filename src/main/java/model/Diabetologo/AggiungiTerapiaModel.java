package model.Diabetologo;

import controller.Session;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class AggiungiTerapiaModel {
    private static final String DB_URL = "jdbc:sqlite:mydatabase.db?busy_timeout=5000";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
    public int insertData(String taxCode, String terapia, String farmacoPrescritto, String quantita, int numeroAssunzioniGiornaliere, String indicazioni) {

        String query = "SELECT farmaco_prescritto FROM terapiePrescritte WHERE taxCode = '" + taxCode + "'";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery()){

            while (rs.next()) {

                if(rs.getString("farmaco_prescritto").equals(farmacoPrescritto)) { return -1;}

            }

        } catch (Exception e){
            System.out.println("Errore caricamento terapie prescritte: " + e.getMessage());
            return -2;
        }

        String sql = "INSERT INTO terapiePrescritte (taxCode, terapia, `farmaco_prescritto`, quantita, `numero_assunzioni_giornaliere`, `indicazioni`, 'dataPrescrizione') VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             var pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, taxCode);
            pstmt.setString(2, terapia);
            pstmt.setString(3, farmacoPrescritto);
            pstmt.setString(4, quantita);
            pstmt.setInt(5, numeroAssunzioniGiornaliere);
            pstmt.setString(6, indicazioni);
            pstmt.setString(7, LocalDate.now().toString());

            pstmt.executeUpdate();

            LogOperationModel.loadLogOperation(Session.getInstance().getTaxCode(), "Prescritta terapia: " + terapia, taxCode, LocalDateTime.now());
            return 0;

        } catch (SQLException e) {
            System.out.println("Errore salvataggio terapia: " + e.getMessage());
            return -2;
        }

    }
}
