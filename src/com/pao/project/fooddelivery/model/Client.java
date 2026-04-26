package com.pao.project.fooddelivery.model;

import java.util.Objects;

public class Client extends Persoana {
    private String email;
    private Adresa adresa;

    public Client(int id, String nume, String prenume, String email, String telefon, Adresa adresa) {
        super(id, nume, prenume, telefon);
        this.email = email;
        this.adresa = adresa;
    }

    public String getEmail() {
        return email;
    }

    public Adresa getAdresa() {
        return adresa;
    }

    @Override
    public String getRol() {
        return "Client";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client client)) return false;
        return id == client.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", email='" + email + '\'' +
                ", telefon='" + telefon + '\'' +
                ", adresa=" + adresa +
                '}';
    }
}
