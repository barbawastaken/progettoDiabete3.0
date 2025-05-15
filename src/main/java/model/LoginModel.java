package model;

import java.sql.*;

public class LoginModel {
    private final String url = "jdbc:sqlite:mydatabase.db";

    public void printAllUsers() throws SQLException {
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM loginTable");
            while (rs.next()) {
                System.out.println("username: " + rs.getString("taxCode") +
                        "\t\t\t password: " + rs.getString("password") +
                        "\t\t\t userType: " + rs.getString("userType"));
            }
        }
    }

    public ResultSet getLoginData() throws SQLException {
        Connection conn = DriverManager.getConnection(url);
        Statement stmt = conn.createStatement();
        return stmt.executeQuery("SELECT * FROM loginTable");
    }
}
