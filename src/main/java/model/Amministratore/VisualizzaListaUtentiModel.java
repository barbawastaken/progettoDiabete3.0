
package model.Amministratore;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VisualizzaListaUtentiModel {
    private static final String DB_URL = "jdbc:sqlite:mydatabase.db?busy_timeout=5000";

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
                String countryOfResidence = rs.getString("CountryOfResidence");
                String gender = rs.getString("gender");
                String telephoneNumber = rs.getString("telephoneNumber");
                String userType = rs.getString("userType");
                String diabetologo = rs.getString("diabetologo");
                Double weight = rs.getDouble("Peso");
                Double height = rs.getDouble("Altezza");

                Utente utente = new Utente(taxCode, password, nome, cognome, email, birthDate, address, number, city, cap, countryOfResidence, gender, telephoneNumber, userType, diabetologo, weight, height);
                lista.add(utente);

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
    public void rimuoviUtente(Utente utente) {

        String sql = "DELETE FROM utenti WHERE taxCode = ?";
        rimuoviUtenteCompleto(sql, utente);

        sql = "DELETE FROM loginTable WHERE taxCode = ?";
        rimuoviUtenteCompleto(sql, utente);

        sql = "DELETE FROM aggiuntaSintomi WHERE taxCode = ?";
        rimuoviUtenteCompleto(sql, utente);

        sql = "DELETE FROM assunzioneFarmaci WHERE taxCode = ?";
        rimuoviUtenteCompleto(sql, utente);

        sql = "DELETE FROM patologieConcomitanti WHERE taxCode = ?";
        rimuoviUtenteCompleto(sql, utente);

        sql = "DELETE FROM rilevazioniGlicemiche WHERE taxCode = ?";
        rimuoviUtenteCompleto(sql, utente);

        sql = "DELETE FROM terapiePrescritte WHERE taxCode = ?";
        rimuoviUtenteCompleto(sql, utente);
    }

    private void rimuoviUtenteCompleto(String query, Utente utente){

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, utente.getTaxCode());
            pstmt.executeUpdate();

            System.out.println("Utente eliminato: " + utente.getTaxCode());

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void aggiornaUtente(Utente utente, String oldTaxCode) {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:mydatabase.db")) {
            String sql = "UPDATE utenti SET taxCode=?, password=?, nome=?, cognome=?, email=?, birthday=?, address=?, number=?, city=?, cap=?, gender=?, telephoneNumber=?, userType=?, diabetologo=?, CountryOfResidence=?, Altezza=?, Peso=? WHERE taxCode=?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, utente.getTaxCode());
                stmt.setString(2, utente.getPassword());
                stmt.setString(3, utente.getNome());
                stmt.setString(4, utente.getCognome());
                stmt.setString(5, utente.getEmail());
                stmt.setString(6, utente.getBirthday().toString());
                stmt.setString(7, utente.getAddress());
                stmt.setInt(8, utente.getNumber());
                stmt.setString(9, utente.getCity());
                stmt.setInt(10, utente.getCap());
                stmt.setString(11, utente.getGender());
                stmt.setString(12, utente.getTelephone());
                stmt.setString(13, utente.getRole());
                stmt.setString(14, utente.getDiabetologo());
                stmt.setString(15, utente.getCountryOfResidence());
                stmt.setDouble(16, utente.getWeight());
                stmt.setDouble(17, utente.getHeight());
                stmt.setString(18, oldTaxCode);

                stmt.executeUpdate();
                System.out.println("Utente aggiornato: " + utente.getTaxCode());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
