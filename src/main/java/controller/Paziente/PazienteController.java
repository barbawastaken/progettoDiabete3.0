package controller.Paziente;

import controller.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import model.Paziente.PazienteModel;
import model.Paziente.TerapiaModel;
import java.util.ArrayList;
import java.util.List;

public class PazienteController {

    private String taxCode; //magari questo lo potremo eliminare più avanti, è solo per non far scoppiare tutto

    @FXML private ComboBox<String> filtroPeriodoComboBox;
    @FXML private LineChart<String, Number> lineChartGlicemia;

    @FXML private TableView<TerapiaModel> terapiaTabella;
    @FXML private TableColumn<TerapiaModel, String> terapia;
    @FXML private TableColumn<TerapiaModel, String> farmaco_prescritto;
    @FXML private TableColumn<TerapiaModel, String> quantita;
    @FXML private TableColumn<TerapiaModel, String> numero_assunzioni_giornaliere;
    @FXML private TableColumn<TerapiaModel, String> indicazioni;

    @FXML private TableView<Notifica> notificheTabella;
    @FXML private TableColumn<Notifica, String> notifica;

    @FXML private HBox navBarContainer;

    @FXML
    public void initialize() {

        NavBar navBar = new NavBar(NavBarTags.PAZIENTE);
        navBar.prefWidthProperty().bind(navBarContainer.widthProperty()); //riga che serve ad adattare la navbar alla pagina
        navBarContainer.getChildren().add(navBar);

        this.taxCode = Session.getInstance().getTaxCode();

        XYChart.Series<String, Number> serie = Session.caricaDatiGlicemia(null, Session.getInstance().getTaxCode());
        lineChartGlicemia.getData().clear();
        lineChartGlicemia.getData().add(serie);

        terapia.setCellValueFactory(new PropertyValueFactory<>("terapia"));
        farmaco_prescritto.setCellValueFactory(new PropertyValueFactory<>("farmacoPrescritto"));
        quantita.setCellValueFactory(new PropertyValueFactory<>("quantita"));
        numero_assunzioni_giornaliere.setCellValueFactory(new PropertyValueFactory<>("numeroAssunzioniGiornaliere"));
        indicazioni.setCellValueFactory(new PropertyValueFactory<>("indicazioni"));

        terapia.prefWidthProperty().bind(terapiaTabella.widthProperty().multiply(0.25));
        farmaco_prescritto.prefWidthProperty().bind(terapiaTabella.widthProperty().multiply(0.25));
        quantita.prefWidthProperty().bind(terapiaTabella.widthProperty().multiply(0.1));
        numero_assunzioni_giornaliere.prefWidthProperty().bind(terapiaTabella.widthProperty().multiply(0.2));
        indicazioni.prefWidthProperty().bind(terapiaTabella.widthProperty().multiply(0.2));

        notifica.setCellValueFactory(new PropertyValueFactory<>("notifica"));
        notifica.prefWidthProperty().bind(notificheTabella.widthProperty().subtract(2));

        PazienteModel model = new PazienteModel();
        List<TerapiaModel> lista = model.getTerapie(taxCode);
        terapiaTabella.setItems(FXCollections.observableArrayList(lista));

        ArrayList<String> farmaciNotifiche;
        farmaciNotifiche = model.getFarmaciNotifiche(taxCode);

        ObservableList<Notifica> notifiche = FXCollections.observableArrayList();
        for(String farmacoAssunto : farmaciNotifiche){
            Notifica notifica = new Notifica("Mancata assunzione di " + farmacoAssunto + " per più di 3 giorni");
            notifiche.add(notifica);
        }

        notificheTabella.setItems(notifiche);
        //mostraNotifiche(farmaciNotifiche);

        filtroPeriodoComboBox.setItems(FXCollections.observableArrayList("Ultima settimana", "Ultimo mese", "Tutto"));
        filtroPeriodoComboBox.setValue("Tutto");

        filtroPeriodoComboBox.setOnAction(event -> aggiornaGrafico());
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
        ViewNavigator.navigateToTerapiaConcomitante();

    }

    @FXML
    private void aggiornaGrafico() {

        String completaQuery = null;

        if(filtroPeriodoComboBox.getValue().equals("Ultima settimana")) {
            completaQuery = " AND date(data) >= date('now', '-7 days')";
        } else if(filtroPeriodoComboBox.getValue().equals("Ultimo mese")) {
            completaQuery = " AND date(data) >= date('now', '-30 days')";
        }

        XYChart.Series<String, Number> serie = Session.getInstance().caricaDatiGlicemia(completaQuery, this.taxCode);
        lineChartGlicemia.getData().clear();
        lineChartGlicemia.getData().add(serie);
    }

    /*private void caricaDatiGlicemia(String query) {
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
    }*/

}
