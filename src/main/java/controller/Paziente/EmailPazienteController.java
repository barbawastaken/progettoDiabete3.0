package controller.Paziente;

import controller.NavBar;
import controller.NavBarTags;
import controller.Session;
import controller.ViewNavigator;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import model.Amministratore.Paziente;
import model.Paziente.EmailPazienteModel;

import java.util.ArrayList;

public class EmailPazienteController {

    private Paziente user;
    private EmailPazienteModel emailPazienteModel;

    @FXML private TextField oggetto;
    @FXML private TextArea messaggio;
    @FXML private HBox navbarContainer;

    @FXML private Text diabetologo;
    @FXML private Text telefono;
    @FXML private Text email;
    @FXML private Text indirizzo;

    @FXML
    private void initialize(){
        NavBar navbar = new NavBar(NavBarTags.PAZIENTE_toHomepage);
        navbar.prefWidthProperty().bind(navbarContainer.widthProperty());
        navbarContainer.getChildren().add(navbar);

        user = Session.getInfosOf(Session.getInstance().getTaxCode());

        this.emailPazienteModel = new EmailPazienteModel();
        ArrayList<String> infoDiabetologo = emailPazienteModel.infoDiabetologo();

        diabetologo.setText("Diabetologo: " + infoDiabetologo.get(0));
        telefono.setText("Numero di telefono: " + infoDiabetologo.get(1));
        email.setText("Email: " + infoDiabetologo.get(2));
        indirizzo.setText("Indirizzo : " + infoDiabetologo.get(3));
    }

    @FXML
    private void onSendButtonPressed(){

        emailPazienteModel.inviaEmail(user.getTaxCode(), oggetto.getText(), messaggio.getText());

        ViewNavigator.navigateToPaziente();
    }


}
