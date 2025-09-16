package controller.Amministratore;

import controller.NavBar;
import controller.NavBarTags;
import controller.ViewNavigator;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;


public class AmministratoreController {
    @FXML private HBox navbarContainer;
    private String taxCode;

    public void setTaxCode(String taxCode) { this.taxCode = taxCode; }

    @FXML
    private void initialize(){
        NavBar navbar = new NavBar(NavBarTags.AMMINISTRATORE);
        navbar.prefWidthProperty().bind(navbarContainer.widthProperty());
        navbarContainer.getChildren().add(navbar);
    }
    @FXML
    private void isInserisciUtenteClicked()  {
        ViewNavigator.navigateToAddUser();
    }

    @FXML
    private void isVisualizzaUtentiClicked() {
        ViewNavigator.navigateToVisualizzaUtenti();
    }

    @FXML
    private void isVisualizzaOperazioniClicked(){
        ViewNavigator.navigateToOperazioniDiabetologi();
    }

}

