package com.pao.project.fooddelivery.model;

public abstract class Persoana {
    protected int id;
    protected String nume;
    protected String prenume;
    protected String telefon;

    public Persoana(int id, String nume, String prenume, String telefon) {
        this.id = id;
        this.nume = nume;
        this.prenume = prenume;
        this.telefon = telefon;
    }

    public int getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public String getTelefon() {
        return telefon;
    }

    public abstract String getRol();
}
