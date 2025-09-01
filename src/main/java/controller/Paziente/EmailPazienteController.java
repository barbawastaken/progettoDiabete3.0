package controller.Paziente;

import controller.NavBar;
import controller.NavBarTags;
import controller.Session;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Paziente.EmailPazienteModel;

public class EmailPazienteController {

    private Session user;

    @FXML private TextField oggetto;
    @FXML private TextArea messaggio;
    @FXML private HBox navbarContainer;



    @FXML
    private void initialize(){
        NavBar navbar = new NavBar(NavBarTags.PAZIENTE_toHomepage);
        navbar.prefWidthProperty().bind(navbarContainer.widthProperty());
        navbarContainer.getChildren().add(navbar);
    }

    @FXML
    private void onSendButtonPressed(){

        EmailPazienteModel emailPazienteModel = new EmailPazienteModel();
        System.out.println("Codice fiscale nella email: " + user.getTaxCode());
        System.out.println("Oggetto: " + oggetto.getText());
        System.out.println("Messaggio: " + messaggio.getText());
        emailPazienteModel.inviaEmail(user.getTaxCode(), oggetto.getText(), messaggio.getText());
        System.out.println("Codice fiscale nella email: " + user.getTaxCode());
        System.out.println("Oggetto: " + oggetto.getText());
        System.out.println("Messaggio: " + messaggio.getText());

        /*
        Stage toClose = (Stage)oggetto.getScene().getWindow();
        toClose.close();
        */
    }


}
