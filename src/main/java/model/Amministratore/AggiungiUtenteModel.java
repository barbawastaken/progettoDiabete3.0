package model.Amministratore;

import java.sql.*;
import java.time.LocalDate;

public class AggiungiUtenteModel {

    private static final String URL = "jdbc:sqlite:mydatabase.db?busy_timeout=30000";

    public void inserisciUtente(String taxCode, String password, String nome, String cognome, String address,
                                String cap, String city, String email, String gender, LocalDate birthday, String number, String telephone)
            throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL)) {
            String query = "INSERT INTO utenti (taxCode, password, nome, cognome, email, birthday, address," +
                    "number, city, cap, gender, telephoneNumber) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, taxCode);
                pstmt.setString(2, password);
                pstmt.setString(3, nome);
                pstmt.setString(4, cognome);
                pstmt.setString(5, email);
                pstmt.setDate(6, java.sql.Date.valueOf(birthday));
                pstmt.setString(7, address);
                pstmt.setString(8, number);
                pstmt.setString(9, city);
                pstmt.setString(10, cap);
                pstmt.setString(11, gender);
                pstmt.setString(12, telephone);

                pstmt.executeUpdate();
            }

        }
    }

    public void inserisciLogin(String taxCode, String password, String userType) throws SQLException {
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
