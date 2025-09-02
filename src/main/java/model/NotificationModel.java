package model;

import controller.Notifica;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Amministratore.Paziente;
import model.Diabetologo.VisualizzaPazientiModel;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NotificationModel {
    private final static String DB_url = "jdbc:sqlite:mydatabase.db";
    private final String taxCode;

    public NotificationModel(String taxCode) {
        this.taxCode = taxCode;
    }

    public ObservableList<Notifica> notifyRitardoAssunzioni() {

        VisualizzaPazientiModel visualizzaPazientiModel = new VisualizzaPazientiModel();
        List<Paziente> pazientiDiabetologo = visualizzaPazientiModel.getPazientiByDiabetologo(taxCode);

        ObservableList<Notifica> notificheFarmaci = FXCollections.observableArrayList();

        for(Paziente p : pazientiDiabetologo){ //per ogni paziente nella lista guardo se negli ultimi 3 giorni c'è stata una assunzione farmaco

            String query = "SELECT farmaco_prescritto FROM terapiePrescritte WHERE taxCode=?";

            try(Connection conn = DriverManager.getConnection(DB_url);
                PreparedStatement ps = conn.prepareStatement(query)){

                ps.setString(1, p.getTaxCode());
                ResultSet rs = ps.executeQuery();

                List<String> farmaciPrescritti = new ArrayList<>();

                while(rs.next()){ farmaciPrescritti.add(rs.getString("farmaco_prescritto")); }


                String query2 = "SELECT farmacoAssunto, dataAssunzione FROM assunzioneFarmaci WHERE taxCode = ? ORDER BY dataAssunzione DESC";

                try (PreparedStatement ps2 = conn.prepareStatement(query2)) {

                    ps2.setString(1, p.getTaxCode());
                    ResultSet rs2 = ps2.executeQuery();

                    while(rs2.next()){

                        LocalDate dataAssunzione = LocalDate.parse(rs2.getString("dataAssunzione"));

                        for(String farmacoPrescritto : farmaciPrescritti){

                            if(farmacoPrescritto.equals(rs2.getString("farmacoAssunto")) && dataAssunzione.isAfter(LocalDate.now().minusDays(3))){
                                farmaciPrescritti.remove(farmacoPrescritto);
                            }

                        }

                    }

                    for(String farmacoPrescritto : farmaciPrescritti){
                        Notifica notifica = new Notifica(
                                p.getNome(),
                                p.getCognome(),
                                farmacoPrescritto,
                                "Mancata assunzione per più di tre giorni");

                        notificheFarmaci.add(notifica);
                    }

                } catch (Exception e){
                    System.out.println("Errore caricamento farmaci assunti by " + p.getTaxCode() + ": " + e.getMessage());
                    return null;
                }

            } catch (Exception e) {
                System.out.println("Errore caricamento terapie prescritte of " + p.getTaxCode() + ": " + e.getMessage());
                return null;
            }

        }

        return notificheFarmaci;
    }

    public ObservableList<Notifica> notifyGlicemia() {

        VisualizzaPazientiModel visualizzaPazientiModel = new VisualizzaPazientiModel();
        List<Paziente> pazientiDiabetologo = visualizzaPazientiModel.getPazientiByDiabetologo(taxCode);

        ObservableList<Notifica> notificheGlicemia = FXCollections.observableArrayList();

        for(Paziente p : pazientiDiabetologo){

            String query = "SELECT quantita, momentoGiornata, prePost, data FROM rilevazioniGlicemiche WHERE taxCode=? ORDER BY data DESC";

            try(Connection conn = DriverManager.getConnection(DB_url);
                PreparedStatement ps = conn.prepareStatement(query)){

                ps.setString(1, p.getTaxCode());
                ResultSet rs = ps.executeQuery();

                while(rs.next()){

                    int quantita = rs.getInt("quantita");
                    String prePost = rs.getString("prePost");
                    String momentoGiornata = rs.getString("momentoGiornata");
                    String data = rs.getString("data");

                    if((quantita < 80 || quantita > 130) && prePost.equals("PRE")){

                        Notifica notifica = new Notifica(
                                p.getNome(),
                                p.getCognome(),
                                "Ha registrato un valore glicemico di " + quantita,
                                prePost + " " + momentoGiornata + " - " + data,
                                "yellow"
                        );

                        notificheGlicemia.add(notifica);

                    } else if(quantita > 180 && prePost.equals("POST")){

                        Notifica notifica = new Notifica(p.getNome(),
                                "Ha registrato un valore glicemico di " + quantita,
                                prePost + " " + momentoGiornata + " - " + data,
                                "red"
                        );

                        notificheGlicemia.add(notifica);

                    }

                }


            } catch (Exception e){
                System.out.println("Errore caricamento rilevazioni glicemiche di " + p.getTaxCode() + ": " + e.getMessage());
                return null;
            }

        }

        return notificheGlicemia;
    }
}
