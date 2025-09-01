package controller.Diabetologo;

import controller.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import model.NotificationModel;
import java.sql.SQLException;
import java.util.ArrayList;

public class DiabetologoController {

    @FXML private Button statisticheButton;
    @FXML private HBox navbarContainer;

    public DiabetologoController() { }


    @FXML
    public void isVisualizzaPazientiClicked() {

        ViewNavigator.navigateToVisualizzaPazienti();

    }

    @FXML
    public void isVisualizzaNotificheClicked() {

    }

    @FXML public void isVisualizzaStatisticheClicked(){

        ViewNavigator.navigateToVisualizzaStatistiche();

    }

    @FXML
    public void initialize() throws SQLException {
        Session.getInfos();
        NotificationModel notificationModel = new NotificationModel(Session.getInstance().getTaxCode());
        ArrayList<String> results = notificationModel.notifyRitardo();
        System.out.println(results.toString());
        NavBar navbar = new NavBar(NavBarTags.DIABETOLOGO);
        navbar.prefWidthProperty().bind(navbarContainer.widthProperty());
        navbarContainer.getChildren().add(navbar);

        System.out.println(Session.getInstance().getCognome());

        if(Session.getInfosOf(Session.getInstance().getTaxCode()).getRole().equals("DIABETOLOGO")){
            this.statisticheButton.setDisable(true);
        }

    }

}
