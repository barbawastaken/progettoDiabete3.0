package controller.Paziente;

import controller.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import model.Paziente.PazienteModel;
import model.Paziente.TerapiaModel;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PazienteController {

    private String taxCode; //magari questo lo potremo eliminare più avanti, è solo per non far scoppiare tutto
    private final static String DB_URL = "jdbc:sqlite:mydatabase.db";

    @FXML private ComboBox<String> filtroPeriodoComboBox;
    @FXML private LineChart<String, Number> lineChartGlicemia;

    @FXML private TableView<TerapiaModel> contenutoScrollPane;
    @FXML private TableColumn<TerapiaModel, String> terapia;
    @FXML private TableColumn<TerapiaModel, String> farmaco_prescritto;
    @FXML private TableColumn<TerapiaModel, String> quantita;
    @FXML private TableColumn<TerapiaModel, String> numero_assunzioni_giornaliere;
    @FXML private HBox navBarContainer;

    @FXML
    public void initialize() {

        NavBar navBar = new NavBar(NavBarTags.PAZIENTE);
        navBar.prefWidthProperty().bind(navBarContainer.widthProperty()); //riga che serve ad adattare la navbar alla pagina
        navBarContainer.getChildren().add(navBar);

        this.setTaxCode();

        String query = "SELECT data, quantita, momentoGiornata, prePost FROM rilevazioniGlicemiche WHERE taxCode = ?";
        caricaDatiGlicemia(query);

        terapia.setCellValueFactory(new PropertyValueFactory<>("terapia"));
        farmaco_prescritto.setCellValueFactory(new PropertyValueFactory<>("farmacoPrescritto"));
        quantita.setCellValueFactory(new PropertyValueFactory<>("quantita"));
        numero_assunzioni_giornaliere.setCellValueFactory(new PropertyValueFactory<>("numeroAssunzioniGiornaliere"));

        PazienteModel model = new PazienteModel();
        List<TerapiaModel> lista = model.getTerapie(taxCode);
        contenutoScrollPane.setItems(FXCollections.observableArrayList(lista));

        ArrayList<String> farmaciNotifiche;
        farmaciNotifiche = model.getFarmaciNotifiche(taxCode);
        mostraNotifiche(farmaciNotifiche);

        filtroPeriodoComboBox.setItems(FXCollections.observableArrayList("Ultima settimana", "Ultimo mese", "Tutto"));
        filtroPeriodoComboBox.setValue("Tutto");

        filtroPeriodoComboBox.setOnAction(event -> aggiornaGrafico(query));
    }

    public void setTaxCode() {
        this.taxCode = Session.getInstance().getTaxCode();
    }

    @FXML
    private void onRilevazioneGlicemiaClicked() {
        ViewNavigator.navigateToRilevazioneGlicemia();

    }

    @FXML
    private void onAddSymptomsClicked() {
        ViewNavigator.navigateToAddSympoms();
    }

    @FXML
    private void onAddAssunzioneFarmacoClicked() {
        ViewNavigator.navigateToAssunzioneFarmaco();
    }

    @FXML
    public void onConcomitantiClicked() {
        ViewNavigator.navigateToAggiungiTerapia();

    }

    @FXML
    private void aggiornaGrafico(String query) {

        if(filtroPeriodoComboBox.getValue().equals("Ultima settimana")) {
            query += " AND date(data) >= date('now', '-7 days')";
        } else if(filtroPeriodoComboBox.getValue().equals("Ultimo mese")) {
            query += " AND date(data) >= date('now', '-30 days')";
        }

        caricaDatiGlicemia(query);
    }

    private void caricaDatiGlicemia(String query) {
        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.setName("Valori glicemici");

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, taxCode);

            ResultSet rs = stmt.executeQuery();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");
            List<XYChart.Data<String, Number>> dati = new ArrayList<>();

            while (rs.next()) {
                String dataStr = rs.getString("data");
                LocalDate data = LocalDate.parse(dataStr);
                int valore = rs.getInt("quantita");
                String momento = rs.getString("momentoGiornata");
                String prePost = rs.getString("prePost");

                String labelX = data.format(formatter) + " - " + momento + " - " + prePost;

                XYChart.Data<String, Number> punto = new XYChart.Data<>(labelX, valore);

                dati.add(punto);
            }

            // Pulisci il grafico prima di aggiungere nuovi dati
            lineChartGlicemia.getData().clear();
            serie.getData().addAll(dati);
            lineChartGlicemia.getData().add(serie);

        } catch (Exception e) {
            System.out.println("Errore caricamento grafico: " + e);
        }
    }
    private void mostraNotifiche(ArrayList<String> farmaciNotifiche) {

        for(String f : farmaciNotifiche) {
            messaggioErrore(f);
        }

    }

    public void messaggioErrore(String farmaco) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Notifica!!!");
        alert.setHeaderText(null); // oppure "Attenzione!"
        alert.setContentText("Non e' stata rilevata alcuna assunzione di " + farmaco + " negli ultimi 3 giorni");
        alert.showAndWait();
    }

}
