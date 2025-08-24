package model.Diabetologo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class ModificaTerapiaModel {

    private static final String DB_URL = "jdbc:sqlite:mydatabase.db?busy_timeout=5000";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
    public void updateData(String taxCode, String terapia, String farmacoPrescritto, int quantita, int numeroAssunzioniGiornaliere, String indicazioni, String taxCodeDiabetologo) {

        String query = "UPDATE terapiePrescritte SET terapia = ?, farmaco_prescritto = ?, quantita = ?, numero_assunzioni_giornaliere = ?, indicazioni = ? WHERE taxCode = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, taxCode);
            stmt.setString(2, terapia);
            stmt.setString(3, farmacoPrescritto);
            stmt.setInt(4, quantita);
            stmt.setInt(5, numeroAssunzioniGiornaliere);
            stmt.setString(6, indicazioni);

            stmt.executeUpdate();
            System.out.println("Salvataggio modifiche eseguito!");

            //LogOperationModel.loadLogOperation(taxCodeDiabetologo, "Terapia modificata: " + nomeTerapiaOriginale, taxCodePaziente, LocalDate.now());


        } catch(Exception e) {
            System.out.println("Errore nel salvataggio dei dati: " + e.getMessage());
        }

    }
}
