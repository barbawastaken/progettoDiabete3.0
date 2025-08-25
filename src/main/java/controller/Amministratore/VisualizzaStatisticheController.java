package controller.Amministratore;

import controller.LoginController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import model.Amministratore.VisualizzaStatisticheModel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class VisualizzaStatisticheController {

    private String taxCode;

    @FXML private Button homePageButton;

    @FXML private LineChart<String, Number> lineChart;

    @FXML private ComboBox<String> pazienteCombo;

    private Map<String, XYChart.Series<String, Number>> pazientiSeriesMap = new HashMap<>();

    @FXML
    public void initialize() {
        // Carico i dati dal DB
        pazientiSeriesMap = VisualizzaStatisticheModel.caricaDati();

        // Popolo la ComboBox
        pazienteCombo.getItems().add("Tutti");
        pazienteCombo.getItems().addAll(pazientiSeriesMap.keySet());

        pazienteCombo.setOnAction(event -> updateChart());

        // Default: mostra tutti
        pazienteCombo.setValue("Tutti");
        updateChart();
    }

    public void setTaxCode(String taxCode) { this.taxCode = taxCode; }

    @FXML
    public void onHomePagePressed(){

        try {

            Stage stage = new Stage();
            stage.setTitle("Amministratore");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlView/amministratore_view.fxml"));

            AmministratoreController amministratoreController = loader.getController();
            amministratoreController.setTaxCode(taxCode);

            Parent root = loader.load();
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
            Stage loginStage = new Stage();
            loginStage.setTitle("Login");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlView/login_view.fxml"));

            LoginController loginController = loader.getController();
            loginController.setTaxCode(taxCode);

            Parent root = loader.load();
            loginStage.setScene(new Scene(root));
            loginStage.show();

            Stage pazienteStage = (Stage)homePageButton.getScene().getWindow();
            pazienteStage.close();

        } catch (IOException e) { System.out.println("Errore caricamento pagina di login!" + e.getMessage()); }

    }

    private void updateChart() {
        lineChart.getData().clear();
        String selected = pazienteCombo.getValue();

        if ("Tutti".equals(selected)) {
            lineChart.getData().addAll(pazientiSeriesMap.values());
        } else if (pazientiSeriesMap.containsKey(selected)) {
            lineChart.getData().add(pazientiSeriesMap.get(selected));
        }
    }
}
