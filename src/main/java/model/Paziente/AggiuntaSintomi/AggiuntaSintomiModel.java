package model.Paziente.AggiuntaSintomi;

import model.Amministratore.Utente;
import view.Paziente.AggiuntaSintomi.AggiuntaSintomiView;

import java.sql.*;
import java.time.LocalDate;
import java.util.Arrays;

public class AggiuntaSintomiModel {

    private static final String DB_URL = "jdbc:sqlite:mydatabase.db";

    public int aggiungiSintomi(String symptom, String otherSpecifications, LocalDate relevationDate, String taxcode){       //String taxCode, String sintomoPrincipale, String altriSintomiSpecificati, LocalDate dataAggiuntaSintomo

        //String sintomoPrincipale = view.getSintomiPrincipaliComboBox().getValue();
        String sintomoPrincipale = symptom;

        if(sintomoPrincipale==null){ sintomoPrincipale = "";}

        String altriSintomiSpecificati = otherSpecifications; //--> appena si ha tempo bisogna togliere queste variabili e usare i parametri
        LocalDate dataAggiuntaSintomo = relevationDate;        //-->
        if(sintomoPrincipale.isEmpty() && altriSintomiSpecificati.isEmpty()){ return 1; }

        if(dataAggiuntaSintomo == null || dataAggiuntaSintomo.isAfter(LocalDate.now())){ return 2; }

        String sql = "INSERT INTO aggiuntaSintomi (taxCode, sintomoPrincipale, sintomiSpecificati, dataInserimento) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM aggiuntaSintomi ORDER BY taxCode")) {

            while (rs.next()) {

                if(rs.getString("taxCode").equals(taxcode) && rs.getString("sintomoPrincipale").equals(sintomoPrincipale) &&
                checkStringa(rs.getString("sintomiSpecificati"), altriSintomiSpecificati) && rs.getString("dataInserimento").equals(dataAggiuntaSintomo.toString())){
                    return 3;
                }

            }

            pstmt.setString(1, taxcode);
            pstmt.setString(2, sintomoPrincipale);
            pstmt.setString(3, altriSintomiSpecificati);
            pstmt.setString(4, dataAggiuntaSintomo.toString());

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
