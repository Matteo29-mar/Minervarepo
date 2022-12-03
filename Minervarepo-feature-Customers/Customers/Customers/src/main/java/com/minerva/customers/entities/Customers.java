package com.minerva.customers.entities;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "customer")
@ToString
public class Customers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private static Long customersid;
    @Column(nullable = false, length = 13)
    @NotEmpty
    @Size(min = 13, max = 13 )

    private String nome;
    @Column(nullable = false)
    @NotEmpty(message = "nome must not be null")

    private String cognome;
    @Column(nullable = false)
    @NotEmpty(message = "cognome must not be null")

    private String email;
    @Column(nullable = false)
    @NotEmpty(message = "email must not be null")

    private String telefono;
    @Column(nullable = false)
    @NotEmpty(message = "telefono must not be null")

    private String data_nascita;



    public Customers(Long customersid, String nome, String cognome, String email, String telefono, String data_nascita) {
        this.customersid = customersid;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.telefono = telefono;
        this.data_nascita = data_nascita ;
    }

    public Customers() {
    }

    public static Long getCustomersid() {
        return customersid;
    }

    public void setCustomersid(Long customersid) {
        this.customersid = customersid;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getData_nascita() {
        return data_nascita;
    }

    public void setData_nascita(String data_nascita) {
        this.data_nascita = data_nascita;
    }

}
