package model.Diabetologo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDate;

public class LogOperationModel {

    private static final String DB_URL = "jdbc:sqlite:mydatabase.db";

    public static void loadLogOperation(String taxCodeDiabetologo, String operazione, String taxCodePaziente, LocalDate data){

        try(Connection conn = DriverManager.getConnection(DB_URL)){

            String sql = "INSERT INTO logTable (taxCodeDiabetologo, operazione, taxCodePaziente, data) VALUES (?, ?, ?, ?)";

            PreparedStatement pstmtLog = conn.prepareStatement(sql);

            pstmtLog.setString(1, taxCodeDiabetologo);
            pstmtLog.setString(2, operazione);
            pstmtLog.setString(3, taxCodePaziente);
            pstmtLog.setString(4, data.toString());

            pstmtLog.executeUpdate();

            System.out.println("Log salvato correttamente!");

        } catch (Exception e) {
            System.out.println("Errore salvataggio log: " + e.getMessage());

        }



    }
}
