package model.Paziente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PazienteModel {

    private final static String DB_URL = "jdbc:sqlite:mydatabase.db";

    public PazienteModel() {

    }

    /*public HashMap<Date, Integer> getRilevazioni(String taxCode, Date from, Date to) throws SQLException {
        HashMap<Date, Integer> map = new HashMap<>();
        String sql;
        if(!from.equals(to)) {
            sql = "SELECT data, quantita FROM rilevazioniGlicemiche WHERE taxCode='"+ taxCode + "' AND data BETWEEN "+ from + " AND "+ to +";";
        } else{
            sql = "SELECT data, quantita FROM rilevazioniGlicemiche WHERE taxCode='"+ taxCode + "' AND data="+ from +";";
        }
        Connection conn = DriverManager.getConnection(sql);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            map.put(rs.getDate("data"), rs.getInt("quantita"));
        }
        conn.close();
        return map;
    }*/

    public List<TerapiaModel> getTerapie(String taxCode){

        List<TerapiaModel> terapie = new ArrayList<>();

        String query = "SELECT terapia, farmaco_prescritto, quantita, numero_assunzioni_giornaliere FROM terapiePrescritte WHERE taxCode = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, taxCode);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                TerapiaModel t = new TerapiaModel(
                        rs.getString("terapia"),
                        rs.getString("farmaco_prescritto"),
                        rs.getString("quantita"),
                        rs.getString("numero_assunzioni_giornaliere")
                );
                terapie.add(t);

            }

        } catch (Exception e) {
            System.out.println("Errore nel caricamento terapie: " + e);
        }

        return terapie;
    }
}
