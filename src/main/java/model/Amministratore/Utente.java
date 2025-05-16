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
    private final String gender;
    private final String telephoneNumber;

    public Utente(String taxCode, String password, String nome, String cognome, String email, Date birthDate, String address, int number, String city, int cap, String gender, String telephoneNumber) {
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
        this.gender = gender;
        this.telephoneNumber = telephoneNumber;
    }

    public String getTaxCode() { return taxCode; }
    public String getPassword() { return password; }
    public String getNome() { return nome; }
    public String getCognome() { return cognome; }
    public String getEmail() { return email; }
    public Date getBirthDate() { return birthDate; }
    public String getAddress() { return address; }
    public int getNumber() { return number; }
    public String getCity() { return city; }
    public int getCap() { return cap; }
    public String getGender() { return gender; }
    public String getTelephoneNumber() { return telephoneNumber; }
}
