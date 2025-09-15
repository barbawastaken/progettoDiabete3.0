package model.Paziente;

import javafx.beans.property.SimpleStringProperty;

public class TerapiaModel {
    private final SimpleStringProperty terapia;
    private final SimpleStringProperty farmacoPrescritto;
    private final SimpleStringProperty quantita;
    private final SimpleStringProperty numeroAssunzioniGiornaliere;
    private final SimpleStringProperty indicazioni;
    private final SimpleStringProperty dataPrescrizione;

    public TerapiaModel(String terapia, String farmacoPrescritto, String quantita, String numeroAssunzioniGiornaliere, String indicazioni, String dataPrescrizione) {
        this.terapia = new SimpleStringProperty(terapia);
        this.farmacoPrescritto = new SimpleStringProperty(farmacoPrescritto);
        this.quantita = new SimpleStringProperty(quantita);
        this.numeroAssunzioniGiornaliere = new SimpleStringProperty(numeroAssunzioniGiornaliere);
        this.indicazioni = new SimpleStringProperty(indicazioni);
        this.dataPrescrizione = new SimpleStringProperty(dataPrescrizione);
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

    public String getIndicazioni() { return indicazioni.get(); }

    public String getDataPrescrizione() { return dataPrescrizione.get(); }
}
