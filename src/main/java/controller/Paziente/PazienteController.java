package controller.Paziente;

import controller.*;
import controller.Paziente.AggiuntaSintomi.AggiuntaSintomiController;
import controller.Paziente.AssunzioneFarmaco.AssunzioneFarmacoController;
import controller.Paziente.PatologieConcomitanti.PatologieConcomitantiController;
import controller.Paziente.RilevazioneGlicemia.RilevazioneGlicemiaController;
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

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PazienteController {

    private Session user = Session.getInstance();
    private String taxCode; //magari questo lo potremo eliminare più avanti, è solo per non far scoppiare tutto
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
    @FXML private HBox navBarContainer;

    @FXML
    public void initialize() {

        /*Platform.runLater(() -> {
            Stage stage = (Stage) lineChartGlicemia.getScene().getWindow();
            stage.setMinWidth(800);
            stage.setMinHeight(500);
        });*/

        /* --> commentato da me (barba 25/08/25)

        topBar.heightProperty().addListener((obs, oldVal, newVal) -> {
            immagineProfilo.setFitHeight(newVal.doubleValue());
        });
        */
        NavBar navBar = new NavBar(NavBarTags.PAZIENTE);
        navBar.prefWidthProperty().bind(navBarContainer.widthProperty()); //riga che serve ad adattare la navbar alla pagina
        System.out.println("tax code quando viene caricato il controller del paziente: " + Session.getInstance().getTaxCode());
        navBarContainer.getChildren().add(navBar);

        this.setTaxCode();

        filtroPeriodoComboBox.setItems(FXCollections.observableArrayList("Ultima settimana", "Ultimo mese", "Tutto"));
        filtroPeriodoComboBox.setValue("Tutto");

        filtroPeriodoComboBox.setOnAction(event -> aggiornaGrafico());
    }

    public void setTaxCode() {
        this.taxCode = Session.getInstance().getTaxCode();
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
        ViewNavigator.navigateToRilevazioneGlicemia();

    }

    @FXML
    private void onAddSymptomsClicked() throws IOException {
        ViewNavigator.navigateToAddSympoms();
    }

    @FXML
    private void onAddAssunzioneFarmacoClicked() throws IOException {
        ViewNavigator.navigateToAssunzioneFarmaco();
    }

    @FXML
    public void onConcomitantiClicked() throws IOException {
        ViewNavigator.navigateToAggiungiTerapia();

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

}
