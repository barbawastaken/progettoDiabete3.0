package model.Amministratore;

public class Paziente {
    private final String taxCode;
    private final String nome;
    private final String cognome;
    private final String gender;
    private final String birthday;
    private final String password;
    private final String address;
    private final String number;
    private final String cap;
    private final String city;
    private final String countryOfResidence;
    private final String email;
    private final String telephone;
    private final String role;
    private final double weight;
    private final double height;

    public Paziente(String taxCode, String nome, String cognome, String gender, String birthday,
                    String password, String address, String number, String cap, String countryOfResidence, String city,
                    String email, String telephone, String role, double weight, double height) {
        this.taxCode = taxCode;
        this.nome = nome;
        this.cognome = cognome;
        this.gender = gender;
        this.birthday = birthday;
        this.password = password;
        this.address = address;
        this.number = number;
        this.cap = cap;
        this.countryOfResidence = countryOfResidence;
        this.city = city;
        this.email = email;
        this.telephone = telephone;
        this.role = role;
        this.weight = weight;
        this.height = height;
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
    public String getCountryOfResidence() { return countryOfResidence; }
    public String getCity() { return city; }
    public String getEmail() { return email; }
    public String getTelephone() { return telephone; }
    public String getRole() { return role; }
    public double getWeight() { return weight; }
    public double getHeight(){ return height;}
}
