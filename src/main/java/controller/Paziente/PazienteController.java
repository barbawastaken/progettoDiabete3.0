package controller.Paziente;

import controller.LoginController;
import controller.Paziente.AggiuntaSintomi.AggiuntaSintomiController;
import controller.Paziente.AssunzioneFarmaco.AssunzioneFarmacoController;
import controller.Paziente.PatologieConcomitanti.PatologieConcomitantiController;
import controller.Paziente.RilevazioneGlicemia.RilevazioneGlicemiaController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Paziente.PazienteModel;
import model.Paziente.TerapiaModel;
import view.Paziente.PazienteView;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PazienteController {

    private String taxCode;
    private final static String DB_URL = "jdbc:sqlite:mydatabase.db";

    @FXML private Button rilevaGlicemia;
    @FXML private ComboBox<String> filtroPeriodoComboBox;
    @FXML private LineChart<String, Number> lineChartGlicemia;
    @FXML private HBox topBar;
    @FXML private ImageView immagineProfilo;

    @FXML private TableView<TerapiaModel> contenutoScrollPane;
    @FXML private TableColumn<TerapiaModel, String> terapia;
    @FXML private TableColumn<TerapiaModel, String> farmaco_prescritto;
    @FXML private TableColumn<TerapiaModel, String> quantita;
    @FXML private TableColumn<TerapiaModel, String> numero_assunzioni_giornaliere;

    @FXML
    public void initialize() {

        /*Platform.runLater(() -> {
            Stage stage = (Stage) lineChartGlicemia.getScene().getWindow();
            stage.setMinWidth(800);
            stage.setMinHeight(500);
        });*/

        topBar.heightProperty().addListener((obs, oldVal, newVal) -> {
            immagineProfilo.setFitHeight(newVal.doubleValue());
        });

        filtroPeriodoComboBox.setItems(FXCollections.observableArrayList("Ultima settimana", "Ultimo mese", "Tutto"));
        filtroPeriodoComboBox.setValue("Tutto");

        filtroPeriodoComboBox.setOnAction(event -> aggiornaGrafico());
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
        caricaDatiGlicemia(LocalDate.of(2025, 1, 1));

        terapia.setCellValueFactory(new PropertyValueFactory<>("terapia"));
        farmaco_prescritto.setCellValueFactory(new PropertyValueFactory<>("farmacoPrescritto"));
        quantita.setCellValueFactory(new PropertyValueFactory<>("quantita"));
        numero_assunzioni_giornaliere.setCellValueFactory(new PropertyValueFactory<>("numeroAssunzioniGiornaliere"));

        PazienteModel model = new PazienteModel();
        List<TerapiaModel> lista = model.getTerapie(taxCode);
        contenutoScrollPane.setItems(FXCollections.observableArrayList(lista));

        ArrayList<String> farmaciNotifiche = new ArrayList<>();
        farmaciNotifiche = model.getFarmaciNotifiche(taxCode);
        mostraNotifiche(farmaciNotifiche);
    }

    @FXML
    private void onEmailClicked(){
        try{

            Stage emailPaziente = new Stage();
            emailPaziente.setTitle("Email a diabetologo");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlView/email_paziente_view.fxml"));
            Parent root = loader.load();

            EmailPazienteController emailPazienteController = loader.getController();
            emailPazienteController.setTaxCode(taxCode);
            emailPaziente.setScene(new Scene(root));
            emailPaziente.show();

        } catch (IOException e){
            System.out.println("Errore caricamento pagina email!" + e.getMessage());
        }
    }

    @FXML
    private void onProfiloClicked(){

        try{

            Stage profiloPaziente = new Stage();
            profiloPaziente.setTitle("Profilo Paziente");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlView/profiloPaziente.fxml"));
            Parent root = loader.load();

            ModificaPazienteController modificaPazienteController = loader.getController();
            modificaPazienteController.setTaxCode(taxCode);
            profiloPaziente.setScene(new Scene(root));
            profiloPaziente.show();

        } catch (IOException e){
            System.out.println("Errore caricamento pagina profilo!" + e.getMessage());
        }

        Stage homePagePaziente = (Stage) topBar.getScene().getWindow();
        homePagePaziente.close();

    }

    @FXML
    private void onLogoutPressed(){

        try {
            Stage loginStage = new Stage();
            loginStage.setTitle("Login");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlView/login_view.fxml"));
            Parent root = loader.load();
            LoginController loginController = loader.getController();
            loginController.setTaxCode(taxCode);
            loginStage.setScene(new Scene(root));
            loginStage.show();

        } catch (IOException e) { System.out.println("Errore caricamento pagina di login!" + e.getMessage()); }

        Stage pazienteStage = (Stage)topBar.getScene().getWindow();
        pazienteStage.close();

    }

    @FXML
    private void onRilevazioneGlicemiaClicked() throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Rilevazione Glicemia");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlView/rilevazione_glicemia_view.fxml"));
        Parent root = loader.load();
        RilevazioneGlicemiaController controller = loader.getController();
        controller.setTaxCode(taxCode);
        stage.setScene(new Scene(root));
        stage.show();

    }

    @FXML
    private void onAddSymptomsClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlView/aggiunta_sintomi_view.fxml"));
        Parent root = loader.load();
        AggiuntaSintomiController controller = loader.getController();
        controller.setTaxCode(taxCode);
        Stage stage = new Stage();
        stage.setTitle("Aggiunta sintomi");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void onAddAssunzioneFarmacoClicked() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlView/assunzione_farmaco_view.fxml"));
        Parent root = loader.load();
        AssunzioneFarmacoController controller = loader.getController();
        controller.setTaxCode(taxCode);

        Stage stage = new Stage();
        stage.setTitle("Assunzione farmaco");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void onConcomitantiClicked() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlView/patologie_concomitanti_view.fxml"));
        Parent root = loader.load();
        PatologieConcomitantiController controller = loader.getController();
        controller.setTaxCode(taxCode);

        Stage stage = new Stage();
        stage.setTitle("Specifica patologie concomitanti");
        stage.setScene(new Scene(root));
        stage.show();

    }

    public PazienteController(){

    }

    @FXML
    private void aggiornaGrafico() {
        String filtro = filtroPeriodoComboBox.getValue();
        LocalDate dataInizio;

        switch (filtro) {
            case "Ultima settimana":
                dataInizio = LocalDate.now().minusDays(7);
                break;
            case "Ultimo mese":
                dataInizio = LocalDate.now().minusMonths(1);
                break;
            default:
                dataInizio = LocalDate.of(2025, 1, 1); // Data molto indietro per ottenere tutti i dati
                break;
        }
        caricaDatiGlicemia(dataInizio);
    }

    private void caricaDatiGlicemia(LocalDate dataInizio) {
        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.setName("Valori glicemici");

        String query = "SELECT data, quantita, momentoGiornata, prePost FROM rilevazioniGlicemiche " +
                "WHERE taxCode = ? AND date(data) >= date(?) " +
                "ORDER BY data ASC"; // Ordinamento crescente per una corretta visualizzazione

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, taxCode);
            stmt.setString(2, dataInizio.toString());

            //System.out.println("Query parameters: " + taxCode + ", " + dataInizio.toString());
            ResultSet rs = stmt.executeQuery();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");
            List<XYChart.Data<String, Number>> dati = new ArrayList<>();

            while (rs.next()) {
                String dataStr = rs.getString("data");
                LocalDate data = LocalDate.parse(dataStr);
                int valore = rs.getInt("quantita");
                String momento = rs.getString("momentoGiornata");
                String prePost = rs.getString("prePost");

                String labelX = data.format(formatter);
                String labelCompleta = momento + " - " + prePost;

                XYChart.Data<String, Number> punto = new XYChart.Data<>(labelX, valore);
                punto.setNode(new Label(labelCompleta));

                dati.add(punto);
            }

            // Pulisci il grafico prima di aggiungere nuovi dati
            lineChartGlicemia.getData().clear();
            serie.getData().addAll(dati);
            lineChartGlicemia.getData().add(serie);

            // Stile per le etichette
            for (XYChart.Data<String, Number> data : serie.getData()) {
                Node node = data.getNode();
                if (node instanceof Label label) {
                    label.setStyle("-fx-font-size: 10px; -fx-background-color: white; -fx-padding: 2;");
                    label.setTranslateY(-20);
                }
            }

        } catch (Exception e) {
            System.out.println("Errore caricamento grafico: " + e);
            e.printStackTrace();
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


    public PazienteController(String taxCode, PazienteModel pazienteModel, PazienteView pazienteView) {



        Stage pazienteStage = new Stage();
        pazienteStage.setTitle("Homepage paziente");
        pazienteStage.setScene(pazienteView.getScene());

        pazienteStage.setMinWidth(600);
        pazienteStage.setMinHeight(800);
        pazienteStage.alwaysOnTopProperty();

        pazienteStage.show();



        /*pazienteView.getRilevazioniGlicemiaButton().setOnAction(e -> {

            RilevazioneGlicemiaModel rilevazioneGlicemiaModel = new RilevazioneGlicemiaModel();
            RilevazioneGlicemiaView rilevazioneGlicemiaView = new RilevazioneGlicemiaView();

            try {
                new RilevazioneGlicemiaController(taxCode, rilevazioneGlicemiaModel, rilevazioneGlicemiaView, pazienteStage);
            } catch (Exception ex) {
                System.out.println("Errore: " + ex.getMessage());
            }
        });*/

        /*pazienteView.getInserimentoSintomiButton().setOnAction(e -> {

            AggiuntaSintomiModel aggiuntaSintomiModel = new AggiuntaSintomiModel();
            AggiuntaSintomiView aggiuntaSintomiView = new AggiuntaSintomiView();

            try {
                new AggiuntaSintomiController(taxCode, aggiuntaSintomiModel, aggiuntaSintomiView, pazienteStage);
            } catch (Exception ex) {
                System.out.println("Errore: " + ex.getMessage());
            }

        });*/
    }
}
