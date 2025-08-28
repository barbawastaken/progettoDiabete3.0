package controller;

/*
    * Classe a pattern singleton = può esistere solo una sessione per volta (perfetta per il nostro caso)
 */

import java.sql.*;




public class Session {

    private final static String DB_URL = "jdbc:sqlite:mydatabase.db";
    private static Session instance;
    private String taxCode;
    private String nome;
    private String cognome;
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
                    rs.getString("email"),
                    rs.getString("telephoneNumber"),
                    rs.getString("birthday"),
                    rs.getString("gender"),
                    rs.getDouble("Altezza"),
                    rs.getDouble("Peso"),
                    rs.getString("address"),
                    rs.getString("number"),
                    rs.getString("city"),
                    rs.getInt("cap")
            );

        } catch(SQLException e) {
            System.out.println("Errore nella raccolta dei dati");
            System.out.println(e.getMessage());
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

    public void brandNewSession(
            String taxCode,
            String nome,
            String cognome,
            String email,
            String telephone,
            String birthday,
            String sex,
            Double height,
            Double weight,
            String address,
            String civic,
            String city,
            int cap
    ){
        this.taxCode = taxCode;
        this.nome = nome;
        this.cognome = cognome;
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

    }
}
