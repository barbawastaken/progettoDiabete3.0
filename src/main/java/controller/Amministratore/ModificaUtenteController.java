package controller.Amministratore;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Amministratore.ModificaUtenteModel;
import model.Amministratore.Utente;
import model.Amministratore.VisualizzaListaUtentiModel;
import view.Amministratore.ModificaUtenteView;
import view.Amministratore.VisualizzaListaUtentiView;

public class ModificaUtenteController {
    private final ModificaUtenteView modificaUtenteView;
    private final Utente utente;
    private  VisualizzaListaUtentiModel model;
    private  ModificaUtenteModel modificaUtenteModel;
    private Stage listaUtentiStage;

    public ModificaUtenteController(ModificaUtenteView modificaUtenteView, Utente utente, VisualizzaListaUtentiModel model, Stage listaUtentiStage) {
        this.modificaUtenteView = modificaUtenteView;
        this.utente = utente;
        this.model = model;
        this.listaUtentiStage = listaUtentiStage;
    }

    public ModificaUtenteController(ModificaUtenteModel modificaUtenteModel, ModificaUtenteView modificaUtenteView, Utente selezionato, Stage visualizzaUtentiStage){

        this.modificaUtenteView = modificaUtenteView;
        this.modificaUtenteModel = modificaUtenteModel;
        this.utente = selezionato;
        this.listaUtentiStage = visualizzaUtentiStage;

        //Utente utenteModificato = this.utenteModificato();
        modificaUtenteView.getSalvaButton().setOnAction(e -> modificaUtenteModel.aggiornaUtente(selezionato.getTaxCode(), modificaUtenteView, listaUtentiStage));

    }

    private Utente utenteModificato() {
        return new Utente(
                modificaUtenteView.getTaxCode(),
                modificaUtenteView.getPassword(),
                modificaUtenteView.getNome(),
                modificaUtenteView.getCognome(),
                modificaUtenteView.getEmail(),
                java.sql.Date.valueOf(modificaUtenteView.getBirthday()),
                modificaUtenteView.getAddress(),
                modificaUtenteView.getNumber(),
                modificaUtenteView.getCity(),
                modificaUtenteView.getCap(),
                modificaUtenteView.getGender(),
                modificaUtenteView.getTelephone(),
                modificaUtenteView.getRole(),
                modificaUtenteView.getDiabetologo()
        );

    }

    public void salvaModifiche(Stage stage) {
        try {
            // Crea oggetto aggiornato
            Utente aggiornato = new Utente(
                    modificaUtenteView.getTaxCode(),
                    modificaUtenteView.getPassword(),
                    modificaUtenteView.getNome(),
                    modificaUtenteView.getCognome(),
                    modificaUtenteView.getEmail(),
                    java.sql.Date.valueOf(modificaUtenteView.getBirthday()),
                    modificaUtenteView.getAddress(),
                    modificaUtenteView.getNumber(),
                    modificaUtenteView.getCity(),
                    modificaUtenteView.getCap(),
                    modificaUtenteView.getGender(),
                    modificaUtenteView.getTelephone(),
                    modificaUtenteView.getRole(),
                    modificaUtenteView.getDiabetologo()
            );

            //System.out.println(modificaUtenteView.getTaxCode());

            // Salva nel DB tramite il model
            model.aggiornaUtente(aggiornato, modificaUtenteView.getTaxCode());
            System.out.println("Utente aggiornato con successo.");
            modificaUtenteView.getStage().close();

            // Ritorna alla lista utenti, nello stesso stage
            this.listaUtentiStage.close();
            VisualizzaListaUtentiView nuovaView = new VisualizzaListaUtentiView();
            VisualizzaListaUtentiController nuovoController = new VisualizzaListaUtentiController(model, nuovaView, stage);

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Errore durante il salvataggio");
            alert.showAndWait();
        }
    }
    }

