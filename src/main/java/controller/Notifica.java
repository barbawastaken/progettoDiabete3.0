package controller;

import java.time.LocalDate;

public class Notifica {

    private String notifica;

    private String pazienteFarmaco;
    private String farmacoFarmaco;
    private String notificaFarmaco;

    private String pazienteGlicemia;
    private String notificaGlicemia;
    private String dataGlicemia;
    private String colore;

    public Notifica(String notifica) {
        this.notifica = notifica;
    }

    public Notifica(String pazienteFarmaco, String farmacoFarmaco, String notificaFarmaco) {
        this.pazienteFarmaco = pazienteFarmaco;
        this.farmacoFarmaco = farmacoFarmaco;
        this.notificaFarmaco = notificaFarmaco;
    }

    public Notifica(String pazienteGlicemia, String notificaGlicemia, String dataGlicemia, String colore) {
        this.pazienteGlicemia = pazienteGlicemia;
        this.notificaGlicemia = notificaGlicemia;
        this.dataGlicemia = dataGlicemia;
        this.colore = colore;
    }

    public String getNotifica() { return this.notifica; }

    public String getPazienteFarmaco() { return this.pazienteFarmaco; }
    public String getFarmacoFarmaco() { return this.farmacoFarmaco; }
    public String getNotificaFarmaco() { return this.notificaFarmaco; }

    public String getPazienteGlicemia() { return this.pazienteGlicemia; }
    public String getNotificaGlicemia() { return this.notificaGlicemia; }
    public String getDataGlicemia() { return this.dataGlicemia; }
    public String getColore() { return this.colore; }

}
