package model.Paziente;

import controller.Session;
import javafx.scene.control.Alert;

import java.awt.*;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class EmailPazienteModel {

    private final static String DB_URL = "jdbc:sqlite:mydatabase.db";

    public void inviaEmail(String taxCode, String oggetto, String messaggio){

        String query = "SELECT diabetologo FROM utenti WHERE taxCode = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, taxCode);

            ResultSet rs = stmt.executeQuery();

            String diabetologo = rs.getString("diabetologo");

            query = "SELECT email FROM utenti WHERE taxCode = ?";

            try (PreparedStatement stmt2 = conn.prepareStatement(query)) {

                stmt2.setString(1, diabetologo);
                rs = stmt2.executeQuery();

                String emailDiabetologo = rs.getString("email");

                try {
                    // Codifica dell'oggetto e del messaggio secondo le regole URI
                    String oggettoCodificato = URLEncoder.encode(oggetto, StandardCharsets.UTF_8);
                    String messaggioCodificato = URLEncoder.encode(messaggio, StandardCharsets.UTF_8);

                    //Sostituzione dei simboli "+" creati dalla codifica con gli spazi " "
                    oggettoCodificato = oggettoCodificato.replace("+", "%20");
                    messaggioCodificato = messaggioCodificato.replace("+", "%20");

                    //Creazoine dell'URI da inviare
                    String uriStr = String.format("mailto:%s?subject=%s&body=%s", emailDiabetologo, oggettoCodificato, messaggioCodificato);
                    URI mailto = new URI(uriStr);

                    //Invio dell'URI
                    Desktop.getDesktop().mail(mailto);

                    System.out.println("URI creata con successo");

                } catch (Exception e) {
                    System.out.println("Errore di email inviata" + e);

                    messaggioErrore("Richiesta non andata a buon fine, riprovare piu' tardi!");
                }

            } catch (Exception e){
                System.out.println("Errore caricamento dati diabetologo" + e);

                messaggioErrore("Richiesta dati diabetologo non andata a buon fine, riprovare piu' tardi!");
            }

        } catch (Exception e) {
            System.out.println("Errore caricamento dati utente: " + e);

            messaggioErrore("Richiesta dati utente non andata a buon fine, riprovare piu' tardi!");
        }

    }

    private void messaggioErrore(String messaggio){

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore!!!");
        alert.setHeaderText(null); // oppure "Attenzione!"
        alert.setContentText(messaggio);
        alert.showAndWait();

    }

    public ArrayList<String> infoDiabetologo() {

        String sql = "SELECT diabetologo FROM utenti WHERE taxCode = ?";

        try(Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, Session.getInstance().getTaxCode());
            ResultSet rs = pstmt.executeQuery();

            String taxCodeDiabetologo = rs.getString("diabetologo");

            sql = "SELECT nome, cognome, telephoneNumber, email, address, number, city FROM utenti WHERE taxCode = ?";

            try {
                PreparedStatement pstmt2 = conn.prepareStatement(sql);
                pstmt2.setString(1, taxCodeDiabetologo);

                ResultSet rs2 = pstmt2.executeQuery();

                String diabetologo = rs2.getString("nome") + " " + rs2.getString("cognome");
                String indirizzo = rs2.getString("address") + ", " + rs2.getString("number") + ", " + rs2.getString("city");

                ArrayList<String> infoDiabetologo = new ArrayList<>();

                infoDiabetologo.add(diabetologo);
                infoDiabetologo.add(rs2.getString("telephoneNumber"));
                infoDiabetologo.add(rs2.getString("email"));
                infoDiabetologo.add(indirizzo);

                return infoDiabetologo;
            } catch (Exception e) {
                System.out.println("Errore caricamento dati diabetologo: " + e.getMessage());
                return null;
            }

        } catch (Exception e) {
            System.out.println("Errore ricerca diabetologo: " + e.getMessage());
            return null;
        }

    }
}
