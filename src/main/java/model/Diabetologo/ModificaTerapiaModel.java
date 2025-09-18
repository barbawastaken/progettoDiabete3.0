package model.Diabetologo;

import controller.Session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ModificaTerapiaModel {

    private static final String DB_URL = "jdbc:sqlite:mydatabase.db?busy_timeout=5000";

    public int updateData(String taxCode, String terapia, String farmacoPrescritto, String quantita, int numeroAssunzioniGiornaliere, String indicazioni, String farmacoOriginale) {

        String query = "SELECT farmaco_prescritto FROM terapiePrescritte WHERE taxCode = '" + taxCode + "'";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()){

            while (rs.next()) {

                if(rs.getString("farmaco_prescritto").equals(farmacoPrescritto) && !farmacoPrescritto.equals(farmacoOriginale)) { return -1;}

            }

        } catch (Exception e){
            System.out.println("Errore caricamento terapie prescritte: " + e.getMessage());
            return -2;
        }

        String query2 = "UPDATE terapiePrescritte SET terapia = ?, farmaco_prescritto = ?, quantita = ?, numero_assunzioni_giornaliere = ?, indicazioni = ?, dataPrescrizione = ? WHERE taxCode = ? AND terapia = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query2)) {

            stmt.setString(1, terapia);
            stmt.setString(2, farmacoPrescritto);
            stmt.setString(3, quantita);
            stmt.setInt(4, numeroAssunzioniGiornaliere);
            stmt.setString(5, indicazioni);
            stmt.setString(6, LocalDate.now().toString());

            stmt.setString(7, taxCode);
            stmt.setString(8, Session.getInstance().getTerapiaInEsame().getTerapia());

            stmt.executeUpdate();

            LogOperationModel.loadLogOperation(Session.getInstance().getTaxCode(), "Terapia modificata(nome originale): " + Session.getInstance().getTerapiaInEsame().getTerapia(), Session.getInstance().getPazienteInEsame().getTaxCode(), LocalDateTime.now());
            return 0;

        } catch(Exception e) {
            System.out.println("Errore nel salvataggio dei dati: " + e.getMessage());
            return -2;
        }

    }
}
