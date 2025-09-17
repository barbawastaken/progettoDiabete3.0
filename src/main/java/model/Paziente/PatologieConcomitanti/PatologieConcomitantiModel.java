package model.Paziente.PatologieConcomitanti;

import java.sql.*;
import java.time.LocalDate;
import java.util.Arrays;

public class PatologieConcomitantiModel {

    private static final String DB_URL = "jdbc:sqlite:mydatabase.db";

    public PatologieConcomitantiModel() {}

    public int inserimentoPatologiaConcomitante(String taxCode, String patologiaConcomitante, LocalDate dataInizio, LocalDate dataFine) {

        if(patologiaConcomitante.isEmpty() || dataInizio == null) { return 1; }

        if(dataFine != null){
            if(dataFine.isBefore(dataInizio)) { return 2; }
        }

        if(dataInizio.isAfter(LocalDate.now()) || (dataFine != null && dataFine.isAfter(LocalDate.now()))) { return 3; }

        String sql = "INSERT INTO patologieConcomitanti (taxCode, patologiaConcomitante, dataInizio, dataFine) VALUES (?, ?, ?, ?)";

        try(Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM patologieConcomitanti ORDER BY taxCode")){

            while(rs.next()) {

                if(checkStringa(rs.getString("patologiaConcomitante"), patologiaConcomitante) &&
                        checkData(dataInizio, dataFine, rs.getString("dataInizio"), rs.getString("dataFine"))) {
                    return 4;
                }

            }

            pstmt.setString(1, taxCode);
            pstmt.setString(2, patologiaConcomitante);
            pstmt.setString(3, dataInizio.toString());

            if(dataFine != null){
                pstmt.setString(4, dataFine.toString());
            } else { pstmt.setString(4, LocalDate.now().toString()); }

            pstmt.executeUpdate();
            return 0;

        } catch (Exception ex) { System.out.println("Errore: " + ex.getMessage()); return 5;}

    }

    private boolean checkStringa(String str1, String str2) {

        // Rimuove tutto tranne lettere e numeri, converte a lowercase
        String cleaned1 = str1.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        String cleaned2 = str2.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();

        char[] chars1 = cleaned1.toCharArray();
        char[] chars2 = cleaned2.toCharArray();
        Arrays.sort(chars1);
        Arrays.sort(chars2);

        return Arrays.equals(chars1, chars2);

    }

    private boolean checkData(LocalDate dataInizioInserimento, LocalDate dataFineInserimento, String dataInizioTable, String dataFineTable) {

        LocalDate dataInizioTabella = LocalDate.parse(dataInizioTable);
        if(dataInizioInserimento.isBefore(dataInizioTabella) && dataFineInserimento != null) {
            if(dataFineInserimento.isBefore(dataInizioTabella)) { return false; }
        }

        if(dataFineTable != null && !dataFineTable.isEmpty()) {
            LocalDate dataFineTabella = LocalDate.parse(dataFineTable);

            return !dataInizioInserimento.isAfter(dataFineTabella);
        }

        return true;
    }
}




