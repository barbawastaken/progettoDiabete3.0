package model.Amministratore;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;

public class AggiungiUtenteModel {

    private static final String URL = "jdbc:sqlite:database/mydatabase.db?busy_timeout=5000";
    public HashMap<String, String> getDiabetologi() throws SQLException {
        String findDiabetologi = "SELECT * FROM utenti WHERE userType='DIABETOLOGO'";
        HashMap<String, String> diabetologi = new HashMap<>();


            try (
                    Connection conn = DriverManager.getConnection(URL);
                    Statement stmt = conn.createStatement();
                    ) {
                ResultSet rs = stmt.executeQuery(findDiabetologi);

                while (rs.next()) {
                    diabetologi.put(((rs.getString("cognome")) + " (" + rs.getString("taxCode") + ")"),
                            rs.getString("taxCode"));
                }
            }
        catch (SQLException e) {
            e.printStackTrace();
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
                                String number, String telephone, String userType, String diabetologoSelezionato)
            throws SQLException {

        String addUserQuery = "INSERT INTO utenti (taxCode, password, nome, cognome, email, birthday, address," +
                "number, city, cap, gender, telephoneNumber, userType, diabetologo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        String addLoginQuery  = "INSERT INTO loginTable (taxCode, password, userType) VALUES (?, ?, ?);";


        try {
                Connection conn = DriverManager.getConnection(URL);
                conn.setAutoCommit(false);
                try(
                        PreparedStatement pstmt = conn.prepareStatement(addUserQuery);
                        PreparedStatement pstmt2 = conn.prepareStatement(addLoginQuery);) {
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
                        pstmt.setString(14, diabetologi.get(diabetologoSelezionato));
                    } else{
                        pstmt.setString(14, null);
                    }

                    System.out.println("Siamo sull'execute update");
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
                    e.printStackTrace();
            }



            } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    }

