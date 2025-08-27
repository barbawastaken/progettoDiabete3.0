package model.Diabetologo;

public class Terapia {
    private String taxCode;
    private String terapia;
    private String farmaco;
    private String quantita;
    private String frequenza;
    private String indicazioni;

    public Terapia(String taxCode, String terapia, String farmaco, String quantita, String frequenza, String indicazioni) {
        this.taxCode = taxCode;
        this.terapia = terapia;
        this.farmaco = farmaco;
        this.quantita = quantita;
        this.frequenza = frequenza;
        this.indicazioni = indicazioni;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public String getTerapia() {
        return terapia;
    }

    public String getFarmaco() {
        return farmaco;
    }

    public String getQuantita() {
        return quantita;
    }

    public String getFrequenza() {
        return frequenza;
    }

    public String getIndicazioni() {
        return indicazioni;
    }


    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public void setTerapia(String terapia) {
        this.terapia = terapia;
    }

    public void setQuantita(String quantita) {
        this.quantita = quantita;
    }

    public void setFarmaco(String farmaco) {
        this.farmaco = farmaco;
    }

    public void setFrequenza(String frequenza) {
        this.frequenza = frequenza;
    }

    public void setIndicazioni(String indicazioni) {
        this.indicazioni = indicazioni;
    }
}
