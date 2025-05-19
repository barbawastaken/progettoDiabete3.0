package controller.Amministratore;

import javafx.stage.Stage;
import model.Amministratore.Utente;
import model.Amministratore.VisualizzaListaUtentiModel;
import view.Amministratore.ModificaUtenteView;

public class ModificaUtenteController {
    private ModificaUtenteView view;
    private Utente utente;
    private VisualizzaListaUtentiModel model;

    public ModificaUtenteController(ModificaUtenteView view, Utente utente, VisualizzaListaUtentiModel model) {
        this.view = view;
        this.utente = utente;
        this.model = model;
    }

    public void salvaModifiche() {
        // Aggiorna i dati dell'oggetto Utente
        Utente aggiornato = new Utente(
                utente.getTaxCode(), // taxCode non si cambia
                utente.getPassword(), // idem
                view.getNome(),
                view.getCognome(),
                view.getEmail(),
                utente.getBirthDate(), // per ora lasciamo invariata la data di nascita
                view.getAddress(),
                view.getNumber(),
                view.getCity(),
                view.getCap(),
                view.getGender(),
                view.getTelephone(),
                view.getUserType(),
                view.getDiabetologo()
        );

        model.aggiornaUtente(aggiornato);
        System.out.println("Utente aggiornato con successo.");
    }
}
