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

    @FXML private HBox navbarContainer;

    @FXML
    public void initialize() {

        NavBar navBar = new NavBar(NavBarTags.PAZIENTE);
        navBar.prefWidthProperty().bind(navbarContainer.widthProperty()); //riga che serve ad adattare la navbar alla pagina
        navbarContainer.getChildren().add(navBar);

        String taxCode = Session.getInstance().getTaxCode();

        XYChart.Series<String, Number> serie = Session.caricaDatiGlicemia("Tutto", Session.getInstance().getTaxCode());
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

        filtroPeriodoComboBox.setValue("Tutto");

        filtroPeriodoComboBox.setOnAction(event -> {
            final XYChart.Series<String, Number> serieAggiornata = Session.caricaDatiGlicemia(
                    filtroPeriodoComboBox.getValue(),
                    Session.getInstance().getTaxCode()
            );

            lineChartGlicemia.getData().clear();
            lineChartGlicemia.getData().add(serieAggiornata);
        });
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

}
