package model.Diabetologo;

import controller.Session;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TabellaModificaTerapiaModel {

    private static final String DB_URL = "jdbc:sqlite:mydatabase.db?busy_timeout=5000";

    public static void rimuoviTerapia(Terapia selected) {

        String query = "DELETE FROM terapiePrescritte WHERE taxCode = ? AND terapia = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, selected.getTaxCode());
            pstmt.setString(2, selected.getTerapia());
            pstmt.executeUpdate();

            LogOperationModel.loadLogOperation(Session.getInstance().getTaxCode(), "Rimossa terapia: " + selected.getTerapia(), selected.getTaxCode(), LocalDateTime.now());

        } catch (SQLException e) {
            System.out.println("Errore rimozione terapia '" + selected.getTerapia() + "' : " + e.getMessage());
        }

    }

    public List<Terapia> getTerapieByTaxCode(String taxCode) {
        List<Terapia> terapie = new ArrayList<>();

        String query = "SELECT * FROM terapiePrescritte WHERE taxCode = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, taxCode);

            try (ResultSet rs = stmt.executeQuery()) {
                while(rs.next()) {
                    Terapia t = new Terapia(
                            rs.getString("taxCode"),
                            rs.getString("terapia"),
                            rs.getString("farmaco_prescritto"),
                            rs.getString("quantita"),
                            rs.getString("numero_assunzioni_giornaliere"),
                            rs.getString("indicazioni"),
                            rs.getString("dataPrescrizione")
                    );
                    terapie.add(t);
                }
            } catch (SQLException e) {
                System.out.println("Errore nel caricamento delle terapie: " + e.getMessage());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return terapie;
    }
}
