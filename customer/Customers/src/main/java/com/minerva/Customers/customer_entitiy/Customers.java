package com.minerva.Customers.customer_entitiy;
import javax.persistence.*;

/*
avremo bisogno
id
nome
cognome
email
telefono
data_nascita
 */
@Entity
@Table(name = "customers")

public class Customers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id; //id univoco
    @Column(nullable = false, length = 13)

    private String Nome; //nome del customers
    @Column(nullable = false)

    private String Cognome; //cognome del customers
    @Column(nullable = false)

    private String Email; //email
    @Column(nullable = false)

    private String Telefono;
    @Column(nullable = false)

    private String Data_nascita;


    public Customers(Long id, String Nome, String Cognome, String Email, String Telefono,String Data_nascita) {
        this.id = id;
        this.Nome = Nome;
        this.Cognome = Cognome;
        this.Email = Email;
        this.Telefono = Telefono;
        this.Data_nascita = Data_nascita;

    }

    public Customers() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getCognome() {
        return Cognome;
    }

    public void setCognome(String cognome) {
        Cognome = cognome;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getData_nascita() {
        return Data_nascita;
    }

    public void setData_nascita(String data_nascita) {
        Data_nascita = data_nascita;
    }
}