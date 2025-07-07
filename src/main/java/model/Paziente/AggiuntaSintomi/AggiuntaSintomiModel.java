package model.Paziente.AggiuntaSintomi;

import java.sql.*;
import java.time.LocalDate;
import java.util.Arrays;

public class AggiuntaSintomiModel {

    private static final String DB_URL = "jdbc:sqlite:mydatabase.db";

    public int aggiungiSintomi(String symptom, String otherSpecifications, LocalDate relevationDate, String taxcode){       //String taxCode, String sintomoPrincipale, String altriSintomiSpecificati, LocalDate dataAggiuntaSintomo

        //String sintomoPrincipale = view.getSintomiPrincipaliComboBox().getValue();

        System.out.println("sono qui");

        if(symptom.equals("ALtro") && (otherSpecifications == null || otherSpecifications.isEmpty())){
            System.out.println("Sono qui dentro"); return 1; }

        if(relevationDate == null || relevationDate.isAfter(LocalDate.now())){ return 2; }

        String sql = "INSERT INTO aggiuntaSintomi (taxCode, sintomoPrincipale, sintomiSpecificati, dataInserimento) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM aggiuntaSintomi ORDER BY taxCode")) {

            while (rs.next()) {

                if(rs.getString("taxCode").equals(taxcode) && rs.getString("sintomoPrincipale").equals(symptom) &&
                checkStringa(rs.getString("sintomiSpecificati"), otherSpecifications) && rs.getString("dataInserimento").equals(relevationDate.toString())){
                    return 3;
                }

            }

            pstmt.setString(1, taxcode);
            pstmt.setString(2, symptom);
            pstmt.setString(3, otherSpecifications);
            pstmt.setString(4, relevationDate.toString());

            pstmt.executeUpdate();
            return 0;

        } catch (Exception e){
            System.out.println("Errore: " + e);
            return 4;
        }

    }

    private boolean checkStringa(String sintomiSpecificati, String altriSintomiSpecificati) {

            // Rimuove tutto tranne lettere e numeri, converte a lowercase
            String cleaned1 = sintomiSpecificati.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
            String cleaned2 = altriSintomiSpecificati.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();

            char[] chars1 = cleaned1.toCharArray();
            char[] chars2 = cleaned2.toCharArray();
            Arrays.sort(chars1);
            Arrays.sort(chars2);

            return Arrays.equals(chars1, chars2);

    }
}
