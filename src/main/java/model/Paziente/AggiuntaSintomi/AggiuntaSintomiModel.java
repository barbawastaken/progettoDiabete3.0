package model.Paziente.AggiuntaSintomi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

public class AggiuntaSintomiModel {


    public void inserisciSintomo(String taxCode, String symptom, String otherSpecifications) throws SQLException {

        String database = "jdbc:sqlite:mydatabase.db?busy_timeout=5000";
        Connection conn = DriverManager.getConnection(database);
        conn.setAutoCommit(false);

        String query = "INSERT INTO symptoms (taxCode, symptom, otherSpecifications) VALUES(?, ?, ?);";

        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, taxCode);
        ps.setString(2, symptom);
        ps.setString(3, otherSpecifications);
        ps.executeUpdate();
        conn.commit();
        ps.close();
        conn.close();


    }





}
