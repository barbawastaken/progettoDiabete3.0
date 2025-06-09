package model.Paziente;

import javafx.beans.property.SimpleStringProperty;

public class TerapiaModel {
    private final SimpleStringProperty terapia;
    private final SimpleStringProperty farmacoPrescritto;
    private final SimpleStringProperty quantita;
    private final SimpleStringProperty numeroAssunzioniGiornaliere;

    public TerapiaModel(String terapia, String farmacoPrescritto, String quantita, String numeroAssunzioniGiornaliere) {
        this.terapia = new SimpleStringProperty(terapia);
        this.farmacoPrescritto = new SimpleStringProperty(farmacoPrescritto);
        this.quantita = new SimpleStringProperty(quantita);
        this.numeroAssunzioniGiornaliere = new SimpleStringProperty(numeroAssunzioniGiornaliere);
    }

    public String getTerapia() {
        return terapia.get();
    }

    public String getFarmacoPrescritto() {
        return farmacoPrescritto.get();
    }

    public String getQuantita() {
        return quantita.get();
    }

    public String getNumeroAssunzioniGiornaliere() {
        return numeroAssunzioniGiornaliere.get();
    }
}
