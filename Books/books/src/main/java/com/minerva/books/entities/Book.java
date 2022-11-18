package com.minerva.books.entities;

import java.lang.String;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ISBN;
    private String titolo;
    private String autore;
    private int anno;
    private String genere;
    private int n_pagine;
    private String stato;
    private String editore;

    public Book() {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;
        return id == book.id && getAnno() == book.getAnno() && getN_pagine() == book.getN_pagine() && getISBN().equals(book.getISBN()) && getTitolo().equals(book.getTitolo()) && getAutore().equals(book.getAutore()) && getGenere().equals(book.getGenere()) && getStato() == book.getStato() && getEditore().equals(book.getEditore());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getISBN(), getTitolo());
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", ISBN='" + ISBN + '\'' +
                ", titolo='" + titolo + '\'' +
                ", autore='" + autore + '\'' +
                ", anno=" + anno +
                ", genere='" + genere + '\'' +
                ", n_pagine=" + n_pagine +
                ", stato=" + stato +
                ", editore='" + editore + '\'' +
                '}';
    }
}
