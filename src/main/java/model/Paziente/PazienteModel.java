package model.Paziente;

import model.Amministratore.Utente;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PazienteModel  {

    private final static String DB_URL = "jdbc:sqlite:mydatabase.db";

    public PazienteModel() {

    }

    /*public HashMap<Date, Integer> getRilevazioni(String taxCode, Date from, Date to) throws SQLException {
        HashMap<Date, Integer> map = new HashMap<>();
        String sql;
        if(!from.equals(to)) {
            sql = "SELECT data, quantita FROM rilevazioniGlicemiche WHERE taxCode='"+ taxCode + "' AND data BETWEEN "+ from + " AND "+ to +";";
        } else{
            sql = "SELECT data, quantita FROM rilevazioniGlicemiche WHERE taxCode='"+ taxCode + "' AND data="+ from +";";
        }
        Connection conn = DriverManager.getConnection(sql);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            map.put(rs.getDate("data"), rs.getInt("quantita"));
        }
        conn.close();
        return map;
    }*/

    public static void updateData(Utente utente) {

        String query = "UPDATE utenti SET nome=?, cognome=?, email=?, birthday=?, address=?, number=?, city=?, cap=?, gender=?, telephoneNumber=?, userType=?, diabetologo=?, CountryOfResidence=?, Altezza=?, Peso=? WHERE taxCode=?;";

        try(Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, utente.getNome());
            stmt.setString(2, utente.getCognome());
            stmt.setString(3, utente.getEmail());
            stmt.setString(4, utente.getBirthday().toString());
            stmt.setString(5, utente.getAddress());
            stmt.setString(6, utente.getNumber());
            stmt.setString(7, utente.getCity());
            stmt.setString(8, String.valueOf(utente.getCap()));
            stmt.setString(9, utente.getGender());
            stmt.setString(10, utente.getTelephone());
            stmt.setString(11, utente.getRole());
            stmt.setString(12, utente.getDiabetologo());
            stmt.setString(13, utente.getCountryOfResidence());
            stmt.setDouble(14, utente.getHeight());
            stmt.setDouble(15, utente.getWeight());
            stmt.setString(16, utente.getTaxCode());

            stmt.executeUpdate();
            
        } catch(SQLException x){
            System.out.println(x.getErrorCode());
        }
    }

    public List<TerapiaModel> getTerapie(String taxCode){

        List<TerapiaModel> terapie = new ArrayList<>();

        String query = "SELECT terapia, farmaco_prescritto, quantita, numero_assunzioni_giornaliere, indicazioni, dataPrescrizione FROM terapiePrescritte WHERE taxCode = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, taxCode);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                TerapiaModel t = new TerapiaModel(
                        rs.getString("terapia"),
                        rs.getString("farmaco_prescritto"),
                        rs.getString("quantita"),
                        rs.getString("numero_assunzioni_giornaliere"),
                        rs.getString("indicazioni"),
                        rs.getString("dataPrescrizione")
                );
                terapie.add(t);

            }

        } catch (Exception e) {
            System.out.println("Errore nel caricamento terapie: " + e);
        }

        return terapie;
    }

    public ArrayList<String> getFarmaciNotifiche(String taxCode){

        ArrayList<String> farmaciDaControllare = new ArrayList<>();

        String query = "SELECT farmaco_prescritto FROM terapiePrescritte WHERE taxCode = ?";

        // Esecuzione query per ottenere tutte i farmaci da assumere specificati dalle terapie relative al taxCode

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, taxCode);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                //Aggiunga di ogni farmaco prescritto alla lista di quelli da controllare
                farmaciDaControllare.add(rs.getString("farmaco_prescritto"));
                //System.out.println(rs.getString("farmaco_prescritto") + " aggiunto alla lista di quelli da controllare");

            }

            // Esecuzione query per ottenere le assunzioni di farmaci relativa al taxCode

            String query2 = "SELECT farmacoAssunto, dataAssunzione FROM assunzioneFarmaci WHERE taxCode = ? ORDER BY dataAssunzione DESC";

            try (Connection conn2 = DriverManager.getConnection(DB_URL);
                 PreparedStatement stmt2 = conn2.prepareStatement(query2)){

                stmt2.setString(1, taxCode);
                ResultSet rs2 = stmt2.executeQuery();

                //Per ogni farmaco assunto dal taxCode controlla se fa parte o no di quelli prescritti (farmaciDaControllare)

                while (rs2.next()) {

                    LocalDate dataAssunzione = LocalDate.parse(rs2.getString("dataAssunzione"));
                    String farmacoAssunto = rs2.getString("farmacoAssunto");

                    Iterator<String> iterator = farmaciDaControllare.iterator();
                    while (iterator.hasNext()) {
                        String farmacoDaControllare = iterator.next();
                        //System.out.println(farmacoDaControllare + " : " + farmacoAssunto);

                        if (farmacoAssunto.equals(farmacoDaControllare) &&
                                !dataAssunzione.isBefore(LocalDate.now().minusDays(3))) {
                            //System.out.println(farmacoDaControllare + " rimosso dalla lista");
                            iterator.remove();
                            break;
                        }
                    }

                }

            } catch(Exception e){
                throw new RuntimeException("Errore nel caricamento assunzione: " + e);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //Per i farmaci rimasti dev'essere generata la notifica
        return farmaciDaControllare;
    }
}
