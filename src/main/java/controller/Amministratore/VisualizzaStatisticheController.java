package controller.Amministratore;

import controller.Diabetologo.TabellaModificaTerapiaController;
import controller.LoginController;
import controller.NavBar;
import controller.NavBarTags;
import controller.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Diabetologo.Terapia;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class VisualizzaStatisticheController {

    private final static String DB_URL = "jdbc:sqlite:mydatabase.db";

    /*
    *
    *   SEZONE COMBOBOX + BUTTON
    *
     */
    @FXML private HBox navbarContainer;
    @FXML private ComboBox<String> pazienteCombo;
    @FXML private Button aggiornaInfoButton;

    /*
    *
    *   SEZIONE GRAFICI !!!
    *
    */

    @FXML private LineChart<String, Number> lineChart;
    @FXML private LineChart<String, Number> lineChartLastMonth;
    @FXML private LineChart<String, Number> lineChartLastWeek;

    // Mappa che contiene TUTTI i dati: taxCode -> lista di rilevazioni
    private final Map<String, List<Rilevazione>> tuttiIDati = new HashMap<>();
    private final Map<String, List<Rilevazione>> tuttiIDatiDiLastMonth = new HashMap<>();
    private final Map<String, List<Rilevazione>> tuttiIDatiDiLastWeek = new HashMap<>();

    //Mappa taxCode --> nome paziente
    private final Map<String, String> taxCodeToNameMap = new HashMap<>();

    /*
    *
    *   SEZIONE TABELLA TERAPIE !!!
    *
     */

    @FXML private TableView<Terapia> tabellaTerapie;
    @FXML private TableColumn<Terapia, String> nomePazienteTerapia;
    @FXML private TableColumn<Terapia, String> nomeTerapia;
    @FXML private TableColumn<Terapia, String> farmacoTerapia;
    @FXML private TableColumn<Terapia, String> quantitaTerapia;
    @FXML private TableColumn<Terapia, String> assunzioniGiornaliereTerapia;
    @FXML private TableColumn<Terapia, String> indicazioniTerapia;

    /*
    *
    *   SEZIONE TABELLA ASSUNZIONI FARMACI !!!
    *
     */

    @FXML private TableView<AssunzioneFarmaco> tabellaAssunzioni;
    @FXML private TableColumn<AssunzioneFarmaco, String> nomePazienteAssunzione;
    @FXML private TableColumn<AssunzioneFarmaco, String> farmacoAssunzione;
    @FXML private TableColumn<AssunzioneFarmaco, Integer> quantitaAssunzione;
    @FXML private TableColumn<AssunzioneFarmaco, LocalDate> dataAssunzione;
    @FXML private TableColumn<AssunzioneFarmaco, LocalTime> orarioAssunzione;

    /*
    *
    *   SEZIONE TABELLA SINTOMI RISCONTRATI !!!
    *
     */

    @FXML private TableView<Sintomo> tabellaSintomi;
    @FXML private TableColumn<Sintomo, String> nomePazienteSintomo;
    @FXML private TableColumn<Sintomo, String> sintomoPrincipale;
    @FXML private TableColumn<Sintomo, String> sintomiSpecificati;
    @FXML private TableColumn<Sintomo, LocalDate> dataSintomo;

    /*
     *
     *   SEZIONE TABELLA PATOLOGIE CONCOMITANTI !!!
     *
     */

    @FXML private TableView<Patologia> tabellaPatologie;
    @FXML private TableColumn<Patologia, String> nomePazientePatologia;
    @FXML private TableColumn<Patologia, String> patologiaConcomitante;
    @FXML private TableColumn<Patologia, LocalDate> dataInizioPatologia;
    @FXML private TableColumn<Patologia, LocalDate> dataFinePatologia;

    @FXML
    public void initialize() {

        setupGrafico();
        NavBar navbar = new NavBar(NavBarTags.AMMINISTRATORE_toHomepage);
        navbar.prefWidthProperty().bind(navbarContainer.widthProperty());
        navbarContainer.getChildren().add(navbar);
        /* SETUP GRAFICO COMPLETO */

        String query = "SELECT r.taxCode, r.quantita, r.data, r.momentoGiornata, r.prePost, " +
                "u.nome, u.cognome " +
                "FROM rilevazioniGlicemiche r " +
                "JOIN utenti u ON r.taxCode = u.taxCode " +
                "ORDER BY r.data, r.taxCode";


        caricaTuttiIDatiDalDatabase(tuttiIDati, query);
        popolaComboBox(tuttiIDati);
        mostraTuttiNelGrafico(tuttiIDati, lineChart);

        /* SETUP GRAFICO ULTIMO MESEE */

        query = """
                SELECT r.taxCode, r.quantita, r.data, r.momentoGiornata, r.prePost,
                       u.nome, u.cognome
                FROM rilevazioniGlicemiche r
                         JOIN utenti u ON r.taxCode = u.taxCode
                WHERE date(r.data) >= date('now', '-30 days')
                ORDER BY r.data DESC, r.taxCode""";

        caricaTuttiIDatiDalDatabase(tuttiIDatiDiLastMonth, query);
        //popolaComboBox(tuttiIDatiDiLastMonth);
        mostraTuttiNelGrafico(tuttiIDatiDiLastMonth, lineChartLastMonth);

        /* SETUP GRAFICO ULTIMA SETTIMANA */

        query = """
                SELECT r.taxCode, r.quantita, r.data, r.momentoGiornata, r.prePost,
                       u.nome, u.cognome
                FROM rilevazioniGlicemiche r
                         JOIN utenti u ON r.taxCode = u.taxCode
                WHERE date(r.data) >= date('now', '-7 days')
                ORDER BY r.data DESC, r.taxCode""";

        caricaTuttiIDatiDalDatabase(tuttiIDatiDiLastWeek, query);
        //popolaComboBox(tuttiIDatiDiLastWeek);
        mostraTuttiNelGrafico(tuttiIDatiDiLastWeek, lineChartLastWeek);

        /* SETUP TABELLA TERAPIE */

        nomePazienteTerapia.setCellValueFactory(new PropertyValueFactory<>("taxCode"));
        nomeTerapia.setCellValueFactory(new PropertyValueFactory<>("terapia"));
        farmacoTerapia.setCellValueFactory(new PropertyValueFactory<>("farmaco"));
        quantitaTerapia.setCellValueFactory(new PropertyValueFactory<>("quantita"));
        assunzioniGiornaliereTerapia.setCellValueFactory(new PropertyValueFactory<>("assunzioni"));
        indicazioniTerapia.setCellValueFactory(new PropertyValueFactory<>("indicazioni"));

        query = "SELECT taxCode, terapia, farmaco_prescritto, quantita, numero_assunzioni_giornaliere, indicazioni FROM terapiePrescritte";

        caricaTerapie(query, "Tutti");

        /* SETUP TABELLA ASSUNZIONE FARMACI */

        nomePazienteAssunzione.setCellValueFactory(new PropertyValueFactory<>("nomePazienteAssunzione"));
        farmacoAssunzione.setCellValueFactory(new PropertyValueFactory<>("farmacoAssunzione"));
        quantitaAssunzione.setCellValueFactory(new PropertyValueFactory<>("quantitaAssunzione"));
        dataAssunzione.setCellValueFactory(new PropertyValueFactory<>("dataAssunzione"));
        orarioAssunzione.setCellValueFactory(new PropertyValueFactory<>("orarioAssunzione"));

        query = "SELECT taxCode, farmacoAssunto, quantitaAssunta, dataAssunzione, orarioAssunzione FROM assunzioneFarmaci";

        caricaAssunzioneFarmaci(query, "Tutti");

        /*  SETUP TABELLA SINTOMI RISCONTRATI */

        nomePazienteSintomo.setCellValueFactory(new PropertyValueFactory<>("nomePazienteSintomo"));
        sintomoPrincipale.setCellValueFactory(new PropertyValueFactory<>("sintomoPrincipale"));
        sintomiSpecificati.setCellValueFactory(new PropertyValueFactory<>("sintomiSpecificati"));
        dataSintomo.setCellValueFactory(new PropertyValueFactory<>("dataSintomo"));

        query = "SELECT taxCode, sintomoPrincipale, sintomiSpecificati, dataInserimento FROM aggiuntaSintomi";

        caricaSintomi(query, "Tutti");

        /* SETUP TABELLA PATOLOGIE CONCOMITANTI */

        nomePazientePatologia.setCellValueFactory(new PropertyValueFactory<>("nomePazientePatologia"));
        patologiaConcomitante.setCellValueFactory(new PropertyValueFactory<>("patologiaConcomitante"));
        dataInizioPatologia.setCellValueFactory(new PropertyValueFactory<>("dataInizioPatologia"));
        dataFinePatologia.setCellValueFactory(new PropertyValueFactory<>("dataFinePatologia"));

        query = "SELECT taxCode, patologiaConcomitante, dataInizio, dataFine FROM patologieConcomitanti";

        caricaPatologie(query, "Tutti");

        // LISTENER SU COMBOBOX: quando cambia la selezione, aggiorna il grafico
        pazienteCombo.setOnAction(event -> {
            String selected = pazienteCombo.getValue();
            aggiornaGrafico(selected, tuttiIDati, lineChart);
            aggiornaGrafico(selected, tuttiIDatiDiLastMonth, lineChartLastMonth);
            aggiornaGrafico(selected, tuttiIDatiDiLastWeek, lineChartLastWeek);

            final String queryUpdateTabellaTerapie = "SELECT taxCode, terapia, farmaco_prescritto, quantita, numero_assunzioni_giornaliere, indicazioni FROM terapiePrescritte";
            caricaTerapie(queryUpdateTabellaTerapie, selected);

            final String queryUpdateTabellaAssunzioneFarmaci = "SELECT taxCode, farmacoAssunto, quantitaAssunta, dataAssunzione, orarioAssunzione FROM assunzioneFarmaci";
            caricaAssunzioneFarmaci(queryUpdateTabellaAssunzioneFarmaci, selected);

            final String queryUpdateTabellaSintomi = "SELECT taxCode, sintomoPrincipale, sintomiSpecificati, dataInserimento FROM aggiuntaSintomi";
            caricaSintomi(queryUpdateTabellaSintomi, selected);

            final String queryUpdateTabellaPatologie = "SELECT taxCode, patologiaConcomitante, dataInizio, dataFine FROM patologieConcomitanti";
            caricaPatologie(queryUpdateTabellaPatologie, selected);
        });

        pazienteCombo.setValue("Tutti");

        tabellaTerapie.setRowFactory(tv -> {
            TableRow<Terapia> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) { // doppio click
                    Terapia terapiaSelezionata = row.getItem();

                    // Qui apri la nuova schermata
                    TabellaModificaTerapiaController terapiaDaModificare = new TabellaModificaTerapiaController();

                    String taxCodePaziente = null;
                    for(String taxCode : taxCodeToNameMap.keySet()){
                        if(taxCodeToNameMap.get(taxCode).equals(row.getItem().getTaxCode())){ taxCodePaziente = taxCode; }
                    }

                    terapiaDaModificare.mostraModificaTerapia(terapiaSelezionata, taxCodePaziente, getTaxCodeDiabetologo(taxCodePaziente));
                }
            });
            return row;
        });
    }

    private String getTaxCodeDiabetologo(String taxCodePaziente) {

        String query = "SELECT diabetologo FROM utenti WHERE taxCode = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, taxCodePaziente);
            ResultSet rs = pstmt.executeQuery();

            return rs.getString("diabetologo");

        } catch (Exception e) {
            System.out.println("Errore ricerca taxCode diabetologo: " + e.getMessage());
            return null;
        }

    }

    /*
    *
    *
    *       ------------>           SEZIONE GRAFICI !!!!    <-----------
    *
    *
     */

    // Configura il grafico
    private void setupGrafico() {
        lineChart.setTitle("Andamento Glicemico Pazienti");
        lineChart.setCreateSymbols(true); // Mostra i pallini sui punti
        lineChart.setAnimated(false); // Più fluido

        lineChartLastMonth.setCreateSymbols(true);
        lineChartLastMonth.setAnimated(false);

        lineChartLastWeek.setCreateSymbols(true);
        lineChartLastWeek.setAnimated(false);
    }

    // Carica TUTTI i dati UNA volta sola
    private void caricaTuttiIDatiDalDatabase(Map<String, List<Rilevazione>> mappaDati, String query) {

        mappaDati.clear(); // Pulisce tutto

        try (Connection conn = DriverManager.getConnection(DB_URL);
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

                taxCodeToNameMap.putIfAbsent(taxCode, nomeCompleto);
                // Se non esiste ancora, crea una nuova lista per questo taxCode
                if (!mappaDati.containsKey(taxCode)) {
                    mappaDati.put(taxCode, new ArrayList<>());
                }

                // Aggiungi la rilevazione alla lista del paziente
                mappaDati.get(taxCode).add(new Rilevazione(taxCode, quantita, data, momento, prePost));

            }

        } catch (Exception e) {
            System.out.println("ERRORE nel caricamento dati: " + e.getMessage());
        }
    }

    // Popola la ComboBox con i taxCode
    private void popolaComboBox(Map<String, List<Rilevazione>> mappaDati) {
        pazienteCombo.getItems().clear();
        pazienteCombo.getItems().add("Tutti");

        // Aggiungi tutti i taxCode trovati nel database
        for (String taxCode : mappaDati.keySet()) {
            String nomeCompleto = taxCodeToNameMap.get(taxCode);

            if (nomeCompleto != null) {
                pazienteCombo.getItems().add(nomeCompleto);

            } else {
                // Evita problemi, se non esiste il nome completo usa il taxcode
                pazienteCombo.getItems().add(taxCode);
            }
        }

    }

    // Mostra TUTTI i pazienti nel grafico
    private void mostraTuttiNelGrafico(Map<String, List<Rilevazione>> mappaDati, LineChart<String, Number> grafico) {
        grafico.getData().clear(); // Pulisce il grafico

        // Per ogni paziente, crea una linea nel grafico
        for (Map.Entry<String, List<Rilevazione>> entry : mappaDati.entrySet()) {
            String taxCode = entry.getKey();
            List<Rilevazione> rilevazioni = entry.getValue();

            XYChart.Series<String, Number> serie = creaSeriePerPaziente(taxCode, rilevazioni);
            grafico.getData().add(serie);
        }
    }

    // Aggiorna il grafico in base alla selezione
    private void aggiornaGrafico(String nomeSelezionato, Map<String, List<Rilevazione>> mappaDati, LineChart<String, Number> grafico) {
        grafico.getData().clear(); // Pulisce il grafico

        if ("Tutti".equals(nomeSelezionato)) {
            // Mostra TUTTI i pazienti
            mostraTuttiNelGrafico(mappaDati, grafico);
            aggiornaInfoButton.setVisible(false);
        } else {

            // Mostra solo il paziente selezionato
            aggiornaInfoButton.setVisible(true);
            String taxCodeSelezionato = null;
            for (Map.Entry<String, String> entry : taxCodeToNameMap.entrySet()) {

                if (entry.getValue().equals(nomeSelezionato)) {
                    taxCodeSelezionato = entry.getKey();
                    break;
                }
            }

            // Mostra solo il paziente selezionato
            if (taxCodeSelezionato != null) {
                List<Rilevazione> rilevazioni = mappaDati.get(taxCodeSelezionato);
                if (rilevazioni != null && !rilevazioni.isEmpty()) {

                    XYChart.Series<String, Number> serie = creaSeriePerPaziente(taxCodeSelezionato, rilevazioni);
                    grafico.getData().add(serie);
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
    private static class Rilevazione {
        String taxCode;
        double quantita;
        LocalDate data;
        String momentoGiornata;
        String prePost;

        public Rilevazione(String taxCode, double quantita, LocalDate data, String momentoGiornata, String prePost) {
            this.taxCode = taxCode;
            this.quantita = quantita;
            this.data = data;
            this.momentoGiornata = momentoGiornata;
            this.prePost = prePost;
        }
    }

    /*
     *
     *
     *       ------------>           FINE SEZIONE GRAFICI !!!!          <-----------
     *
     *
     */
    /*
     *
     *
     *       ------------>           SEZIONE TABELLA TERAPIE !!!!    <-----------
     *
     *
     */

    private void caricaTerapie(String query, String selected){

        tabellaTerapie.getItems().clear();  //Pulisce il contenuto della tabella

        Connection conn = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            rs = completaQuery(query, selected, null);

            if (rs == null) return;

            ObservableList<Terapia> terapie = FXCollections.observableArrayList();

            try {
                //Ciclo che scorre ogni terapia trovata per aggiungerla alla tabella
                while (rs.next()) {

                    //Creazione della terapia da immettere nella tabella
                    String nomePaziente = taxCodeToNameMap.get(rs.getString("taxCode"));

                    Terapia terapia = new Terapia(
                            nomePaziente,
                            rs.getString("terapia"),
                            rs.getString("farmaco_prescritto"),
                            rs.getString("quantita"),
                            rs.getString("numero_assunzioni_giornaliere"),
                            rs.getString("indicazioni")
                    );
                    terapie.add(terapia);
                }

                //Aggiunta della terapia nella tabella
                tabellaTerapie.setItems(terapie);
            } catch (Exception e) {
                System.out.println("Errore caricamento terapie prescritte: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Errore caricamento terapie prescritte: " + e.getMessage());
        } finally {
            // Chiudi tutte le risorse in ordine inverso
            try { if (rs != null) rs.close(); } catch (SQLException e) { /* ignore */ }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { /* ignore */ }
            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) { /* ignore */ }
            try { if (conn != null) conn.close(); } catch (SQLException e) { /* ignore */ }
        }
    }

    private ResultSet completaQuery(String query, String selected, String colonnaDataName) {

        String taxCodeSelezionato = null;
        //Controllo sulla selezione della combobox e creazione delle query adeguate in base
        // a se viene selezionato "Tutti" oppure un nome specifico
        if(!selected.equals("Tutti")) {

            query += " WHERE taxCode = ?  ORDER BY taxCode, " + colonnaDataName + " DESC";

            //Rilevazione del taxcode associato al nome selezionato nella combobox
            for (Map.Entry<String, String> entry : taxCodeToNameMap.entrySet()) {
                if (entry.getValue().equals(selected)) {
                    taxCodeSelezionato = entry.getKey();
                    break;
                }
            }

        } else {
            query += " ORDER BY taxCode, " + colonnaDataName + " DESC";
        }

        ResultSet rs;

        try {

            Connection conn = DriverManager.getConnection(DB_URL);
            //Esecuzione delle query in base alla selezione della combobox --> se viene selezionato "tutti" non
            // abbiamo bisogno di alcun taxCode, altrimenti se viene selezionato un nome specifico
            // consideriamo anche quello nella query
            if(!selected.equals("Tutti")) {

                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, taxCodeSelezionato);
                rs = pstmt.executeQuery();

            } else {

                Statement stmt = conn.createStatement();
                rs = stmt.executeQuery(query);
            }

            return rs;

        } catch (SQLException e) {
            System.out.println("Errore caricamento dati per tabella: " + e.getMessage());
            return null;
        }
    }

    /*
     *
     *
     *       ------------>       FINE SEZIONE TABELLA TERAPIE !!!!    <-----------
     *
     *
     */

    /*
     *
     *
     *       ------------>       SEZIONE TABELLA ASSUNZIONE FARMACI !!!!    <-----------
     *
     *
     */

    private void caricaAssunzioneFarmaci(String query, String selected){

        tabellaAssunzioni.getItems().clear(); //Pulisce il contenuto della tabella
        Connection conn = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            rs = completaQuery(query, selected, "dataAssunzione");

            if (rs == null) return;

            ObservableList<AssunzioneFarmaco> assunzioneFarmaci = FXCollections.observableArrayList();

            try {
                //Ciclo che scorre ogni assunzione di farmaco per aggiungerla alla tabella
                while (rs.next()) {

                    //Creazione dell'oggetto assunzioneFarmaco da immettere nella tabella
                    String nomePaziente = taxCodeToNameMap.get(rs.getString("taxCode"));

                    LocalDate dataAssunzioneParsed = LocalDate.parse(rs.getString("dataAssunzione"));
                    LocalTime orarioAssunzioneParsed = LocalTime.parse(rs.getString("orarioAssunzione"));

                    AssunzioneFarmaco assunzioneFarmaco = new AssunzioneFarmaco(
                            nomePaziente,
                            rs.getString("farmacoAssunto"),
                            rs.getInt("quantitaAssunta"),
                            dataAssunzioneParsed,
                            orarioAssunzioneParsed
                    );
                    assunzioneFarmaci.add(assunzioneFarmaco);
                }

                //Aggiunta delle assunzioni nella tabella
                tabellaAssunzioni.setItems(assunzioneFarmaci);
            } catch (Exception e) {
                System.out.println("Errore caricamento assunzione farmaci: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Errore caricamento assunzione farmaci: " + e.getMessage());
        } finally {
            // Chiudi tutte le risorse in ordine inverso
            try { if (rs != null) rs.close(); } catch (SQLException e) { /* ignore */ }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { /* ignore */ }
            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) { /* ignore */ }
            try { if (conn != null) conn.close(); } catch (SQLException e) { /* ignore */ }
        }

    }

    public static class AssunzioneFarmaco {

        private final String nomePazienteAssunzione;
        private final String farmacoAssunzione;
        private final int quantitaAssunzione;
        private final LocalDate dataAssunzione;
        private final LocalTime orarioAssunzione;

        public AssunzioneFarmaco(String nomePazienteAssunzione, String farmacoAssunzione, int quantitaAssunzione, LocalDate dataAssunzione, LocalTime orarioAssunzione) {
            this.nomePazienteAssunzione = nomePazienteAssunzione;
            this.farmacoAssunzione = farmacoAssunzione;
            this.quantitaAssunzione = quantitaAssunzione;
            this.dataAssunzione = dataAssunzione;
            this.orarioAssunzione = orarioAssunzione;
        }

        public String getNomePazienteAssunzione(){ return nomePazienteAssunzione; }
        public String getFarmacoAssunzione(){ return farmacoAssunzione; }
        public int getQuantitaAssunzione() { return quantitaAssunzione; }
        public LocalDate getDataAssunzione() { return dataAssunzione; }
        public LocalTime getOrarioAssunzione() { return orarioAssunzione; }
    }

    /*
     *
     *
     *       ------------>       FINE SEZIONE TABELLA ASSUNZIONE FARMACI !!!!    <-----------
     *
     *
     */

    /*
    *
    *
    *       ------------>       SEZIONE TABELLA SINTOMI RISCONTRATI !!!!        <------------
    *
    *
     */

    private void caricaSintomi(String query, String selected){

        tabellaSintomi.getItems().clear();  //Pulisce il contenuto della tabella

        Connection conn = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            rs = completaQuery(query, selected, "dataInserimento");

            if (rs == null) return;

            ObservableList<Sintomo> sintomi = FXCollections.observableArrayList();

            try {
                //Ciclo che scorre ogni sintomo trovato per aggiungerla alla tabella
                while (rs.next()) {

                    //Creazione del sintomo da immettere nella tabella
                    String nomePaziente = taxCodeToNameMap.get(rs.getString("taxCode"));

                    LocalDate dataSintomiParsed = LocalDate.parse(rs.getString("dataInserimento"));

                    Sintomo sintomo = new Sintomo(
                            nomePaziente,
                            rs.getString("sintomoPrincipale"),
                            rs.getString("sintomiSpecificati"),
                            dataSintomiParsed
                    );

                    sintomi.add(sintomo);
                }

                //Aggiunta dei sintomi nella tabella
                tabellaSintomi.setItems(sintomi);

            } catch (Exception e) {
                System.out.println("Errore caricamento sintomi riscontrati: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Errore caricamento sintomi riscontrati: " + e.getMessage());
        } finally {
            // Chiudi tutte le risorse in ordine inverso
            try { if (rs != null) rs.close(); } catch (SQLException e) { /* ignore */ }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { /* ignore */ }
            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) { /* ignore */ }
            try { if (conn != null) conn.close(); } catch (SQLException e) { /* ignore */ }
        }

    }

    public static class Sintomo {

        private final String nomePazienteSintomo;
        private final String sintomoPrincipale;
        private final String sintomiSpecificati;
        private final LocalDate dataSintomo;

        public Sintomo(String nomePazienteSintomo, String sintomoPrincipale, String sintomiSpecificati, LocalDate dataSintomo) {
            this.nomePazienteSintomo = nomePazienteSintomo;
            this.sintomoPrincipale = sintomoPrincipale;
            this.sintomiSpecificati = sintomiSpecificati;
            this.dataSintomo = dataSintomo;
        }

        public String getNomePazienteSintomo() { return this.nomePazienteSintomo; }
        public String getSintomoPrincipale() { return this.sintomoPrincipale; }
        public String getSintomiSpecificati() { return this.sintomiSpecificati; }
        public LocalDate getDataSintomo() { return this.dataSintomo; }

    }

    /*
     *
     *
     *       ------------>       FINE SEZIONE TABELLA SINTOMI RISCONTRATI !!!!    <-----------
     *
     *
     */

    /*
     *
     *
     *       ------------>       SEZIONE TABELLA PATOLOGIE CONCOMITANTI !!!!    <-----------
     *
     *
     */

    private void caricaPatologie(String query, String selected){

        tabellaPatologie.getItems().clear();  //Pulisce il contenuto della tabella

        Connection conn = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            rs = completaQuery(query, selected, "dataInizio");

            if (rs == null) return;

            ObservableList<Patologia> patologie = FXCollections.observableArrayList();

            try {
                //Ciclo che scorre ogni patologia trovata per aggiungerla alla tabella
                while (rs.next()) {

                    //Creazione della patologia da immettere nella tabella
                    String nomePaziente = taxCodeToNameMap.get(rs.getString("taxCode"));

                    LocalDate dataInizioPatologiaParsed = LocalDate.parse(rs.getString("dataInizio"));

                    LocalDate dataFinePatologiaParsed = null;
                    if(!rs.getString("dataFine").isEmpty() && rs.getString("dataFine") != null){
                        dataFinePatologiaParsed = LocalDate.parse(rs.getString("dataFine"));
                    }

                    Patologia patologia = new Patologia(
                            nomePaziente,
                            rs.getString("patologiaConcomitante"),
                            dataInizioPatologiaParsed,
                            dataFinePatologiaParsed
                    );

                    patologie.add(patologia);
                }

                //Aggiunta delle patologie nella tabella
                tabellaPatologie.setItems(patologie);

            } catch (Exception e) {
                System.out.println("Errore caricamento patologie concomitanti: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Errore caricamento patologie concomitanti: " + e.getMessage());
        } finally {
            // Chiudi tutte le risorse in ordine inverso
            try { if (rs != null) rs.close(); } catch (SQLException e) { /* ignore */ }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { /* ignore */ }
            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) { /* ignore */ }
            try { if (conn != null) conn.close(); } catch (SQLException e) { /* ignore */ }
        }

    }

    public static class Patologia {

        private final String nomePazientePatologia;
        private final String patologiaConcomitante;
        private final LocalDate dataInizioPatologia;
        private final LocalDate dataFinePatologia;

        public Patologia(String nomePazientePatologia, String patologiaConcomitante, LocalDate dataInizioPatologia, LocalDate dataFinePatologia) {
            this.nomePazientePatologia = nomePazientePatologia;
            this.patologiaConcomitante = patologiaConcomitante;
            this.dataInizioPatologia = dataInizioPatologia;
            this.dataFinePatologia = dataFinePatologia;
        }

        public String getNomePazientePatologia() { return this.nomePazientePatologia; }
        public String getPatologiaConcomitante() { return this.patologiaConcomitante; }
        public LocalDate getDataInizioPatologia() { return this.dataInizioPatologia; }
        public LocalDate getDataFinePatologia() { return this.dataFinePatologia; }

    }

    /*
     *
     *
     *       ------------>       FINE SEZIONE TABELLA PATOLOGIE CONCOMITANTI !!!!    <-----------
     *
     *
     */

    @FXML
    public void onHomePagePressed(){

        try {

            Stage stage = new Stage();
            stage.setTitle("Amministratore");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlView/amministratore_view.fxml"));


            Parent root = loader.load();
            stage.setScene(new Scene(root, 650, 500));
            stage.show();

            // Chiudi la finestra corrente
            Stage currentStage = (Stage)lineChart.getScene().getWindow();
            currentStage.close();

        } catch (IOException e) { System.out.println("Errore caricamento homepage amministratore!" + e.getMessage()); }

    }

    @FXML
    public void onLogoutPressed(){

        try {
            Stage loginStage = new Stage();
            loginStage.setTitle("Login");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlView/login_view.fxml"));

            LoginController loginController = loader.getController();
            loginController.setTaxCode(Session.getInstance().getTaxCode());

            Parent root = loader.load();
            loginStage.setScene(new Scene(root));
            loginStage.show();

            Stage pazienteStage = (Stage)lineChart.getScene().getWindow();
            pazienteStage.close();

        } catch (IOException e) { System.out.println("Errore caricamento pagina di login!" + e.getMessage()); }

    }
}
