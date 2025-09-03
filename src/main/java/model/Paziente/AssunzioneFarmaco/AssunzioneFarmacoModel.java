package model.Paziente.AssunzioneFarmaco;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class AssunzioneFarmacoModel {

    private static final String DB_URL = "jdbc:sqlite:mydatabase.db";

    public AssunzioneFarmacoModel() {}

    public int inserimentoFarmacoAssunto(String taxCode, String farmacoAssunto, String quantitaAssunta,
                                         LocalDate dataAssunzione, LocalTime orarioAssunzione) {

        if(farmacoAssunto == null || farmacoAssunto.isEmpty() || quantitaAssunta.isEmpty() || dataAssunzione == null || orarioAssunzione == null) { return 2; }

        if (dataAssunzione.isAfter(LocalDate.now()) || (dataAssunzione.equals(LocalDate.now()) && orarioAssunzione.isAfter(LocalTime.now()))) { return 3; }

        String sql = "INSERT INTO assunzioneFarmaci (taxCode, farmacoAssunto, quantitaAssunta, dataAssunzione, orarioAssunzione) VALUES (?, ?, ?, ?, ?)";

        try(Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM assunzioneFarmaci ORDER BY taxCode")){

            while (rs.next()) {

                if (rs.getString("taxCode").equals(taxCode) &&
                        checkStringa(rs.getString("farmacoAssunto"), farmacoAssunto) &&
                        checkStringa(rs.getString("quantitaAssunta"), quantitaAssunta) &&
                        rs.getString("dataAssunzione").equals(dataAssunzione.toString()) &&
                        rs.getString("orarioAssunzione").equals(orarioAssunzione.toString())) {

                    return 4;
                }

            }

            pstmt.setString(1, taxCode);
            pstmt.setString(2, farmacoAssunto);
            pstmt.setString(3, quantitaAssunta);
            pstmt.setString(4, dataAssunzione.toString());
            pstmt.setString(5, orarioAssunzione.toString());

            pstmt.executeUpdate();
            return 0;

        } catch(Exception ex){
            System.out.println("Errore: " + ex.getMessage());
            return 5;
        }

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

    public ArrayList<String> getFarmaciTerapie(String taxCode) {

        ArrayList<String> farmaci = new ArrayList<>();
        String sql = "SELECT farmaco_prescritto FROM terapiePrescritte WHERE taxCode = ?";

        try(Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement pstmt = conn.prepareStatement(sql)){
                pstmt.setString(1, taxCode);
                ResultSet rs = pstmt.executeQuery();

                while(rs.next()){
                    farmaci.add(rs.getString("farmaco_prescritto"));

                }

        } catch(Exception ex){
                System.out.println("Errore: " + ex.getMessage());
        }

        return farmaci;
    }
}
