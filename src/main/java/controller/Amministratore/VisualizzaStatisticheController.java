package controller.Amministratore;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class VisualizzaStatisticheController {

    @FXML private LineChart<String, Number> lineChart;
    @FXML private ComboBox<String> pazienteCombo;
    @FXML private Button homePageButton;

    // Mappa che contiene TUTTI i dati: taxCode -> lista di rilevazioni
    private final Map<String, List<Rilevazione>> tuttiIDati = new HashMap<>();
    private Map<String, String> taxCodeToNameMap = new HashMap<>();

    @FXML
    public void initialize() {
        setupGrafico();
        caricaTuttiIDatiDalDatabase();
        popolaComboBox();
        mostraTuttiNelGrafico();
    }

    // Configura il grafico
    private void setupGrafico() {
        lineChart.setTitle("Andamento Glicemico Pazienti");
        lineChart.setCreateSymbols(true); // Mostra i pallini sui punti
        lineChart.setAnimated(false); // Più fluido
    }

    // Carica TUTTI i dati UNA volta sola
    private void caricaTuttiIDatiDalDatabase() {
        tuttiIDati.clear(); // Pulisce tutto
        Map<String, String> taxCodeToName = new HashMap<>(); // Mappa taxCode -> nome

        String query = "SELECT r.taxCode, r.quantita, r.data, r.momentoGiornata, r.prePost, " +
                "u.nome, u.cognome " +
                "FROM rilevazioniGlicemiche r " +
                "JOIN utenti u ON r.taxCode = u.taxCode " +
                "ORDER BY r.data, r.taxCode";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:mydatabase.db");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String taxCode = rs.getString("taxCode");
                double quantita = rs.getDouble("quantita");
                String dataString = rs.getString("data");
                LocalDate data = LocalDate.parse(dataString);
                String momento = rs.getString("momentoGiornata");
                String prePost = rs.getString("prePost");

                String nome = rs.getString("nome");
                String cognome = rs.getString("cognome");
                String nomeCompleto = nome + " " + cognome;
                taxCodeToName.put(taxCode, nomeCompleto);

                // Se non esiste ancora, crea una nuova lista per questo taxCode
                if (!tuttiIDati.containsKey(taxCode)) {
                    tuttiIDati.put(taxCode, new ArrayList<>());
                }

                // Aggiungi la rilevazione alla lista del paziente
                tuttiIDati.get(taxCode).add(new Rilevazione(taxCode, quantita, data, momento, prePost));

                this.taxCodeToNameMap = taxCodeToName;
            }

        } catch (Exception e) {
            System.out.println("ERRORE nel caricamento dati: " + e.getMessage());
        }
    }

    // Popola la ComboBox con i taxCode
    private void popolaComboBox() {
        pazienteCombo.getItems().clear();
        pazienteCombo.getItems().add("Tutti");

        // Aggiungi tutti i taxCode trovati nel database
        for (String taxCode : tuttiIDati.keySet()) {
            String nomeCompleto = taxCodeToNameMap.get(taxCode);
            if (nomeCompleto != null) {
                pazienteCombo.getItems().add(nomeCompleto);
            } else {
                // Evita problemi, se non esiste il nome completo usa il taxcode
                pazienteCombo.getItems().add(taxCode);
            }
        }

        // Quando cambia la selezione, aggiorna il grafico
        pazienteCombo.setOnAction(event -> {
            String selected = pazienteCombo.getValue();
            aggiornaGrafico(selected);
        });

        pazienteCombo.setValue("Tutti");
    }

    // Mostra TUTTI i pazienti nel grafico
    private void mostraTuttiNelGrafico() {
        lineChart.getData().clear(); // Pulisce il grafico

        // Per ogni paziente, crea una linea nel grafico
        for (Map.Entry<String, List<Rilevazione>> entry : tuttiIDati.entrySet()) {
            String taxCode = entry.getKey();
            List<Rilevazione> rilevazioni = entry.getValue();

            XYChart.Series<String, Number> serie = creaSeriePerPaziente(taxCode, rilevazioni);
            lineChart.getData().add(serie);
        }
    }

    // Aggiorna il grafico in base alla selezione
    private void aggiornaGrafico(String nomeSelezionato) {
        lineChart.getData().clear(); // Pulisce il grafico

        if ("Tutti".equals(nomeSelezionato)) {
            // Mostra TUTTI i pazienti
            mostraTuttiNelGrafico();
        } else {
            String taxCodeSelezionato = null;
            for (Map.Entry<String, String> entry : taxCodeToNameMap.entrySet()) {
                if (entry.getValue().equals(nomeSelezionato)) {
                    taxCodeSelezionato = entry.getKey();
                    break;
                }
            }

            // Mostra solo il paziente selezionato
            if (taxCodeSelezionato != null) {
                List<Rilevazione> rilevazioni = tuttiIDati.get(taxCodeSelezionato);
                if (rilevazioni != null && !rilevazioni.isEmpty()) {
                    XYChart.Series<String, Number> serie = creaSeriePerPaziente(taxCodeSelezionato, rilevazioni);
                    lineChart.getData().add(serie);
                }
            }
        }
    }

    // Crea una serie (linea) per un paziente
    private XYChart.Series<String, Number> creaSeriePerPaziente(String taxCode, List<Rilevazione> rilevazioni) {
        XYChart.Series<String, Number> serie = new XYChart.Series<>();

        //Seleziona il nome per la linea del grafico
        String nomePaziente = taxCodeToNameMap.get(taxCode);
        if (nomePaziente != null) {
            serie.setName(nomePaziente);
        } else {
            serie.setName(taxCode); // Evita problemi, se non esiste il nome completo usa il taxcode
        }

        // Ordina le rilevazioni per data (cronologicamente)
        rilevazioni.sort((r1, r2) -> r1.data.compareTo(r2.data));

        // Aggiungi ogni rilevazione come punto nel grafico
        for (Rilevazione r : rilevazioni) {
            String labelAsseX = r.data + " - " + r.momentoGiornata + " - " + r.prePost;
            XYChart.Data<String, Number> punto = new XYChart.Data<>(labelAsseX, r.quantita);
            serie.getData().add(punto);
        }

        return serie;
    }

    // CLASSE INTERNA per rappresentare una rilevazione
    // (Tutto in un unico file, niente complicazioni!)
    private static class Rilevazione {
        String taxCode;
        double quantita;
        LocalDate data;
        String momentoGiornata;
        String prePost;

        public Rilevazione(String taxCode, double quantita, LocalDate data,
                           String momentoGiornata, String prePost) {
            this.taxCode = taxCode;
            this.quantita = quantita;
            this.data = data;
            this.momentoGiornata = momentoGiornata;
            this.prePost = prePost;
        }
    }

    @FXML
    public void onHomePagePressed(){

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlView/amministratore_view.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Amministratore");
            stage.setScene(new Scene(root, 650, 500));
            stage.show();

            // Chiudi la finestra corrente
            Stage currentStage = (Stage)homePageButton.getScene().getWindow();
            currentStage.close();

        } catch (IOException e) { System.out.println("Errore caricamento homepage amministratore!" + e.getMessage()); }

    }

    @FXML
    public void onLogoutPressed(){

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlView/login_view.fxml"));
            Parent root = loader.load();

            Stage loginStage = new Stage();
            loginStage.setTitle("Login");
            loginStage.setScene(new Scene(root));
            loginStage.show();

            Stage pazienteStage = (Stage)homePageButton.getScene().getWindow();
            pazienteStage.close();

        } catch (IOException e) { System.out.println("Errore caricamento pagina di login!" + e.getMessage()); }

    }
}
