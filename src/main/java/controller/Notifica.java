package controller;

import java.time.LocalDate;

public class Notifica {

    private String notifica;

    private String nomePazienteFarmaco;
    private String cognomePazienteFarmaco;
    private String farmacoFarmaco;
    private String notificaFarmaco;

    private String nomePazienteGlicemia;
    private String cognomePazienteGlicemia;
    private String notificaGlicemia;
    private String dataGlicemia;
    private String colore;

    public Notifica(String notifica) {
        this.notifica = notifica;
    }

    public Notifica(String nomePazienteFarmaco, String cognomePazienteFarmaco, String farmacoFarmaco, String notificaFarmaco) {
        this.nomePazienteFarmaco = nomePazienteFarmaco;
        this.cognomePazienteFarmaco = cognomePazienteFarmaco;
        this.farmacoFarmaco = farmacoFarmaco;
        this.notificaFarmaco = notificaFarmaco;
    }

    public Notifica(String nomePazienteGlicemia, String cognomePazienteGlicemia, String notificaGlicemia, String dataGlicemia, String colore) {
        this.nomePazienteGlicemia = nomePazienteGlicemia;
        this.cognomePazienteGlicemia = cognomePazienteGlicemia;
        this.notificaGlicemia = notificaGlicemia;
        this.dataGlicemia = dataGlicemia;
        this.colore = colore;
    }

    public String getNotifica() { return this.notifica; }

    public String getPazienteFarmaco() { return this.nomePazienteFarmaco + " " + this.cognomePazienteFarmaco; }
    public String getFarmacoFarmaco() { return this.farmacoFarmaco; }
    public String getNotificaFarmaco() { return this.notificaFarmaco; }
    public String getNomePazienteFarmaco() { return this.nomePazienteFarmaco; }
    public String getCognomePazienteFarmaco() { return this.cognomePazienteFarmaco; }

    public String getPazienteGlicemia() { return this.nomePazienteGlicemia + " " + this.cognomePazienteGlicemia; }
    public String getNotificaGlicemia() { return this.notificaGlicemia; }
    public String getDataGlicemia() { return this.dataGlicemia; }
    public String getColore() { return this.colore; }
    public String getNomePazienteGlicemia() { return this.nomePazienteGlicemia; }
    public String getCognomePazienteGlicemia() { return this.cognomePazienteGlicemia; }

}
