
package model.Amministratore;

import java.sql.*;
import java.util.ArrayList;
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
                String number = rs.getString("number");
                String city = rs.getString("city");
                int cap = rs.getInt("cap");
                String countryOfResidence = rs.getString("CountryOfResidence");
                String gender = rs.getString("gender");
                String telephoneNumber = rs.getString("telephoneNumber");
                String userType = rs.getString("userType");
                String diabetologo = rs.getString("diabetologo");
                double weight = rs.getDouble("Peso");
                double height = rs.getDouble("Altezza");

                Utente utente = new Utente(taxCode, password, nome, cognome, email, birthDate, address, number, city, cap, countryOfResidence, gender, telephoneNumber, userType, diabetologo, weight, height);
                lista.add(utente);

            }


        } catch (SQLException e) {
            System.out.println("Errore caricamento degli utenti: " + e.getMessage());
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

        System.out.println("Utente eliminato: " + utente.getTaxCode());
    }

    private void rimuoviUtenteCompleto(String query, Utente utente){

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, utente.getTaxCode());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Errore rimozione utente '" + utente.getTaxCode() + "' : " + e.getMessage());
        }

    }
}
