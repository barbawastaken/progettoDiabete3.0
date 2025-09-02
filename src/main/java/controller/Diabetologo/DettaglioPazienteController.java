package controller.Diabetologo;

import controller.NavBar;
import controller.NavBarTags;
import controller.Session;
import controller.ViewNavigator;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import model.Amministratore.Paziente;
import javafx.scene.control.Label;


public class DettaglioPazienteController {
    
    @FXML private HBox navbarContainer;

    @FXML private Label nomeLabel;
    @FXML private Label cognomeLabel;
    @FXML private Label codiceFiscaleLabel;
    @FXML private Label sessoLabel;
    @FXML private Label dataNascitaLabel;
    @FXML private Label passwordLabel;
    @FXML private Label viaLabel;
    @FXML private Label numeroCivicoLabel;
    @FXML private Label capLabel;
    @FXML private Label paeseLabel;
    @FXML private Label cittaLabel;
    @FXML private Label emailLabel;
    @FXML private Label cellulareLabel;
    @FXML private Label pesoLabel;
    @FXML private Label altezzaLabel;

    @FXML private TextArea infoAggiuntivePaziente;
    @FXML private LineChart<String, Number> lineChartGlicemia;
    @FXML private ComboBox<String> filtroPeriodoComboBox;

    @FXML
    private void initialize() {

        String schermataDiArrivo = Session.getInstance().getSchermataDiArrivo();
        NavBar navbar;

        if(schermataDiArrivo != null && schermataDiArrivo.equals("TABELLA_PAZIENTI")){
            navbar = new NavBar(NavBarTags.DIABETOLOGO_toModificaUtenti);
        } else {
            navbar = new NavBar(NavBarTags.DIABETOLOGO_toModificaUtentiFromNotifiche);
        }

        navbar.prefWidthProperty().bind(navbarContainer.widthProperty());
        navbarContainer.getChildren().add(navbar);
        Paziente paziente = Session.getInstance().getPazienteInEsame();
        String taxCodeDiabetologo = Session.getInstance().getTaxCode();

        nomeLabel.setText("Nome: " + paziente.getNome());
        cognomeLabel.setText("Cognome: " + paziente.getCognome());
        codiceFiscaleLabel.setText("Codice Fiscale: " + paziente.getTaxCode());
        sessoLabel.setText("Sesso: " + paziente.getGender());
        dataNascitaLabel.setText("Data di Nascita: " + paziente.getBirthday());
        passwordLabel.setText("Password: " + paziente.getPassword());
        viaLabel.setText("Via: " + paziente.getAddress());
        numeroCivicoLabel.setText("Numero Civico: " + paziente.getNumber());
        capLabel.setText("CAP: " + paziente.getCap());
        paeseLabel.setText("Paese di Residenza: " + paziente.getCountryOfResidence());
        cittaLabel.setText("Città: " + paziente.getCity());
        emailLabel.setText("Email: " + paziente.getEmail());
        cellulareLabel.setText("Cellulare: " + paziente.getTelephone());
        pesoLabel.setText("Peso: " + paziente.getWeight());
        altezzaLabel.setText("Altezza: " + paziente.getHeight());

        infoAggiuntivePaziente.setText(Session.getInfoAggiuntiveOf(paziente.getTaxCode()));

        XYChart.Series<String, Number> serie = Session.caricaDatiGlicemia("Tutto", paziente.getTaxCode());
        lineChartGlicemia.getData().clear();
        lineChartGlicemia.getData().add(serie);

        filtroPeriodoComboBox.setValue("Tutto");

        filtroPeriodoComboBox.setOnAction(event -> {
            final XYChart.Series<String, Number> serieAggiornata = Session.caricaDatiGlicemia(
                    filtroPeriodoComboBox.getValue(),
                    paziente.getTaxCode()
            );

            lineChartGlicemia.getData().clear();
            lineChartGlicemia.getData().add(serieAggiornata);
        });

    }

    @FXML private void handleAggiungiTerapia() {

        ViewNavigator.navigateToAggiungiTerapia();

    }

    @FXML private void handleModificaTerapia() {

        ViewNavigator.navigateToTabellaModificaTerapia();

    }

    @FXML private void handleAggiornaInfo() {

        ViewNavigator.navigateToInfoPaziente();

    }

}

