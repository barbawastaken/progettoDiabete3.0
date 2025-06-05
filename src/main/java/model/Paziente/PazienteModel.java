package model.Paziente;

import java.sql.*;
import java.util.HashMap;

public class PazienteModel {

    public PazienteModel() {

    }

    public HashMap<Date, Integer> getRilevazioni(String taxCode, Date from, Date to) throws SQLException {
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
    }
}
