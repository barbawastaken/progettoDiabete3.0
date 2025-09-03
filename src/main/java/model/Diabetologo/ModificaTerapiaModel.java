package model.Diabetologo;

import controller.Session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDate;

public class ModificaTerapiaModel {

    private static final String DB_URL = "jdbc:sqlite:mydatabase.db?busy_timeout=5000";

    public void updateData(String taxCode, String terapia, String farmacoPrescritto, String quantita, int numeroAssunzioniGiornaliere, String indicazioni) {

        String query = "UPDATE terapiePrescritte SET terapia = ?, farmaco_prescritto = ?, quantita = ?, numero_assunzioni_giornaliere = ?, indicazioni = ? WHERE taxCode = ? AND terapia = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, terapia);
            stmt.setString(2, farmacoPrescritto);
            stmt.setString(3, quantita);
            stmt.setInt(4, numeroAssunzioniGiornaliere);
            stmt.setString(5, indicazioni);

            stmt.setString(6, taxCode);
            stmt.setString(7, Session.getInstance().getTerapiaInEsame().getTerapia());

            stmt.executeUpdate();

            LogOperationModel.loadLogOperation(Session.getInstance().getTaxCode(), "Terapia modificata(nome originale): " + Session.getInstance().getTerapiaInEsame().getTerapia(), Session.getInstance().getPazienteInEsame().getTaxCode(), LocalDate.now());

        } catch(Exception e) {
            System.out.println("Errore nel salvataggio dei dati: " + e.getMessage());
        }

    }
}
