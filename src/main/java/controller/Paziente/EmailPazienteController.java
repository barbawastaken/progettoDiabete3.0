package controller.Paziente;

import controller.NavBar;
import controller.NavBarTags;
import controller.Session;
import controller.ViewNavigator;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import model.Amministratore.Paziente;
import model.Paziente.EmailPazienteModel;

public class EmailPazienteController {

    private Paziente user;

    @FXML private TextField oggetto;
    @FXML private TextArea messaggio;
    @FXML private HBox navbarContainer;



    @FXML
    private void initialize(){
        NavBar navbar = new NavBar(NavBarTags.PAZIENTE_toHomepage);
        navbar.prefWidthProperty().bind(navbarContainer.widthProperty());
        navbarContainer.getChildren().add(navbar);

        user = Session.getInfosOf(Session.getInstance().getTaxCode());
    }

    @FXML
    private void onSendButtonPressed(){

        EmailPazienteModel emailPazienteModel = new EmailPazienteModel();
        emailPazienteModel.inviaEmail(user.getTaxCode(), oggetto.getText(), messaggio.getText());

        ViewNavigator.navigateToPaziente();
    }


}
