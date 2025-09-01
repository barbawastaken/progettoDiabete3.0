package controller.Diabetologo;

import controller.NavBar;
import controller.NavBarTags;
import controller.Session;
import controller.ViewNavigator;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
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

    @FXML
    private void initialize() {

        NavBar navbar = new NavBar(NavBarTags.toModificaUtenti);
        navbar.prefWidthProperty().bind(navbarContainer.widthProperty());
        
        navbarContainer.getChildren().add(navbar);
        Paziente paziente = Session.getInstance().getPazienteInEsame();
        String taxCodeDiabetologo = Session.getInstance().getTaxCode();

        System.out.println("VALORE PAZIENTE: " + paziente.toString());
        System.out.println("TAXCODE DIABETOLOGO: " + taxCodeDiabetologo);

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

        XYChart.Series<String, Number> serie = Session.getInstance().caricaDatiGlicemia(null, paziente.getTaxCode());
        lineChartGlicemia.getData().clear();
        lineChartGlicemia.getData().add(serie);

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

