package controller;

import HomePages.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import model.LoginModel;

import java.io.IOException;
import java.net.URL;

/**
 * This class handles navigation between different views in the application.
 * It works as a bridge between controllers and views, allowing for simplified navigation.
 */
public class ViewNavigator {
    // Reference to the main controller
    private static MainController mainController;

    // Current authenticated username
    private static String authenticatedUser = null;
    private static Object controllerUsed;

    /**
     * Set the main controller reference
     * @param controller The MainController instance
     */
    public static void setMainController(MainController controller) {
        mainController = controller;
    }
    public static Object getControllerUsed() {
        return controllerUsed;
    }
    /**
     * Load and switch to a view
     * @param fxml The name of the FXML file to load
     */
    public static void loadView(String fxml) {
        URL fxmlUrl = null;
        try {
            fxmlUrl = Main.class.getResource("/fxmlView/" + fxml);

            FXMLLoader loader = new FXMLLoader(fxmlUrl);

            Node view = loader.load();
            controllerUsed = loader.getController();
            mainController.setContent(view);
        } catch (IOException e) {
            System.err.println("Error loading " + fxmlUrl + " : " + e.getMessage());
            e.printStackTrace();

        }
    }

    public static void getController(String fxml, String taxCode){

    }


    /**
     * Navigate to the login view
     */
    public static void navigateToLogin() {
        loadView("login_view.fxml");
    }





    /**
     * Navigate to the dashboard view (protected)
     * Will redirect to login if not authenticated
     */


    public static void navigateToEmail(){
        loadView("email_paziente_view.fxml");
    }

    /**
     * Navigate to the profile view (protected)
     * Will redirect to login if not authenticated
     */
    public static void navigateToProfilePaziente() {

            loadView("profiloPaziente.fxml");
    }

    public static void navigateToRilevazioneGlicemia(){
        loadView("rilevazione_glicemia_view.fxml");
    }

    /**
     * Set the authenticated user
     * @param username The username of the authenticated user
     */
    public static void setAuthenticatedUser(String username) {
        authenticatedUser = username;
        mainController.updateNavBar(isAuthenticated());
    }

    /**
     * Get the authenticated user
     * @return The username of the authenticated user, or null if not authenticated
     */
    public static String getAuthenticatedUser() {
        return authenticatedUser;
    }

    /**
     * Check if a user is authenticated
     * @return true if a user is authenticated, false otherwise
     */
    public static boolean isAuthenticated() {
        return authenticatedUser != null;
    }

    /**
     * Logout the current user
     */
    public static void logout() {
        authenticatedUser = null;
        mainController.updateNavBar(false);
        navigateToLogin();
    }

    public static void navigateToPaziente() {

        //System.out.println("taxCode quando si naviga sul paziente: "  + Session.getInstance().getTaxCode());
        loadView("paziente_view.fxml");
    }

    public static void navigateToLogout() {
        Session.getInstance().deleteSession();
        navigateToLogin();
    }

    public static void navigateToAddSympoms() {
        loadView("aggiunta_sintomi_view.fxml");
    }

    public static void navigateToAssunzioneFarmaco(){
        loadView("assunzione_farmaco_view.fxml");
    }

    public static void navigateToTerapiaConcomitante(){
        loadView("patologie_concomitanti_view.fxml");
    }

    public static void navigateToDiabetologo(){
        loadView("diabetologo_view.fxml");
    }

    public static void navigateToAmministratore(){ loadView("amministratore_view.fxml");}

    public static void navigateToAddUser(){loadView("add_user_view.fxml");}

    public static void navigateToVisualizzaUtenti(){loadView("visualizza_utenti_view.fxml");}

    public static void navigateToProfileDiabetologo() {
        loadView("profiloDiabetologo.fxml");
    }

    public static void navigateToVisualizzaPazienti() { loadView("visualizza_pazienti_view.fxml"); }

    public static void navigateToAggiungiTerapia(){ loadView("aggiungi_terapia_view.fxml"); }

    public static void navigateToTabellaModificaTerapia() {loadView("tabella_modifica_terapia_view.fxml");}

    public static void navigateToModificaTerapia() { loadView("modifica_terapia_view.fxml");}

    public static void navigateToPatientDetails() {loadView("dettaglio_paziente_view.fxml");}

    public static void navigateToInfoPaziente() { loadView("aggiorna_info_paziente.fxml"); }

    public static void navigateToVisualizzaStatistiche() {loadView("visualizzaStatistiche.fxml");}

    public static void navigateToModificaUtente(){loadView("modifica_utente_view.fxml");}

    public static void relogPaziente(String TaxCode, String password) {

        Session.getInstance().deleteSession();
        Session.getInstance().setTaxCode(TaxCode);
        Session.getInstance().setPassword(password);
        Session.getInfos();
        navigateToProfilePaziente();

    }
}
