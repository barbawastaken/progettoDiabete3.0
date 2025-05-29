package model.Amministratore;

import java.util.Date;

public class Utente {
    private final String taxCode;
    private final String password;
    private final String nome;
    private final String cognome;
    private final String email;
    private final Date birthDate;
    private final String address;
    private final int number;
    private final String city;
    private final int cap;
    private final String countryOfResidence;
    private final String gender;
    private final String telephoneNumber;
    private final String ruolo;
    private final String diabetologo;
    private final double weight;
    private final double height;

    public Utente(String taxCode, String password, String nome, String cognome, String email, Date birthDate, String address,
                  int number, String city, int cap, String countryOfResidence, String gender, String telephoneNumber, String ruolo,
                  String diabetologo, double weight, double height) {
        this.taxCode = taxCode;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.birthDate = birthDate;
        this.address = address;
        this.number = number;
        this.city = city;
        this.cap = cap;
        this.countryOfResidence = countryOfResidence;
        this.gender = gender;
        this.telephoneNumber = telephoneNumber;
        this.ruolo = ruolo;
        this.diabetologo = diabetologo;
        this.weight = weight;
        this.height = height;
    }

    public String getTaxCode() { return taxCode; }
    public String getPassword() { return password; }
    public String getNome() { return nome; }
    public String getCognome() { return cognome; }
    public String getEmail() { return email; }
    public Date getBirthday() { return birthDate; }
    public String getAddress() { return address; }
    public int getNumber() { return number; }
    public String getCity() { return city; }
    public int getCap() { return cap; }
    public String getCountryOfResidence() { return countryOfResidence; }
    public String getGender() { return gender; }
    public String getTelephone() { return telephoneNumber; }
    public String getDiabetologo() { return diabetologo; }
    public String getRole(){return ruolo; }
    public double getWeight(){return weight; }
    public double getHeight(){return height; }



}
