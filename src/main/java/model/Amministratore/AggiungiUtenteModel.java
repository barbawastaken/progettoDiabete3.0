package model.Amministratore;
import controller.Amministratore.GestioneUtenti;

import java.sql.*;
import java.util.HashMap;

public class AggiungiUtenteModel {

    private static final String DB_URL = "jdbc:sqlite:mydatabase.db?busy_timeout=5000";

    public static HashMap<String, String> getDiabetologi() throws SQLException {
        String findDiabetologi = "SELECT * FROM utenti WHERE userType='DIABETOLOGO'";
        HashMap<String, String> diabetologi = new HashMap<>();


            try (Connection conn = DriverManager.getConnection(DB_URL);
                 Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(findDiabetologi);


                while (rs.next()) {

                    diabetologi.put(((rs.getString("cognome")) + " (" + rs.getString("taxCode") + ")"),
                            rs.getString("taxCode"));
                }

            }
        catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }

        return diabetologi;
            /*
                Creiamo in questo modo una hashmap che ha come key la combo cognome + codice fiscale (quest'ultima sarebbe la key del database)
                su View ci sarà una comboBox che ci farà scegliere proprio la key. Una volta scelta basterà trovare con la key il codice fiscale
                corrispondente e avremo il CF del diabetologo
            */
    }

    public void inserisciUtente(String taxCode, String password, String nome, String cognome, String address,
                                String cap, String city, String email, String gender, java.sql.Date birthday,
                                String number, String telephone, String userType, String diabetologoSelezionato, String country, String altezza, String peso)
             {

        if(!GestioneUtenti.singleValues(taxCode)){
            System.out.println("è stato inserito un taxCode già utilizzato");
            return;
        }

        double heightParsed = 0;
        double weightParsed = 0;
        if(altezza != null && peso != null){
            heightParsed = Double.parseDouble(altezza);
            heightParsed = Math.floor(heightParsed * 100) / 100.0;

            weightParsed = Double.parseDouble(peso);
            weightParsed = Math.floor(weightParsed * 100) / 100.0;
        }

        String addUserQuery = "INSERT INTO utenti (taxCode, password, nome, cognome, email, birthday, address," +
                "number, city, cap, gender, telephoneNumber, userType, diabetologo, CountryOfResidence, altezza, peso) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        String addLoginQuery  = "INSERT INTO loginTable (taxCode, password, userType) VALUES (?, ?, ?);";


        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            conn.setAutoCommit(false);
            try(PreparedStatement pstmt = conn.prepareStatement(addUserQuery);
                PreparedStatement pstmt2 = conn.prepareStatement(addLoginQuery)) {

                HashMap<String, String> diabetologi = getDiabetologi();
                System.out.println("Connesso");
                pstmt.setString(1, taxCode);
                pstmt.setString(2, password);
                pstmt.setString(3, nome);
                pstmt.setString(4, cognome);
                pstmt.setString(5, email);
                pstmt.setString(6, birthday.toString());
                pstmt.setString(7, address);
                pstmt.setString(8, number);
                pstmt.setString(9, city);
                pstmt.setString(10, cap);
                pstmt.setString(11, gender);
                pstmt.setString(12, telephone);
                pstmt.setString(13, userType);
                if(diabetologoSelezionato != null) {
                    System.out.println(diabetologoSelezionato);
                    pstmt.setString(14, diabetologi.get(diabetologoSelezionato));

                    System.out.println(diabetologi.get(diabetologoSelezionato));//! FACENDO COSÌ UN PAZIENTE NON PUÒ NON AVERE UN MEDICO
                    pstmt.setString(15, country);
                    pstmt.setDouble(16, heightParsed);
                    pstmt.setDouble(17, weightParsed);
                } else{
                    pstmt.setString(14, null);
                    pstmt.setString(15, null);
                    pstmt.setString(16, null);
                }


                pstmt.executeUpdate();
                System.out.println("Utente aggiunto!");

                pstmt2.setString(1, taxCode);
                pstmt2.setString(2, password);
                pstmt2.setString(3, userType);
                pstmt2.executeUpdate();
                conn.commit();

                System.out.println("Login aggiunto!");
            }catch (SQLException e) {
                try {
                    conn.rollback();
                    System.err.println("rollback eseguito");
                } catch (SQLException rollback) {

                    rollback.printStackTrace();
                }
                System.err.println("Errore durante inserimento login:");
                System.out.println("Errore: "+ e.getMessage());
            }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}


