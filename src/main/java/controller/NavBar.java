package controller;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;

public class NavBar extends HBox {


    private final NavBarTags navbarRequested;


    public NavBar(NavBarTags navbarRequested) {
        this.navbarRequested = navbarRequested;

        initialize();
    }

    /**
     * Initialize the navigation bar
     */
    private void initialize() {
        Label brandLabel = new Label("Glucose Keeper");
        Font font = Font.loadFont(getClass().getResourceAsStream("/fonts/fontLogo.ttf"), 25);
        brandLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 25px;");
        brandLabel.setFont(font);
        this.getChildren().add(brandLabel);

        // Spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        this.getChildren().add(spacer);

        if(navbarRequested == NavBarTags.PAZIENTE) {
            navbarPerPazienti();
        } else if(navbarRequested == NavBarTags.PAZIENTE_toHomepage) {
            navbarToHomepagePaziente();
        } else if(navbarRequested == NavBarTags.DIABETOLOGO){
            navbarPerDiabetologi();
        } else if (navbarRequested == NavBarTags.DIABETOLOGO_toHomepage) {
            navbarToHomepageDiabetologo();
        } else if (navbarRequested == NavBarTags.AMMINISTRATORE){
            navbarPerAmministratore();
        } else if (navbarRequested == NavBarTags.AMMINISTRATORE_toHomepage){
            navbarToHomepageAmministratore();
        } else if(navbarRequested == NavBarTags.DIABETOLOGO_toModificaUtenti){
            navbarPerVisualizzaPaziente();
        } else if(navbarRequested == NavBarTags.DIABETOLOGO_toModificaUtentiFromNotifiche){
            navbarPerVisualizzaPazienteFromNotifiche();
        } else if(navbarRequested == NavBarTags.DIABETOLOGO_operazioniDiabetologo){
            navbarPerOperazioniDiabetologo();
        } else if(navbarRequested == NavBarTags.DIABETOLOGO_operazioneRitornoTabellaTerapie){
            navbarPerRitornoTabellaModificaTerapia();
        } else if(navbarRequested == NavBarTags.DIABETOLOGO_ritornoVisualizzaStatistiche){
            navbarPerRitornoVisualizzaStatistiche();
        } else if(navbarRequested == NavBarTags.DIABETOLOGO_fromNotifiche) {
            navbarPerDiabetologoNotifiche();
        } else if(navbarRequested == NavBarTags.DIABETOLOGO_infoFromStatistiche) {
            navbarPerInfoPazienteFromNotifiche();
        } else if(navbarRequested == NavBarTags.DIABETOLOGO_fromVisualizzaTerapie) {
            navbarPerVisualizzaTerapie();
        } else if(navbarRequested == NavBarTags.AMMINISTRATORE_ritornoVisualizzaListaUtenti){
            navbarPerRitornoVisualizzaListaUtenti();
        }
    }

    /**
     * Create navigation buttons for authenticated users
     */

    /*
    private void createAuthenticatedNavButtons() {
        Button homeBtn = createNavButton("Home", e -> ViewNavigator.navigateToHome());
        Button dashboardBtn = createNavButton("Dashboard", e -> ViewNavigator.navigateToDashboard());
        Button profileBtn = createNavButton("Profile", e -> ViewNavigator.navigateToProfile());

        //Label userLabel = new Label("Hello, " + username);
        //userLabel.setStyle("-fx-text-fill: white;");

        Button logoutBtn = createNavButton("Logout", e -> ViewNavigator.logout());

        this.getChildren().addAll(homeBtn, dashboardBtn, profileBtn, logoutBtn);
    }
    */
    /*
    * creo la navbar per i pazienti
     */


    /*
    NavBar che ci serve nella homepage del paziente
     */
    private void navbarPerPazienti(){
        Button emailButton = createNavButton("CONTATTI", e -> ViewNavigator.navigateToEmail());
        Button profileButton = createNavButton("PROFILO", e-> ViewNavigator.navigateToProfilePaziente());
        Button logoutButton = createNavButton("LOGOUT", e -> ViewNavigator.navigateToLogout());

        this.getChildren().addAll(emailButton, profileButton, logoutButton);
    }

    private void navbarPerDiabetologi(){
        Button profileButton = createNavButton("PROFILO", e-> ViewNavigator.navigateToProfileDiabetologo());
        Button logoutButton = createNavButton("LOGOUT", e -> ViewNavigator.navigateToLogout());

        this.getChildren().addAll(profileButton, logoutButton);
    }

    /*
    * NavBar che usiamo in generale quando in un menu c'è solo la possibilità di tornare alla home
     */

    private void navbarToHomepagePaziente(){
        Button homepageButton = createNavButton("HOMEPAGE", e->ViewNavigator.navigateToPaziente());
        Button logoutButton = createNavButton("LOGOUT", e -> ViewNavigator.navigateToLogout());
        this.getChildren().addAll(homepageButton, logoutButton);
    }

    private void navbarToHomepageDiabetologo(){
        Button homepageButton = createNavButton("HOMEPAGE", e->ViewNavigator.navigateToDiabetologo());
        Button logoutButton = createNavButton("LOGOUT", e -> ViewNavigator.navigateToLogout());
        this.getChildren().addAll(homepageButton, logoutButton);
    }
    private void navbarPerVisualizzaPaziente(){
        Button modificaTerapiaButton = createNavButton("VISUALIZZA TERAPIE", e->ViewNavigator.navigateToTabellaModificaTerapia());
        Button modificaInfoButton = createNavButton("MODIFICA INFO", e->ViewNavigator.navigateToInfoPaziente());
        Button indietroButton = createNavButton("INDIETRO" , e->ViewNavigator.navigateToVisualizzaPazienti());
        Button logoutButton = createNavButton("LOGOUT", e -> ViewNavigator.navigateToLogout());
        this.getChildren().addAll(modificaTerapiaButton, modificaInfoButton, indietroButton, logoutButton);
    }

    private void navbarPerVisualizzaPazienteFromNotifiche(){
        Button modificaTerapiaButton = createNavButton("VISUALIZZA TERAPIE", e->ViewNavigator.navigateToTabellaModificaTerapia());
        Button modificaInfoButton = createNavButton("MODIFICA INFO", e->ViewNavigator.navigateToInfoPaziente());
        Button indietroButton = createNavButton("INDIETRO" , e->ViewNavigator.navigateToDiabetologo());
        Button logoutButton = createNavButton("LOGOUT", e -> ViewNavigator.navigateToLogout());
        this.getChildren().addAll(modificaTerapiaButton, modificaInfoButton, indietroButton, logoutButton);
    }

    private void navbarPerOperazioniDiabetologo(){
        Button indietroButton = createNavButton("INDIETRO" , e->ViewNavigator.navigateToPatientDetails());
        Button homepageButton = createNavButton("HOMEPAGE", e->ViewNavigator.navigateToDiabetologo());
        Button logoutButton = createNavButton("LOGOUT", e -> ViewNavigator.navigateToLogout());

        this.getChildren().addAll(indietroButton, homepageButton, logoutButton);
    }

    private void navbarPerVisualizzaTerapie(){
        Button aggiungiTerapiaButton = createNavButton("AGGIUNGI TERAPIA", e->ViewNavigator.navigateToAggiungiTerapia());
        Button indietroButton = createNavButton("INDIETRO" , e->ViewNavigator.navigateToPatientDetails());
        Button homepageButton = createNavButton("HOMEPAGE", e->ViewNavigator.navigateToDiabetologo());
        Button logoutButton = createNavButton("LOGOUT", e -> ViewNavigator.navigateToLogout());

        this.getChildren().addAll(aggiungiTerapiaButton, indietroButton, homepageButton, logoutButton);
    }

    private void navbarPerRitornoTabellaModificaTerapia(){
        //Button aggiungiTerapiaButton = createNavButton("AGGIUNGI TERAPIA", e->ViewNavigator.navigateToAggiungiTerapia());
        Button indietroButton = createNavButton("INDIETRO" , e->ViewNavigator.navigateToTabellaModificaTerapia());
        Button homepageButton = createNavButton("HOMEPAGE", e->ViewNavigator.navigateToDiabetologo());
        Button logoutButton = createNavButton("LOGOUT", e -> ViewNavigator.navigateToLogout());

        this.getChildren().addAll(indietroButton, homepageButton, logoutButton);

    }

    private void navbarPerAmministratore(){
        Button logoutButton = createNavButton("LOGOUT", e -> ViewNavigator.navigateToLogout());
        this.getChildren().addAll(logoutButton);
    }

    private void navbarToHomepageAmministratore(){

        Button homepageButton = createNavButton("HOMEPAGE", e->ViewNavigator.navigateToAmministratore());
        Button logoutButton = createNavButton("LOGOUT", e -> ViewNavigator.navigateToLogout());

        this.getChildren().addAll( homepageButton, logoutButton);
    }

    private void navbarPerRitornoVisualizzaListaUtenti(){
        Button indietroButton = createNavButton("INDIETRO", e->ViewNavigator.navigateToVisualizzaUtenti());
        Button homepageButton = createNavButton("HOMEPAGE", e->ViewNavigator.navigateToAmministratore());
        Button logoutButton = createNavButton("LOGOUT", e -> ViewNavigator.navigateToLogout());

        this.getChildren().addAll(indietroButton, homepageButton, logoutButton);
    }

    private void navbarPerRitornoVisualizzaStatistiche(){
        Button indietroButton = createNavButton("INDIETRO" , e->ViewNavigator.navigateToVisualizzaStatistiche());
        Button homepageButton = createNavButton("HOMEPAGE", e->ViewNavigator.navigateToDiabetologo());
        Button logoutButton = createNavButton("LOGOUT", e -> ViewNavigator.navigateToLogout());

        this.getChildren().addAll(indietroButton, homepageButton, logoutButton);
    }

    private void navbarPerDiabetologoNotifiche(){
        Button indietroButton = createNavButton("INDIETRO" , e->ViewNavigator.navigateToDiabetologo());
        Button logoutButton = createNavButton("LOGOUT", e -> ViewNavigator.navigateToLogout());

        this.getChildren().addAll(indietroButton, logoutButton);
    }

    private void navbarPerInfoPazienteFromNotifiche() {
        Button indietroButton = createNavButton("INDIETRO" , e->ViewNavigator.navigateToVisualizzaStatistiche());
        Button homepageButton = createNavButton("HOMEPAGE", e->ViewNavigator.navigateToDiabetologo());
        Button logoutButton = createNavButton("LOGOUT", e -> ViewNavigator.navigateToLogout());

        this.getChildren().addAll(indietroButton, homepageButton, logoutButton);
    }

    /*
    private void createUnauthenticatedNavButtons() {
        Button homeBtn = createNavButton("Home", e -> ViewNavigator.navigateToHome());
        Button loginBtn = createNavButton("Login", e -> ViewNavigator.navigateToLogin());
        Button registerBtn = createNavButton("Register", e -> ViewNavigator.navigateToRegister());

        this.getChildren().addAll(homeBtn, loginBtn, registerBtn);
    }

     */

    /**
     * Create a styled navigation button
     */
    private Button createNavButton(String text, javafx.event.EventHandler<javafx.event.ActionEvent> handler) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-cursor: hand;");
        button.setOnAction(handler);

        // Hover effect
        button.setOnMouseEntered(e ->
                button.setStyle("-fx-background-color: #0069d9; -fx-text-fill: white; -fx-cursor: hand;"));
        button.setOnMouseExited(e ->
                button.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-cursor: hand;"));

        return button;
    }

    /**
     * Update the navigation bar based on authentication status
     */
    public void updateAuthStatus() {
        this.getChildren().clear();
        initialize();
    }
}