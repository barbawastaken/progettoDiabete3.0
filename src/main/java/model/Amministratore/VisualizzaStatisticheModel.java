package model.Amministratore;

import javafx.scene.chart.XYChart;
import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;

public class VisualizzaStatisticheModel {

    private final static String DB_URL = "jdbc:sqlite:mydatabase.db";

    public static HashMap<String, XYChart.Series<String, Number>> caricaDati(){

        HashMap<String, XYChart.Series<String, Number>> pazientiSeriesMap = new HashMap<>();

        String query = "SELECT taxCode, quantita, data, momentoGiornata, prePost FROM rilevazioniGlicemiche ORDER BY taxcode, data";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String taxcode = rs.getString("taxCode");

                // prendo solo la data (senza orario)
                String data = rs.getString("data");
                double valore = rs.getDouble("quantita");

                //String dataStr = data.toString(); // formato ISO: yyyy-MM-dd

                XYChart.Series<String, Number> series =
                        pazientiSeriesMap.computeIfAbsent(taxcode, k -> {
                            XYChart.Series<String, Number> s = new XYChart.Series<>();
                            s.setName(k); // label del paziente
                            return s;
                        });

                String labelGrafico = data + " - " + rs.getString("momentoGiornata") + " - " + rs.getString("prePost");
                series.getData().add(new XYChart.Data<>(labelGrafico, valore));
            }

            return pazientiSeriesMap;

        } catch (Exception e) {
            System.out.println("Errore nel caricamento valori glicemici: " + e);
            return null;
        }

    }

}
