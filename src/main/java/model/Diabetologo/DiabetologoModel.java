package model.Diabetologo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DiabetologoModel {

    private final static String DB_url = "jdbc:sqlite:mydatabase.db";

    public String getTaxCodeFromPaziente(String nomePaziente, String cognomePaziente){

        String query = "SELECT taxCode FROM utenti WHERE nome = ? AND cognome = ?";

        try(Connection conn = DriverManager.getConnection(DB_url);
            PreparedStatement pstmt = conn.prepareStatement(query)){

            pstmt.setString(1, nomePaziente);
            pstmt.setString(2, cognomePaziente);
            ResultSet rs = pstmt.executeQuery();

            return rs.getString("taxCode");

        } catch (Exception e) {
            System.out.println("Errore caricamento taxCode del paziente " + nomePaziente + " " + cognomePaziente + ": " + e.getMessage());
            return null;
        }

    }

    public Terapia getTerapia(String taxCodePaziente, String farmacoNotifica) {

        String query = "SELECT * FROM terapiePrescritte WHERE taxCode = ? AND farmaco_prescritto = ?";

        try(Connection conn = DriverManager.getConnection(DB_url);
            PreparedStatement pstmt = conn.prepareStatement(query)){

            pstmt.setString(1, taxCodePaziente);
            pstmt.setString(2, farmacoNotifica);
            ResultSet rs = pstmt.executeQuery();

            return new Terapia(
                    taxCodePaziente,
                    rs.getString("terapia"),
                    farmacoNotifica,
                    rs.getString("quantita"),
                    rs.getString("numero_assunzioni_giornaliere"),
                    rs.getString("indicazioni")
            );

        } catch (Exception e) {
            System.out.println("Errore creazione terapia per la modifica " + taxCodePaziente + " " + farmacoNotifica + ": " + e.getMessage());
            return null;
        }

    }
}