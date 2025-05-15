
package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VisualizzaListaUtentiModel {
    private static final String DB_URL = "jdbc:sqlite:database.db";

    public List<Utente> getTuttiGliUtenti() {
        List<Utente> lista = new ArrayList<>();

        String query = "SELECT * FROM loginTable";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String taxCode = rs.getString("Codice Fiscale");
                String password = rs.getString("Password");
                String nome = rs.getString("Nome");
                String cognome = rs.getString("Cognome");
                String email = rs.getString("Email");
                Date birthDate = rs.getDate("Data di nascita");
                String address = rs.getString("Indirizzo");
                int number = rs.getInt("Numero");
                String city = rs.getString("Città");
                int cap = rs.getInt("Cap");
                String gender = rs.getString("Genere");
                int telephoneNumber = rs.getInt("Numero di telefono");

                Utente utente = new Utente(taxCode, password, nome, cognome, email, birthDate, address, number, city, cap, gender, telephoneNumber);
                lista.add(utente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
