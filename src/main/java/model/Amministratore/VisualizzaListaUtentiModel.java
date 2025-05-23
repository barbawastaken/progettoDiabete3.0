
package model.Amministratore;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VisualizzaListaUtentiModel {
    private static final String DB_URL = "jdbc:sqlite:mydatabase.db";

    public VisualizzaListaUtentiModel() {}

    public List<Utente> getTuttiGliUtenti() {
        List<Utente> lista = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM utenti")) {

            while (rs.next()) {
                String taxCode = rs.getString("taxCode");
                String password = rs.getString("password");
                String nome = rs.getString("nome");
                String cognome = rs.getString("cognome");
                String email = rs.getString("email");
                String birthDateStr = rs.getString("birthday");
                Date birthDate = Date.valueOf(birthDateStr); // java.sql.Date
                String address = rs.getString("address");
                int number = rs.getInt("number");
                String city = rs.getString("city");
                int cap = rs.getInt("cap");
                String gender = rs.getString("gender");
                String telephoneNumber = rs.getString("telephoneNumber");
                String userType = rs.getString("userType");
                String diabetologo = rs.getString("diabetologo");

                Utente utente = new Utente(taxCode, password, nome, cognome, email, birthDate, address, number, city, cap, gender, telephoneNumber, userType, diabetologo);
                lista.add(utente);

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
    public void rimuoviUtente(Utente utente) {
        String sql = "DELETE FROM utenti WHERE taxCode = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, utente.getTaxCode());
            pstmt.executeUpdate();

            System.out.println("Utente eliminato: " + utente.getTaxCode());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
