package controller;

/*
    * Classe a pattern singleton = può esistere solo una sessione per volta (perfetta per il nostro caso)
 */

import javafx.scene.chart.XYChart;
import model.Amministratore.Paziente;
import model.Amministratore.Utente;
import model.Diabetologo.Terapia;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class Session {

    private final static String DB_URL = "jdbc:sqlite:mydatabase.db";
    private static Session instance;
    private String taxCode;
    private String nome;
    private String cognome;
    private String password;
    private String email;
    private String telephone;
    private String birthday;
    private String sex;
    private Double height;
    private Double weight;
    private String address;
    private String civic;
    private String city;
    private Integer cap;
    private String countryOfResidence;
    private String medicoCurante;
    private Paziente pazienteInEsame;
    private Utente utenteInEsame;
    private static Terapia terapiaInEsame;
    private static String schermataDiArrivo;

    private Session() {

    }

    public static Session getInstance(){
        if(instance == null){
            instance = new Session();
        }
        return instance;
    }


    public static void getInfos() {
        String query = "SELECT * FROM utenti WHERE taxCode='"+ Session.getInstance().getTaxCode() +"'";
        try(Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();
            Session.getInstance().brandNewSession(
                    rs.getString("taxCode"),
                    rs.getString("nome"),
                    rs.getString("cognome"),
                    rs.getString("password"),
                    rs.getString("email"),
                    rs.getString("telephoneNumber"),
                    rs.getString("birthday"),
                    rs.getString("gender"),
                    rs.getDouble("Altezza"),
                    rs.getDouble("Peso"),
                    rs.getString("address"),
                    rs.getString("number"),
                    rs.getString("city"),
                    rs.getString("diabetologo"),
                    rs.getInt("cap"),
                    rs.getString("CountryOfResidence")
            );



        } catch(SQLException e) {
            System.out.println("Errore nella raccolta dei dati");
            System.out.println(e.getMessage());
        }
    }

    public String getCountryOfResidence() {
        return countryOfResidence;
    }

    public void setCountryOfResidence(String countryOfResidence) {
        this.countryOfResidence = countryOfResidence;
    }

    public String getMedicoCurante() {
        return medicoCurante;
    }

    public void setMedicoCurante(String medicoCurante) {
        this.medicoCurante = medicoCurante;
    }

    public static Paziente getInfosOf(String taxCode) {

        String query = "SELECT * FROM utenti WHERE taxCode='"+ taxCode +"'";
        try(Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();
            Paziente paziente = new Paziente(
                    rs.getString("taxCode"),
                    rs.getString("nome"),
                    rs.getString("cognome"),
                    rs.getString("gender"),
                    rs.getString("birthday"),
                    rs.getString("password"), //password
                    rs.getString("address"),
                    rs.getString("number"),
                    rs.getString("cap"),
                    rs.getString("CountryOfResidence"),
                    rs.getString("city"),
                    rs.getString("email"),
                    rs.getString("telephoneNumber"),
                    rs.getString("userType"),
                    rs.getDouble("Peso"),
                    rs.getDouble("Altezza"),
                    rs.getString("diabetologo")

            );
            return paziente;

        } catch(SQLException e) {
            System.out.println("Errore nella raccolta dei dati" + e.getMessage());
            return null;
        }
    }

    public static String getInfoAggiuntiveOf(String taxCode) {

        String query = "SELECT noteDaDiabetologo FROM infoAggiuntivePaziente WHERE taxCode='"+ taxCode +"'";
        try(Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            String result = "";
            if(rs.next()) {
                result += rs.getString("noteDaDiabetologo") + "\n";
            }

            HashMap<String, LocalDate> mapInfoPaziente = new HashMap<>();

            query = "SELECT * FROM assunzioneFarmaci WHERE taxCode='"+ taxCode +"'";

            Statement stmt2 = conn.createStatement();
            ResultSet rs2 = stmt2.executeQuery(query);

            while(rs2.next()) {

                mapInfoPaziente.put("Assunzione di " + rs2.getString("farmacoAssunto") +
                        ", quantità: " + rs2.getString("quantitaAssunta") +
                        ", il " + rs2.getString("dataAssunzione") + " alle " + rs2.getString("orarioAssunzione") ,
                        LocalDate.parse(rs2.getString("dataAssunzione")));

            }

            query = "SELECT * FROM aggiuntaSintomi WHERE taxCode='"+ taxCode +"'";

            Statement stmt3 = conn.createStatement();
            ResultSet rs3 = stmt3.executeQuery(query);

            while(rs3.next()) {

                mapInfoPaziente.put("Riscontrato/a " + rs3.getString("sintomoPrincipale") +
                        ", specificato: " + rs3.getString("sintomiSpecificati") +
                        ", il " + rs3.getString("dataInserimento"),
                        LocalDate.parse(rs3.getString("dataInserimento")));

            }

            query = "SELECT * FROM patologieConcomitanti WHERE taxCode='"+ taxCode +"'";

            Statement stmt4 = conn.createStatement();
            ResultSet rs4 = stmt4.executeQuery(query);

            while(rs4.next()) {

                mapInfoPaziente.put("Patologia/Terapia concomitante: " + rs4.getString("patologiaConcomitante") +
                        ", dal : " + rs4.getString("dataInizio") +
                        ", al: " + rs4.getString("dataFine"),
                        LocalDate.parse(rs4.getString("dataInizio")));

            }

            //Ordina gli elementi della mappa per data decrescente
            LinkedHashMap<String, LocalDate> ordinata = mapInfoPaziente.entrySet()
                    .stream()
                    .sorted(Map.Entry.<String, LocalDate>comparingByValue().reversed())
                    .collect(
                            LinkedHashMap::new,
                            (map, entry) -> map.put(entry.getKey(), entry.getValue()),
                            LinkedHashMap::putAll
                    );

            for(Map.Entry<String, LocalDate> entry : ordinata.entrySet()) {
                result += "\n" + entry.getKey();
            }

            return result;

        } catch(SQLException e) {
            System.out.println("Errore nella raccolta delle informazioni aggiuntive sul paziente" + e.getMessage());
            return null;
        }

    }


    public Utente getUtenteInEsame() {
        return utenteInEsame;
    }

    public void setUtenteInEsame(Utente utenteInEsame) {
        this.utenteInEsame = utenteInEsame;
    }

    public static XYChart.Series<String, Number> caricaDatiGlicemia(String periodo, String taxCode) {

        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.setName("Valori glicemici");

        String query = "SELECT data, quantita, momentoGiornata, prePost FROM rilevazioniGlicemiche WHERE taxCode = ?";

        if(periodo.equals("Ultima settimana")) {
            query  += " AND date(data) >= date('now', '-7 days')";
        } else if(periodo.equals("Ultimo mese")) {
            query += " AND date(data) >= date('now', '-30 days')";
        }

        query += "ORDER BY data ASC, CASE momentoGiornata  " +
                "WHEN 'Colazione' THEN 1 " +
                "WHEN 'Pranzo'    THEN 2 " +
                "WHEN 'Cena'      THEN 3 " +
                "ELSE 4 END, " +
                "CASE prePost " +
                "WHEN 'PRE'  THEN 1 " +
                "WHEN 'POST' THEN 2 " +
                "ELSE 3 END;";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, taxCode);

            ResultSet rs = stmt.executeQuery();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");
            List<XYChart.Data<String, Number>> dati = new ArrayList<>();

            while (rs.next()) {
                String dataStr = rs.getString("data");
                LocalDate data = LocalDate.parse(dataStr);
                int valore = rs.getInt("quantita");
                String momento = rs.getString("momentoGiornata");
                String prePost = rs.getString("prePost");

                String labelX = data.format(formatter) + " - " + momento + " - " + prePost;

                XYChart.Data<String, Number> punto = new XYChart.Data<>(labelX, valore);

                dati.add(punto);
            }

            serie.getData().addAll(dati);
            return serie;

        } catch (Exception e) {
            System.out.println("Errore caricamento grafico: " + e.getMessage());
            return null;
        }
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setPassword(String password){ this.password = password; }

    public String getPassword(){ return password; }

    public static void setInstance(Session instance) {
        Session.instance = instance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBirthday() {
        return birthday;
    }


    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCivic() {
        return civic;
    }

    public void setCivic(String civic) {
        this.civic = civic;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getCap() {
        return cap;
    }

    public void setCap(int cap) {
        this.cap = cap;
    }

    public void setCap(Integer cap) {
        this.cap = cap;
    }

    public Paziente getPazienteInEsame() {
        return pazienteInEsame;
    }

    public void setPazienteInEsame(Paziente pazienteInEsame) {
        this.pazienteInEsame = pazienteInEsame;
    }

    public void deletePazienteInEsame(){
        this.pazienteInEsame = null;
    }

    public Terapia getTerapiaInEsame() { return terapiaInEsame; }

    public static void setTerapiaInEsame(Terapia terapiaInEsameAss) { terapiaInEsame = terapiaInEsameAss; }

    public String getSchermataDiArrivo() { return schermataDiArrivo; }

    public static void setSchermataDiArrivo(String schermataDiArrivoPar) { schermataDiArrivo = schermataDiArrivoPar; }

    public void brandNewSession(
            String taxCode,
            String nome,
            String cognome,
            String password,
            String email,
            String telephone,
            String birthday,
            String sex,
            Double height,
            Double weight,
            String address,
            String civic,
            String city,
            String medicoCurante,
            int cap,
            String nation
    ){
        this.taxCode = taxCode;
        this.nome = nome;
        this.cognome = cognome;
        this.password = password;
        this.email = email;
        this.telephone = telephone;
        this.birthday = birthday;
        this.sex = sex;
        this.height = height;
        this.weight = weight;
        this.address = address;
        this.civic = civic;
        this.city = city;
        this.cap = cap;
        this.medicoCurante = medicoCurante;
        this.pazienteInEsame = null;
        this.countryOfResidence = nation;
    }

    public void deleteSession(){
        this.taxCode = null;
        this.nome = null;
        this.cognome = null;
        this.email = null;
        this.telephone = null;
        this.birthday = null;
        this.sex = null;
        this.height = null;
        this.weight = null;
        this.address = null;
        this.civic = null;
        this.city = null;
        this.cap = null;
        this.pazienteInEsame = null;

    }



}
