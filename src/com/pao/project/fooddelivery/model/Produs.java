package com.pao.project.fooddelivery.model;

import java.util.Objects;

public class Produs {
    private int id;
    private String nume;
    private String categorie;
    private double pret;

    public Produs(int id, String nume, String categorie, double pret) {
        this.id = id;
        this.nume = nume;
        this.categorie = categorie;
        this.pret = pret;
    }

    public int getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public String getCategorie() {
        return categorie;
    }

    public double getPret() {
        return pret;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Produs produs)) return false;
        return id == produs.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Produs{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", categorie='" + categorie + '\'' +
                ", pret=" + pret +
                '}';
    }
}
