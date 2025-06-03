package controller.Paziente;

import controller.LoginController;
import controller.Paziente.AggiuntaSintomi.AggiuntaSintomiController;
import controller.Paziente.AssunzioneFarmaco.AssunzioneFarmacoController;
import controller.Paziente.PatologieConcomitanti.PatologieConcomitantiController;
import controller.Paziente.RilevazioneGlicemia.RilevazioneGlicemiaController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Paziente.PazienteModel;
import view.Paziente.PazienteView;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PazienteController {

    private String taxCode;
    private final static String DB_URL = "jdbc:sqlite:mydatabase.db";

    @FXML private Button rilevaGlicemia;
    @FXML private ComboBox<String> filtroPeriodoComboBox;
    @FXML private LineChart<String, Number> lineChartGlicemia;

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
        caricaDatiGlicemia(LocalDate.of(2025, 1, 1));
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

        Stage pazienteStage = (Stage)rilevaGlicemia.getScene().getWindow();
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
        LocalDate dataInizio = switch (filtro) {
            case "Ultima settimana" -> LocalDate.now().minusDays(7);
            case "Ultimo mese" -> LocalDate.now().minusMonths(1);
            default -> LocalDate.MIN; // nel dubbio, nessun filtro
        };
        caricaDatiGlicemia(dataInizio);
    }

    private void caricaDatiGlicemia(LocalDate dataInizio) {
        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.setName("Valori glicemici");

        String query = "SELECT data, quantita, momentoGiornata, prePost FROM rilevazioniGlicemiche WHERE taxCode = ? AND data >= ? ORDER BY data DESC";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, taxCode);
            stmt.setDate(2, java.sql.Date.valueOf(dataInizio));
            ResultSet rs = stmt.executeQuery();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");
            List<XYChart.Data<String, Number>> dati = new ArrayList<>();
            int count = 0;

            while (rs.next() && count < 5) {

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
                count++;
            }

            Collections.reverse(dati);
            serie.getData().addAll(dati);
            lineChartGlicemia.getData().clear();
            lineChartGlicemia.getData().add(serie);

            for (XYChart.Data<String, Number> data : dati) {
                Node node = data.getNode();
                if (node instanceof Label label) {
                    label.setStyle("-fx-font-size: 10px; -fx-background-color: white; -fx-padding: 2;");
                    label.setTranslateY(-20);
                }
            }

        } catch (Exception e) {
            System.out.println("Errore caricamento grafico: " + e);
        }
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
