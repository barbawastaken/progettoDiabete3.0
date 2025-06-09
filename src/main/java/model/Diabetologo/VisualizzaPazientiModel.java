package model.Diabetologo;

import model.Amministratore.Paziente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VisualizzaPazientiModel {

    private static final String DB_URL = "jdbc:sqlite:mydatabase.db?busy_timeout=5000";

    public List<Paziente> getPazientiByDiabetologo(String diabetologoCodiceFiscale) {
        List<Paziente> pazienti = new ArrayList<>();

        String query = "SELECT * FROM utenti WHERE diabetologo=?";



        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, diabetologoCodiceFiscale);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Paziente p = new Paziente(
                            rs.getString("taxCode"),
                            rs.getString("nome"),
                            rs.getString("cognome"),
                            rs.getString("gender"),
                            rs.getString("birthday"),
                            rs.getString("password"),
                            rs.getString("address"),
                            rs.getString("number"),
                            rs.getString("cap"),
                            rs.getString("CountryOfResidence"),
                            rs.getString("city"),
                            rs.getString("email"),
                            rs.getString("telephoneNumber"),
                            rs.getString("userType"),
                            rs.getDouble("Peso"),
                            rs.getDouble("Altezza")
                    );
                    pazienti.add(p);
                }
            }




        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pazienti;
    }

    public List<String> getPazientiInRitardo() throws SQLException {
        String not = "SELECT taxCode FROM rilevazioniGlicemiche\n" +
                "GROUP BY taxCode\n" +
                "HAVING data > DATE('now', '-3 day');";

        try(Connection conn = DriverManager.getConnection(DB_URL)){
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(not);
            List<String>pazientiInRitardo = new ArrayList<>();
            while (rs.next()) {
                pazientiInRitardo.add(rs.getString("taxCode"));
                System.out.println(rs.getString("taxCode"));
            }
            return pazientiInRitardo;

        }
    }
}
