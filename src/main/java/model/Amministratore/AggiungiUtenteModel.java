package model.Amministratore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AggiungiUtenteModel {

    private static final String URL = "jdbc:sqlite:mydatabase.db";

    public void inserisciUtente(String taxCode, String password, String userType) throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL)) {
            String query = "INSERT INTO loginTable (taxCode, password, userType) VALUES (?, ?, ?);";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, taxCode);
                pstmt.setString(2, password);
                pstmt.setString(3, userType);
                pstmt.executeUpdate();
            }
        }
    }
}
