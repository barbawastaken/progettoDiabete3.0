package model;

import java.util.Date;

public class Utente {
    private String taxCode;
    private String password;
    private String nome;
    private String cognome;
    private String email;
    private Date birthDate;
    private String address;
    private int number;
    private String city;
    private int cap;
    private String gender;
    private int telephoneNumber;

    public Utente(String taxCode, String password, String nome, String cognome, String email, Date birthDate, String address, int number, String city, int cap, String gender, int telephoneNumber) {
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
    public int getTelephoneNumber() { return telephoneNumber; }
}
