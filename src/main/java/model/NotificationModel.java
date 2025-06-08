package model;

import model.Amministratore.Paziente;
import model.Diabetologo.VisualizzaPazientiModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationModel {
    private final String url = "jdbc:sqlite:mydatabase.db";
    private String taxCode;

    public NotificationModel(String taxCode) {
        this.taxCode = taxCode;
    }
    public ArrayList<String> notifyRitardo() throws SQLException {
        ArrayList<String> taxCodeInRitardo = new ArrayList<>();
        VisualizzaPazientiModel visualizzaPazientiModel = new VisualizzaPazientiModel();
        visualizzaPazientiModel.getPazientiByDiabetologo(taxCode);       //chiamando il Model di visualizzaPazienti riusciamo a trovare tutti i pazienti del diabetologo
        System.out.println("TaxCode diabetologo: " + taxCode);
        List<Paziente> toCheck = visualizzaPazientiModel.getPazientiByDiabetologo(taxCode);
        System.out.println("utenti nel toCheck: " + toCheck.size());
        for(int i = 0; i < toCheck.size(); i++){ //per ogni paziente nella lista guardo se negli ultimi 3 giorni c'è stata una rilevazione
            String query = "SELECT data FROM rilevazioniGlicemiche " +
                    "WHERE taxCode=? " +
                    "GROUP BY data " +
                    "HAVING MAX(data) > DATE('now', '-3 day') " +
                    "ORDER BY data DESC LIMIT 1;";

            try(Connection conn = DriverManager.getConnection(url);){
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, toCheck.get(i).getTaxCode());
                ResultSet rs = ps.executeQuery();

                if(rs.next()){  //Se viene trovato un risultato allora si passa al successivo
                    break;
                } else{//...altrimenti si aggiunge il taxCode del paziente all'array list dei "cattivi"
                    System.out.println(toCheck.get(i).getTaxCode());
                    taxCodeInRitardo.add(toCheck.get(i).getTaxCode());
                }

            }

        }
        System.out.println("Dal model: " + taxCodeInRitardo.toString());
        return taxCodeInRitardo;
    }
}
