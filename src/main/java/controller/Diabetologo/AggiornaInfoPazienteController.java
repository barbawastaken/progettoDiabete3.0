package controller.Diabetologo;

import javafx.scene.control.TextArea;
import controller.NavBar;
import controller.NavBarTags;
import controller.Session;
import controller.ViewNavigator;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import model.Amministratore.Paziente;
import model.Diabetologo.AggiornaInfoPazienteModel;

public class AggiornaInfoPazienteController {

    private Paziente paziente;
    private String taxCodeDiabetologo;

    private final AggiornaInfoPazienteModel model = new AggiornaInfoPazienteModel();

    @FXML private TextArea infoTextArea;
    @FXML private HBox navbarContainer;

    public void setTaxCode() {
        this.paziente = Session.getInstance().getPazienteInEsame();
        this.taxCodeDiabetologo = Session.getInstance().getTaxCode();
    }

    @FXML
    private void initialize() {

        NavBar navbar = new NavBar(NavBarTags.DIABETOLOGO_operazioniDiabetologo);
        navbar.prefWidthProperty().bind(navbarContainer.widthProperty());
        this.setTaxCode(); //IMPORTANTE! altrimenti si rompe tutto
        navbarContainer.getChildren().add(navbar);

        infoTextArea.setText(model.getInfoPaziente(paziente.getTaxCode()));
    }

    @FXML
    private void onResetClicked() {
        infoTextArea.setText("");
    }

    @FXML
    private void onSaveClicked() {

        model.insertData(paziente.getTaxCode(), infoTextArea.getText(), taxCodeDiabetologo);

        ViewNavigator.navigateToDiabetologo();
    }
}
