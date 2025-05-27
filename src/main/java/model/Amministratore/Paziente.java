package model.Amministratore;

public class Paziente {
    private String taxCode;
    private String nome;
    private String cognome;
    private String gender;
    private String birthday;
    private String password;
    private String address;
    private String number;
    private String cap;
    private String city;
    private String email;
    private String telephone;
    private String role;

    public Paziente(String taxCode, String nome, String cognome, String gender, String birthday,
                    String password, String address, String number, String cap, String city,
                    String email, String telephone, String role) {
        this.taxCode = taxCode;
        this.nome = nome;
        this.cognome = cognome;
        this.gender = gender;
        this.birthday = birthday;
        this.password = password;
        this.address = address;
        this.number = number;
        this.cap = cap;
        this.city = city;
        this.email = email;
        this.telephone = telephone;
        this.role = role;
    }

    public String getTaxCode() { return taxCode; }
    public String getNome() { return nome; }
    public String getCognome() { return cognome; }
    public String getGender() { return gender; }
    public String getBirthday() { return birthday; }
    public String getPassword() { return password; }
    public String getAddress() { return address; }
    public String getNumber() { return number; }
    public String getCap() { return cap; }
    public String getCity() { return city; }
    public String getEmail() { return email; }
    public String getTelephone() { return telephone; }
    public String getRole() { return role; }
}
