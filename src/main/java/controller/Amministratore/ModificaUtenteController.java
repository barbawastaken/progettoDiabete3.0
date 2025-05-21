package controller.Amministratore;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Amministratore.Utente;
import model.Amministratore.VisualizzaListaUtentiModel;
import view.Amministratore.ModificaUtenteView;
import view.Amministratore.VisualizzaListaUtentiView;

import java.util.Date;

public class ModificaUtenteController {
    private final ModificaUtenteView view;
    private final Utente utente;
    private final VisualizzaListaUtentiModel model;

    public ModificaUtenteController(ModificaUtenteView view, Utente utente, VisualizzaListaUtentiModel model) {
        this.view = view;
        this.utente = utente;
        this.model = model;
    }
    public void salvaModifiche(Stage stage) {
        try {
            // Crea oggetto aggiornato
            Utente aggiornato = new Utente(
                    view.getTaxCode(),
                    view.getPassword(),
                    view.getNome(),
                    view.getCognome(),
                    view.getEmail(),
                    java.sql.Date.valueOf(view.getBirthDate()),
                    view.getAddress(),
                    view.getNumber(),
                    view.getCity(),
                    view.getCap(),
                    view.getGender(),
                    view.getTelephone(),
                    view.getUserType(),
                    view.getDiabetologo()
            );

            // Salva nel DB tramite il model
            model.aggiornaUtente(aggiornato);
            System.out.println("Utente aggiornato con successo.");

            // Ritorna alla lista utenti, nello stesso stage
            VisualizzaListaUtentiView nuovaView = new VisualizzaListaUtentiView();
            VisualizzaListaUtentiController nuovoController = new VisualizzaListaUtentiController(model, nuovaView, stage);

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Errore durante il salvataggio");
            alert.showAndWait();
        }
    }
    }

