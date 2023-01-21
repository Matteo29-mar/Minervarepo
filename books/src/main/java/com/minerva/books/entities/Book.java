package com.minerva.books.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "books")
@ToString
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 13)
    @NotEmpty
    @Size(min = 13, max = 13 )
    private String ISBN;
    @Column(nullable = false)
    @NotEmpty(message = "titolo must not be null")
    private String titolo;
    @Column(nullable = false)
    @NotEmpty(message = "autore must not be null")
    private String autore;
    @Column(nullable = false)
    @NotNull
    @Min(1)
    private int anno;
    @Column(nullable = false)
    @NotEmpty
    private String genere;
    @Column(nullable = false)
    @NotNull
    @Min(1)
    private int n_pagine;
    @Column(nullable = false)
    @NotEmpty
    private String stato;
    @Column(nullable = false)
    @NotEmpty
    private String editore;
    @Column(nullable = false)
    @NotNull
    private LocalDate data_inizio;

    public Book(Long id, String ISBN, String titolo, String autore, int anno, String genere, int n_pagine, String stato, String editore, LocalDate data_inizio) {
        this.id = id;
        this.ISBN = ISBN;
        this.titolo = titolo;
        this.autore = autore;
        this.anno = anno;
        this.genere = genere;
        this.n_pagine = n_pagine;
        this.stato = stato;
        this.editore = editore;
        this.data_inizio = data_inizio;
    }

    public Book() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public int getN_pagine() {
        return n_pagine;
    }

    public void setN_pagine(int n_pagine) {
        this.n_pagine = n_pagine;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getEditore() {
        return editore;
    }

    public void setEditore(String editore) {
        this.editore = editore;
    }

    public LocalDate getData_inizio() {
        return data_inizio;
    }

    public void setData_inizio(LocalDate data_inizio) {
        this.data_inizio = data_inizio;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Book book = (Book) o;
        return id != null && Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
